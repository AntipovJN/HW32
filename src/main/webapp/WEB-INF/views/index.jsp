<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello!</title>
</head>
<body>

<%--/register - это путь относительно домена - т.е. домен + эта строка--%>
<%--register - это путь относительного того где мы сейчас - т.е. где мы сейчас + эта строка--%>

<center>
    Welcome! You must sign in
    <br/>${error}
    <form action="/login" method="post">
        <input type="text" name="email"/>
        <input type="password" name="password"/>
        <input type="submit" value="Sign In">
    </form>
</center>
</body>
</html>
