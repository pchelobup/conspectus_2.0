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
            <form:form method="post" modelAttribute="topic" action="${pageContext.request.contextPath}/topic/update">
                topic <form:input path="name" value="${topic.name}"/>
                <form:hidden path="id"/>
                <input type="submit" value="OK">
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
