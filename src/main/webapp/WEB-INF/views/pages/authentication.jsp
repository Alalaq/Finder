<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<html>
<head>
    <meta charset='UTF-8'>
    <title>Authentication page</title>
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
</style>
<body>
<c:if test="${not empty message}">
    <h3>${message}</h3>
</c:if>
<form action='' method='POST'>
    <fieldset>
        <legend>Specify your email address</legend>
        <input type='text' name='email' lang="en" required>
    </fieldset>
    <fieldset>
        <legend> Specify your password </legend>
        <input type='password' name='password' required>
    </fieldset>
    <a href="/Finder/registration/">Don't have an account yet?</a>
    <input type='reset' name='reset' value="Reset"> <br>
    <input type='submit' value='Submit'>
</form>
</body>
</html>
