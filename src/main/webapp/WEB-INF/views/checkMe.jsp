<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
</head>
<body>
<jsp:include page="section/navigation.jsp"/>
<div class="container">
    <jsp:include page="section/nothingShow.jsp"/>
    <c:if test="${requestScope.get('summary')!=null}">
        <jsp:include page="section/checked.jsp"/>
        <div class="row">
            <div class="col-12">
                <jsp:useBean id="summary" class="ru.alina.model.Summary" scope="request"/>
                <div class="col-12">
                    <h5 class="topic">Тема: ${summary.topic.name}</h5>
                    <h5>${summary.question}</h5>
                    <a href="check/answer?sid=${summary.id}">show answer</a>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
