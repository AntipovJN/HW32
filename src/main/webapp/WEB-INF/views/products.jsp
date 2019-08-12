<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
    <a href="/signout">
        <button>End Session</button>
    </a>
    <c:if test="${isAdmin == null}">
    <a href="/user/order/add"><button>Confirm order</button></a>
    </c:if>
<center>
    <h2> Список товаров </h2>
    <table border="1">
        <tr>
            <th>Наименование</th>
            <th>Описание</th>
            <th>Цена</th>
            <c:choose>
                <c:when test="${isAdmin==true}">
                    <th>Edit</th>
                    <th>Remove</th>
                </c:when>
                <c:otherwise>
                    <th>Buy</th>
                </c:otherwise>
            </c:choose>
        </tr>
        <c:forEach items="${products}" var="product">
        <tr>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <c:choose>
                <c:when test="${isAdmin==true}">
                    <td><a href='/admin/edit?productID=${product.id}'>Edit </a></td>
                    <td><a href='/admin/remove?productID=${product.id}'>Remove </a></td>
                </c:when>
                <c:otherwise>
                    <td><a href="/user/products/buy?productID=${product.id}">
                        <button>Buy</button>
                    </a></td>
                </c:otherwise>
            </c:choose>
        </tr>
        </c:forEach>
        <c:if test="${isAdmin==true}">
        <a href="/users">
            <button>Все пользователи</button>
        </a>
        <a href="/products/add">
            <button>Добавить товар</button>
        </a>
        </c:if>
</center>
</table>
</body>
</html>
