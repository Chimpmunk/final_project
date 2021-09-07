<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>hello, ${user.login}</h1>


<a href="/request-list">request list</a>

<c:if test="${user.role=='manager'}">
    <a href="/add-repairman.jsp">add repairman</a>
</c:if>

</body>
</html>
