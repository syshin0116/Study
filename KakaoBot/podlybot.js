
let conversationHistory = {};

// 현재 날짜와 시간
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

    let reply = "";
    // 현재 시간 포함
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
        reply = summarizeUrl(msg);
        reply = "[링크 요약]\n" + reply;
    } else if (msg == "&help") {
        reply = "[포들리봇 사용법]\n" +
            "1. &+텍스트: Openai의 gpt-4o 모델이 응답\n" +
            "2. &&+텍스트: 서버로 요청(Upstage, Openai중 설정된 모델이 응답)\n" +
            "3. 링크: 링크 요약\n" +
            "4. &recruit: 채용 정보";
    } else if (msg == "$recruit" || msg == "평일 매일 올라오는 IT 정보들 확인 🤓") {
        callRecruitApis(room, sender, replier);
        return;
    } else if (msg.startsWith("&&")) {
        let cmd = msg.substr(2);
        reply = getResponseFromApi("https://podly.fun/api/podlybot/chat", room, sender, cmd);
    } else if (msg.startsWith("&")) {
        let cmd = msg.substr(1);
        let messages = [
            {
                "role": "system",
                "content":
                    "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers called 십대영님 and 해달님." +
                    "You are based on OpenAI's gpt-4o model. " +
                    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
                    "If the response is lengthy, use bullet points for better readability. " +
                    "Always maintain a polite tone."
            }
        ].concat(conversationHistory[room]);

        reply = getResponse("openai", messages);
    } else if ((sender === "이현지") &&
        (/^[ㅋㅎ]+$/.test(msg) || (msg.match(/[ㅋㅎ]/g) || []).length >= 5)) {
        reply = "현지야, 웃어?";
    }

    reply = reply.replace(/\*\*/g, "");

    conversationHistory[room].push({
        role: "assistant",
        content: reply
    });

    replier.reply(reply);
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
        let maxLength = 5000;

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
                    "Always maintain a polite tone.\n\n" +
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

        return getResponse("openai", messages);
    } catch (e) {
        return "웹페이지를 불러오거나 요약하는 중 오류가 발생했습니다: " + e.message;
    }
}

function getResponseFromApi(url, room, sender, msg) {
    let result;
    let data = {
        "room": room,
        "sender": sender,
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

function callRecruitApis(room, sender, replier) {
    // API endpoints
    const apiBaseUrl = "https://podly.fun/api/recruit";
    const apiEndpoints = [
        { name: "OKKY IT Events", url: apiBaseUrl + "/crawl_okky_info" },
        { name: "JobKorea Dev/Data Jobs", url: apiBaseUrl + "/crawl_dev_data" },
        { name: "JobKorea IT Jobs", url: apiBaseUrl + "/crawl_it_jobs" },
        { name: "ITWorld News", url: apiBaseUrl + "/crawl_itworld_news" }
    ];

    // Call each API and handle the results
    apiEndpoints.forEach(endpoint => {
        try {

            let response = org.jsoup.Jsoup.connect(endpoint.url)
                .header("Content-Type", "application/json")
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .timeout(200000)
                .get();

            let responseText = response.text();
            let jsonResponse;


            try {
                jsonResponse = JSON.parse(responseText);
            } catch (e) {
                replier.reply("[" + endpoint.name + "] Invalid JSON response: " + responseText);
                return;
            }

            // Check if 'response' field exists
            if (jsonResponse.response) {
                replier.reply(jsonResponse.response);
            } else {
                replier.reply("[" + endpoint.name + "] 'response' field missing in the response.");
            }
        } catch (e) {

            replier.reply("[" + endpoint.name + "] Error occurred: " + e.message);
        }
    });
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
        key = openaiKey;
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