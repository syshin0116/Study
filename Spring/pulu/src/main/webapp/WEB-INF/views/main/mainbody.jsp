<%@ page language="java" pageEncoding="UTF-8"%>

<!-- 배너와 상품리스트는 이미지 더미데이터를 넣어뒀습니다. -->
<!-- 더미데이터는 클래스 이름을 dummy로 설정해 두었으니 작업이 끝나면 바꿔주세요. -->
<div class="content">					<!-- 전체 -->
	<div class="topmainBanner">			<!-- 탑 메인배너 --> 
		<div class="topSlclasseBanner">	<!-- 슬라이드배너 -->
			<div class="slclasseBanner">
				<a href="main.jsp"><img class="dummy" src="./resources/img/banner/2.jpg" width="1920" height="400"></a>
			</div>
		</div>
	</div>
	<div class="topsubBannerAll">		<!-- 슬라이드배너 아래쪽 서브배너 -->
		<div class="topsubBanner">
				<a class="topsubBannerinsert" href="main.jsp"><img class="dummy" src="./resources/img/banner/sub/1.jpg"></a>
				<a class="topsubBannerinsert" href="main.jsp"><img class="dummy" src="./resources/img/banner/sub/2.jpg"></a>
		</div>
	</div>
	<div class="mainEventCon">			<!-- 상품리스트 배너 -->
		<div class="mainEventBanner">
			<a href="main.jsp"><img class="dummy" src="./resources/img/banner/event/1.jpg" width="1285"></a>
		</div>
		<div class="EventContent">		<!-- 상품리스트 -->
			<img class="dummy" src="./resources/img/test1.jpg" width="1285">
		</div>
	</div>
</div>
