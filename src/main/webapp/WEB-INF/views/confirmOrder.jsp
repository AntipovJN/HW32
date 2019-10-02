<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Enter authentication code from email, for confirm order
<br/>Sum of the order: ${sum}
${error}
<form action="/user/order/confirm" method="post">
    <input type="hidden" value="${orderId}" name="orderId"/>
    Code:<input type="text" name="codeValue"/>
    <input type="submit" value="confirm"/>
    </form>
</body>
</html>
