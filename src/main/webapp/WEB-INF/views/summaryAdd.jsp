<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
    <jsp:include page="section/headWithRedactor.jsp"/>

</head>
<body>
<jsp:include page="section/navigation.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-12">
            <form style="padding-top: 30px" method="post" action="${pageContext.request.contextPath}/conspectus/add">
                <select class="mySelect" name="topicId">
                    <jsp:useBean id="topics" scope="request" type="java.util.List"/>
                    <jsp:useBean id="topicSelected" scope="request" type="ru.alina.model.Topic"/>
                    <c:forEach items="${topics}" var="topic">
                        <jsp:useBean id="topic" class="ru.alina.model.Topic" scope="request"/>
                        <c:choose>
                            <c:when test="${topicSelected.id eq topic.id}">
                                <option value="${topic.id}" name="select" selected>${topic.name}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${topic.id}" name="select">${topic.name}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>

                <a href="${pageContext.request.contextPath}/topic">add or change topic</a>

                <span style="display: block">question</span>
                <textarea name="question" class="textareaQuestion"></textarea>

                <br>
                <span>answer</span>
                <br>
                <textarea id="textareaAddQ" name="answer" class="textareaAnswer"></textarea>
                <button type="submit">ОК</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>

