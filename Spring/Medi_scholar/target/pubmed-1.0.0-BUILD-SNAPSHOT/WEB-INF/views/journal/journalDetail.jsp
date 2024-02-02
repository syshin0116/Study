<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 하나에 집중 둘에 하자! -->
   		<!-- 헤더 -->
    	<jsp:include page="../common/header.jsp" />
    	
        <section class="content">

            <div class="tit-con clear">
                <h2 class="f-l col-navy">Journal</h2>
                <div class="f-r ma-t-5">
                    <a href="/">Home</a> >
                    <a href="/Journal">Journal List</a> > Journal Deatil</a>
                </div>
            </div>

            <div class="con-tbl ma-t-15">
                
                <div class="tbl-scr">
                    <table class="tbl-1">
                    <tr>
                        <th>Journal</th>
                        <td colspan="3">${detail.journal_nm}</td>
                    </tr>
                    <tr>
                        <th>Journal URL</th>
                        <td>${detail.journal_url}</td>
                        <th>Journal Mesh</th>
                        <td>${detail.journal_mesh}</td>
                    </tr>
                    <tr>
                        <th>Journal Country</th>
                        <td>${detail.journal_country}</td>
                        <th>Journal Language</th>
                        <td>${detail.journal_language}</td>
                    </tr>
                    <tr>
                        <th>ISSN</th>
                        <td>${detail.issn}</td>
                        <th>EISSN</th>
                        <td>${detail.eissn}</td>
                    </tr>
                    <tr>
                        <th colspan="4">등재정보</th>
                    </tr>
                    </table>
                </div>
                    
                <div class="tbl-scr">
                    <table class="ma-t-10">
                    <thead>
                        <tr>
                            <th colspan="3">JSR</th>
                            <th colspan="2">SClmago</th>
                            <th rowspan="2">Journal<br />Impact Index</th>
                            <th rowspan="2">Article</th>
                        </tr>
                        <tr>
                            <th>SCI</th>
                            <th>Level</th>
                            <th>ESCI</th>
                            <th>SCI</th>
                            <th>SLevel</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>O</td>
                            <td>Q1</td>
                            <td>X</td>
                            <td>O</td>
                            <td>Q1</td>
                            <td>7.7</td>
                            <td><a href="#">3,001</a></td>
                        </tr>
                    </tbody>
                    </table>
                </div>
            </div>
        </section>
        
    	<jsp:include page="../common/footer.jsp" />