// index.js - API2 메인 진입점

// Bot 인스턴스 가져오기
const bot = BotManager.getCurrentBot();

// 모듈 가져오기
const handlers = require('./modules/handlers');

/**
 * 메시지 이벤트 핸들러
 * @param {Object} msg - 메시지 객체
 */
function onMessage(msg) {
    try {
        // 메시지 처리 및 응답 생성
        const reply = handlers.handleMessage(msg);

        // 응답이 있는 경우에만 전송
        if (reply !== null) {
            msg.reply(reply);
        }
    } catch (e) {
        // 오류 발생 시 오류 메시지 전송
        msg.reply("오류가 발생했습니다: " + e.message);
    }
}

/**
 * 봇 로드 완료 이벤트 핸들러
 */
function onLoaded() {
    Log.i("포들리봇이 로드되었습니다.");
}

/**
 * 컴파일 시작 이벤트 핸들러
 */
function onStartCompile() {
    Log.i("포들리봇 컴파일을 시작합니다.");
}

// 이벤트 리스너 등록
bot.addListener(Event.MESSAGE, onMessage);
bot.addListener(Event.LOADED, onLoaded);
bot.addListener(Event.START_COMPILE, onStartCompile);

// 초기화 메시지
Log.i("포들리봇 API2 버전이 초기화되었습니다."); 