<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>

if('${message}' != ""){
	alert('${message}');
}

function findform_check() {
var name = document.getElementById("NAME");
var email = document.getElementById("EMAIL");
var id = document.getElementById("ID");

if (name.value == "") {
    alert("이름를 입력하세요.");
    name.focus();
    return false;
  };
  
if (id.value == "") {
    alert("아이디를 입력하세요.");
    id.focus();
    return false;
  };
  if (email.value == "") {
    alert("이메일 주소를 입력하세요.");
    email.focus();
    return false;
  };

  document.find_form.submit();
}
</script>

<meta charset="EUC-KR">
<title>비밀번호 찾기</title>
</head>
<body>

<div style = "padding: 30px 10px 10px 10px;">
<center>

<h1> 비밀번호 찾기 </h1>
<h4>비밀번호를 잊어버리셨나요?</h4>
<h4>전화번호를 통해 비밀번호 찾기가 가능합니다.</h4>
</center>
<br>
<hr width = "30%" color = "grey">
<br>


<center>
	<form name="find_form" action="findPwForm2.pulu" method="post">
<br>
<br>

		<label for="username">이름</label>&nbsp;&nbsp;&nbsp;
			<input type="text" id="NAME" name="NAME" placeholder="userName" style="width:300px;height:30px;font-size:10px;">
			
			<div class="input-box">
		</div>

<br>
		<label for="userid">아이디</label>&nbsp;&nbsp;
			<input type="text" id="ID" name="ID" placeholder="userID" style="width:300px;height:30px;font-size:10px;"> 
				<div class="input-box">
		</div>
<br>
		<label for="EMAIL">EMAIL</label>&nbsp;&nbsp;
			<input type="text" id="EMAIL" name="EMAIL" placeholder="email" style="width:300px;height:30px;font-size:10px;">
			<div class="input-box">
			<br>
		</div>
		<br>
		<input type="button" value="로그인" onclick="location.href='loginForm'">&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="비밀번호찾기" onclick="findform_check()">
		<br>
		<br>
		<br>
		

	</form>
	</center>
</body>
</html>