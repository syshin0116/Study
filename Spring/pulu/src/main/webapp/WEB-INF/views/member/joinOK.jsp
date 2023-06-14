<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="5;url=main.pulu" charset="UTF-8">
<title>회원가입 완료</title>

<!-- 선민: 페이지 이동 함수 -->
<script language="JavaScript">
	function Timer() {
		setTimeout("locateMain()", 10000);
	}
	function locateMain() {
		location.replace("main.pulu");
	}
</script>

<!-- 선민: 카운트다운 함수 -->
<script language='javascript'>
	count = 5; // 시간 설정
	function countdown() {
		if (count == 0) { // 남은시간이 0일 경우 이동
			locateMain();
		} else {
			// 시간이 남았을 경우 시간 1초씩 감소
			document.getElementById("joinOKDiv").style.fontSize = "15px"; // joinOKDiv에 속한 폰트 사이즈 15px
			document.all.joinOKDiv.innerHTML = count + "초 후 메인페이지로 이동합니다.";
			setTimeout("countdown()", 1000);
			count--;
		}
	}
</script>
</head>

<div style="text-align: center">
<br>
<br>
<br>

	<h1>회원가입을 환영합니다!</h1>
	<body onLoad="Timer();">
		<div id="joinOKDiv"></div>
		<script>countdown();</script>
	</body>
</div>
<div style="text-align: center; margin-top: 30px">
	<button type="button" onclick="location.href='loginForm.pulu';">로그인</button>
	<button type="button" onclick="location.href='main.pulu';">메인페이지</button>
</div>
</html>