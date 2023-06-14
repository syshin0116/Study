<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<script>
function checkId() {
if(${idCount}==0){
	alert("${checkId} 는 사용가능한 아이디입니다.");
	history.go(-1);
	return true;
}
if(${idCount}!=0){
	alert("해당 아이디는 사용하실수 없습니다.");
	window.close();
	return false;
}
}
checkId();
</script>
<meta charset="UTF-8">
<title>중복확인</title>
</head>
<body>

</body>
</html>