<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<a href="/exit">
    <button>End Session</button>
</a>
<center>
    <h2> Список пользователей </h2>
    <table border="1">
        <tr>
            <th>Email</th>
            <th>Password</th>
            <th>Edit</th>
            <th>Remove</th>
        </tr>
        <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td><a href="/users/edit?id=${user.id}">Edit </a></td>
            <td><a href="/users/remove?id=${user.id}">Remove </a></td>
        </tr>
        </c:forEach>
        <a href="/register">
            <button>Register new user</button>
        </a>
        <a href="/products/store">
            <button>All products</button>
        </a>
</center>
</table>
</body>
</html>
