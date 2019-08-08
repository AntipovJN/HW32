<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<center>
    <h4>
        ${error}
    </h4>
    <form action="register" method="post">
        Email <input name="email" type="email" value="${email}"/> <br>
        Password <input name="password" type="password"> <br>
        Repeat password <input name="repeatPassword" type="password"> <br>
        <input type="radio" value="user" name="role" checked> user
        <input type="radio" value="admin" name="role"> admin
        <button type="submit">Register</button>
    </form>
</center>
</body>
</html>
