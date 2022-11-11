<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<html>
<head>
    <meta charset='UTF-8'>
    <title>Profiles</title>
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

    button[type=submit] {
        width: 100%;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    body {
        font-family: -apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif;
        font-size: 16px;
        font-weight: 400;
        line-height: 1.5;
        color: #292b2c;
        background-color: #fff;
        align-content: center;
    }
</style>

<body>
<c:if test="${not empty message}">
    <h1>${message}</h1>
</c:if>
<c:if test="${not empty name}">
    <h3>${name}</h3>
</c:if>
<c:if test="${not empty age}">
    <p>${age}</p>
</c:if>
<c:if test="${not empty city}">
    <p>${city}</p>
</c:if>

<c:if test="${not empty description}">
    <p>${description}</p>
</c:if>
<c:if test="${not empty hobbies}">
    <p>Hobbies: ${hobbies}</p>
</c:if>
<c:if test="${not empty university}">
    <p>${university}</p>
</c:if>
</body>
<form method="post">
        <script>
            function mutual(message){
                alert(message)
            }
        </script>
    <button type="submit" id="Like" name="Like" value="Like">Like</button>
    <button type="submit" id="Skip" name="Skip" value="Skip">Skip</button>
</form>
<input type="submit" value="Personal Account"
       onclick="window.location.href = 'http://localhost:8080/Finder/personal_account/';"/>
</html>
