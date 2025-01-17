require('dotenv').config();

const NOTION_DATABASE_ID = process.env.NOTION_DATABASE_ID;
const NOTION_API_KEY = process.env.NOTION_API_KEY;

/**
 * Notion API를 사용하여 데이터를 추가하는 함수
 * @param {string} url 저장할 URL
 * @param {string} summary 요약 내용
 * @returns {Promise<string>} Notion API 호출 결과
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


function addItemToNotion(url, summary, room, user, title) {
    return new Promise((resolve, reject) => {
        const notionUrl = "https://api.notion.com/v1/pages";

        // 요청 바디
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
                room: {
                    select: {
                        name: room
                    }
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

        // 요청 옵션
        const options = {
            hostname: "api.notion.com",
            path: "/v1/pages",
            method: "POST",
            headers: {
                "Authorization": "Bearer " + NOTION_API_KEY,
                "Content-Type": "application/json",
                "Notion-Version": "2022-06-28"
            }
        };

        // 요청 실행
        const https = require("https");
        const req = https.request(options, (res) => {
            let data = "";

            res.on("data", (chunk) => {
                data += chunk;
            });

            res.on("end", () => {
                if (res.statusCode === 200 || res.statusCode === 201) {
                    resolve("Notion 등록 완료: " + data);
                } else {
                    reject("Notion API 오류: " + res.statusCode + "-" + data);
                }
            });
        });

        req.on("error", (e) => {
            reject("HTTPS 요청 오류: " + e.message);
        });

        // 요청 바디 데이터 전송
        req.write(JSON.stringify(payload));
        req.end();
    });
}

// 테스트 호출
addItemToNotion("https://news.hada.io/topic?id=18698", "이 기사는 마크 저커버그가 2025년까지 AI가 중급 코딩 엔지니어 수준에 도달할 것이라는 예측을 다루고 있습니다. 주요 내용은 다음과 같습니다:\n- AI는 2025년까지 중간 수준의 엔지니어와 동등한 코딩 능력을 가질 것으로 예상됩니다.\n- 메타의 Llama 시리즈는 오픈소스로 개발되어 누구나 활용할 수 있습니다. \n- AI는 엔지니어의 생산성을 30 % 향상시키고 있으며, 여러 기업들이 AI를 통해 업무 효율성을 극대화하고 있습니다. \n- 세일즈포스와 클라르나 같은 기업들은 AI 덕분에 엔지니어 채용을 줄이는 추세입니다. \n- AI의 발전은 기술 산업 전반에 근본적인 변화를 가져올 잠재력을 지니고 있습니다.", "위캔코딩 스터디방🤗", "유저1", "title")
    .then(console.log)
    .catch(console.error);
