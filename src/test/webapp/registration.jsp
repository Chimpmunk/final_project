<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${userLocale==null}">
    <fmt:setLocale value="${defaultLocale}"/>
</c:if>
<c:if test="${userLocale!=null}">
    <fmt:setLocale value="${userLocale}"/>
</c:if>
<fmt:setBundle basename="messages"/>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="fragment/header2.jsp"%>

<div class="container mt-5">
    <form action="/registration" method="post">
        <input type="text" maxlength="45" name="login" pattern="(?=.*[a-z\dA-Z_]).{3,}" required class="form-control mt-2">
        <input type="password" maxlength="45" name="password" pattern="(?=.*[a-z\dA-Z]).{8,}" required class="form-control mt-2">
        <fmt:message key="register.button" var="submitValue"/>
        <input type="submit" name="submit" value="${submitValue}" class="btn btn-success mt-2">
    </form>
</div>

</body>
</html>
