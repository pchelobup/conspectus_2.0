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
    <div class="row">
        <div class="col-12">
            <h5>${summary.topic.name}</h5>
            <hr>
            <jsp:include page="section/checked.jsp"/>
            <h5>${summary.question}</h5>
            <div class="text">${summary.answer}</div>
            <form method="post" action="${pageContext.request.contextPath}/check" class="app-btn">
                <input type="hidden" name="sid" value="${summary.id}">
                <button type="submit" name="btn" value="true" class="true_button btn">know</button>
                <button type="submit" name="btn" value="false" class="false_button btn">don't know</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
