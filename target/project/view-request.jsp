<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>${requestAuthor.login}</h2>
<h4>${req.time}</h4>
<h4>${req.title}</h4>
<h4>${req.status}</h4>
<p>${req.description}</p>
<c:if test="${req.price!=0}">
    <h4>${req.price}</h4>
</c:if>
<c:if test="${req.status=='waiting_for_acceptance' && user.role=='manager'}">
    <form action="/set-price?request_id=${req.id}" method="post">
        <input type="number" name="price">
        <input type="submit" value="set price">
    </form>
</c:if>

<c:if test="${req.status=='waiting_for_payment' && user.role=='manager'}">
    <form action="/refill?user_id=${requestAuthor.id}&request_id=${req.id}" method="post">
        <input type="number" name="value">
        <input type="submit" value="refill account">
    </form>
    <form action="/pay?user_id=${requestAuthor.id}&request_id=${req.id}" method="post">
        <input type="submit">
    </form>
</c:if>

<c:if test="${req.status=='paid' && user.role=='manager'}">
    <form action="/assign?request_id=${req.id}" method="post">
        <select name="repairman">
            <c:forEach items="${repairmanList}" var="r">
                <option value="${r.id}">${r.login}</option>
            </c:forEach>
        </select>
        <input type="submit" value="assign request">
    </form>
</c:if>

<c:if test="${req.status=='assigned' && user.role=='repairman'}">
    <form action="/in-progress?request_id=${req.id}" method="post">
        <input type="submit" value="start work">
    </form>
</c:if>

<c:if test="${req.status=='in_progress' && user.role=='repairman'}">
    <form action="/done?request_id=${req.id}" method="post">
        <input type="submit" value="done">
    </form>
</c:if>
</body>
</html>
