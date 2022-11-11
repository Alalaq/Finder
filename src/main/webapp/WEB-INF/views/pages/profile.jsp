<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<html>
<head>
    <meta charset='UTF-8'>
    <title>Fill up your profile</title>
</head>
<style>
</style>
<body>
<h3> ${message} </h3>
<form method="post">
    <fieldset>
        <input type='text' name='name' placeholder="Enter your name">
    </fieldset>
    <fieldset>
        <input type='text' name='city' placeholder="Enter your city">
    </fieldset>
    <fieldset>
        <input type='number' name='age' placeholder="Enter your age">
    </fieldset>
    <fieldset>
        <select id="sex" name="sex">
            <option>Male</option>
            <option>Female</option>
        </select>
    </fieldset>
    <input type='text' id="description" name='description' placeholder="Tell something about yourself">
    <fieldset>
        <input type='text' name='howToConnect' placeholder="Enter something so users could connect with you">
    </fieldset>
    <fieldset>
        <input type='number' name='minDesiredAge' placeholder="Enter minimal age of a person you want to find">
    </fieldset>
    <fieldset>
        <input type='number' name='maxDesiredAge' placeholder="Enter maximal age of a person you want to find">
    </fieldset>
    <p>Enter the sex of a person you want to find</p>
    <fieldset>
        <select id="desiredSex" name="desiredSex">
            <option>Male</option>
            <option>Female</option>
            <option>Any</option>
        </select>
    </fieldset>
    <p>Please choose up to 5 hobbies</p>
    <div>
        <select id="hobbies" name="hobbies" size="8" multiple>
            <option value="1:Archery">Archery</option>
            <option value="2:Arm-wrestling">Arm-wrestling</option>
            <option value="3:Ballroom dancing">Ballroom dancing</option>
            <option value="4:Basketball">Basketball</option>
            <option value="5:Beading">Beading</option>
            <option value="6:Billiards">Billiards</option>
            <option value="7:Board games">Board games</option>
            <option value="8:Booze">Booze</option>
            <option value="9:Bowling">Bowling</option>
            <option value="10:Boxing">Boxing</option>
            <option value="11:Break dance">Break dance</option>
            <option value="12:Calisthenics">Calisthenics</option>
            <option value="13:Collecting marks">Collecting marks</option>
            <option value="14:Computer games">Computer games</option>
            <option value="15:Dancing">Dancing</option>
            <option value="16:Diving">Diving</option>
            <option value="17:Doing puzzles">Doing puzzles</option>
            <option value="18:Drawing">Drawing</option>
            <option value="19:Fitness">Fitness</option>
            <option value="20:Fishing">Fishing</option>
            <option value="21:Gardening">Gardening</option>
            <option value="22:Gymnastics">Gymnastics</option>
            <option value="23:Hand-to-hand fighting">Hand-to-hand fighting</option>
            <option value="24:Horse riding">Horse riding</option>
            <option value="25:Knitting">Knitting</option>
            <option value="26:Music">Music</option>
            <option value="27:Learning languages">Learning languages</option>
            <option value="28:Parties">Parties</option>
            <option value="29:Play the guitar">Play the guitar</option>
            <option value="30:Programming">Programming</option>
            <option value="31:Reading Books">Reading Books</option>
            <option value="32:Role-playing">Role-playing</option>
            <option value="33:Singing">Singing</option>
            <option value="34:Swimming">Swimming</option>
            <option value="35:Yoga">Yoga</option>
            <option value="36:Workout">Workout</option>
            <option value="37:Writing poetry">Writing poetry</option>
        </select>
    </div>
    <script>
        window.onmousedown = function (e) {
            let el = e.target;
            if (el.tagName.toLowerCase() === 'option' && el.parentNode.hasAttribute('multiple')) {
                e.preventDefault();
                if (el.hasAttribute('selected')) el.removeAttribute('selected');
                else el.setAttribute('selected', '');
                let select = el.parentNode.cloneNode(true);
                el.parentNode.parentNode.replaceChild(select, el.parentNode);
            }
        }
    </script>


    <p>Select your university</p>
    <c:if test="${not empty universities}">
        <select id="university" name="university" size="5">
            <c:forEach items="${universities}" var="university">
                <option value="${university}">${university}</option>
            </c:forEach>
        </select>
    </c:if>
    <input type="submit" id="Submit" value="Submit">
</form>
</body>
</html>
