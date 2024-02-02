<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

   		<!-- 헤더 -->
    	<jsp:include page="../common/header.jsp" />
    	
        <section class="content">

            <div class="tit-con clear">
                <h2 class="f-l col-navy">Journal List</h2>
                <div class="f-r ma-t-5">
                    <a href="/">Home</a> > Journal List
                </div>
            </div>

            <div class="con-search clear">
                <form action="" method="get">
                    <div class="con-search-form">
                        <div class="clear">
                            <select class="dominimal f-l">
                                <option value="">Select Journal</option>
                            </select>
                            <label for="SearchInput"></label>
                            <input type="text" id="SearchInput" name="sjournal" />
                        </div>
                        <div class="clear ma-t-5">
                            <span class="sp1">By Period</span>
                            <input type="radio" class="btn-check check-1" name="speriod" id="Search1Year" autocomplete="off" checked>
                            <label for="Search1Year">1 Year</label>
    
                            <input type="radio" class="btn-check" name="speriod" id="Search6Month" autocomplete="off">
                            <label for="Search6Month">6 Month</label>
    
                            <input type="radio" class="btn-check" name="speriod" id="Search3Month" autocomplete="off">
                            <label for="Search3Month">3 Month</label>
    
                            <input type="radio" class="btn-check" name="speriod" id="Search1Month" autocomplete="off">
                            <label for="Search1Month">1 Month</label>
    
                            <input type="radio" class="btn-check perd" name="speriod" id="SearchPeriod" autocomplete="off">
                            <label for="SearchPeriod">Period</label>

                            <!-- Period 선택시 disabled 사라지고 활성화 -->
                            <input type="date" name="" class="dateInput" disabled />
                            <span class="sp2">~</span>
                            <input type="date" name="" class="dateInput" disabled />
                        </div>
                    </div>
                    <input type="submit" value="Search" class="btn-con-search" />
                </form>
            </div>

            <div class="con-tbl">
                <div class="tbl-scr">
                    <table>
                    <thead>
                        <tr>
                            <th rowspan="2">No</th>
                            <th rowspan="2">ISSN / EISSN</th>
                            <th rowspan="2">Journal</th>
                            <th colspan="3">JSR</th>
                            <th colspan="2">SClmago</th>
                            <th rowspan="2">Journal Impact Index</th>
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
		                <c:forEach var="journal" items="">
		                    <tr>
		                        <td></td>
		                        <td><a href="#"></a> / <a href="#"></a></td>
		                        <td class="a-l"><a href="JournalDetail?id=">}</a></td>
		                        <td>O</td>
	                            <td>Q1</td>
	                            <td>X</td>
	                            <td>O</td>
	                            <td>Q1</td>
	                            <td>7.7</td>
	                            <td><a href="#">3,001</a></td>
		                        <%-- <td>${journal.sci}</td>
		                        <td>${journal.level}</td>
		                        <td>${journal.esci}</td>
		                        <td>${journal.scimagoSci}</td>
		                        <td>${journal.scimagoSLevel}</td>
		                        <td>${journal.journalImpactIndex}</td>
		                        <td><a href="#">${journal.articleCount}</a></td> --%>
		                    </tr>
		                </c:forEach>
		            </tbody>
                    </table>
                </div>

                <div class="paging ma-t-20">
                    <ul class="pagination">
                        <li>
                            <a href="#" class="arrow">
                                <svg class="ic-arrow" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 8 14">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 1 1.3 6.326a.91.91 0 0 0 0 1.348L7 13"></path>
                                </svg>
                            </a>
                        </li>
                        <li><a href="#" class="active">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li>
                            <a href="#" class="arrow">
                                <svg class="ic-arrow" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 8 14">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 13 5.7-5.326a.909.909 0 0 0 0-1.348L1 1"/>
                                </svg>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </section>
        
    	<jsp:include page="../common/footer.jsp" />