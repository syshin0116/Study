// handlers.js - 메시지 처리 로직

// 모듈 가져오기
const config = require('./config');
const utils = require('./utils');
const api = require('./api');
const notion = require('./notion');

/**
 * 메시지 처리 핸들러
 * @param {Object} msg - 메시지 객체
 * @returns {string|null} 응답 메시지 또는 null
 */
function handleMessage(msg) {
    try {
        let reply = null;
        const room = msg.room;
        const sender = msg.author.name;
        const content = msg.content;
        const currentTime = utils.getCurrentDateTime();

        // 사용자 메시지를 히스토리에 추가
        utils.addUserMessageToHistory(config.conversationHistory, room, sender, content);

        // 메시지 조건에 따른 처리
        if (utils.isValidUrl(content)) {
            reply = handleUrlMessage(content, room, sender);
        } else if (content === "/help") {
            reply = handleHelpMessage(room);
        } else if (content === "&recruit" || content === "평일 매일 올라오는 IT 정보들 확인 🤓") {
            api.callRecruitApis(room, sender, {
                reply: function (text) {
                    // API2에서는 msg.reply()를 사용하므로 여기서는 직접 호출하지 않고 반환
                    if (reply === null) {
                        reply = text;
                    } else {
                        // 이미 reply가 있는 경우 별도로 처리 필요
                        // 실제 구현에서는 여러 응답을 어떻게 처리할지 결정 필요
                    }
                }
            });
            return null; // 여기서 바로 종료
        } else if (content.startsWith("&&")) {
            let cmd = content.substr(2);
            reply = api.getResponseFromApi(config.API_ENDPOINTS.CHAT, room, sender, cmd);
        } else if (content.startsWith("&")) {
            reply = handleAiMessage(content, room, currentTime);
        } else if (sender === "이현지" && (/^[ㅋㅎ]+$/.test(content) || (content.match(/[ㅋㅎ]/g) || []).length >= 5)) {
            reply = "현지야, 웃어?";
        }

        // reply가 null이라면 응답하지 않고 함수 종료
        if (reply === null) {
            return null;
        }

        // '**' 제거
        reply = reply.replace(/\*\*/g, "");

        // assistant 응답을 히스토리에 추가
        utils.addAssistantMessageToHistory(config.conversationHistory, room, reply);

        return reply;
    } catch (e) {
        return "메시지 처리 중 오류가 발생했습니다: " + e.message;
    }
}

/**
 * URL 메시지 처리
 * @param {string} url - URL 문자열
 * @param {string} room - 방 이름
 * @param {string} sender - 발신자 이름
 * @returns {string|null} 응답 메시지 또는 null
 */
function handleUrlMessage(url, room, sender) {
    const result = api.summarizeUrl(url);
    if (result && result.summary) {
        // 크롤링 결과에서 title, summary 추출
        let { title, summary } = result;

        // 요약문에서 ** 제거
        summary = summary.replace(/\*\*/g, "");

        if (config.wecoRooms.has(room)) {
            notion.addItemToNotion(config.WECO_NOTION_DATABASE_ID, url, summary, room, sender, title);
        }
        else if (room === "승엽") {
            notion.addItemToNotion(config.PERSONAL_NOTION_DATABASE_ID, url, summary, room, sender, title);
        }

        return "[링크 요약]\n제목:" + title + "\n" + summary;
    }
    return null;
}

/**
 * 도움말 메시지 처리
 * @param {string} room - 방 이름
 * @returns {string} 도움말 메시지
 */
function handleHelpMessage(room) {
    let reply = "[포들리봇 사용법]\n" +
        "1. &+텍스트: Openai의 gpt-4o 모델이 응답\n" +
        "2. &&+텍스트: 서버로 요청(Upstage, Openai중 설정된 모델이 응답)\n" +
        "3. 링크: 링크 요약\n" +
        "4. &recruit: 채용 정보";

    if (config.wecoRooms.has(room)) {
        reply += "\n\n[위캔코딩 스터디방 url 정리 페이지]\n" +
            "📌 https://tinyurl.com/c4ywwb2v";
    }

    return reply;
}

/**
 * AI 메시지 처리
 * @param {string} msg - 메시지 내용
 * @param {string} room - 방 이름
 * @param {string} currentTime - 현재 시간
 * @returns {string} AI 응답 메시지
 */
function handleAiMessage(msg, room, currentTime) {
    let cmd = msg.substr(1);

    let basePromptContent = config.BASE_PROMPT + "Current date and time:" + currentTime + "\n";

    let combinedPrompt = config.ROOM_SPECIFIC_PROMPTS[room]
        ? basePromptContent + " " + config.ROOM_SPECIFIC_PROMPTS[room]
        : basePromptContent;

    let messages = [
        { role: "system", content: combinedPrompt }
    ].concat(config.conversationHistory[room] || []);

    return api.getResponse("openai", messages);
}

// 모듈 내보내기
module.exports = {
    handleMessage
}; 