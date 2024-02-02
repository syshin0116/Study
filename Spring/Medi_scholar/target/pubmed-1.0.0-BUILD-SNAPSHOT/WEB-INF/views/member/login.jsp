<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://fonts.cdnfonts.com/css/dinpro-medium" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/member.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
	<!-- pageContext: 현재 요청에 대한 페이지 컨텍스트를 참조합니다. 주소창에 기재되어있는 정보
		 contextPath: 현재 웹 애플리케이션의 컨텍스트 루트 경로를 나타냅니다. 예를 들어, 웹 애플리케이션이 http://localhost:8080/pubmed 에서 실행되고 있다면, contextPath는 /pubmed 임 -->
	<title>Login</title>
</head>
<body>
	<div class="login-container">
		<section>
			<main>
				<a href="/" class="logo a-c ma-b-10"><img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="로고염" /></a>
			    <% if(Boolean.TRUE.equals(session.getAttribute("signUpSuccess"))) { %>
				    <p class="sign-up-success ma-t-10 ma-b-10 col-robl a-c"><strong>Membership registration successful!</strong></p>
				    <% session.removeAttribute("signUpSuccess"); %>
				<% } %>
                 <button name="button" type="submit" class="btn btn-2" data-auth-action="Sign In" onclick="redirectToGoogle()">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" fill="none" role="img" class="icon-google">
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M17.64 9.20419C17.64 8.56601 17.5827 7.95237 17.4764 7.36328H9V10.8446H13.8436C13.635 11.9696 13.0009 12.9228 12.0477 13.561V15.8192H14.9564C16.6582 14.2524 17.64 11.9451 17.64 9.20419Z" fill="#4285F4"></path>
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M8.99976 18C11.4298 18 13.467 17.1941 14.9561 15.8195L12.0475 13.5613C11.2416 14.1013 10.2107 14.4204 8.99976 14.4204C6.65567 14.4204 4.67158 12.8372 3.96385 10.71H0.957031V13.0418C2.43794 15.9831 5.48158 18 8.99976 18Z" fill="#34A853"></path>
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M3.96409 10.7098C3.78409 10.1698 3.68182 9.59301 3.68182 8.99983C3.68182 8.40664 3.78409 7.82983 3.96409 7.28983V4.95801H0.957273C0.347727 6.17301 0 7.54755 0 8.99983C0 10.4521 0.347727 11.8266 0.957273 13.0416L3.96409 10.7098Z" fill="#FBBC05"></path>
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M8.99976 3.57955C10.3211 3.57955 11.5075 4.03364 12.4402 4.92545L15.0216 2.34409C13.4629 0.891818 11.4257 0 8.99976 0C5.48158 0 2.43794 2.01682 0.957031 4.95818L3.96385 7.29C4.67158 5.16273 6.65567 3.57955 8.99976 3.57955Z" fill="#EA4335"></path>
                    </svg>
                    Sign in with Google
                </button>
                <div class="hr-txt ma-t-20 ma-b-15">or sign in with email</div>

                <div>
                    <form action="/member/login" method="post">
                        <div class="form-fields">
                            <fieldset>
                                <label for="email">Your Email</label>
                                <input type="text" name="email" id="email" tabindex="1" placeholder="Email" class="text-input" autocorrect="off" autocapitalize="off" />
                            </fieldset>
                    
                            <fieldset>
                                <label for="password" class="pw">Password <a href="#">Forgot?</a></label>
                                <input type="password" name="pass" id="password" tabindex="2" placeholder="Password" class="text-input" />
                            </fieldset>
                        </div>
                        
                        <% if(request.getAttribute("errorMessage") != null) { %>
						    <p class="error-message ma-t-10 pa-l-5 col-crim a-c"><%= request.getAttribute("errorMessage") %></p>
						<% } %>
						
                        <input class="btn btn-1 ma-t-15 ma-b-10" type="submit" value="LOGIN" tabindex="3" />
                    </form>
                    <p class="a-c">
                        Don't have an account? 
                        <a class="underline" href="/Member/SignUp">Sign up</a>
                    </p>
                </div>
            </main>
        </section>

    </div>
	
</body>
</html>