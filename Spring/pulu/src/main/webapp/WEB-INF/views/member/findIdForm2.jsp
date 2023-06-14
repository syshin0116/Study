<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>아이디 찾기 결과</title> <!-- 선아: 아이디 찾기 결과 창 -->

<div id="memberInfo">
		<div id="findIdPwd_result" style="text-align: center;">
			<br> <br>

			<center><h2 class="desc">아이디 찾기 </h2></center>
			<br>
			<hr width="40%" color="grey">
			<div id="find_Success">
			
					<br>
					<h4>고객님의 아이디는 ${findId.ID} 입니다</h4>
					<h4><strong>${findId.NAME}</strong> 고객님 즐거운 쇼핑 하세요!</h4>
					
			</div>
				<br>
				<a href="loginForm.pulu"><span style="color:black"><h4>로그인으로 돌아가기</h4></span></a>
				<a href="findPwForm.pulu"><span style="color:black"><h4>비밀번호 찾기</h4></span></a>
			</div>
		</div></html>