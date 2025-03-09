// utils.js - 유틸리티 함수 모음

/**
 * 현재 날짜와 시간을 포맷팅하여 반환
 * @returns {string} 포맷팅된 날짜와 시간 문자열 (YYYY-MM-DD HH:MM)
 */
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

/**
 * 문자열이 유효한 URL인지 확인
 * @param {string} string - 확인할 문자열
 * @returns {boolean} URL 유효성 여부
 */
function isValidUrl(string) {
    let urlPattern = new RegExp('^(https?:\\/\\/)?' +
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' +
        '((\\d{1,3}\\.){3}\\d{1,3}))' +
        '(\\:\\d+)?(\\/[-a-z\\d%.~+]*)*' +
        '(\\?[;&a-z\\d%_.~+=-]*)?' +
        '(\\#[-a-z\\d_]*)?$', 'i');
    return urlPattern.test(string);
}

/**
 * 대화 기록에 메시지 추가
 * @param {Object} conversationHistory - 대화 기록 객체
 * @param {string} room - 방 이름
 * @param {string} role - 메시지 역할 (user/assistant)
 * @param {string} content - 메시지 내용
 * @param {number} maxHistory - 최대 보관할 메시지 수
 */
function addMessageToHistory(conversationHistory, room, role, content, maxHistory = 10) {
    // room별 히스토리 배열이 없으면 생성
    if (!conversationHistory[room]) {
        conversationHistory[room] = [];
    }

    // 메시지를 히스토리에 추가
    conversationHistory[room].push({
        role: role,
        content: content
    });

    // 히스토리가 너무 길어지면 맨 앞(오래된) 메시지 제거
    while (conversationHistory[room].length > maxHistory) {
        conversationHistory[room].shift();
    }
}

/**
 * 사용자 메시지를 대화 기록에 추가
 * @param {Object} conversationHistory - 대화 기록 객체
 * @param {string} room - 방 이름
 * @param {string} sender - 발신자 이름
 * @param {string} msg - 메시지 내용
 * @param {number} maxHistory - 최대 보관할 메시지 수
 */
function addUserMessageToHistory(conversationHistory, room, sender, msg, maxHistory = 10) {
    const currentTime = getCurrentDateTime();
    const content = "username: " + (sender || "Unknown") + "\nmessage: " + (msg || "") + "\ntime: " + currentTime;

    addMessageToHistory(conversationHistory, room, "user", content, maxHistory);
}

/**
 * 어시스턴트 메시지를 대화 기록에 추가
 * @param {Object} conversationHistory - 대화 기록 객체
 * @param {string} room - 방 이름
 * @param {string} reply - 응답 메시지
 * @param {number} maxHistory - 최대 보관할 메시지 수
 */
function addAssistantMessageToHistory(conversationHistory, room, reply, maxHistory = 10) {
    addMessageToHistory(conversationHistory, room, "assistant", reply, maxHistory);
}

// 모듈 내보내기
module.exports = {
    getCurrentDateTime,
    isValidUrl,
    addMessageToHistory,
    addUserMessageToHistory,
    addAssistantMessageToHistory
}; 