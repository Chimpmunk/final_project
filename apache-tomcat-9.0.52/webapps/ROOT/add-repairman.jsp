<%--
  Created by IntelliJ IDEA.
  User: Jim
  Date: 02.09.2021
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="fragment/header2.jsp"%>

<form action="/add-repairman" method="post">
    <input type="text" name="login" pattern="(?=.*[a-z\dA-Z_]).{3,}" required>
    <input type="password" name="password" pattern="(?=.*[a-z\dA-Z]).{8,}" required>
    <input type="submit" value="add repairman">
</form>
</body>
</html>
