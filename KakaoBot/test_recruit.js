
let conversationHistory = {};
const wecoRooms = new Set(["위캔코딩 스터디방🤗", "SQLD & ADsP 스터디방", "제1회 정보처리기사 스터디"]);

function getCurrentDateTime() {
    let now = new Date();
    let year = now.getFullYear();
    let month = String(now.getMonth() + 1).padStart(2, '0');
    let date = String(now.getDate()).padStart(2, '0');
    let hours = String(now.getHours()).padStart(2, '0');
    let minutes = String(now.getMinutes()).padStart(2, '0');
    let seconds = String(now.getSeconds()).padStart(2, '0');
    return year + "-" + month + "-" + date + " " + hours + ":" + minutes;
}

function response(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    try {
        let reply = null; // 기본적으로 reply는 null로 설정
        const currentTime = getCurrentDateTime();

        // room별 히스토리 배열이 없으면 생성
        if (!conversationHistory[room]) {
            conversationHistory[room] = [];
        }

        // 이번 사용자 메시지를 히스토리에 추가
        conversationHistory[room].push({
            role: "user",
            content: "username: " + (sender || "Unknown") + "\nmessage: " + (msg || "") + "\ntime: " + currentTime
        });

        // 히스토리가 너무 길어지면 맨 앞(오래된) 메시지 제거 k=10
        while (conversationHistory[room].length > 10) {
            conversationHistory[room].shift();
        }

        // 메시지 조건에 따른 처리
        if (isValidUrl(msg)) {
            const result = summarizeUrl(msg);
            if (result && result.summary) {
                // 크롤링 결과에서 title, summary 추출
                let { title, summary } = result;

                // 요약문에서 ** 제거
                summary = summary.replace(/\*\*/g, "");
                if (wecoRooms.has(room)) {
                    addItemToNotion(WECO_NOTION_DATABASE_ID, msg, summary, room, sender, title);
                }
                else if (room === "승엽") {
                    addItemToNotion(PERSONAL_NOTION_DATABASE_ID, msg, summary, room, sender, title);
                }
                reply = "[링크 요약]\n제목:" + title + "\n" + summary;
            }
        } else if (msg === "/help") {
            reply = "[포들리봇 사용법]\n" +
                "1. &+텍스트: AI 모델이 응답 (기본: OpenAI gpt-4o)\n" +
                "2. &openai+텍스트: OpenAI gpt-4o 모델이 응답\n" +
                "3. &gemini+텍스트: Google Gemini 모델이 응답\n" +
                "4. &&+텍스트: 웹 검색 결과 제공\n" +
                "5. 링크: 링크 요약\n" +
                "6. &recruit: 채용 정보";
            if (wecoRooms.has(room)) {
                reply += "\n\n[위캔코딩 스터디방 url 정리 페이지]\n" +
                    "📌 https://tinyurl.com/c4ywwb2v";
            }
        } else if (msg === "&recruit" || msg === "평일 매일 올라오는 IT 정보들 확인 🤓") {
            callRecruitApis(room, sender, replier);
            return; // 여기서 바로 종료
        } else if (msg.startsWith("&&")) {
            let cmd = msg.substr(2);
            reply = getResponseFromApi(null, room, sender, cmd);
        } else if (msg.startsWith("&openai")) {
            let cmd = msg.substr(7).trim();
            if (cmd) {
                let basePromptContent =
                    "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers called 십대영님 and 해달님. " +
                    "You are based on OpenAI's gpt-4o model. " +
                    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                    "If the response is lengthy, use bullet points for better readability. " +
                    "Always maintain a polite tone and do not use markdown. use '-' for bullet points.\n" +
                    "Current date and time:" + currentTime + "\n";

                let roomSpecificPrompts = {
                    "제1회 정보처리기사 스터디":
                        "You are assisting a group of developers preparing and discussing a certification exam called 정보처리기사.\n" +
                        "\n" +
                        "Here are some information on 2025년도 정보처리기사.\n" +
                        "\n" +
                        "<시험일정>\n" +
                        "필기원서접수 (인터넷, 휴일제외)\n" +
                        "  - 정기 기사 1회: 2025.01.13 ~ 2025.01.16 [빈자리접수: 2025.02.01 ~ 2025.02.02]\n" +
                        "  - 정기 기사 2회: 2025.04.14 ~ 2025.04.17\n" +
                        "  - 정기 기사 3회: 2025.07.21 ~ 2025.07.24\n" +
                        "\n" +
                        "필기시험\n" +
                        "  - 정기 기사 1회: 2025.02.07 ~ 2025.03.04\n" +
                        "  - 정기 기사 2회: 2025.05.10 ~ 2025.05.30\n" +
                        "  - 정기 기사 3회: 2025.08.09 ~ 2025.09.01\n" +
                        "\n" +
                        "필기합격(예정자) 발표\n" +
                        "  - 정기 기사 1회: 2025.03.12\n" +
                        "  - 정기 기사 2회: 2025.06.11\n" +
                        "  - 정기 기사 3회: 2025.09.10\n" +
                        "\n" +
                        "실기원서접수 (휴일제외)\n" +
                        "  - 정기 기사 1회: 2025.03.24 ~ 2025.03.27\n" +
                        "  - 정기 기사 2회: 2025.06.23 ~ 2025.06.26\n" +
                        "  - 정기 기사 3회: 2025.09.22 ~ 2025.09.25\n" +
                        "\n" +
                        "실기시험\n" +
                        "  - 정기 기사 1회: 2025.04.19 ~ 2025.05.09\n" +
                        "  - 정기 기사 2회: 2025.07.19 ~ 2025.08.06\n" + ~
                        "  - 정기 기사 3회: 2025.11.01 ~ 2025.11.21\n" +
                        "\n" +
                        "최종합격자 발표일\n" +
                        "  - 정기 기사 1회: 2025.06.13\n" +
                        "  - 정기 기사 2회: 2025.09.12\n" +
                        "  - 정기 기사 3회: 2025.12.24\n" +
                        "</시험일정>\n" +
                        "\n" +
                        "원서접수시간: 원서접수 첫날 10:00부터 마지막 날 18:00까지임\n" +
                        "필기시험 합격예정자 및 최종합격자 발표시간: 해당 발표일 09:00임\n",
                    "SQLD & ADsP 스터디방":
                        "You are assisting a group of developers preparing and discussing two certification exams called ADsP and SQLD. " +
                        "Here is some information on the exams: " +
                        "<ADsP> " +
                        "데이터분석 준전문가(ADsP: Advanced Data Analytics Semi-Professional)는 데이터 이해에 대한 기본 지식을 바탕으로 " +
                        "데이터 분석 기획 및 데이터 분석 등의 직무를 수행하는 실무자를 의미합니다. " +
                        "<2025 시험일정> " +
                        "제44회: 접수 1.20~1.24, 시험일 2.22(토). " +
                        "제45회: 접수 4.14~4.18, 시험일 5.17(토). " +
                        "제46회: 접수 7.7~7.11, 시험일 8.9(토). " +
                        "제47회: 접수 9.22~9.26, 시험일 11.2(일). " +
                        "</2025 시험일정> " +
                        "<추천 책> " +
                        "1. 미어캣: 이지패스 ADsP 데이터분석 준전문가, " +
                        "2. 민트책: ADsP 데이터 분석 준전문가 " +
                        "</추천 책> " +
                        "<공부법> " +
                        "미어캣 교재는 어플로 문제 풀이를 제공하므로 매우 편리합니다. " +
                        "시간이 부족하다면 출퇴근 시간을 활용해 앱으로 문제를 풀어보세요. " +
                        "</공부법> " +
                        "</ADsP> " +

                        "<SQLD> " +
                        "SQL 개발자(SQLD, SQL Developer)는 데이터베이스와 데이터 모델링에 대한 지식을 바탕으로 " +
                        "응용 소프트웨어를 개발하며 데이터를 조작하고 추출하는 데 있어서 정확하고 최적의 성능을 발휘하는 SQL을 작성할 수 있는 개발자를 의미합니다. " +
                        "<2025 시험일정> " +
                        "제56회: 접수 2.3~2.7, 시험일 3.8(토). " +
                        "제57회: 접수 4.28~5.2, 시험일 5.31(토). " +
                        "제58회: 접수 7.21~7.25, 시험일 8.23(토). " +
                        "제59회: 접수 10.13~10.17, 시험일 11.16(일). " +
                        "</2025 시험일정> " +
                        "<추천 책> " +
                        "1. 노랭이: SQL 자격검정 실전문제 " +
                        "   - 기출문제 중심으로 구성된 실전 문제집으로, 실전 감각을 익히는 데 적합합니다. " +
                        "   - 문제 풀이와 해설이 상세히 제공되어 혼자서도 학습이 가능합니다. " +
                        "2. 민트책: 2024 SD에듀 유선배 SQL개발자(SQLD) 과외노트 " +
                        "   - SQL 이론과 실전 문제를 쉽게 풀어 설명한 교재로, 초보자도 이해하기 쉬운 방식으로 작성되었습니다. " +
                        "   - 시험 준비를 체계적으로 할 수 있도록 기출문제 외에도 추가 학습자료가 포함되어 있습니다. " +
                        "</추천 책> " +
                        "<공부법> " +
                        "민트책으로 기초를 다시고, 노랭이책으로 실전 문제 풀이하는 것이 효과적입니다. " +
                        "</공부법> " +
                        "</SQLD>" +
                        "네이버카페 데이터포럼 https://cafe.naver.com/sqlpd 에 유용한 정보가 많이 있습니다. "
                };

                let combinedPrompt = roomSpecificPrompts[room]
                    ? basePromptContent + " " + roomSpecificPrompts[room]
                    : basePromptContent;

                let messages = [
                    { role: "system", content: combinedPrompt },
                    { role: "user", content: cmd }
                ];

                reply = getResponse("openai", messages);
            } else {
                reply = "OpenAI에게 물어볼 내용을 입력해주세요. 예: &openai 안녕하세요";
            }
        } else if (msg.startsWith("&gemini")) {
            let cmd = msg.substr(7).trim();
            if (cmd) {
                // Gemini 프롬프트 구성
                const prompt = "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers. " +
                    "You are based on Google's Gemini Pro model. " +
                    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                    "If the response is lengthy, use bullet points for better readability. " +
                    "Always maintain a polite tone and do not use markdown. use '-' for bullet points.\n" +
                    "Current date and time: " + currentTime + "\n\n" +
                    "User: " + cmd;

                let messages = [
                    { role: "system", content: prompt }
                ];

                reply = getResponse("gemini", messages);
            } else {
                reply = "Gemini에게 물어볼 내용을 입력해주세요. 예: &gemini 안녕하세요";
            }
        } else if (msg.startsWith("&")) {
            let cmd = msg.substr(1);

            // 기본 프롬프트 구성 (OpenAI 사용)
            let basePromptContent =
                "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers called 십대영님 and 해달님. " +
                "You are based on OpenAI's gpt-4o model. " +
                "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                "If the response is lengthy, use bullet points for better readability. " +
                "Always maintain a polite tone and do not use markdown. use '-' for bullet points.\n" +
                "Current date and time:" + currentTime + "\n";

            let roomSpecificPrompts = {
                "제1회 정보처리기사 스터디":
                    "You are assisting a group of developers preparing and discussing a certification exam called 정보처리기사.\n" +
                    "\n" +
                    "Here are some information on 2025년도 정보처리기사.\n" +
                    "\n" +
                    "<시험일정>\n" +
                    "필기원서접수 (인터넷, 휴일제외)\n" +
                    "  - 정기 기사 1회: 2025.01.13 ~ 2025.01.16 [빈자리접수: 2025.02.01 ~ 2025.02.02]\n" +
                    "  - 정기 기사 2회: 2025.04.14 ~ 2025.04.17\n" +
                    "  - 정기 기사 3회: 2025.07.21 ~ 2025.07.24\n" +
                    "\n" +
                    "필기시험\n" +
                    "  - 정기 기사 1회: 2025.02.07 ~ 2025.03.04\n" +
                    "  - 정기 기사 2회: 2025.05.10 ~ 2025.05.30\n" +
                    "  - 정기 기사 3회: 2025.08.09 ~ 2025.09.01\n" +
                    "\n" +
                    "필기합격(예정자) 발표\n" +
                    "  - 정기 기사 1회: 2025.03.12\n" +
                    "  - 정기 기사 2회: 2025.06.11\n" +
                    "  - 정기 기사 3회: 2025.09.10\n" +
                    "\n" +
                    "실기원서접수 (휴일제외)\n" +
                    "  - 정기 기사 1회: 2025.03.24 ~ 2025.03.27\n" +
                    "  - 정기 기사 2회: 2025.06.23 ~ 2025.06.26\n" +
                    "  - 정기 기사 3회: 2025.09.22 ~ 2025.09.25\n" +
                    "\n" +
                    "실기시험\n" +
                    "  - 정기 기사 1회: 2025.04.19 ~ 2025.05.09\n" +
                    "  - 정기 기사 2회: 2025.07.19 ~ 2025.08.06\n" + ~
                    "  - 정기 기사 3회: 2025.11.01 ~ 2025.11.21\n" +
                    "\n" +
                    "최종합격자 발표일\n" +
                    "  - 정기 기사 1회: 2025.06.13\n" +
                    "  - 정기 기사 2회: 2025.09.12\n" +
                    "  - 정기 기사 3회: 2025.12.24\n" +
                    "</시험일정>\n" +
                    "\n" +
                    "원서접수시간: 원서접수 첫날 10:00부터 마지막 날 18:00까지임\n" +
                    "필기시험 합격예정자 및 최종합격자 발표시간: 해당 발표일 09:00임\n",
                "SQLD & ADsP 스터디방":
                    "You are assisting a group of developers preparing and discussing two certification exams called ADsP and SQLD. " +
                    "Here is some information on the exams: " +
                    "<ADsP> " +
                    "데이터분석 준전문가(ADsP: Advanced Data Analytics Semi-Professional)는 데이터 이해에 대한 기본 지식을 바탕으로 " +
                    "데이터 분석 기획 및 데이터 분석 등의 직무를 수행하는 실무자를 의미합니다. " +
                    "<2025 시험일정> " +
                    "제44회: 접수 1.20~1.24, 시험일 2.22(토). " +
                    "제45회: 접수 4.14~4.18, 시험일 5.17(토). " +
                    "제46회: 접수 7.7~7.11, 시험일 8.9(토). " +
                    "제47회: 접수 9.22~9.26, 시험일 11.2(일). " +
                    "</2025 시험일정> " +
                    "<추천 책> " +
                    "1. 미어캣: 이지패스 ADsP 데이터분석 준전문가, " +
                    "2. 민트책: ADsP 데이터 분석 준전문가 " +
                    "</추천 책> " +
                    "<공부법> " +
                    "미어캣 교재는 어플로 문제 풀이를 제공하므로 매우 편리합니다. " +
                    "시간이 부족하다면 출퇴근 시간을 활용해 앱으로 문제를 풀어보세요. " +
                    "</공부법> " +
                    "</ADsP> " +

                    "<SQLD> " +
                    "SQL 개발자(SQLD, SQL Developer)는 데이터베이스와 데이터 모델링에 대한 지식을 바탕으로 " +
                    "응용 소프트웨어를 개발하며 데이터를 조작하고 추출하는 데 있어서 정확하고 최적의 성능을 발휘하는 SQL을 작성할 수 있는 개발자를 의미합니다. " +
                    "<2025 시험일정> " +
                    "제56회: 접수 2.3~2.7, 시험일 3.8(토). " +
                    "제57회: 접수 4.28~5.2, 시험일 5.31(토). " +
                    "제58회: 접수 7.21~7.25, 시험일 8.23(토). " +
                    "제59회: 접수 10.13~10.17, 시험일 11.16(일). " +
                    "</2025 시험일정> " +
                    "<추천 책> " +
                    "1. 노랭이: SQL 자격검정 실전문제 " +
                    "   - 기출문제 중심으로 구성된 실전 문제집으로, 실전 감각을 익히는 데 적합합니다. " +
                    "   - 문제 풀이와 해설이 상세히 제공되어 혼자서도 학습이 가능합니다. " +
                    "2. 민트책: 2024 SD에듀 유선배 SQL개발자(SQLD) 과외노트 " +
                    "   - SQL 이론과 실전 문제를 쉽게 풀어 설명한 교재로, 초보자도 이해하기 쉬운 방식으로 작성되었습니다. " +
                    "   - 시험 준비를 체계적으로 할 수 있도록 기출문제 외에도 추가 학습자료가 포함되어 있습니다. " +
                    "</추천 책> " +
                    "<공부법> " +
                    "민트책으로 기초를 다시고, 노랭이책으로 실전 문제 풀이하는 것이 효과적입니다. " +
                    "</공부법> " +
                    "</SQLD>" +
                    "네이버카페 데이터포럼 https://cafe.naver.com/sqlpd 에 유용한 정보가 많이 있습니다. "
            };

            let combinedPrompt = roomSpecificPrompts[room]
                ? basePromptContent + " " + roomSpecificPrompts[room]
                : basePromptContent;

            let messages = [
                { role: "system", content: combinedPrompt }
            ].concat(conversationHistory[room]);

            reply = getResponse("openai", messages);
        } else if (sender === "이현지" && (/^[ㅋㅎ]+$/.test(msg) || (msg.match(/[ㅋㅎ]/g) || []).length >= 5)) {
            reply = "현지야, 웃어?";
        }

        // reply가 null이라면 응답하지 않고 함수 종료
        if (reply === null) {
            return;
        }

        // '**' 제거
        reply = reply.replace(/\*\*/g, "");

        // assistant 응답을 히스토리에 추가
        conversationHistory[room].push({
            role: "assistant",
            content: reply
        });

        // 응답 전송
        replier.reply(reply);

    } catch (e) {
        replier.reply("response() 내에서 오류가 발생했습니다: " + e.message);
    }
}

function isValidUrl(string) {
    let urlPattern = new RegExp('^(https?:\\/\\/)?' +
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' +
        '((\\d{1,3}\\.){3}\\d{1,3}))' +
        '(\\:\\d+)?(\\/[-a-z\\d%.~+]*)*' +
        '(\\?[;&a-z\\d%_.~+=-]*)?' +
        '(\\#[-a-z\\d_]*)?$', 'i');
    return urlPattern.test(string);
}

function summarizeUrl(url) {
    try {
        let response = org.jsoup.Jsoup.connect(url).get();
        let title = response.title();
        let body = response.body().text();
        let maxLength = 10000;

        if (body.length > maxLength) {
            body = body.substring(0, body.lastIndexOf(" ", maxLength)) + "..."; // Ensures truncation at word boundary
        }
        let currentTime = getCurrentDateTime();
        let messages = [
            {
                role: "system",
                content:
                    "You are 포들리봇, a helpful KakaoTalk assistant created by 십대영님 using OpenAI's gpt-4o model. " +
                    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                    "If the response is lengthy, use bullet points for better readability. " +
                    "Always maintain a polite tone. Do not use markdown. use '-' for bullet points.\n\n\n" +
                    "Current date and time: " + currentTime + "\n"
            },
            {
                role: "user",
                content:
                    "Summarize the following webpage content in Korean, focusing on the user's intended main points. " +
                    "Avoid including irrelevant details such as platform policies, disclaimers, or general operational information (e.g., YouTube's product sales policies or platform features). " +
                    "For YouTube links, focus on the main topic or discussion points of the video. " +
                    "For articles or blogs, summarize the key insights or instructions. " +
                    "Use clear and concise bullet points with '-' to highlight the most important information. " +
                    "Provide a concise and informative summary.\n\n" +
                    "Title: " + title + "\n\n" +
                    "Content: " + body + "\n\n" +
                    "Instructions:\n" +
                    "1. Summarize the key points in Korean\n" +
                    "2. Use '-' for bullet points (not markdown)\n" +
                    "3. Focus only on the relevant content related to the video, blog, or article\n" +
                    "4. Ignore unrelated sections like platform policies or disclaimers\n" +
                    "5. Keep the summary concise and informative, using simple, polite and clear language\n" +
                    "6. Provide a brief intro about what the page is about at the beginning"
            }
        ];

        let summary = getResponse("openai", messages);

        return {
            title: title,
            summary: summary
        };
    } catch (e) {
        return null;
    }
}

function getResponseFromApi(url, room, sender, msg) {
    try {
        // 웹 검색 구현 (SearchAPI 사용)
        const searchQuery = encodeURIComponent(msg);
        const searchUrl = `https://www.searchapi.io/api/v1/search?api_key=${SEARCH_API_KEY}&engine=duckduckgo&q=${searchQuery}`;

        // API 요청
        const response = org.jsoup.Jsoup.connect(searchUrl)
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(30000)
            .get();

        // 응답 파싱
        const responseText = response.text();
        const jsonResponse = JSON.parse(responseText);

        // 검색 결과 추출
        const searchResults = [];

        if (jsonResponse.organic_results && jsonResponse.organic_results.length > 0) {
            // 최대 5개 결과만 추출
            const maxResults = Math.min(5, jsonResponse.organic_results.length);

            for (let i = 0; i < maxResults; i++) {
                const result = jsonResponse.organic_results[i];

                searchResults.push({
                    title: result.title || "제목 없음",
                    url: result.url || "",
                    description: result.snippet || "설명 없음"
                });
            }
        }

        // 결과 포맷팅
        let formattedResult = "🔍 \"" + msg + "\" 검색 결과:\n\n";

        if (searchResults.length === 0) {
            return "검색 결과가 없습니다.";
        }

        for (let i = 0; i < searchResults.length; i++) {
            const result = searchResults[i];
            formattedResult += (i + 1) + ". " + result.title + "\n";
            formattedResult += "URL: " + result.url + "\n";
            formattedResult += "설명: " + result.description + "\n\n";
        }

        return formattedResult;
    } catch (e) {
        return "웹 검색 중 오류가 발생했습니다: " + e.message;
    }
}

function callRecruitApis(room, sender, replier) {
    try {
        // 현재 날짜 가져오기
        const currentDate = getCurrentDateTime().split(" ")[0]; // YYYY-MM-DD 형식

        // 각 크롤링 함수 호출 및 결과 전송
        replier.reply(getOkkyItEvents(currentDate));
        replier.reply(getJobKoreaDevData(currentDate));
        replier.reply(getJobKoreaItJobs(currentDate));
        replier.reply(getItworldNews(currentDate));
    } catch (e) {
        replier.reply("채용 정보를 가져오는 중 오류가 발생했습니다: " + e.message);
    }
}

/**
 * OKKY IT 이벤트 정보를 크롤링
 * @param {string} currentDate - 현재 날짜 (YYYY-MM-DD)
 * @returns {string} 포맷팅된 이벤트 정보
 */
function getOkkyItEvents(currentDate) {
    try {
        const url = "https://okky.kr/events/it";
        const response = org.jsoup.Jsoup.connect(url).get();

        // __NEXT_DATA__ 스크립트 태그 찾기
        const scriptTag = response.select("script#__NEXT_DATA__").first();
        if (!scriptTag) {
            return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n데이터를 찾을 수 없습니다.";
        }

        // JSON 데이터 파싱
        const jsonData = JSON.parse(scriptTag.html());
        const content = jsonData.props.pageProps.result.content;

        // 데이터 추출 및 포맷팅
        let formattedData = "";
        for (let i = 0; i < content.length; i++) {
            const item = content[i];
            const title = item.title;
            const nickname = item.displayAuthor.nickname;
            const dateCreated = item.dateCreated.split("T")[0];

            formattedData += (i + 1) + ". " + title + " | " + nickname + " | 작성일: " + dateCreated + "\n\n";
        }

        return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

/**
 * 잡코리아 개발·데이터 직종별 일자리 수 크롤링
 * @param {string} currentDate - 현재 날짜 (YYYY-MM-DD)
 * @returns {string} 포맷팅된 직종별 일자리 정보
 */
function getJobKoreaDevData(currentDate) {
    try {
        const url = "https://www.jobkorea.co.kr/recruit/joblist?menucode=duty";
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
        };

        const response = org.jsoup.Jsoup.connect(url)
            .headers(headers)
            .get();

        // dd 태그 찾기
        const ddTags = response.select("dd.nano.has-scrollbar");
        if (ddTags.size() < 3) {
            return "● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n데이터를 찾을 수 없습니다.";
        }

        const selectorTags = ddTags.get(2);
        const liTags = selectorTags.select("li");

        let selectedLiTag = null;
        for (let i = 0; i < liTags.size(); i++) {
            const liTag = liTags.get(i);
            const dataValueJson = liTag.attr("data-value-json");

            if (dataValueJson) {
                const dataJson = JSON.parse(dataValueJson);
                if (dataJson.groupName === "개발·데이터") {
                    selectedLiTag = dataValueJson;
                    break;
                }
            }
        }

        if (!selectedLiTag) {
            return "● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n개발·데이터 직종 데이터를 찾을 수 없습니다.";
        }

        const selectedLiJson = JSON.parse(selectedLiTag);
        const result = selectedLiJson.subList;

        // 일자리 수 기준으로 내림차순 정렬
        result.sort((a, b) => b.giCnt - a.giCnt);

        // 데이터 포맷팅
        let formattedData = "";
        for (let i = 0; i < result.length; i++) {
            const item = result[i];
            formattedData += (i + 1) + ". " + item.subName + " (" + item.giCnt + " 개)\n";
        }

        return "● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

/**
 * 잡코리아 IT 직종 일자리 리스트 크롤링
 * @param {string} currentDate - 현재 날짜 (YYYY-MM-DD)
 * @returns {string} 포맷팅된 IT 직종 일자리 정보
 */
function getJobKoreaItJobs(currentDate) {
    try {
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
        };

        const baseUrl = "https://www.jobkorea.co.kr";
        const url = baseUrl + "/recruit/joblist?menucode=duty&dutyCtgr=10031#anchorGICnt_1";

        const results = [];

        // 첫 번째 페이지만 크롤링 (성능 고려)
        const response = org.jsoup.Jsoup.connect(url)
            .headers(headers)
            .get();

        const trElements = response.select("tr.devloopArea");

        for (let i = 0; i < trElements.size(); i++) {
            const element = trElements.get(i);
            const mainData = element.select("a.link.normalLog");
            const deadlineData = element.select("td.odd span.date.dotum");

            if (mainData.size() >= 2 && deadlineData.size() > 0) {
                const company = mainData.get(0).text().trim();
                const originalUrl = baseUrl + mainData.get(1).attr("href");
                const title = mainData.get(1).attr("title");
                const deadline = deadlineData.get(0).text().trim();

                results.push({
                    company: company,
                    url: originalUrl,
                    title: title,
                    deadline: deadline
                });
            }
        }

        // 데이터 포맷팅
        let formattedData = "";
        for (let i = 0; i < results.length; i++) {
            const item = results[i];
            formattedData += (i + 1) + ". [" + item.company + "] " + item.title + " (" + item.deadline + ")\n> " + item.url + "\n\n";
        }

        return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

/**
 * ITWorld 뉴스 크롤링
 * @param {string} currentDate - 현재 날짜 (YYYY-MM-DD)
 * @returns {string} 포맷팅된 ITWorld 뉴스 정보
 */
function getItworldNews(currentDate) {
    try {
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
        };

        const urls = [
            { keyword: "개발자", url: "https://www.itworld.co.kr/t/61023/%EA%B0%9C%EB%B0%9C%EC%9E%90" },
            { keyword: "미래기술", url: "https://www.itworld.co.kr/t/65212/%EB%AF%B8%EB%9E%98%EA%B8%B0%EC%88%A0" },
            { keyword: "AI/ML", url: "https://www.itworld.co.kr/t/69500/AI%E3%86%8DML" },
            { keyword: "글로벌트렌드", url: "https://www.itworld.co.kr/t/55049/%EA%B8%80%EB%A1%9C%EB%B2%8C+%ED%8A%B8%EB%A0%8C%EB%93%9C" }
        ];

        let finalResult = [];

        for (let i = 0; i < urls.length; i++) {
            const { keyword, url } = urls[i];

            const response = org.jsoup.Jsoup.connect(url)
                .headers(headers)
                .get();

            const cardLists = response.select("div.latest-content__card-secondary");

            // 최대 5개까지만 추출 (성능 고려)
            const items = [];
            const totalNum = Math.min(5, cardLists.size());

            for (let j = 0; j < totalNum; j++) {
                const card = cardLists.get(j);
                const titleTag = card.select("h3").first();
                const linkTag = card.select("a").first();

                if (titleTag && linkTag) {
                    const title = titleTag.text().trim();
                    const originalUrl = linkTag.attr("href");

                    items.push({
                        title: title,
                        url: originalUrl
                    });
                }
            }

            finalResult.push({
                keyword: keyword,
                items: items
            });
        }

        // 데이터 포맷팅
        let formattedData = "";

        for (let i = 0; i < finalResult.length; i++) {
            const section = finalResult[i];

            formattedData += "● " + currentDate + " 기준 IT 뉴스 (키워드: " + section.keyword + ")\n\n";

            for (let j = 0; j < section.items.length; j++) {
                const item = section.items[j];
                formattedData += (j + 1) + ". " + item.title + "\n> " + item.url + "\n";
            }

            formattedData += "\n\n\n";
        }

        return "● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

function getResponse(type, messages) {
    let result;
    let responseText = "";

    let data = {
        "messages": messages,
        "temperature": 0.7,
        "max_tokens": 1024,
        "top_p": 1,
        "frequency_penalty": 0.0,
        "presence_penalty": 0.0
    };

    let url, key;
    if (type === "openai") {
        url = "https://api.openai.com/v1/chat/completions";
        key = OPENAI_API_KEY;
        data.model = "gpt-4o";
    } else if (type === "gemini") {
        // Gemini API 호출
        const prompt = messages[0].content + "\n\n" +
            messages[messages.length - 1].content;
        return getGeminiResponse(prompt);
    } else {
        // 기본값은 OpenAI
        url = "https://api.openai.com/v1/chat/completions";
        key = OPENAI_API_KEY;
        data.model = "gpt-4o";
    }

    try {
        let response = org.jsoup.Jsoup.connect(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + key)
            .requestBody(JSON.stringify(data))
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(200000)
            .post();

        responseText = response.text();
        let result1 = JSON.parse(responseText);
        result = result1.choices[0].message.content;
    } catch (e) {
        result = "오류가 발생했습니다: " + e.message + "\n응답: " + responseText;
    }

    return result;
}

function addItemToNotion(NOTION_DATABASE_ID, url, summary, room, user, title) {
    try {
        const notionUrl = "https://api.notion.com/v1/pages";

        // 요청 데이터 (JSON 형식)
        const payload = {
            parent: { database_id: NOTION_DATABASE_ID },
            properties: {
                title: {
                    title: [
                        {
                            text: {
                                content: title
                            }
                        }
                    ]
                },
                url: {
                    url: url
                },
                user: {
                    rich_text: [
                        {
                            text: {
                                content: user
                            }
                        }
                    ]
                },
                room: {
                    select: {
                        name: room
                    }
                },
                "created date": {
                    date: {
                        start: new Date().toISOString()
                    }
                }
            },
            children: [
                {
                    object: "block",
                    heading_2: {
                        rich_text: [
                            {
                                text: {
                                    content: title + "요약"
                                }
                            }
                        ]
                    }
                },
                {
                    object: "block",
                    paragraph: {
                        rich_text: [
                            {
                                text: {
                                    content: url,
                                    link: {
                                        url: url
                                    }
                                }
                            }
                        ],
                        color: "default"
                    }
                },
                {
                    object: "block",
                    paragraph: {
                        rich_text: [
                            {
                                text: {
                                    content: summary
                                }
                            }
                        ],
                        color: "default"
                    }
                }
            ]
        };

        // Java 기반 HTTP 요청
        const urlObj = new java.net.URL(notionUrl);
        const connection = urlObj.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + NOTION_API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Notion-Version", "2022-06-28");
        connection.setDoOutput(true);

        // 요청 데이터 전송
        const writer = new java.io.OutputStreamWriter(connection.getOutputStream());
        writer.write(JSON.stringify(payload));
        writer.flush();
        writer.close();

        // 응답 받기
        const reader = new java.io.BufferedReader(
            new java.io.InputStreamReader(connection.getInputStream(), "UTF-8")
        );
        let response = "";
        let line;
        while ((line = reader.readLine()) !== null) {
            response += line;
        }
        reader.close();

        // 결과 반환
        return "Notion 등록 완료: " + response;
    } catch (e) {
        return "Notion API 호출 중 오류 발생: " + e.message;
    }
}

// Google Gemini API 호출 함수 추가
function getGeminiResponse(prompt) {
    try {
        // API 엔드포인트
        const url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-lite:generateContent";

        // 요청 데이터
        const data = {
            contents: [
                {
                    parts: [
                        {
                            text: prompt
                        }
                    ]
                }
            ],
            generationConfig: {
                temperature: 0.7,
                maxOutputTokens: 1024,
                topP: 0.95,
                topK: 40
            }
        };

        // API 요청
        const response = org.jsoup.Jsoup.connect(url + "?key=" + GEMINI_API_KEY)
            .header("Content-Type", "application/json")
            .requestBody(JSON.stringify(data))
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(200000)
            .post();

        // 응답 파싱
        const responseText = response.text();
        const jsonResponse = JSON.parse(responseText);

        // 응답 추출
        if (jsonResponse.candidates && jsonResponse.candidates.length > 0 &&
            jsonResponse.candidates[0].content &&
            jsonResponse.candidates[0].content.parts &&
            jsonResponse.candidates[0].content.parts.length > 0) {

            return jsonResponse.candidates[0].content.parts[0].text;
        } else {
            return "Gemini API 응답에서 텍스트를 찾을 수 없습니다.";
        }
    } catch (e) {
        return "Gemini API 호출 중 오류가 발생했습니다: " + e.message;
    }
}