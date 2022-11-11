<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<html>
<head>
    <meta charset='UTF-8'>
    <title>Settings</title>
</head>
<style>
    input[type = checkbox] {
        margin-bottom: -8px;
        width: 10px;
    }

    input[type=text], input[type = password], input[type = date], select {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        display: inline-block;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }

    input[type=submit], input[type=reset] {
        width: 100%;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    #message p {
        padding: 10px 35px;
        font-size: 18px;
    }

</style>
<body>
<c:if test="${not empty message}">
    <h2>${message}</h2>
</c:if>
<form action='' method='POST'>
    <fieldset>
        <input type='text' name='name' value="${name}" placeholder="Enter your new name">
    </fieldset>
    <fieldset>
        <input type='text' name='city' value="${city}" placeholder="Enter your new city">
    </fieldset>
    <fieldset>
        <input type='number' name='age' value="${age}" placeholder="Enter your new age">
    </fieldset>
    <fieldset>
        <input type='text' name='description' value="${description}" placeholder="Enter your new description">
    </fieldset>
    <fieldset>
        <input type='number' name='minDesiredAge' placeholder="Enter minimal age of a person you want to find" value="${minDesiredAge}" }>
    </fieldset>
    <fieldset>
        <input type='number' name='maxDesiredAge' placeholder="Enter maximal age of a person you want to find" value="${maxDesiredAge}">
    </fieldset>
    <p>Enter the sex of a person you want to find</p>
    <fieldset>
        <select id="desiredSex">
            <option>Male</option>
            <option>Female</option>
            <option>Any</option>
        </select>
    </fieldset>
    <input type='submit' value='Submit' name="Submit" formmethod="post">
</form>
<input type="submit" value="Personal Account"
       onclick="window.location.href = 'http://localhost:8080/Finder/personal_account/';"/>
</body>
</html>
