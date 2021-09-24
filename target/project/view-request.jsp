<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/style/rating.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<%@include file="fragment/header2.jsp"%>

<h2>${requestAuthor.login}</h2>
<h4>${req.time}</h4>
<h4>${req.title}</h4>
<h4>${req.status}</h4>
<p>${req.description}</p>
<c:if test="${req.price!=0}">
    <h4>${req.price}</h4>
</c:if>
<c:if test="${repairman!=null}">
    <h4>${repairman.login}</h4>
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

<c:if test="${req.status=='done' && user.role=='customer' && review==null}">
    <form  action="/rate?request_id=${req.id}&repairman=${repairman.id}" class="mt-5" method="post">
        <div class="rating-area">
            <input type="radio" id="5" name="rating" value="5">
            <label for="5" title="Оценка «5»"></label>

            <input type="radio" id="4" name="rating" value="4">
            <label for="4" title="Оценка «4»"></label>

            <input type="radio" id="3" name="rating" value="3">
            <label for="3" title="Оценка «3»"></label>

            <input type="radio" id="2" name="rating" value="2">
            <label for="2" title="Оценка «2»"></label>

            <input type="radio" id="1" name="rating" value="1">
            <label for="1" title="Оценка «1»"></label>
        </div>
        <textarea name="comment" placeholder="Введите комментарий" class="form-control"></textarea>
        <input type="submit" class="btn btn-success">
    </form>
</c:if>

<c:if test="${review!=null}">
    <h3>Review</h3>
    <h4>${review.rating}!</h4>
    <p>${review.text}</p>
</c:if>
</body>
</html>
