<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>비밀번호 찾기</title>
</head>
<body>
<div id="memberInfo">
<div id="findIdPwd_result" style="text-align: center;">
<br>
<br>
<h2>비밀번호 찾기</h2>
<br>
<hr width="40%" color="grey">
<br>
<h4>회원님의 비밀번호는 ${findPw.PASSWORD} 입니다.</h4>
<h4><strong>${findId.NAME}</strong> 고객님 즐거운 쇼핑 하세요!</h4>

<br>
<a href="loginForm.pulu"><span style="color:black">로그인 페이지로 돌아가기</a>
</div>
</div>
</body>
</html>