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

</head>
<body>
<nav class="col-12 navbar bg-info">
    <h2 class="nav-brand m-2 col-md-">SHop</h2>

    <c:if test="${user!=null}">
        <a href="/profile" class="btn btn-outline-secondary"><fmt:message key="profile.button"/></a>
        <form class="form-inline my-2 my-lg-0" action="/logout">
            <fmt:message var="logoutValue" key="logout.button"/>
            <input type="submit" class="btn btn-outline-danger my-2 my-sm-0 btn-sm" value="${logoutValue}">
        </form>
    </c:if>
    <form action="/lang" method="post" class="form-inline my-2 my-lg-0">
        <select class="form-control" name="locale">
            <option value="en">en</option>
            <option value="ru">ru</option>
        </select>
        <fmt:message key="apply.button" var="applyValue"/>
        <input type="submit" class="btn btn-secondary" value="${applyValue}">
    </form>
</nav>
</body>
</html>
