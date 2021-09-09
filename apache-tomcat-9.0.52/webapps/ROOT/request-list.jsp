<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${user.role=='manager'}">
    form
</c:if>
<c:forEach items="${requests}" var="req">
    <h4>${req.time}</h4>
    <h4>${req.title}</h4>
    <h4>${req.status}</h4>
    <p>${req.description}</p>
    <a href="/request-list/display?request_id=${req.id}">open</a>
</c:forEach>
</body>
</html>
