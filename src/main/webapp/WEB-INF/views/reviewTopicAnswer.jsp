<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
</head>
<body>
<jsp:include page="section/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-12">
            <jsp:useBean id="review" type="ru.alina.to.Review" scope="session"/>
            <h6>${review.topicName}</h6>
            <hr>
            <div>
                <h6 class="counter">${review.number}/${review.count}</h6>
            </div>
            <h5>${review.get().question}</h5>
            <div class="text">${review.get().answer}</div>
            <form method="post" action="${pageContext.request.contextPath}/review" class="app-btn">
                <input type="hidden" name="sid" value="${review.get().id}">
                <input type="hidden" name="topicId" value="${review.get().topic.id}">
                <c:choose>
                    <c:when test="${review.get().check}">
                        know <input type="radio" name="btn" value="true" checked>
                        don't know <input type="radio" name="btn" value="false">
                    </c:when>
                    <c:otherwise>
                        know <input type="radio" name="btn" value="true">
                        don't know <input type="radio" name="btn" value="false" checked>
                    </c:otherwise>
                </c:choose>
                <input type="submit" value="next">
            </form>
        </div>
    </div>
</div>
</body>
</html>
