<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 15.11.2021
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
</head>
<body>
<jsp:include page="section/navigation.jsp"/>

<jsp:useBean id="review" scope="request" class="ru.alina.to.Review"/>
<div class="container">
    <div class="row">
        <div class="col-12">
            <h6>${review.topicName}</h6>
            <hr>
            <p>Вы ответили правильно на ${review.getCountRightAnswer()} вопросов из ${review.count}</p>
            <p>Из них:</p>
            <p>Подтвердили знания ${review.rightAnswerChecked}/${review.countChecked}</p>
            <p>Выучили новые ${review.rightAnswerUnchecked}/${review.countUnchecked}</p>
        </div>
    </div>
</div>
</body>
</html>
