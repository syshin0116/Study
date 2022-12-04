<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>

<!-- 선아: 아이디 찾기 폼 -->
<script>

if('${message}' != ""){
	alert('${message}');
}

function findform_check() {
	
var name = document.getElementById("NAME");
var email = document.getElementById("EMAIL");

if (name.value == "") {
    alert("이름을 입력하세요.");
    name.focus();
    return false;
  };
  if (email.value == "") {
    alert("이메일 주소를 입력하세요.");
    email.focus();
    return false;
  };
  document.find_form.submit(); //find_form의 submit 찾아서 감
}
</script>

<title>아이디 찾기</title>
</head>
<body>

	<div style="padding: 30px 10px 10px 10px;">
		<center>
			<h1>아이디 찾기</h1>
			<h4>아이디를 잊어버리셨나요?</h4>
			<h4>이메일을 통해 아이디 찾기가 가능합니다.</h4>
		</center>
		<br>
		<hr width="30%" color="grey">
		<br>
		<form name="find_form" action="findIdForm2.pulu" method="post">
			<!-- 아이디찾기 완료창으로 액션 넣어줌 -->
			<br> <br>
			<center>

				<div class="input-box">
					<label for="username">이름 </label> &nbsp;&nbsp; <input type="text"
						id="NAME" name="NAME" placeholder="userName"
						style="width: 300px; height: 30px; font-size: 10px;"> <br>
					<br>

					<div class="input-box">
						<label for="EMAIL">EMAIL</label>&nbsp; <input type="text"
							id="EMAIL" name="EMAIL" placeholder="email"
							style="width: 300px; height: 30px; font-size: 10px;" />
					</div>
					<br> <br> <input type="submit" name="submit"
						class="insert_bt" value="작성완료" onclick="findform_check()"> 
						<br> <br> <br> <br> <br>
				</div>
	</div>
	</form>
	</center>
	</div>
</body>
</html>