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
        <jsp:useBean id="topicName" scope="request" type="java.lang.String"/>
        <div class="col-12">
            <h5>${topicName}</h5>
            <hr>
         <%--   <jsp:useBean id="summaries" scope="request" type="java.util.List"/> --%>
            <c:forEach var="summary" items="${summaries}">
                <jsp:useBean id="summary" class="ru.alina.model.Summary"/>
                <div class="col-12 textBlock">
                    <a style="font-size: 10px" href="conspectus/update?sid=${summary.id}">update</a>
                    <h5>${summary.question}</h5>
                    <p class="text">${summary.answer}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
