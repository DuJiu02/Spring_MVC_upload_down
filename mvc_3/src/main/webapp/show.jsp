<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<img alt="" src="${pageContext.request.contextPath }/upload/${fileName }" width="200" height="200">
	
	<a href="${pageContext.request.contextPath }/file/down?name=${fileName }">下载</a>
</body>
</html>