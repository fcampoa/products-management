<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <head>
        ...
        <style type="text/css">
            label {
                display: inline-block;
                width: 200px;
                margin: 5px;
                text-align: left;
            }
            input[type=text], input[type=text], select {
                width: 200px;
            }
            button {
                padding: 10px;
                margin: 10px;
            }
        </style>
    </head>
    <meta charset="ISO-8859-1">
    <title>User Registration Form</title>
</head>
<body>
<div align="center">
    <h2>User Registration</h2>
    <form action="/products/register" method="post">
        <label>Full name:</label>
        <input type="text" required name="name"/><br/>

        <label>price:</label>
        <input type="number" name="price"/><br/>

        <label>brand:</label>
        <input type="text" name="brand"/><br/>

        <label>barode:</label>
        <input type="text" name="barCode" required/><br/>

        <button>Register</button>
    </form>
</div>
</body>
</html>