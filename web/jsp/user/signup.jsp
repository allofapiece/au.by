<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/fc?command=signup" method="post">
        <c:forEach items="${errors}" var="field">
           Field: ${field.key} Errors:
            <c:forEach items="${field.value}" var="error">
                <p>${error}</p>
            </c:forEach>
        </c:forEach>
        <%--<div>${errors["user.field.password"]}</div>--%>
        <input type="email" name="email" placeholder="email">
        <input type="password" name="password">
        <input type="password" name="confirm-password">
        <input type="text" name="name" placeholder="name"/>
        <input type="text" name="surname" placeholder="surname"/>
        <button type="submit">Sign up</button>
    </form>
</body>
</html>
