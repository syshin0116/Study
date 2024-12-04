const scriptName = "포들리봇";

function response(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    let reply = "";

    if (isValidUrl(msg)) {
        reply = summarizeUrl(msg);
        reply = "[링크 요약]\n" + reply;
    } else if (msg == "/help") {
        reply = "[포들리봇 사용법]\n" +
            "1. $: OpenAI의 GPT-4o가 응답.\n" +
            // "2. $$: 정보 보호 챗봇이 응답.\n" +
            // "3. $$$: 청설모(주택 대출 상품 추천) 챗봇이 응답.\n" +
            "2. 링크: 링크 요약.";
        // } else if (msg.startsWith("$$$")) {
        //     let cmd = msg.substr(3);
        //     let url = "https://podly.fun/api/chat";
        //     reply = getResponseFromApi(cmd, url);
        // } else if (msg.startsWith("$$")) {
        //     let cmd = msg.substr(2);
        //     let url = "http://34.47.94.2:8000/api/chat";
        reply = getResponseFromApi(cmd, url);
    } else if (msg.startsWith("$")) {
        let cmd = msg.substr(1);
        reply = getResponse(cmd, "openai");
    }

    // 특정 조건에 따른 응답 처리
    if ((sender === "승엽" || sender === "이현지") &&
        (/^[ㅋ]+$/.test(msg) || (msg.match(/ㅋ/g) || []).length >= 5)) {
        reply = "웃어?";
    } else {
        // 모든 응답에서 ** 제거
        reply = reply.replace(/\*\*/g, "");

        reply = "isGroupChat: " + isGroupChat + "\n" +
            "room: " + room + "\n" +
            "sender: " + sender + "\n" +
            "msg: " + msg + "\n" +
            "imageDB: " + imageDB + "\n" +
            "packageName: " + packageName;
    }

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
        let maxLength = 1000;

        if (body.length > maxLength) {
            body = body.substring(0, body.lastIndexOf(" ", maxLength)) + "..."; // Ensures truncation at word boundary
        }

        let prompt = "Summarize the following webpage content in Korean, focusing on the user's intended main points. " +
            "Avoid including irrelevant details such as platform policies, disclaimers, or general operational information " +
            "(e.g., YouTube’s product sales policies or platform features). For YouTube links, focus on the main topic or discussion points of the video. " +
            "For articles or blogs, summarize the key insights or instructions. Use clear and concise bullet points with '-' to highlight the most important information. " +
            "Provide a concise and informative summary.\n\n" +
            "Title: " + title + "\n\n" +
            "Content: " + body + "\n\n" +
            "Instructions:\n" +
            "1. Summarize the key points in Korean.\n" +
            "2. Use '-' for bullet points (not markdown).\n" +
            "3. Focus only on the relevant content related to the video, blog, or article.\n" +
            "4. Ignore unrelated sections like platform policies or disclaimers.\n" +
            "5. Keep the summary concise and informative, using simple and clear language." +
            "6. Provide a brief intro about the what the page is about at the beginning";

        return getResponse(prompt, "openai"); // Ensure getResponse is properly implemented
    } catch (e) {
        return "웹페이지를 불러오거나 요약하는 중 오류가 발생했습니다: " + e.message;
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

function getResponse(msg, type) {
    let result;
    let data = {
        "messages": [
            { "role": "system", "content": "You are 포들리봇, a helpful KakaoTalk assistant. Provide friendly and useful information to users. Always respond in Korean." },
            { "role": "user", "content": msg }
        ],
        "temperature": 0,
        "max_tokens": 1024,
        "top_p": 1,
        "frequency_penalty": 0.0,
        "presence_penalty": 0.0
    }

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
            .post()
        let responseText = response.text();
        result1 = JSON.parse(responseText);
        result = result1.choices[0].message.content;

    } catch (e) {
        result = "오류가 발생했습니다: " + e.message + "\n응답: " + responseText;
    }
    return result;
}