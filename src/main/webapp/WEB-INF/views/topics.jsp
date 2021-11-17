<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <h3>Добавить новую тему</h3>
            <form:form modelAttribute="topic" action="${pageContext.request.contextPath}/topic/add">
                new topic <form:input type="text" name="name" path="name"/> <br>
                <input type="submit" value="OK">
            </form:form>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <table class="update-topic" border="1" cellpadding="8" cellspacing="0">
                <jsp:useBean id="topics" scope="request" type="java.util.List"/>
                <c:if test="${topics eq null}">
                    <h5>Перед тем как добавить вопрос, добавте хотя бы одну тему</h5>
                </c:if>
                <c:forEach var="topic" items="${topics}">
                    <jsp:useBean id="topic" class="ru.alina.model.Topic" scope="request"/>
                    <tr>
                        <td>${topic.name}</td>
                        <td><a href="topic/update?topicId=${topic.id}">update</a></td>
                        <td><a href="topic/delete?topicId=${topic.id}">delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
