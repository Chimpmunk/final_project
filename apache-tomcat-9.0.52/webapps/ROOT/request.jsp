<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/request" method="post">
    <input type="text" name="title">
    <input type="text" name="description">
    <input type="submit" value="send request">
</form>
</body>
</html>
