<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String oid = request.getParameter("oid");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/upload" method="post"  enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="提交">
</form>
</body>
</html>
