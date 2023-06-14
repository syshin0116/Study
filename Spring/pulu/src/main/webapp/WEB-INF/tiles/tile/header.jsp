<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

</head>
<body>
	<div class="container">

		<div id="logoWrap">
			<div id="Logo">
				<a href="main.pulu"><img src="./resources/Pulu_Logo.png"
					width="110" height="88"></a>
			</div>
		</div>
		<div id="topMainMenuAll">
				<div id="topMenu" style="z-index: 0;">
					<div id="totalMenu">	<!-- 탑 메인바 '전체 카테고리' -->
						<a class="menuLink" href=""> 전체 카테고리 </a>
					</div>
					<div id="totalSide">	<!-- 탑 메인바 왼쪽 '전체 카테고리' 마우스 올릴 시 나올 창 -->
						<div id="total_Category"></div>
					</div>
					<div id="secondMenu">	<!-- 탑 메인바 중앙 카테고리 -->
						<ul id="menu_Categori">
							<li class="topMenuLi"><a class="menuLink" href="goodslist.pulu">샐러드<span class="circle"></span></a></li>
							<li class="topMenuLi"><a class="menuLink" href="">샌드위치</a></li>
							<li class="topMenuLi"><a class="menuLink" href="">간식/음료</a></li>
							<li class="topMenuLi"><a class="menuLink" href="">정기배송</a></li>
							<li class="topMenuLi"><a class="menuLink" href="">영양정보</a></li>
							<li class="topMenuLi"><a class="menuLink" href="">매장</a></li>
						</ul>
					</div>

					<div id="thirdMenu">	<!-- 탑 메인바 오른쪽 로그인 메뉴 -->
						<ul id="loginUl">
						
						<c:choose>
							<c:when test="${loginStatus == null}">	<!-- 로그인하지 않았을 때 -->
								<li><a class="loginLink" href="loginForm.pulu">로그인</a></li>
								<li><a class="loginLink" href="joinForm.pulu">회원가입</a></li>
							</c:when>
							<c:otherwise>

								<c:if test="${loginGrade == 'Admin'}">	<!-- 로그인한 사람이 관리자일 때 -->
									<li><strong>${loginName }</strong>님 </li> 
									<li><a class="loginLink" href="logout.pulu">로그아웃</a></li>
									<li><a class="loginLink" href="adminMain.pulu">관리자페이지</a></li>
								</c:if>
								<c:if test="${loginGrade != 'Admin'}">	<!-- 로그인한 사람이 관리자가 아닐 때 -->
									<li><strong>${loginName }</strong>님 </li> 
									<li><a class="loginLink" href="logout.pulu">로그아웃</a></li>
									<li><a class="loginLink" href="myPage.pulu">마이페이지</a></li>
								</c:if>
							</c:otherwise>
						</c:choose>
						<li><a class="loginLink" href="">고객센터</a></li>
							<li><a class="loginLink" href=""><img scr=""/></a></li>
							<li><a class="loginLink" href=""><img scr=""/></a></li>
						</ul>
						

					</div>
				</div>
			</div>
		

	</div>
</body>
</html>
