<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��й�ȣ ã��</title>
</head>
<body>
<div id="memberInfo">
<div id="findIdPwd_result" style="text-align: center;">
<br>
<br>
<h2>��й�ȣ ã��</h2>
<br>
<hr width="40%" color="grey">
<br>
<h4>ȸ������ ��й�ȣ�� ${findPw.PASSWORD} �Դϴ�.</h4>
<h4><strong>${findId.NAME}</strong> ���� ��ſ� ���� �ϼ���!</h4>

<br>
<a href="loginForm.pulu"><span style="color:black">�α��� �������� ���ư���</a>
</div>
</div>
</body>
</html>