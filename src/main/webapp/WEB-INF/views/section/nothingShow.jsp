<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-12">
        <c:if test="${requestScope.get('nothing') eq 'noAdd'}">
            <c:choose>
                <c:when test="${requestScope.get('type') eq 'check'}">
                    <p>Нечего проверять, нет выученных вопросов</p>
                </c:when>
                <c:otherwise>
                    <p>Нечего проверять и учить, ни одного вопроса не найденно</p>
                    <p><a href="${pageContext.request.contextPath}/conspectus/add">Добавить вопрос</a></p>
                </c:otherwise>
            </c:choose>
        </c:if>

        <c:if test="${requestScope.get('nothing') eq 'allDone'}">
            <p>Все вопросы проверенны</p>
            <c:if test="${requestScope.get('type') eq 'check'}">
                Правильных ответов ${requestScope.get('rightAnswer')} из ${requestScope.get('count')}
                <br>
                Вопросы на которые вы ответили не правильно больше не считаются проверенными.
            </c:if>
        </c:if>

        <c:if test="${requestScope.get('nothing') eq 'noChecked'}">
            Невозможно проверить как хорошо вы запомнили, то что недавно выучили. Вы еще ничего не выучили((
        </c:if>
    </div>
</div>

