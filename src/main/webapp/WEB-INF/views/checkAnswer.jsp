<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
</head>
<body>
<jsp:include page="section/navigation.jsp"/>

<jsp:useBean id="summary" class="ru.alina.model.Summary" scope="request"/>
<div class="container">
    <jsp:include page="section/checked.jsp"/>
    <div class="row">
        <div class="col-12">
            <h5 class="topic">Тема: ${summary.topic.name}</h5>
            <h5>${summary.question}</h5>
            <div class="text">${summary.answer}</div>
            <form method="post" action="${pageContext.request.contextPath}/check">
                <input type="hidden" name="sid" value="${summary.id}">
                <button type="submit" name="btn" value="true" class="true_button btn">know</button>
                <button type="submit" name="btn" value="false" class="false_button btn">don't know</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
