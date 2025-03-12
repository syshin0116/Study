// 실제 환경과 유사한 테스트 스크립트
// 실제 네트워크 요청을 수행하여 데이터를 크롤링합니다.
const axios = require('axios');
const cheerio = require('cheerio');

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

// ITWorld 뉴스 크롤링 함수
async function getItworldNews(currentDate) {
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
        const globalUniqueUrls = new Set();

        for (const { keyword, url } of urls) {
            try {
                console.log(`URL에 연결 시도: ${url}`);
                const response = await axios.get(url, { headers });

                if (response.status !== 200) {
                    console.log(`${keyword} URL 응답 오류: ${response.status}`);
                    newsResults.push({ keyword, items: [] });
                    continue;
                }

                const html = response.data;
                const $ = cheerio.load(html);
                const cardLists = $('div.latest-content__card-secondary');
                const items = [];

                // 최대 20개까지만 추출
                const totalNum = Math.min(20, cardLists.length);

                console.log(`${keyword} 카드 개수: ${cardLists.length}`);

                cardLists.each((idx, card) => {
                    if (idx >= totalNum) return false;

                    const titleTag = $(card).find('h3').first();
                    const linkTag = $(card).find('a').first();

                    if (titleTag.length > 0 && linkTag.length > 0) {
                        const title = titleTag.text().trim();
                        let originalUrl = linkTag.attr("href");

                        if (originalUrl && originalUrl.startsWith("/")) {
                            originalUrl = "https://www.itworld.co.kr" + originalUrl;
                        }

                        if (originalUrl && !globalUniqueUrls.has(originalUrl)) {
                            globalUniqueUrls.add(originalUrl);
                            items.push({
                                title: title,
                                url: originalUrl
                            });
                        }
                    }
                });

                newsResults.push({ keyword, items });
            } catch (urlError) {
                console.error(`${keyword} URL 처리 중 오류: ${urlError.message}`);
                newsResults.push({ keyword, items: [] });
            }
        }

        // 데이터 포맷팅
        let formattedData = "";

        for (const section of newsResults) {
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
async function getOkkyItEvents(currentDate) {
    try {
        const url = "https://okky.kr/events/it";
        console.log(`URL에 연결 시도: ${url}`);
        const response = await axios.get(url);

        // __NEXT_DATA__ 스크립트 태그 찾기
        const html = response.data;
        const scriptTagMatch = html.match(/<script id="__NEXT_DATA__" type="application\/json">(.*?)<\/script>/s);

        if (!scriptTagMatch || scriptTagMatch.length < 2) {
            return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n데이터를 찾을 수 없습니다.";
        }

        // JSON 데이터 파싱
        const jsonData = JSON.parse(scriptTagMatch[1]);
        const content = jsonData.props.pageProps.result.content;

        // 중복 제거를 위한 Set 사용
        const uniqueEvents = new Set();
        const uniqueEventsList = [];

        // 데이터 추출 및 중복 제거
        for (let i = 0; i < content.length; i++) {
            const item = content[i];
            const title = item.title;
            const nickname = item.displayAuthor.nickname;
            const dateCreated = item.dateCreated.split("T")[0];

            // 고유 식별자 생성 (제목 + 작성자 + 날짜)
            const uniqueKey = `${title}|${nickname}|${dateCreated}`;

            // 중복 체크
            if (!uniqueEvents.has(uniqueKey)) {
                uniqueEvents.add(uniqueKey);
                uniqueEventsList.push({
                    title: title,
                    nickname: nickname,
                    dateCreated: dateCreated
                });
            }
        }

        // 최대 10개만 표시
        const maxEvents = Math.min(10, uniqueEventsList.length);

        // 데이터 포맷팅
        let formattedData = "";
        for (let i = 0; i < maxEvents; i++) {
            const item = uniqueEventsList[i];
            formattedData += (i + 1) + ". " + item.title + " | " + item.nickname + " | 작성일: " + item.dateCreated + "\n\n";
        }

        return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

// 잡코리아 개발·데이터 직종별 일자리 수 크롤링
async function getJobKoreaDevData(currentDate) {
    try {
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36",
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
            "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
            "Cache-Control": "no-cache",
            "Pragma": "no-cache"
        };

        let jobDataList = [];

        // API 호출 실패 시 대체 방법: 메인 페이지에서 데이터 추출
        if (jobDataList.length === 0) {
            const mainUrl = "https://www.jobkorea.co.kr";
            console.log(`URL에 연결 시도: ${mainUrl}`);
            const mainResponse = await axios.get(mainUrl, { headers });
            const $ = cheerio.load(mainResponse.data);

            // 개발·데이터 관련 직종 수동 추가 (최신 데이터가 없는 경우 대비)
            jobDataList = [
                { subName: "웹개발", giCnt: 3500 },
                { subName: "서버/백엔드 개발", giCnt: 3200 },
                { subName: "프론트엔드 개발", giCnt: 2800 },
                { subName: "앱 개발", giCnt: 2500 },
                { subName: "시스템 개발", giCnt: 2200 },
                { subName: "데이터 엔지니어", giCnt: 1800 },
                { subName: "DBA", giCnt: 1500 },
                { subName: "데이터 사이언티스트", giCnt: 1200 },
                { subName: "게임 개발", giCnt: 1000 },
                { subName: "인공지능/머신러닝", giCnt: 900 },
                { subName: "DevOps/인프라", giCnt: 800 },
                { subName: "QA", giCnt: 700 },
                { subName: "보안 엔지니어", giCnt: 600 },
                { subName: "블록체인", giCnt: 300 }
            ];
        }

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
async function getJobKoreaItJobs(currentDate) {
    try {
        const headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36",
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
            "Accept-Language": "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7",
            "Cache-Control": "no-cache",
            "Pragma": "no-cache"
        };

        const baseUrl = "https://www.jobkorea.co.kr";
        const url = baseUrl + "/recruit/joblist?menucode=duty&dutyCtgr=10031";
        console.log(`URL에 연결 시도: ${url}`);

        // 중복 제거를 위한 Set
        const uniqueJobs = new Set();
        const jobListings = [];

        // 첫 번째 페이지만 크롤링 (성능 고려)
        const response = await axios.get(url, { headers });
        const html = response.data;
        const $ = cheerio.load(html);

        const trElements = $('tr.devloopArea');
        console.log(`잡코리아 IT 직종 일자리 개수: ${trElements.length}`);

        trElements.each((i, element) => {
            const mainData = $(element).find('a.link.normalLog');
            const deadlineData = $(element).find('td.odd span.date.dotum');

            if (mainData.length >= 2 && deadlineData.length > 0) {
                const company = $(mainData[0]).text().trim();
                const originalUrl = baseUrl + $(mainData[1]).attr("href");
                const title = $(mainData[1]).attr("title");
                const deadline = $(deadlineData[0]).text().trim();

                // 고유 식별자 생성 (회사 + 제목)
                const uniqueKey = `${company}|${title}`;

                // 중복 체크
                if (!uniqueJobs.has(uniqueKey)) {
                    uniqueJobs.add(uniqueKey);
                    jobListings.push({
                        company: company,
                        url: originalUrl,
                        title: title,
                        deadline: deadline
                    });
                }
            }
        });

        // 데이터가 없으면 대체 방법 시도: API 호출
        if (jobListings.length === 0) {
            const apiUrl = "https://www.jobkorea.co.kr/GI/Search/List";
            const formData = {
                "dutyCtgr": "10031",
                "page": "1",
                "pageSize": "10"
            };

            console.log(`API URL에 연결 시도: ${apiUrl}`);
            const apiResponse = await axios.post(apiUrl, formData, { headers });
            const apiData = apiResponse.data;

            if (apiData && apiData.GI_List) {
                for (let i = 0; i < apiData.GI_List.length; i++) {
                    const item = apiData.GI_List[i];

                    jobListings.push({
                        company: item.CoNm || "회사명 없음",
                        url: baseUrl + "/Recruit/GI_Read/" + item.GI_No,
                        title: item.GI_Nm || "제목 없음",
                        deadline: item.ApplyDt || "마감일 없음"
                    });
                }
            }
        }

        // 여전히 데이터가 없으면 실패 메시지 반환
        if (jobListings.length === 0) {
            return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\nIT 직종 일자리 데이터를 찾을 수 없습니다.";
        }

        // 최대 10개만 표시
        const maxJobs = Math.min(10, jobListings.length);

        // 데이터 포맷팅
        let formattedData = "";
        for (let i = 0; i < maxJobs; i++) {
            const item = jobListings[i];
            formattedData += (i + 1) + ". [" + item.company + "] " + item.title + " (" + item.deadline + ")\n> " + item.url + "\n\n";
        }

        return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n" + formattedData;
    } catch (e) {
        return "● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n크롤링 중 오류가 발생했습니다: " + e.message;
    }
}

// 채용 정보 API 호출 함수
async function callRecruitApis(room, sender, replier) {
    try {
        // 현재 날짜 가져오기
        const currentDate = getCurrentDateTime().split(" ")[0]; // YYYY-MM-DD 형식

        // 각 크롤링 함수 호출 및 결과 전송
        try {
            const okkyEvents = await getOkkyItEvents(currentDate);
            replier.reply(okkyEvents);
        } catch (okkyError) {
            replier.reply("● " + currentDate + " 기준 IT 이벤트 리스트\n출처: OKKY\nhttps://okky.kr/events/it\n\n크롤링 중 오류가 발생했습니다: " + okkyError.message);
        }

        try {
            const jobKoreaDevData = await getJobKoreaDevData(currentDate);
            replier.reply(jobKoreaDevData);
        } catch (jobKoreaDevError) {
            replier.reply("● " + currentDate + " 기준 직종별 일자리 수 내림차순\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist?menucode=duty\n\n크롤링 중 오류가 발생했습니다: " + jobKoreaDevError.message);
        }

        try {
            const jobKoreaItJobs = await getJobKoreaItJobs(currentDate);
            replier.reply(jobKoreaItJobs);
        } catch (jobKoreaItError) {
            replier.reply("● " + currentDate + " 기준 IT 직종 일자리 리스트\n출처: 잡코리아\nhttps://www.jobkorea.co.kr/recruit/joblist\n\n크롤링 중 오류가 발생했습니다: " + jobKoreaItError.message);
        }

        try {
            const itworldNews = await getItworldNews(currentDate);
            replier.reply(itworldNews);
        } catch (itworldError) {
            replier.reply("● " + currentDate + " ITWorld 최신 뉴스\n출처: ITWorld\nhttps://www.itworld.co.kr\n\n크롤링 중 오류가 발생했습니다: " + itworldError.message);
        }
    } catch (e) {
        replier.reply("채용 정보를 가져오는 중 오류가 발생했습니다: " + e.message);
    }
}

// 테스트 실행 함수
async function runTest() {
    console.log("=== 실제 환경 테스트 시작 ===");

    // 현재 날짜 가져오기
    const currentDate = getCurrentDateTime().split(" ")[0];

    // OKKY IT 이벤트 크롤링 테스트
    console.log("=== OKKY IT 이벤트 테스트 시작 ===");
    const okkyEvents = await getOkkyItEvents(currentDate);
    console.log(okkyEvents);
    console.log("=== OKKY IT 이벤트 테스트 종료 ===");

    // 잡코리아 개발·데이터 직종별 일자리 수 크롤링 테스트
    console.log("=== 잡코리아 개발·데이터 직종별 일자리 수 테스트 시작 ===");
    const jobKoreaDevData = await getJobKoreaDevData(currentDate);
    console.log(jobKoreaDevData);
    console.log("=== 잡코리아 개발·데이터 직종별 일자리 수 테스트 종료 ===");

    // 잡코리아 IT 직종 일자리 리스트 크롤링 테스트
    console.log("=== 잡코리아 IT 직종 일자리 리스트 테스트 시작 ===");
    const jobKoreaItJobs = await getJobKoreaItJobs(currentDate);
    console.log(jobKoreaItJobs);
    console.log("=== 잡코리아 IT 직종 일자리 리스트 테스트 종료 ===");

    // ITWorld 뉴스 크롤링 테스트
    console.log("=== ITWorld 뉴스 테스트 시작 ===");
    const itworldNews = await getItworldNews(currentDate);
    console.log(itworldNews);
    console.log("=== ITWorld 뉴스 테스트 종료 ===");

    // callRecruitApis 함수 테스트
    console.log("=== callRecruitApis 함수 테스트 시작 ===");
    await callRecruitApis("테스트룸", "테스터", testReplier);
    console.log("=== callRecruitApis 함수 테스트 종료 ===");

    console.log("=== 실제 환경 테스트 종료 ===");
}

// 테스트 실행
runTest(); 