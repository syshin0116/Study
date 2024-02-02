<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 헤더 -->
<jsp:include page="../common/header.jsp" />

<section class="content">

	<div class="tit-con clear">
		<h2 class="f-l col-navy">Community</h2>
		<div class="f-r ma-t-5">
			<a href="/">Home</a> > Community
		</div>
	</div>
	<form action="/community/list" method="get">
		<div class="list-search clear ma-t-15">
			<div class="f-r">
				<div class="clear">
					<select class="dominimal f-l" name="search">
						<option ${(param.search=="title")?"selected":""} value="title">Title</option>
						<option ${(param.search=="user")?"selected":""} value="user">Writer</option>
					</select> <label for="SearchInput"></label> <input type="text"
						id="SearchInput" name="input" /> <input type="button"
						value="Search" class="btn-con-search" />
				</div>
			</div>
	</form>
	<div class="f-l">
		Total : <strong>78</strong> posts
	</div>
	</div>

	<div class="con-list ma-t-5">
		<ul>
			<c:forEach var="cl" items="${cl}">
				<li><a class="clear" href="/community/detailcomm">
						<div class="list-no">
							<strong>${cl.postid}</strong>
						</div>
						<div class="list-tit nowrap"><a href="/community/detailcomm?postid=${cl.postid}">${cl.title} / 내용 : ${cl.memo}</a></div>
						<div class="list-writer">${cl.user_nm}</div>
						<div class="list-etc">
							<span>Comment : <strong>21</strong></span> <span class="sep">|</span>
							<span>Like : <strong>104</strong></span> <span class="sep">|</span>
							<span>Hit : <strong>50</strong></span> <span class="sep">|</span>
							<span>Date : <strong>${cl.regdate}</strong></span>
						</div>
				</a></li>
			</c:forEach>

			<div class="ma-t-10 clear">
				<button class="btn btn-1 f-r"
					onclick="location.href='/community/writecomm'">Write</button>
			</div>

			<div class="paging ma-t-10">
				<ul class="pagination">
					<li><a href="#" class="arrow"> <svg class="ic-arrow"
								aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
								fill="none" viewBox="0 0 8 14">
                                <path stroke="currentColor"
									stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
									d="M7 1 1.3 6.326a.91.91 0 0 0 0 1.348L7 13"></path>
                            </svg>
					</a></li>
					<li><a href="#" class="active">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#" class="arrow"> <svg class="ic-arrow"
								aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
								fill="none" viewBox="0 0 8 14">
                                <path stroke="currentColor"
									stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
									d="m1 13 5.7-5.326a.909.909 0 0 0 0-1.348L1 1" />
                            </svg>
					</a></li>
				</ul>
			</div>
</section>

<jsp:include page="../common/footer.jsp" />

