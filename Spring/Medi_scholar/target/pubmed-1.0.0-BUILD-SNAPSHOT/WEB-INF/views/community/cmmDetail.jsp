<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 헤더 -->
<jsp:include page="../common/header.jsp" />
<script type="text/javascript" src=/resources/js/reply.js></script>

	<h1>글번호 : ${cd.postid}</h1>
<section class="content">

	<div class="tit-con clear">
		<h2 class="f-l col-navy">Journal</h2>
		<div class="f-r ma-t-5">
			<a href="/">Home</a> > 
			<a href="/Journal">Journal List</a> > Journal
			Detail</a>
		</div>
	</div>
	<div class="con-tbl ma-t-15">
		<div class="tbl-scr" href="/community/detailcomm">
			<table class="tbl-1">
				<tr>
					<th>CommunityTitle</th>
					<td colspan="3">${cd.title}</td>
				</tr>
				<tr>
					<th>CommunityMemo</th>
					<td>${cd.memo}</td>
				</tr>
				<tr>
					<th>CommunityWriter</th>
					<td>${cd.user_nm}</td>
				</tr>
				<tr>
					<th>CommunityRegdate</th>
					<td>${cd.regdate}</td>
				</tr>
				<tr>
					<th>CommunityLastdate</th>
					<td>${cd.lastdate}</td>
				</tr>
			</table>
		</div>
  
  		<div class="ma-t-10 clear">
				<button class="btn btn-1 f-r"
					onclick="location.href='/community/updatecomm?postid=${cd.postid}'">Udate</button>
		</div>
<		<div id="replydiv">
    <form id="replyForm">
        <textarea name="replyContent" id="replyContent" rows="4" cols="50"></textarea>
        <input type="hidden" class="postidhidden" name="postid" value="${postid}">
        <input type="button" value="Reply" onclick="submitReply()">
    </form>
    <table border="1">
        <c:forEach var="rl" items="${rl}">
            <tr>
                <td class="a-c">${rl.reno}</td>
                <td class="a-c">${rl.userno}</td>
                <td class="a-c">${rl.usernm}</td>
                <td class="a-c" onclick="toggleReplyInput(${rl.reno})">${rl.rememo}</td>
                <td class="a-c">${rl.regdate}</td>
                  <td>  
                    <form id="replymodify">
                        <input type="hidden" class="renohidden" name="renoh" value="${rl.reno}"> 
                        <input type="hidden" class="usernohidden" name="usernoh" value="${rl.userno}"> 
                        <c:if test="${userNo eq rl.userno || userNo eq 1 }">
                            <input type="button" value="삭제" onclick="deleteReply(${rl.reno},${rl.userno})">
                        </c:if>
                    </form>
                <tr id="replyInput-${rl.reno}" style="display: none;">
               	<td colspan="5">
               		<input type="hidden" id="userId" value="${userId}" />
                    <textarea name="subreplyContent" id="subreplyContent" rows="2" cols="50"></textarea>
                    <input type="button" value="대댓글 작성" onclick="submitSubreply(${rl.reno})">
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
		<jsp:include page="../common/footer.jsp" />