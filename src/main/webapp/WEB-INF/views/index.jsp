<%--
  Created by IntelliJ IDEA.
  User: Alina
  Date: 19.10.2021
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="section/head.jsp" />
</head>
<body>
    <form method="post" action="/">
       <select name="userId">
           <option value="1">user1</option>
           <option value="2">user2</option>
       </select>
        <input type="submit" value="OK">
    </form>
</body>
</html>
