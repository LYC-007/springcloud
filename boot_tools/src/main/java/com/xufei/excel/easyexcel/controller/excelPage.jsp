<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>测试excel文件下载</title>
</head>
<body>
<h3>点击下面链接, 进行excel文件下载</h3>
<a href="<c:url value='/xlsx/downloadExcel'/>">Excel文件下载</a>
<hr/>
<hr/>
<h3>点击下面按钮, 进行excel文件上传</h3>
<form action="<c:url value='/xlsx/uploadExcel'/>" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/><br/>
    <input type="submit" value="上传Excel"/>
</form>
</body>

</html>
