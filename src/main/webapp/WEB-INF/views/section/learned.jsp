<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-12">
        <div class="count">
            <span>Проверенно: ${requestScope.get("checkedCount")} / ${requestScope.get("count")}</span>
        </div>
    </div>
</div>
