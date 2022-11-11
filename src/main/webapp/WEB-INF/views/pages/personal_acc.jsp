<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<html>
<head>
    <meta charset='UTF-8'>
    <title>Personal Account</title>
</head>
<body>
<h2>Your profile:</h2>
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
<c:if test="${not empty mutuals}">
    <p>Mutuals: ${mutuals}</p>
</c:if>
<%--<c:if test="${not empty mutuals}">--%>
<%--    <p>Your mutual likes:</p>--%>
<%--    <c:forEach items="${mutuals}" var="mutual">--%>
<%--        <td>${mutual}</td>--%>
<%--    </c:forEach>--%>
<%--</c:if>--%>

<input type="submit" value="Settings"
       onclick="window.location.href = 'http://localhost:8080/Finder/settings/';"/>
<input type="submit" value="Catalogue"
       onclick="window.location.href = 'http://localhost:8080/Finder/profiles/';"/>
</body>
<form method="get"><input type="submit" id="logout" name="logout" value="Logout"></form>
</html>