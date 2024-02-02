<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="../resources/css/global.css" />
	<link rel="stylesheet" type="text/css" href="../resources/css/content.css" />
    <link href="https://fonts.cdnfonts.com/css/dinpro-medium" rel="stylesheet">
	<title></title>
</head>
<body>
	
	<% 
	    String email = (String) session.getAttribute("email");
	%>
    <div class="main-container">

		<header>
	       <div class="top">
	           <div class="menu">
	               <a href="/" class="">
	                   <svg class="w-[48px] h-[48px] text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 17 14">
	                       <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M1 1h15M1 7h15M1 13h15"/>
	                   </svg>
	               </a>
	           </div>
	           <div class="logo"><a href="/"><img src="resources/images/logo_main.png" alt="" /></a></div>
	           <div class="top-search">
	               <input type="text" class="text-search" />
	               <input type="button" class="btn-search" value="Find Keyword" />
	           </div>
	           <div class="gnb">
	               <ul class="clear">
		               <% if(email == null) { // 로그인이 되어있지 않은 경우 %>
		                   <li><a href="/Member/Login">Login</a></li>
						   <li><a href="/Member/SignUp">SignUp</a></li>
					   <% } else { // 로그인이 된 경우 %>
						   <li><a href="/Member/Logout">Logout</a></li>
						   <li><a href="/Member/Modify">Modify</a></li>
				       <% } %>
	               </ul>
	           </div>
	       </div>
	       <nav>
	           <ul class="clear">
	               <li><a href="/Journal" class="active">Journal</a></li>
	               <li><a href="/Pubmed">Pubmed</a></li>
	               <li><a href="/Stat">Statistics</a></li>
	               <li><a href="/community/list">Community</a></li>
	           </ul>
	       </nav>
	    </header>