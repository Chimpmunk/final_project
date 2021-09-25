<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="WEB-INF/tag/custom.tld" %>
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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="fragment/header2.jsp" %>

<c:set var="req" value="${pageContext.request}"/>
<c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}"/>
<c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="pageUrl" value="${ baseURL }${ requestPath }${ not empty params?'?'+=params:'' }"/>
<div class="d-flex row m-2">
    <div class="col-2 alert alert-info" style="height: 100%">
        <form action="/request-list" class="ml-2">
            <c:if test="${user.role == 'manager'}">
                <div>
                    <p><fmt:message key="choose.repairman"/></p>
                    <c:forEach var="r" items="${repairmanList}">
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" name="repairman" id="${r.login}"
                                   value="${r.id}">
                            <label for="${r.login}">${r.login}</label>
                        </div>
                    </c:forEach>
                </div>

            </c:if>
            <div>
                <p><fmt:message key="choose.status"/></p>
                <c:if test="${user.role!='repairman'}">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" name="status" id="accept"
                               value="waiting_for_acceptance">
                        <label for="accept">waiting_for_acceptance</label>
                    </div>
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" name="status" id="wpay" value="waiting_for_payment">
                        <label for="wpay">waiting_for_payment</label>
                    </div>
                </c:if>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="status" id="assign" value="assigned">
                    <label for="assign">assigned</label>
                </div>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="status" id="progress" value="in_progress">
                    <label for="progress">in_progress</label>
                </div>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="status" id="done" value="done">
                    <label for="done">done</label>
                </div>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" name="status" id="cancelled" value="cancelled">
                    <label for="cancelled">cancelled</label>
                </div>
            </div>
            <div>
                <p><fmt:message key="choose.sort"/></p>
                <div class="form-check">
                    <input type="radio" class="form-check-input" name="sort" id="price" value="price">
                    <label for="price">price</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" name="sort" id="time" value="time">
                    <label for="time">time</label>
                </div>
            </div>
            <input type="submit" class="btn btn-info">
        </form>
    </div>
    <div class="ml-2 col-lg">
        <c:forEach items="${requests}" var="req">
            <c:if test="${userLocale==null}">
                <my:requestTag title="${req.title}" description="${req.description}" status="${req.status}"
                               date="${req.time}" locale="${defaultLocale}" link="/request-list/display?request_id=${req.id}"/>
            </c:if>
            <c:if test="${userLocale!=null}">
                <my:requestTag title="${req.title}" description="${req.description}" status="${req.status}"
                               date="${req.time}" locale="${userLocale}" link="/request-list/display?request_id=${req.id}"/>
            </c:if>

        </c:forEach>
    </div>
</div>

<div class="d-flex justify-content-center mt-2 mb-2">

    <c:forEach items="${pages}" var="p">
        <c:if test="${fn:contains(pageUrl, 'page')}">
            <a href="${fn:replace(pageUrl, currentPage, 'page='+=p)}">
                <button class="mr-1 ml-1 btn btn-info">
                        ${p}
                </button>
            </a>
        </c:if>
        <c:if test="${!fn:contains(pageUrl, 'page')&&fn:contains(pageUrl, '?')}">
            <a href="${pageUrl+='&page='+=p}">
                <button class="mr-1 ml-1 btn btn-info">
                        ${p}
                </button>
            </a>
        </c:if>
        <c:if test="${!fn:contains(pageUrl, 'page')&& !fn:contains(pageUrl, '?')}">
            <a href="${pageUrl+='?page='+=p}">
                <button class="mr-1 ml-1 btn btn-info">
                        ${p}
                </button>
            </a>
        </c:if>
    </c:forEach>

</div>

</body>
</html>
