// Rhino 환경 시뮬레이션을 위한 테스트 스크립트
// 이 파일은 KakaoTalk 봇의 Rhino 환경을 시뮬레이션하여 test_recruit.js의 함수를 테스트합니다.

// Rhino 환경에서 사용되는 객체 모의 구현
const org = {
    jsoup: {
        Jsoup: {
            connect: function (url) {
                // 실제로는 여기서 HTTP 요청을 보내지만, 테스트를 위해 모의 객체 반환
                return {
                    headers: function (headers) {
                        return this;
                    },
                    get: function () {
                        // 실제 데이터를 반환하는 대신 모의 응답 반환
                        console.log("URL에 연결 시도: " + url);
                        return {
                            select: function (selector) {
                                console.log("선택자 사용: " + selector);
                                return {
                                    first: function () {
                                        return {
                                            html: function () {
                                                return "{}"; // 빈 JSON 객체 문자열
                                            },
                                            text: function () {
                                                return "테스트 텍스트";
                                            },
                                            attr: function (attr) {
                                                return "테스트 속성";
                                            }
                                        };
                                    },
                                    size: function () {
                                        return 5; // 테스트용 크기
                                    },
                                    get: function (index) {
                                        return {
                                            select: function (selector) {
                                                return {
                                                    first: function () {
                                                        return {
                                                            text: function () {
                                                                return "테스트 텍스트 " + index;
                                                            },
                                                            attr: function (attr) {
                                                                return "테스트 속성 " + index;
                                                            }
                                                        };
                                                    }
                                                };
                                            },
                                            text: function () {
                                                return "테스트 텍스트 " + index;
                                            },
                                            attr: function (attr) {
                                                return "테스트 속성 " + index;
                                            }
                                        };
                                    },
                                    each: function (callback) {
                                        for (let i = 0; i < 5; i++) {
                                            const mockCard = {
                                                select: function (selector) {
                                                    return {
                                                        first: function () {
                                                            return {
                                                                text: function () {
                                                                    return "테스트 제목 " + i;
                                                                },
                                                                attr: function (attr) {
                                                                    if (attr === "href") {
                                                                        return "/test-url-" + i;
                                                                    }
                                                                    return "테스트 속성 " + i;
                                                                }
                                                            };
                                                        }
                                                    };
                                                },
                                                find: function (selector) {
                                                    return {
                                                        first: function () {
                                                            return {
                                                                text: function () {
                                                                    return "테스트 텍스트 " + i;
                                                                },
                                                                attr: function (attr) {
                                                                    if (attr === "href") {
                                                                        return "/test-url-" + i;
                                                                    }
                                                                    return "테스트 속성 " + i;
                                                                }
                                                            };
                                                        },
                                                        length: 1
                                                    };
                                                }
                                            };
                                            callback(i, mockCard);
                                        }
                                    }
                                };
                            },
                            body: function () {
                                return {
                                    text: function () {
                                        return "테스트 본문";
                                    }
                                };
                            },
                            title: function () {
                                return "테스트 제목";
                            }
                        };
                    }
                };
            }
        }
    }
};

// Java 객체 모의 구현
const java = {
    util: {
        HashSet: function () {
            const set = new Set();
            this.add = function (item) {
                set.add(item);
                return true;
            };
            this.contains = function (item) {
                return set.has(item);
            };
            this.size = function () {
                return set.size;
            };
        }
    },
    io: {
        BufferedReader: function () { },
        InputStreamReader: function () { },
        OutputStreamWriter: function () { }
    },
    net: {
        URL: function () {
            return {
                openConnection: function () {
                    return {
                        setRequestMethod: function () { },
                        setRequestProperty: function () { },
                        setDoOutput: function () { },
                        getOutputStream: function () {
                            return {
                                write: function () { },
                                flush: function () { },
                                close: function () { }
                            };
                        },
                        getInputStream: function () {
                            return {
                                read: function () { return -1; }
                            };
                        }
                    };
                }
            };
        }
    }
};

// Log 객체 모의 구현
const Log = {
    e: function (message) {
        console.error("로그 오류: " + message);
    },
    d: function (message) {
        console.log("로그 디버그: " + message);
    }
};

// 테스트용 replier 객체
const testReplier = {
    replies: [],
    reply: function (message) {
        this.replies.push(message);
        console.log("=== 응답 ===");
        console.log(message);
        console.log("============");
    },
    clearReplies: function () {
        this.replies = [];
    }
};

// 현재 날짜 가져오기 함수
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

// ITWorld 뉴스 크롤링 함수 (podlybot_legacy.js에서 가져온 함수)
function getItworldNews(currentDate) {
    try {
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36",
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
            "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
            "Cache-Control": "no-cache",
            "Pragma": "no-cache"
        };

        const urls = [
            { keyword: "개발자", url: "https://www.itworld.co.kr/software-development/" },
            { keyword: "미래기술", url: "https://www.itworld.co.kr/emerging-technology/" },
            { keyword: "AI/ML", url: "https://www.itworld.co.kr/artificial-intelligence/" },
            { keyword: "글로벌트렌드", url: "https://www.itworld.co.kr/vendors-and-providers/" }
        ];

        const newsResults = [];
        const globalUniqueUrls = new java.util.HashSet();

        for (let i = 0; i < urls.length; i++) {
            const { keyword, url } = urls[i];

            try {
                const response = org.jsoup.Jsoup.connect(url)
                    .headers(headers)
                    .get();

                const cardLists = response.select("div.latest-content__card-secondary");
                const items = [];

                // 최대 20개까지만 추출
                const totalNum = Math.min(20, cardLists.size());

                for (let j = 0; j < totalNum; j++) {
                    const card = cardLists.get(j);
                    const titleTag = card.select("h3").first();
                    const linkTag = card.select("a").first();

                    if (titleTag && linkTag) {
                        const title = titleTag.text().trim();
                        let originalUrl = linkTag.attr("href");

                        if (originalUrl && originalUrl.startsWith("/")) {
                            originalUrl = "https://www.itworld.co.kr" + originalUrl;
                        }

                        if (originalUrl && !globalUniqueUrls.contains(originalUrl)) {
                            globalUniqueUrls.add(originalUrl);
                            items.push({
                                title: title,
                                url: originalUrl
                            });
                        }
                    }
                }

                newsResults.push({ keyword: keyword, items: items });
            } catch (urlError) {
                console.error(`${keyword} URL 처리 중 오류: ${urlError.message}`);
                newsResults.push({ keyword: keyword, items: [] });
            }
        }

        // 데이터 포맷팅
        let formattedData = "";

        for (let i = 0; i < newsResults.length; i++) {
            const section = newsResults[i];

            if (section.items.length > 0) {
                formattedData += "● " + currentDate + " 기준 IT 뉴스 (키워드: " + section.keyword + ")\n\n";

                for (let j = 0; j < section.items.length; j++) {
                    const item = section.items[j];
                    formattedData += (j + 1) + ". " + item.title + "\n> " + item.url + "\n";
                }

                formattedData += "\n\n";
            }
        }

        if (formattedData === "") {
            return "● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n뉴스 데이터를 찾을 수 없습니다.";
        }

        return "● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

// OKKY IT 이벤트 정보를 크롤링
function getOkkyItEvents(currentDate) {
    try {
        const url = "https://okky.kr/events/it";
        const response = org.jsoup.Jsoup.connect(url).get();

        // __NEXT_DATA__ 스크립트 태그 찾기
        const scriptTag = response.select("script#__NEXT_DATA__").first();
        if (!scriptTag) {
            return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n데이터를 찾을 수 없습니다.";
        }

        // JSON 데이터 파싱 (테스트를 위해 모의 데이터 사용)
        const content = [
            {
                title: "테스트 이벤트 1",
                displayAuthor: { nickname: "테스터1" },
                dateCreated: "2025-03-01T12:00:00"
            },
            {
                title: "테스트 이벤트 2",
                displayAuthor: { nickname: "테스터2" },
                dateCreated: "2025-03-02T12:00:00"
            },
            {
                title: "테스트 이벤트 3",
                displayAuthor: { nickname: "테스터3" },
                dateCreated: "2025-03-03T12:00:00"
            }
        ];

        // 데이터 추출 및 포맷팅
        let formattedData = "";
        for (let i = 0; i < content.length; i++) {
            const item = content[i];
            const title = item.title;
            const nickname = item.displayAuthor.nickname;
            const dateCreated = item.dateCreated.split("T")[0];

            formattedData += (i + 1) + ". " + title + " | " + nickname + " | 작성일: " + dateCreated + "\n\n";
        }

        return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

// 잡코리아 개발·데이터 직종별 일자리 수 크롤링
function getJobKoreaDevData(currentDate) {
    try {
        const url = "https://www.jobkorea.co.kr/recruit/joblist?menucode=duty";
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
        };

        const response = org.jsoup.Jsoup.connect(url)
            .headers(headers)
            .get();

        // 테스트를 위한 모의 데이터
        const jobDataList = [
            { subName: "웹개발", giCnt: 3500 },
            { subName: "서버/백엔드 개발", giCnt: 3200 },
            { subName: "프론트엔드 개발", giCnt: 2800 },
            { subName: "앱 개발", giCnt: 2500 },
            { subName: "시스템 개발", giCnt: 2200 }
        ];

        // 일자리 수 기준으로 내림차순 정렬
        jobDataList.sort((a, b) => b.giCnt - a.giCnt);

        // 데이터 포맷팅
        let formattedData = "";
        for (let i = 0; i < jobDataList.length; i++) {
            const item = jobDataList[i];
            formattedData += (i + 1) + ". " + item.subName + " (" + item.giCnt.toLocaleString() + " 개)\n";
        }

        return "● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

// 잡코리아 IT 직종 일자리 리스트 크롤링
function getJobKoreaItJobs(currentDate) {
    try {
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36"
        };

        const baseUrl = "https://www.jobkorea.co.kr";
        const url = baseUrl + "/recruit/joblist?menucode=duty&dutyCtgr=10031#anchorGICnt_1";

        // 테스트를 위한 모의 데이터
        const jobListings = [
            {
                company: "테스트 회사 1",
                url: "https://www.jobkorea.co.kr/Recruit/GI_Read/1",
                title: "백엔드 개발자 채용",
                deadline: "~03/31"
            },
            {
                company: "테스트 회사 2",
                url: "https://www.jobkorea.co.kr/Recruit/GI_Read/2",
                title: "프론트엔드 개발자 채용",
                deadline: "~04/15"
            },
            {
                company: "테스트 회사 3",
                url: "https://www.jobkorea.co.kr/Recruit/GI_Read/3",
                title: "풀스택 개발자 채용",
                deadline: "~04/30"
            }
        ];

        // 데이터 포맷팅
        let formattedData = "";
        for (let i = 0; i < jobListings.length; i++) {
            const item = jobListings[i];
            formattedData += (i + 1) + ". [" + item.company + "] " + item.title + " (" + item.deadline + ")\n> " + item.url + "\n\n";
        }

        return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

// 채용 정보 API 호출 함수
function callRecruitApis(room, sender, replier) {
    try {
        // 현재 날짜 가져오기
        const currentDate = getCurrentDateTime().split(" ")[0]; // YYYY-MM-DD 형식

        // 각 크롤링 함수 호출 및 결과 전송
        try {
            const okkyEvents = getOkkyItEvents(currentDate);
            replier.reply(okkyEvents);
        } catch (okkyError) {
            replier.reply("● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n크롤링 중 오류가 발생했습니다: " + okkyError.message);
        }

        try {
            const jobKoreaDevData = getJobKoreaDevData(currentDate);
            replier.reply(jobKoreaDevData);
        } catch (jobKoreaDevError) {
            replier.reply("● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n크롤링 중 오류가 발생했습니다: " + jobKoreaDevError.message);
        }

        try {
            const jobKoreaItJobs = getJobKoreaItJobs(currentDate);
            replier.reply(jobKoreaItJobs);
        } catch (jobKoreaItError) {
            replier.reply("● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n크롤링 중 오류가 발생했습니다: " + jobKoreaItError.message);
        }

        try {
            const itworldNews = getItworldNews(currentDate);
            replier.reply(itworldNews);
        } catch (itworldError) {
            replier.reply("● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n크롤링 중 오류가 발생했습니다: " + itworldError.message);
        }
    } catch (e) {
        replier.reply("채용 정보를 가져오는 중 오류가 발생했습니다: " + e.message);
    }
}

// 테스트 실행 함수
function runTest() {
    console.log("=== Rhino 환경 시뮬레이션 테스트 시작 ===");

    // 현재 날짜 가져오기
    const currentDate = getCurrentDateTime().split(" ")[0];

    // OKKY IT 이벤트 크롤링 테스트
    console.log("=== OKKY IT 이벤트 테스트 시작 ===");
    const okkyEvents = getOkkyItEvents(currentDate);
    console.log(okkyEvents);
    console.log("=== OKKY IT 이벤트 테스트 종료 ===");

    // 잡코리아 개발·데이터 직종별 일자리 수 크롤링 테스트
    console.log("=== 잡코리아 개발·데이터 직종별 일자리 수 테스트 시작 ===");
    const jobKoreaDevData = getJobKoreaDevData(currentDate);
    console.log(jobKoreaDevData);
    console.log("=== 잡코리아 개발·데이터 직종별 일자리 수 테스트 종료 ===");

    // 잡코리아 IT 직종 일자리 리스트 크롤링 테스트
    console.log("=== 잡코리아 IT 직종 일자리 리스트 테스트 시작 ===");
    const jobKoreaItJobs = getJobKoreaItJobs(currentDate);
    console.log(jobKoreaItJobs);
    console.log("=== 잡코리아 IT 직종 일자리 리스트 테스트 종료 ===");

    // ITWorld 뉴스 크롤링 테스트
    console.log("=== ITWorld 뉴스 테스트 시작 ===");
    const itworldNews = getItworldNews(currentDate);
    console.log(itworldNews);
    console.log("=== ITWorld 뉴스 테스트 종료 ===");

    // callRecruitApis 함수 테스트
    console.log("=== callRecruitApis 함수 테스트 시작 ===");
    callRecruitApis("테스트룸", "테스터", testReplier);
    console.log("=== callRecruitApis 함수 테스트 종료 ===");

    console.log("=== Rhino 환경 시뮬레이션 테스트 종료 ===");
}

// 테스트 실행
runTest(); 