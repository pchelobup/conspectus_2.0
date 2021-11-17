<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
</head>
<body>
<jsp:include page="section/navigation.jsp"/>

<div class="container">
    <jsp:include page="section/learned.jsp"/>
    <div class="row">
        <div class="col-12">
            <h4>Вопросы по темам</h4>
            <ul>
                <c:forEach items="${topics}" var="topic">
                    <jsp:useBean id="topic" class="ru.alina.model.Topic" scope="request"/>
                    <li><a style="display: block" href="conspectus/read?topicId=${topic.id}">${topic.name}</a></li>
                </c:forEach>
                <li><a href="conspectus/read">all</a></li>
            </ul>
        </div>
    </div>
</div>
</html>
