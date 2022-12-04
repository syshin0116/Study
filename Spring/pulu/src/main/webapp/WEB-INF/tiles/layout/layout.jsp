<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/style.css'/>" />
<meta charset="UTF-8">
<title>PuluDev</title>
</head>
<body>
<div>
<tiles:insertAttribute name="header" />
</div>


<div style="height:auto;">
<tiles:insertAttribute name="body" />
</div>

<div>
<tiles:insertAttribute name="footer" />
</div>
</body>
</html>