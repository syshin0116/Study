<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���� ������</title>

<style>
*{margin:0; padding:0;}

body {
font:20px
}

#wrap {
width:1200px;
height:700px;
margin:0 auto;
}

#header{
padding: 60px 100px 10px 1px;
}
#container {
height:500px;
}
#aside {
width:300px;
height:700px;
float:left;
}
#contents {
width:900px;
height:700px;
float:left;
}
#footer{
height:100px
clear:both;
}
.child {
  padding: 50px;
  margin: 1rem;
  background-color: #ccc;
}
.parent {
  display: flex;
}
.buttons {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
</head>

<body>

<br>
<br>

<div id="wrap">
<div id="header">

<h1>
Mypage</h1>
<br>
<br>

<h2><strong>${loginName}ȸ����</strong> ����� ${loginGrade} �Դϴ�.</h2>
</div>

<div>

<div id="container">
<div id="aside">
<br>
<br>
<br>

        <div>
          <h2><a href=".pulu"><span style="color:black">�ֹ�/���� ����</h2></a>
        </div>
<br>
        <div>
           <h2><a href=".pulu"><span style="color:black">�ı� �ۼ�</h2></a>

        </div>
<br>
        <div>
          <h2><a href=".pulu"><span style="color:black">QNA</h2></a>
        </div>
<br>
        <div>
          <h2><a href="deleteForm.pulu"><span style="color:black">ȸ��Ż��</h2></a>
        </div>
        <br>
        <div>
          <h2><a href=".pulu"><span style="color:black">���� ����</h2></a>
        </div>
        <br>
  </div>
  <br>
  <br>
  
  <div id="contents">
  <h2>�ֹ�/���� ����</h2> <h4>�ֱ� 30�� ���� �ֹ��Ͻ� �����Դϴ�.</h4>
  <br>

  �ֹ� ó�� ��Ȳ(�ֱ� 3����) 
  <br>
  <br>
    <div class="parent">
    <div class="child">
    <center><h4>
      �Ա��� 
      <br><br>
      0
      </center>
    </div>
  
      <div class="child">
      <center><h4>
      ����غ��� 
      <br><br>
      0
      </center>
    </div>
    
     <div class="child">
    <center><h4>
      �����
      <br><br>
      0
      </center>
    </div>
    
    <div class="child">
    <center><h4>
      ��ۿϷ�
      <br><br>
      0
      </center>
    </div>
  </div>
  
  <br> 
  
  <hr width = "90%" color = "grey">
  
  <div style = "padding: 40px 100px 10px 100px;">
  
  <button class="buttons">
  <h4>�Ϲ��ֹ�</h4></button>
  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
  <button class="buttons">
  <h4>�������</button></h4>
  
  

  </div>

  </div>
  </div>
  </div>
  </div>
</body>
</html>