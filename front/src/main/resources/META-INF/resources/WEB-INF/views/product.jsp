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

        <script>
            function validateFields(form) {
                debugger;
                let regex = new RegExp("^(([a-z0-9])*\\s*|\-([a-z0-9]))*(?!([\/@\_\*\<\>]))$");
                var name = document.getElementById("name");
                if (!regex.test(name.value)) {
                    alert('invalid value for field name');
                    name.setInvalid("invalid");
                    return false;
                }
                var price = document.getElementById("price");
                if (isNaN(price.value)) {
                    alert('invalid value for field price');
                    return false;
                }
                var brand = document.getElementById("brand");
                if (!regex.test(brand.value)) {
                    alert('invalid value for field brand');
                    return false;
                }
                var barCode = document.getElementById("barCode");
                if (!regex.test(barCode.value)) {
                    alert('invalid value for field barCode');
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <meta charset="ISO-8859-1">
    <title>Product Registration Form</title>
</head>
<body>
<div align="center">
    <h2>Products Registration</h2>
    <form action="/products" method="post" id="productsForm" onsubmit="return validateFields(this)">
        <label>Full name:</label>
        <input type="text" required name="name" id="name"/><br/>

        <label>price:</label>
        <input type="number" name="price" id="price"/><br/>

        <label>brand:</label>
        <input type="text" name="brand" id="brand"/><br/>

        <label>barCode:</label>
        <input type="text" name="barCode" required id="barCode"/><br/>

        <button>Register</button>
    </form>
</div>
</body>
</html>