<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/file/upload" method="post" enctype="multipart/form-data">
		文件:<input type="file" name="img" />
		<input type="submit" value="上传" />
	</form>
	${msg }
</body>
</html>