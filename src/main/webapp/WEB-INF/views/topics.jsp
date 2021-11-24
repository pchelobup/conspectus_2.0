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
            <h6>Добавить новую тему</h6>
            <hr>
            <form:form modelAttribute="topic" action="${pageContext.request.contextPath}/topic/add" cssClass="add-topic">
                <span>new topic</span> <form:input type="text" name="name" path="name"/>
                <input type="submit" value="OK">
            </form:form>
        </div>
    </div>

    <c:choose>
    <c:when test="${requestScope.get('topics') eq null}">
        <div class="row">
            <div class="col-12">
                <span>*Перед тем как добавить вопрос, добавте хотя бы одну тему</span>
            </div>
        </div>
    </c:when>
    <c:otherwise>
    <div class="row">
        <div class="col-12">
            <table class="update-topic table table-bordered">
                <jsp:useBean id="topics" scope="request" class="java.util.ArrayList"/>

                        <c:forEach var="topic" items="${topics}">
                            <jsp:useBean id="topic" class="ru.alina.model.Topic" scope="request"/>
                            <tr>
                                <td>${topic.name}</td>
                                <td><a href="topic/update?topicId=${topic.id}">update</a></td>
                                <td><a href="topic/delete?topicId=${topic.id}">delete</a></td>
                            </tr>
                        </c:forEach>
            </table>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
