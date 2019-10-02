<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 17.07.2019
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirm order</title>
</head>
<body>
<center>
    <table>
        <form action="/user/order/add" method="post">
            <tr>
                <td>Write delivery address</td>
                <td><input type="text" name="address" value=""/></td>
            </tr>
            <tr>
                <td>Choose payment method</td>
                <td><input type="radio" name="payment" value="card" checked>Card</td>
            </tr>
            <tr>
                <td></td>
                <td><input type="radio" name="payment" value="cash">Cash</td>
            </tr>
            <input type="submit" value="Confirm">
        </form>
    </table>
</center>
</body>
</html>
