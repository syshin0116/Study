// notion.js - Notion API 관련 함수 모음

// 설정 가져오기
const config = require('./config');

/**
 * Notion 데이터베이스에 항목 추가
 * @param {string} NOTION_DATABASE_ID - Notion 데이터베이스 ID
 * @param {string} url - 추가할 URL
 * @param {string} summary - URL 요약 내용
 * @param {string} room - 방 이름
 * @param {string} user - 사용자 이름
 * @param {string} title - 제목
 * @returns {string} 결과 메시지
 */
function addItemToNotion(NOTION_DATABASE_ID, url, summary, room, user, title) {
    try {
        const notionUrl = config.API_ENDPOINTS.NOTION;

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
                room: {
                    select: {
                        name: room
                    }
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
        connection.setRequestProperty("Authorization", "Bearer " + config.NOTION_API_KEY);
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

// 모듈 내보내기
module.exports = {
    addItemToNotion
}; 