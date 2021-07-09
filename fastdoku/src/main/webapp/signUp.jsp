<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 07/02/2021
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>FASTDOKU</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="images/sudoku.png"/>
</head>
<body>

    <div class="wrapper fadeInDown">
        <div id="formContent">
            <!-- Icon -->
            <div class="fadeIn first">
                <div class="divImage"></div>
            </div>

            <!-- SignUp Form -->
            <form action="access-servlet" class="formStyle" method="post">
                <label for="username"></label><input type="text" id="username" class="fadeIn second" placeholder="Enter Username" name="username" required>
                <label for="password"></label><input type="text" id="password" class="fadeIn third" placeholder="Enter Password" name="password" required>
                <label for="repeatpassword"></label><input type="text" id="repeatpassword" class="fadeIn third" placeholder="Repeat Password" name="repeatpassword" required>
                <input type="submit" class="fadeIn fourth" name="signUp" value="signUp">
            </form>
            <div id="formFooter">
                <a class="underlineHover" href="index.jsp">Sign In</a>
            </div>
        </div>
    </div>
</body>
</html>
