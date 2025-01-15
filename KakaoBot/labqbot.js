let conversationHistory = {};

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
        let reply = null;
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

        // 히스토리가 너무 길어지면 맨 앞(오래된) 메시지 제거 k=20
        while (conversationHistory[room].length > 20) {
            conversationHistory[room].shift();
        }
        if (isValidUrl(msg)) {
            const result = summarizeUrl(msg);
            if (result && result.summary) {
                // 크롤링 결과에서 title, summary 추출
                let { title, summary } = result;

                // 요약문에서 ** 제거
                summary = summary.replace(/\*\*/g, "");

                // Notion에 url, summary, title 전달
                error = addItemToNotion(msg, summary, room, sender, title);
                reply = "[링크 요약]\n제목: " + title + "\n" + summary;
            } else {
                return;
            }
        } else if (msg == "/help") {
            reply = "[랩큐봇 사용법]\n" +
                "1. $: OpenAI의 GPT-4o가 응답.\n" +
                "2. $$: 정보 보호 챗봇이 응답.\n" +
                "3. 링크: 링크 요약.";
        } else if (msg.startsWith("$$$")) {
            let cmd = msg.substr(3);
            let url = "http://61.109.236.184:8080/api/chat";
            // reply = getResponseFromApi(cmd, url);
            reply = "API가 변경되었습니다. 사용 불가능합니다.";
        } else if (msg.startsWith("$$")) {
            let cmd = msg.substr(2);
            let url = "http://175.45.203.51:8081/api/kakao";
            reply = getResponseFromApi(cmd, url);
        } else if (msg.startsWith("$")) {
            let messages = [
                {
                    role: "system",
                    content:
                        "You are 랩큐봇, a helpful KakaoTalk assistant. " +
                        "Provide friendly and useful information to users. " +
                        "You are based on OpenAI's gpt-4o model. " +
                        "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                        "If the response is lengthy, use bullet points for better readability. " +
                        "Always maintain a polite tone. Do not use markdown. use '-' for bullet points.\n" +
                        "Current date and time: " + currentTime
                }
            ].concat(conversationHistory[room]);

            reply = getResponse("openai", messages);
        }
        // reply가 null이라면 응답하지 않고 함수 종료
        if (reply === null) {
            return;
        }
        // 모든 응답에서 ** 제거
        reply = reply.replace(/\*\*/g, "");

        replier.reply(reply);
    }
    catch (e) {
        replier.reply("오류가 발생했습니다: " + e.message);
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
            body = body.substring(0, body.lastIndexOf(" ", maxLength)) + "...";
        }
        let currentTime = getCurrentDateTime();
        let messages = [
            {
                role: "system",
                content:
                    "You are 랩큐봇, a helpful KakaoTalk assistant using OpenAI's gpt-4o model. " +
                    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                    "If the response is lengthy, use bullet points for better readability. " +
                    "Always maintain a polite tone. Do not use markdown. use '-' for bullet points.\n" +
                    "Current date and time: " + currentTime + "\n"
            },
            {
                role: "user",
                content:
                    "Summarize the following webpage content in Korean, focusing on the user's intended main points. " +
                    "Avoid including irrelevant details such as platform policies, disclaimers, or general operational information (e.g., YouTube’s product sales policies or platform features). " +
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


function getResponseFromApi(msg, url) {
    let result;
    let data = {
        "message": msg
    };

    try {
        let response = org.jsoup.Jsoup.connect(url)
            .header("Content-Type", "application/json")
            .requestBody(JSON.stringify(data))
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(200000)
            .post();

        let responseText = response.text();
        let jsonResponse = JSON.parse(responseText);

        if (jsonResponse.response) {
            result = jsonResponse.response;
        } else {
            result = "API 응답에 'response' 필드가 없습니다.";
        }
    } catch (e) {
        result = "새로운 API에 요청 중 오류가 발생했습니다: " + e.message;
    }

    return result;
}

function getResponse(type, messages) {
    let result;

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
    } else {
        url = "https://api.upstage.ai/v1/solar/chat/completions";
        key = upstageKey;
        data.model = "solar-pro";
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
        let responseText = response.text();
        let result1 = JSON.parse(responseText);
        result = result1.choices[0].message.content;

    } catch (e) {
        result = "오류가 발생했습니다: " + e.message + "\n응답: " + responseText;
    }
    return result;
}



function addItemToNotion(url, summary, room, user, title) {
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