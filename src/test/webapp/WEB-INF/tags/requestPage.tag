<%@ attribute name="requestAuthor" type="entity.User" required="true" %>
<%@ attribute name="req" type="entity.RepairRequest" required="true"%>
<%@attribute name="repairman" type="entity.User" required="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${userLocale==null}">
    <fmt:setLocale value="${defaultLocale}"/>
</c:if>
<c:if test="${userLocale!=null}">
    <fmt:setLocale value="${userLocale}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<div class="row m-2 justify-content-between">
    <div>
        <h4>${req.title}</h4>
    </div>
    <div>
        <h4>${req.time}</h4>
    </div>
</div>
<div class="m-2">
    <h2>${requestAuthor.login}</h2>
    <c:if test="${req.price!=0}">
        <h4>${req.price}</h4>
    </c:if>
    <h4> <fmt:message key="status"/> :${req.status}</h4>
    <c:if test="${repairman!=null}">
        <h4>${repairman.login}</h4>
    </c:if>
    <p>${req.description}</p>
</div>