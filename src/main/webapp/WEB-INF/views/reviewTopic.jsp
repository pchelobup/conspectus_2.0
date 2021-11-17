<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 15.11.2021
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
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
            <a href="${pageContext.request.contextPath}/review/answer?sid=${review.get().id}">show answer</a>
        </div>
    </div>
</div>
</body>
</html>
