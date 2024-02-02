<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

<script language="javascript">
	function checkLoginForm() {
		var id = document.getElementById("id");
		var password = document.getElementById("password");

		/* alert(${message}); */
		if (id.value == "") {
			alert("아이디를 입력하세요.");
			uid.focus();
			return false;
		}
		if (password.value == "") {
			alert("비밀번호를 입력하세요.");
			uid.focus();
			return false; 
		}
	}
</script>
 
</head>
<body>

<div style = "padding: 80px 100px 10px 100px;">
		
	<form id="login_Form" method="post" action="login.pulu" onsubmit="return checkLoginForm()">
		<div id="login_id" style="text-align: center">
		
			<div>
				<h1>로그인</h1>
				<hr width="24%" color="grey" align="center"><br/><br/>
			</div>
			<div id="join_Title"></div>
			<span class="insertbox"> 
			<input type="text" name="ID" id="id" class="insert" maxlength="20" placeholder="아이디" style="width:300px;height:30px;font-size:10px;">
		</span>
		</div>
		<br/>
		<div id="login_password" style="text-align: center">
			<div id="join_Title"></div>
			<span class="insertbox"> 
			<input type="password" name="PASSWORD" id="password" class="insert" maxlength="20" placeholder="비밀번호" style="width:300px;height:30px;font-size:10px;">
			</span>
		</div>
		<br/>
		<br/>
		<div id="login_button" style="text-align: center">
			<input type="submit" name="SUBMIT" class="insert_bt" value="로그인" color="green" style="width:300px;height:30px;font-size:11px;">
		</div>
		<div style = "padding: 16px 0px 0px 0px; text-align: center" >
		<a href="findIdForm.pulu"><span style="color:black">아이디 찾기</span></a> &nbsp;|&nbsp;
		<a href="findPwForm.pulu"><span style="color:black">비밀번호 찾기</span></a> &nbsp;| &nbsp;
		<a href="join.pulu"><span style="color:black">회원가입</span></a>
		</div>
	</form>
</div>
</body> 
</html>