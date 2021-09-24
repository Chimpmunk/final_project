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
</head>
<body>
<fmt:message key="404.message"/>
</body>
</html>
