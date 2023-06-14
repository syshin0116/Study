<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 헤더 공통영역 -->
<%@ include file="../includes/header.jsp" %>
<!-- 메뉴 공통영역 -->
<%@ include file="../includes/menu.jsp" %>
</head>
	<style>
	body {
		padding-top: 70px;
		padding-bottom: 30px;
	}
	.board_title {
		font-weight : 700;
		font-size : 22pt;
		margin : 10pt;
	}
	.board_info_box {
		color : #6B6B6B;
		margin : 10pt;
	}
	.board_author {
		font-size : 10pt;
		margin-right : 10pt;
	}
	.board_date {
		font-size : 10pt;
	}
	.board_content {
		color : #444343;
		font-size : 12pt;
		margin : 10pt;
	}
	</style>
	<title>게시판 상세조회</title>
</head>
<script type="text/javascript">
	function goList(){
		location.href="/board/list";	
	}

	function goDelete(){
		var tbSeq = '<c:out value="${boardVo.tbSeq}"/>';
		location.href="/board/delete?tbSeq="+tbSeq;
	}

	function goModify(){
		var tbSeq = '<c:out value="${boardVo.tbSeq}"/>';
		location.href="/board/write?tbSeq="+tbSeq;
	}
	</script>
<!-- 컨텐츠 영역 -->
 <main>
	<article>
		<h2>게시판 상세조회</h2>
		<div class="bg-white rounded shadow-sm">
			<div class="board_title"><c:out value="${boardVo.tbTitle}"/></div>
			<div class="board_info_box">
				<span class="board_author"><c:out value="${boardVo.tbRegId}"/>,</span>
				<span class="board_date"><c:out value="${boardVo.tbRegDt}"/></span>
			</div>
			<div class="board_content">${boardVo.tbContent}</div>
		</div>
		<div style="margin-top : 20px">
			<!-- 로그인한 사용자만 수정,삭제 버튼이 노출된다 -->
			<c:if test="${!empty sessionScope.loginUserData}">
					<button type="button" class="btn btn-sm btn-primary" onclick="javascript:goModify();">수정</button>
					<button type="button" class="btn btn-sm btn-primary" onclick="javascript:goDelete();">삭제</button>
		   	</c:if>
			<button type="button" class="btn btn-sm btn-primary" onclick="javascript:goList();">목록</button>
		</div>
	</article>
</main>
<!-- 컨텐츠 영역 끝-->
<!-- 풋터 공통영역 -->
<%@ include file="../includes/footer.jsp" %>

