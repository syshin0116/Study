<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>마이 페이지</title>

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

<h2><strong>${loginName}회원님</strong> 등급은 ${loginGrade} 입니다.</h2>
</div>

<div>

<div id="container">
<div id="aside">
<br>
<br>
<br>

        <div>
          <h2><a href=".pulu"><span style="color:black">주문/결제 내역</h2></a>
        </div>
<br>
        <div>
           <h2><a href=".pulu"><span style="color:black">후기 작성</h2></a>

        </div>
<br>
        <div>
          <h2><a href=".pulu"><span style="color:black">QNA</h2></a>
        </div>
<br>
        <div>
          <h2><a href="deleteForm.pulu"><span style="color:black">회원탈퇴</h2></a>
        </div>
        <br>
        <div>
          <h2><a href=".pulu"><span style="color:black">정보 수정</h2></a>
        </div>
        <br>
  </div>
  <br>
  <br>
  
  <div id="contents">
  <h2>주문/결제 내역</h2> <h4>최근 30일 내에 주문하신 내역입니다.</h4>
  <br>

  주문 처리 현황(최근 3개월) 
  <br>
  <br>
    <div class="parent">
    <div class="child">
    <center><h4>
      입금전 
      <br><br>
      0
      </center>
    </div>
  
      <div class="child">
      <center><h4>
      배송준비중 
      <br><br>
      0
      </center>
    </div>
    
     <div class="child">
    <center><h4>
      배송중
      <br><br>
      0
      </center>
    </div>
    
    <div class="child">
    <center><h4>
      배송완료
      <br><br>
      0
      </center>
    </div>
  </div>
  
  <br> 
  
  <hr width = "90%" color = "grey">
  
  <div style = "padding: 40px 100px 10px 100px;">
  
  <button class="buttons">
  <h4>일반주문</h4></button>
  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
  <button class="buttons">
  <h4>정기결제</button></h4>
  
  

  </div>

  </div>
  </div>
  </div>
  </div>
</body>
</html>