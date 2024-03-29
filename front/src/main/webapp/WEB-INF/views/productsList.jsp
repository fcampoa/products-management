<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Spring Boot JSP Example</title>
</head>
<body>
<table style="border: 1px solid">
    <thead>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Brand</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${productsList}" var="product">
        <tr style="border: 1px solid">
            <td style="border: 1px solid">${product.name}</td>
            <td style="border: 1px solid">${product.price}</td>
            <td style="border: 1px solid">${product.brand}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<button id="addButton" onclick="window.location.href = '/products/add';">Add</button>
</body>
</html>