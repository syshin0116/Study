<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

   		<!-- 헤더 -->
    	<jsp:include page="../common/header.jsp" />
    	
        <section class="content">

            <div class="tit-con clear">
                <h2 class="f-l col-navy">Community</h2>
                <div class="f-r ma-t-5">
                    <a href="/">Home</a> > Community
                </div>
            </div>

            <div class="con-reg ma-t-10">
                <form action="" method="post">
                    <div class="form-fields">
                        
                        <fieldset>
                            <label for="title">Title</label>
                            <input type="text" name="title" id="title" tabindex="1" placeholder="Title" class="text-input" autocorrect="off" autocapitalize="off" />
                        </fieldset>

                        <fieldset>
                            <label for="content">Content</label>
                            <textarea name="content" id="content" tabindex="2" class="text-input" placeholder="Text here."></textarea>
                        </fieldset>
                    </div>
                    <div class="a-c ma-t-15 ma-b-10">
                        <input class="btn btn-write ma-r-5" type="submit" value="Write" tabindex="3" />
                        <input class="btn btn-cancel" type="button" value="Cancel" tabindex="4" onclick="history.back();" />
                    </div>
                </form>
            </div>
        </section>
        
    	<jsp:include page="../common/footer.jsp" />