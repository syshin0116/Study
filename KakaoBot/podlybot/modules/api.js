// api.js - API 관련 함수 모음

// 설정 가져오기
const config = require('./config');
const utils = require('./utils');

/**
 * URL 내용을 요약
 * @param {string} url - 요약할 URL
 * @returns {Object|null} 제목과 요약 내용을 포함한 객체 또는 null
 */
function summarizeUrl(url) {
    try {
        let response = org.jsoup.Jsoup.connect(url).get();
        let title = response.title();
        let body = response.body().text();
        let maxLength = 10000;

        if (body.length > maxLength) {
            body = body.substring(0, body.lastIndexOf(" ", maxLength)) + "..."; // 단어 경계에서 잘림 보장
        }

        let currentTime = utils.getCurrentDateTime();
        let messages = [
            {
                role: "system",
                content: config.SUMMARY_PROMPT + "Current date and time: " + currentTime + "\n"
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

/**
 * API에 요청을 보내고 응답을 받음
 * @param {string} url - API URL
 * @param {string} room - 방 이름
 * @param {string} sender - 발신자 이름
 * @param {string} msg - 메시지 내용
 * @returns {string} API 응답
 */
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
        result = "API에 요청 중 오류가 발생했습니다: " + e.message;
    }

    return result;
}

/**
 * 채용 정보 API 호출
 * @param {string} room - 방 이름
 * @param {string} sender - 발신자 이름
 * @param {Object} replier - 응답 객체
 */
function callRecruitApis(room, sender, replier) {
    // API 엔드포인트 목록 가져오기
    const apiEndpoints = config.RECRUIT_ENDPOINTS;

    // 각 API 호출 및 결과 처리
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

            // 'response' 필드 확인
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

/**
 * AI 모델에 요청을 보내고 응답을 받음
 * @param {string} type - AI 모델 타입 (openai 또는 기타)
 * @param {Array} messages - 메시지 배열
 * @returns {string} AI 모델 응답
 */
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
        url = config.API_ENDPOINTS.OPENAI;
        key = config.openaiKey;
        data.model = "gpt-4o";
    } else {
        url = config.API_ENDPOINTS.UPSTAGE;
        key = config.upstageKey;
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

        responseText = response.text();
        let result1 = JSON.parse(responseText);
        result = result1.choices[0].message.content;
    } catch (e) {
        result = "오류가 발생했습니다: " + e.message + "\n응답: " + responseText;
    }

    return result;
}

// 모듈 내보내기
module.exports = {
    summarizeUrl,
    getResponseFromApi,
    callRecruitApis,
    getResponse
}; 