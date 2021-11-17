<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/static/js/niceEdit.js" type="text/javascript"></script>
<script type="text/javascript">bkLib.onDomLoaded(function () {
    var myNicEditor = new nicEditor();
    if (document.getElementById('textareaAddQ')) {
        myNicEditor.panelInstance('textareaAddQ');
    }

    if (document.getElementById('textareaChangeQ')) {
        myNicEditor.panelInstance('textareaChangeQ');
    }

});
</script>

