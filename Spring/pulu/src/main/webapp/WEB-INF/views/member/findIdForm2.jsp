<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���̵� ã�� ���</title> <!-- ����: ���̵� ã�� ��� â -->

<div id="memberInfo">
		<div id="findIdPwd_result" style="text-align: center;">
			<br> <br>

			<center><h2 class="desc">���̵� ã�� </h2></center>
			<br>
			<hr width="40%" color="grey">
			<div id="find_Success">
			
					<br>
					<h4>������ ���̵�� ${findId.ID} �Դϴ�</h4>
					<h4><strong>${findId.NAME}</strong> ���� ��ſ� ���� �ϼ���!</h4>
					
			</div>
				<br>
				<a href="loginForm.pulu"><span style="color:black"><h4>�α������� ���ư���</h4></span></a>
				<a href="findPwForm.pulu"><span style="color:black"><h4>��й�ȣ ã��</h4></span></a>
			</div>
		</div></html>