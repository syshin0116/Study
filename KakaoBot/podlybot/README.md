# 포들리봇 (PodlyBot)

포들리봇은 카카오톡 자동응답 봇으로, API2 구조로 개발되었습니다. 이 봇은 URL 요약, AI 응답, 채용 정보 제공 등 다양한 기능을 제공합니다.

## 주요 기능

- URL 요약: 공유된 링크의 내용을 자동으로 요약
- AI 응답: OpenAI의 GPT-4o 모델을 활용한 대화 기능
- 채용 정보: IT 관련 채용 정보 제공
- Notion 연동: 공유된 링크를 Notion 데이터베이스에 자동 저장

## 파일 구조

```
podlybot/
├── index.js           # 메인 진입점
├── README.md          # 프로젝트 설명
└── modules/           # 모듈 디렉토리
    ├── api.js         # API 관련 함수
    ├── config.js      # 설정 및 상수
    ├── handlers.js    # 메시지 처리 로직
    ├── notion.js      # Notion API 관련 함수
    └── utils.js       # 유틸리티 함수
```

## 설정 방법

1. `config.js` 파일에서 다음 항목을 설정해야 합니다:
   - `WECO_NOTION_DATABASE_ID`: 위캔코딩 스터디방용 Notion 데이터베이스 ID
   - `PERSONAL_NOTION_DATABASE_ID`: 개인용 Notion 데이터베이스 ID
   - `NOTION_API_KEY`: Notion API 키
   - `openaiKey`: OpenAI API 키
   - `upstageKey`: Upstage API 키

## 사용 방법

### 메신저봇R에서 설정

1. 스크립트 설정에서 "레거시 API를 사용합니다. (더 이상 사용되지 않음)" 항목을 선택 해제
2. 스크립트 파일을 메신저봇R에 업로드

### 봇 명령어

- `&텍스트`: OpenAI의 GPT-4o 모델이 응답
- `&&텍스트`: 서버로 요청 (Upstage, OpenAI 중 설정된 모델이 응답)
- URL 입력: 링크 요약
- `&recruit`: 채용 정보
- `/help`: 도움말

## API2 마이그레이션 정보

이 프로젝트는 레거시 API에서 API2로 마이그레이션되었습니다. 주요 변경 사항은 다음과 같습니다:

1. `response()` 함수 대신 `onMessage()` 함수와 이벤트 리스너 사용
2. 모듈화된 구조로 코드 재구성
3. Bot 인스턴스를 통한 이벤트 관리

## 라이선스

이 프로젝트는 개인 및 교육용으로 자유롭게 사용할 수 있습니다. 