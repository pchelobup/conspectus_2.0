<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-12">
        <div class="counter">
            <h6>Выученно: ${requestScope.get("checkedCount")} / ${requestScope.get("count")}</h6>
        </div>
    </div>
</div>
