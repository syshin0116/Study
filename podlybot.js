const scriptName = "포들리봇";

function response(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    let reply = "";

    if (isValidUrl(msg)) {
        reply = summarizeUrl(msg);
        reply = "[링크 요약]\n" + reply;
    } else if (msg == "/help") {
        reply = "[포들리봇 사용법]\n" +
            "1. $+텍스트: Upstage의 Solar-pro 모델이 응답\n" +
            "2. 링크: 링크 요약";
    } else if (msg.startsWith("$")) {
        let cmd = msg.substr(1);
        reply = getResponse(cmd, "upstage");
    }

    if ((sender === "이현지") &&
        (/^[ㅋ]+$/.test(msg) || (msg.match(/ㅋ/g) || []).length >= 5)) {
        reply = "현지야, 웃어?";
    }
    reply = reply.replace(/\*\*/g, "");


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

    // 현재 날짜와 시간 가져오는 함수
    function getCurrentDateTime() {
        let now = new Date();
        let year = now.getFullYear();
        let month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
        let date = String(now.getDate()).padStart(2, '0');
        let hours = String(now.getHours()).padStart(2, '0');
        let minutes = String(now.getMinutes()).padStart(2, '0');
        let seconds = String(now.getSeconds()).padStart(2, '0');
        return year + "-" + month + "-" + date + " " + hours + ":" + minutes;
    }

    // 현재 시간 포함
    const currentTime = getCurrentDateTime();

    let data = {
        "messages": [
            {
                "role": "system",
                "content": "You are 포들리봇, a helpful KakaoTalk assistant created by 십대영님, a developer. You are based on Upstage's Solar-pro model. Provide friendly and useful information to users. Always respond in Korean. 현재 날짜와 시간:" + currentTime
            },
            {
                "role": "user",
                "content": msg
            }
        ],
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