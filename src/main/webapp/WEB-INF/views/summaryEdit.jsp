<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp"/>
    <jsp:include page="section/headWithRedactor.jsp"/>
</head>
<body>
<jsp:include page="section/navigation.jsp"/>
<div class="container" id="changeBlock">
    <jsp:useBean id="summary" class="ru.alina.model.Summary" scope="request"/>
    <div class="row" style="margin-bottom: 20px">
        <div class="col-12">

            <form style="padding-top: 30px" method="post" action="${pageContext.request.contextPath}/conspectus/update">
                <input type="hidden" name="sid" value="${summary.id}">
                <select name="topicId" style="margin-right: 20px">
                    <jsp:useBean id="topics" scope="request" type="java.util.List"/>
                    <c:forEach items="${topics}" var="t">
                        <jsp:useBean id="t" class="ru.alina.model.Topic" scope="request"/>
                        <c:if test="${summary.topic.name eq t.name}">
                            <option value="${t.id}" selected> ${t.name} </option>
                        </c:if>
                        <c:if test="${summary.topic.name ne t.name}">
                            <option value="${t.id}">${t.name} </option>
                        </c:if>
                    </c:forEach>
                </select>

                <span style="display: block">question</span>
                <textarea name="question" class="textareaQuestion">${summary.question}</textarea>
                <br>
                <span>answer</span>
                <br>
                <textarea name="answer" class="textareaAnswer text" id="textareaChangeQ">${summary.answer}</textarea>
                <button type="submit" name="button" value="save">ОК</button>
                <button type="submit" name="button" value="delete">DELETE</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
