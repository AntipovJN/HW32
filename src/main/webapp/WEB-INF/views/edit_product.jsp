<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit product</title>
</head>
<body>
<center>
    <h4>
        ${error}
    </h4>
    <form action="/products/edit" method="post">
        <input type="hidden" name="id" value="${id}">
        Name <input name="name" type="text" value="${name}"/> <br>
        Description <input name="description" type="text" value="${description}"> <br>
        Price <input name="price" type="number" value="${price}"> <br>
        <button type="submit">Register</button>
    </form>
</center>
</body>
</html>
