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
    <c:if test="${requestScope.get('topics') !=null}">
    <div class="row">
        <div class="col-12">
            <h3>Choose a topic</h3>
            <hr>

            <jsp:useBean id="topics" scope="request" type="java.util.List"/>
            <c:forEach items="${topics}" var="t">
                <jsp:useBean id="t" class="ru.alina.model.Topic" scope="request"/>
                <a href="${pageContext.request.contextPath}/setReview?topicId=${t.id}">${t.name}</a>
                <br>
            </c:forEach>
        </div>
    </div>
    </c:if>
</div>
</body>
</html>
