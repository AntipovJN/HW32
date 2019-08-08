<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new product</title>
</head>
<body>
<center>
    <h4>
        ${error}
    </h4>
    <form action="/products/add" method="post">
        Name <input name="name" type="text" value="${name}"/> <br>
        Description <input name="description" type="text" value="${description}"> <br>
        Price <input name="price" type="number"> <br>
        <button type="submit">Register</button>
    </form>
</center>
</body>
</html>
