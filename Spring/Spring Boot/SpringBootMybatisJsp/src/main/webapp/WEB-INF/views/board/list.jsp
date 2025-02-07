<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 헤더 공통영역 -->
<%@ include file="../includes/header.jsp" %>
<!-- 메뉴 공통영역 -->
<%@ include file="../includes/menu.jsp" %>
<!-- 컨텐츠 영역 -->
 <main>
	<div class="table-responsive">
		<table class="table table-striped table-sm">
			<colgroup>
				<col style="width:5%;" />
				<col style="width:auto;" />
				<col style="width:10%;" />
				<col style="width:10%;" />
			</colgroup>
			<thead>
				<tr>
					<th>no</th>
					<th>글제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty list }" >
						<tr><td colspan="4" align="center">데이터가 없습니다.</td></tr>
					</c:when> 
					<c:when test="${!empty list}">
						<c:forEach var="list" items="${list}">
								<tr>
									<td><c:out value="${list.tbSeq}"/></td>
									<td><a href="/board/detail?tbSeq=<c:out value="${list.tbSeq}"/>"><c:out value="${list.tbTitle}"/></a></td>
									<td><c:out value="${list.tbRegId}"/></td>
									<td><c:out value="${list.tbRegDt}"/></td>
								</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
	</div>
	<!-- 로그인한 사용자만 글쓰기 버튼이 노출된다 -->
	<c:if test="${!empty sessionScope.loginUserData}">
		<a href="/board/write"><button type="button" class="btn btn-sm btn-primary" >등록</button></a>
   	</c:if>
</main>
<!-- 컨텐츠 영역 끝-->
<!-- 풋터 공통영역 -->
<%@ include file="../includes/footer.jsp" %>

