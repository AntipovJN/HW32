<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
${error}
<form action="/admin/users/edit" method="post">
    <input type="hidden" name="id" value="${id}">
    <input type="hidden" name="salt" value="${salt}">
    <input type="email" name="email" value="${email}"/>
    <input type="password" name="password"/>
    <input type="password" name="repeatPassword"/>
    <input type="submit" value="Edit">
</form>
</body>
</html>
