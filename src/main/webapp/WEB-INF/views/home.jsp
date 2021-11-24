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
            <c:choose>
                <c:when test="${requestScope.get('topics') != null}">
                    <h5>Topics</h5>
                    <hr>
                    <jsp:include page="section/learned.jsp"/>
                    <ul>
                        <c:forEach items="${topics}" var="topic">
                            <jsp:useBean id="topic" class="ru.alina.model.Topic" scope="request"/>
                            <li><a style="display: block" href="conspectus/read?topicId=${topic.id}">${topic.name}</a>
                            </li>
                        </c:forEach>

                        <li><a href="conspectus/read">all</a></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p>Вы еще не добавли вопросы</p>
                </c:otherwise>
            </c:choose>


        </div>
    </div>
</div>
</html>
