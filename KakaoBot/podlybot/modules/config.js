// config.js - 설정 및 상수 관리

// 대화 기록 저장소
const conversationHistory = {};

// 위캔코딩 스터디방 목록
const wecoRooms = new Set(["위캔코딩 스터디방🤗", "SQLD & ADsP 스터디방", "제1회 정보처리기사 스터디"]);

// Notion 데이터베이스 ID
const WECO_NOTION_DATABASE_ID = ""; // 실제 ID로 교체 필요
const PERSONAL_NOTION_DATABASE_ID = ""; // 실제 ID로 교체 필요
const NOTION_API_KEY = ""; // 실제 API 키로 교체 필요

// OpenAI 및 Upstage API 키
const openaiKey = ""; // 실제 API 키로 교체 필요
const upstageKey = ""; // 실제 API 키로 교체 필요

// API 엔드포인트
const API_ENDPOINTS = {
    CHAT: "https://podly.fun/api/podlybot/chat",
    RECRUIT_BASE: "https://podly.fun/api/recruit",
    OPENAI: "https://api.openai.com/v1/chat/completions",
    UPSTAGE: "https://api.upstage.ai/v1/solar/chat/completions",
    NOTION: "https://api.notion.com/v1/pages"
};

// 채용 정보 API 엔드포인트 목록
const RECRUIT_ENDPOINTS = [
    { name: "OKKY IT Events", url: API_ENDPOINTS.RECRUIT_BASE + "/crawl_okky_info" },
    { name: "JobKorea Dev/Data Jobs", url: API_ENDPOINTS.RECRUIT_BASE + "/crawl_dev_data" },
    { name: "JobKorea IT Jobs", url: API_ENDPOINTS.RECRUIT_BASE + "/crawl_it_jobs" },
    { name: "ITWorld News", url: API_ENDPOINTS.RECRUIT_BASE + "/crawl_itworld_news" }
];

// 방별 프롬프트 설정
const ROOM_SPECIFIC_PROMPTS = {
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

// 기본 프롬프트 설정
const BASE_PROMPT =
    "You are 포들리봇, a helpful KakaoTalk assistant created by anonymous developers called 십대영님 and 해달님. " +
    "You are based on OpenAI's gpt-4o model. " +
    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
    "If the response is lengthy, use bullet points for better readability. " +
    "Always maintain a polite tone and do not use markdown. use '-' for bullet points.\n";

// 요약 프롬프트 설정
const SUMMARY_PROMPT =
    "You are 포들리봇, a helpful KakaoTalk assistant created by 십대영님 using OpenAI's gpt-4o model. " +
    "Your primary goal is to provide accurate, friendly, and useful responses in Korean. " +
    "If the response is lengthy, use bullet points for better readability. " +
    "Always maintain a polite tone. Do not use markdown. use '-' for bullet points.\n\n\n";

// 모듈 내보내기
module.exports = {
    conversationHistory,
    wecoRooms,
    WECO_NOTION_DATABASE_ID,
    PERSONAL_NOTION_DATABASE_ID,
    NOTION_API_KEY,
    openaiKey,
    upstageKey,
    API_ENDPOINTS,
    RECRUIT_ENDPOINTS,
    ROOM_SPECIFIC_PROMPTS,
    BASE_PROMPT,
    SUMMARY_PROMPT
}; 