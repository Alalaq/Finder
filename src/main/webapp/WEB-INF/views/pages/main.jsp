<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<html>
<head>
    <title>Welcome page</title>
    <h2>Welcome to Finder! Here you can find your love.</h2>
    <br>
    <br>
    <br>
    <h3>Please, register or sign in.</h3>
</head>
<style>
    .body text{
        text-align: center;
    }
    .head text{
        text-align: center;
    }
    input[type=submit], input[type=reset] {
        width: 100%;
        padding: 14px 20px;
        margin: 8px 0;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
</style>
<body>
<p><input type="submit" value="Authorization"
          onclick="window.location.href = 'http://localhost:8080/Finder/authentication/';"/>
    <br/>
    <input type="submit" value="Registration"
           onclick="window.location.href = 'http://localhost:8080/Finder/registration/';"/>
</p>
</body>
</html>