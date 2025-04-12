let conversationHistory = {};
const wecoRooms = new Set(["위캔코딩 스터디방🤗", "SQLD & ADsP 스터디방", "제1회 정보처리기사 스터디"]);

// 기본 프롬프트 정의
const basePromptContent =
    "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers called 십대영님 and 해달님. " +
    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
    "If the response is lengthy, use bullet points for better readability. " +
    "Always maintain a polite tone and do not use markdown. use '-' for bullet points.\n" +
    "IMPORTANT: Each user message will be prefixed with [username at timestamp]. " +
    "You should understand that the username between [ and at ] is the name of the person you're talking to. " +
    "However, never format your own responses with timestamps or usernames. " +
    "Just provide direct answers without any metadata formatting.\n" +
    "For example, if you see a message '[승엽 at 2024-03-21 15:30] 내가 누구야', " +
    "you should understand that you are talking to 승엽, but your response should be a normal message without the [username at timestamp] format.\n";

// 방별 프롬프트 정의
const roomSpecificPrompts = {
    "제1회 정보처리기사 스터디":
        "You are assisting a group of developers preparing and discussing a certification exam called 정보처리기사.\n" +
        "\n" +
        "Here are some information on 2025년도 정보처리기사.\n" +
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
        "  - 정기 기사 2회: 2025.07.19 ~ 2025.08.06\n" +
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

        // 이번 사용자 메시지를 히스토리에 추가 - 사용자 정보를 메시지 내용에 직접 포함
        conversationHistory[room].push({
            role: "user",
            content: "[" + (sender || "Unknown") + " at " + currentTime + "] " + (msg || "")
        });


        // 히스토리가 너무 길어지면 맨 앞(오래된) 메시지 제거 k=10
        while (conversationHistory[room].length > 10) {
            conversationHistory[room].shift();
        }

        // 메시지 조건에 따른 처리
        if (isValidUrl(msg)) {
            const urlSummaryResult = summarizeUrl(msg);
            if (urlSummaryResult && urlSummaryResult.summary) {
                // 크롤링 결과에서 title, summary 추출
                let { title, summary } = urlSummaryResult;

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
                "2. &gemini+텍스트: Google Gemini 모델이 응답\n" +
                "3. &&+텍스트: 웹 검색 결과 제공\n" +
                "4. 링크: 링크 요약\n";
            if (wecoRooms.has(room)) {
                reply += "\n\n[위캔코딩 스터디방 url 정리 페이지]\n" +
                    "📌 https://tinyurl.com/c4ywwb2v";
            }
        } else if (msg.startsWith("&&")) {
            let cmd = msg.substr(2);
            reply = getResponseFromApi(null, room, sender, cmd);
        } else if (msg.startsWith("&openai")) {
            let cmd = msg.substr(7).trim();
            if (cmd) {
                let promptContent = basePromptContent +
                    "You are based on OpenAI's gpt-4o model. " +
                    "Current date and time:" + currentTime + "\n";

                let combinedPrompt = roomSpecificPrompts[room]
                    ? promptContent + " " + roomSpecificPrompts[room]
                    : promptContent;

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
                let promptContent = basePromptContent +
                    "You are based on Google's Gemini model. " +
                    "Current date and time:" + currentTime + "\n";

                let combinedPrompt = roomSpecificPrompts[room]
                    ? promptContent + " " + roomSpecificPrompts[room]
                    : promptContent;

                let messages = [
                    { role: "system", content: combinedPrompt }
                ].concat(conversationHistory[room]);

                reply = getGeminiResponse(messages);
            } else {
                reply = "Gemini에게 물어볼 내용을 입력해주세요. 예: &gemini 안녕하세요";
            }
        } else if (msg.startsWith("&")) {
            let cmd = msg.substr(1);

            // 기본 프롬프트 구성 (OpenAI 사용)
            let promptContent = basePromptContent +
                "You are based on OpenAI's gpt-4o model. " +
                "Current date and time:" + currentTime + "\n";

            let combinedPrompt = roomSpecificPrompts[room]
                ? promptContent + " " + roomSpecificPrompts[room]
                : promptContent;

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

        // assistant 응답을 히스토리에 추가
        if (reply !== null) {
            // ** 제거
            reply = reply.replace(/\*\*/g, "");

            // 앞뒤 공백 제거
            reply = reply.trim();

            conversationHistory[room].push({
                role: "assistant",
                content: "[포들리봇 at " + getCurrentDateTime() + "] " + reply
            });

            // 응답 전송
            replier.reply(reply);
        }

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

function searchAPI(searchQuery) {
    try {
        // 웹 검색 구현 (SearchAPI 사용)
        const searchUrl = "https://www.searchapi.io/api/v1/search";
        const searchApiUrlWithParams = searchUrl + "?api_key=" + SEARCH_API_KEY + "&engine=google&q=" + searchQuery;

        // API 요청
        const response = org.jsoup.Jsoup.connect(searchApiUrlWithParams).ignoreContentType(true).get();
        return response;
    } catch (e) {
        return "웹 검색 중 오류가 발생했습니다: " + e.message;
    }
}

function getResponse(type, messages) {
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
    } else if (type === "upstage") {
        url = "https://api.upstage.ai/v1/chat/completions";
        key = upstageKey;
        data.model = "upstage-mixedgen-v2";
    } else if (type === "gemini") {
        return getGeminiResponse(messages);
    } else {
        // 기본값은 OpenAI
        url = "https://api.openai.com/v1/chat/completions";
        key = OPENAI_API_KEY;
        data.model = "gpt-4o";
    }

    try {
        let apiResponse = org.jsoup.Jsoup.connect(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + key)
            .requestBody(JSON.stringify(data))
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(200000)
            .post();

        responseText = apiResponse.text();
        let jsonResponse = JSON.parse(responseText);

        // API 응답이 오류를 포함하는지 확인
        if (jsonResponse.error || !jsonResponse.choices || jsonResponse.choices.length === 0) {
            // Gemini로 폴백
            return getGeminiResponse(messages);
        }

        return jsonResponse.choices[0].message.content;
    } catch (e) {
        // 예외 발생 시 Gemini로 폴백
        try {
            return getGeminiResponse(messages);
        } catch (geminiError) {
            return "OpenAI와 Gemini 모두 오류가 발생했습니다: " + e.message;
        }
    }
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

// 대화 히스토리를 지원하는 Gemini API 함수 추가
function getGeminiResponse(messages) {
    try {
        // API 엔드포인트 - 대화 기능이 더 좋은 flash 모델 사용
        const url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

        // 대화 기록을 Gemini 형식으로 변환
        const contents = [];

        // 시스템 메시지 처리 (첫 번째 메시지가 시스템 메시지인 경우)
        let systemPrompt = null;
        let startIdx = 0;

        if (messages.length > 0 && messages[0].role === "system") {
            systemPrompt = messages[0].content;
            startIdx = 1;
        }

        // 사용자와 어시스턴트 메시지 변환
        for (let i = startIdx; i < messages.length; i++) {
            const message = messages[i];

            // 메시지 내용 - 이제 content에 직접 메시지가 저장됨
            let messageText = message.content;

            // Gemini API는 system 역할을 지원하지 않으므로 user/model로 매핑
            let geminiRole = message.role === "assistant" ? "model" : "user";

            contents.push({
                role: geminiRole,
                parts: [{ text: messageText }]
            });
        }

        // 시스템 프롬프트가 있으면 첫 번째 사용자 메시지에 추가
        if (systemPrompt && contents.length > 0 && contents[0].role === "user") {
            contents[0].parts[0].text = systemPrompt + "\n\n" + contents[0].parts[0].text;
        }

        // 요청 데이터
        const data = {
            contents: contents,
            generationConfig: {
                temperature: 0.7,
                maxOutputTokens: 1024,
                topP: 0.95,
                topK: 40
            }
        };

        // API 요청
        const geminiApiUrl = url.concat("?key=").concat(GEMINI_API_KEY);
        const geminiHistoryResponse = org.jsoup.Jsoup.connect(geminiApiUrl)
            .header("Content-Type", "application/json")
            .requestBody(JSON.stringify(data))
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(200000)
            .post();

        // 응답 파싱
        const responseText = geminiHistoryResponse.text();
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

function getResponseFromApi(url, room, sender, msg) {
    try {
        const currentTime = getCurrentDateTime();
        // 1. 먼저 GPT-4o-mini를 사용하여 최적화된 검색 쿼리 생성
        const queryGenerationMessages = [
            {
                role: "system",
                content: "You are a search query optimizer. Your task is to convert user questions into effective search queries. " +
                    "Keep the query concise, focused on the key information needs, and optimized for search engines. " +
                    "Today's date: " + currentTime + "\n\n" +
                    "Return ONLY the optimized search query without any explanation or additional text."
            },
            {
                role: "user",
                content: msg
            }
        ];

        // 쿼리 생성을 위한 API 호출 데이터
        const queryGenData = {
            "messages": queryGenerationMessages,
            "temperature": 0.3,
            "max_tokens": 100,
            "top_p": 1,
            "frequency_penalty": 0.0,
            "presence_penalty": 0.0,
            "model": "gpt-4o-mini"
        };

        // 쿼리 생성 API 호출
        let optimizedQuery = msg; // 기본값으로 원래 메시지 사용
        try {
            const queryGenResponse = org.jsoup.Jsoup.connect("https://api.openai.com/v1/chat/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .requestBody(JSON.stringify(queryGenData))
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .timeout(10000)
                .post();

            const queryGenResponseText = queryGenResponse.text();
            const queryGenJsonResponse = JSON.parse(queryGenResponseText);

            if (!queryGenJsonResponse.error && queryGenJsonResponse.choices && queryGenJsonResponse.choices.length > 0) {
                optimizedQuery = queryGenJsonResponse.choices[0].message.content.trim();
            }
        } catch (queryGenError) {
            // 쿼리 생성 실패 시 원래 메시지 사용
            optimizedQuery = msg;
        }

        // 2. 최적화된 쿼리로 검색 실행
        const searchResults = searchAPI(optimizedQuery);
        const searchData = searchResults.text();

        // 3. OpenAI에 검색 결과와 함께 질문을 전송
        const messages = [
            {
                role: "system",
                content: "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers called 십대영님 and 해달님. " +
                    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                    "If the response is lengthy, use bullet points for better readability. " +
                    "Always maintain a polite tone and do not use markdown. This includes for url too. Use '-' for bullet points. \n" +
                    "IMPORTANT: Never format your responses with 'username:', 'message:', or 'time:' prefixes. " +
                    "Just provide the direct answer without mimicking the input format.\n" +
                    "Today's date: " + currentTime + "\n\n" +
                    "Citation guidelines:\n" +
                    "1. When citing information, include a number in square brackets at the end of the relevant sentence or paragraph. Example: '이천수는 1981년 7월 9일에 태어났습니다. [1]'\n" +
                    "2. For direct quotes, use quotation marks and include a number in brackets. Example: '이천수는 한국 축구의 전설적인 인물이다. [2]'\n" +
                    "3. If multiple sources provide the same information, use a single number in brackets. Example: '[1][2]'\n" +
                    "4. At the end of your response, include a '출처:' section listing all the sources you used with links to the source, numbered accordingly."
            },
            {
                role: "user",
                content: "Original question: " + msg + "\nOptimized search query: " + optimizedQuery
            },
            {
                role: "function",
                content: searchData
            }
        ];

        // 5. getResponse 함수를 통해 OpenAI API 호출
        return getResponse("openai", messages) + "\n검색 쿼리: " + optimizedQuery;

    } catch (e) {
        return "웹 검색 중 오류가 발생했습니다: " + e.message;
    }
}
