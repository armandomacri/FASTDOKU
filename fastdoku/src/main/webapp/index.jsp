<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>FASTDOKU</title>

        <link href="css/login.css" rel="stylesheet" type="text/css">
        <!--
        <link rel="icon" type="image/png" href="resources/images/icon.png"/>
        -->
    </head>
    <body>
        <h1>Welcome to FASTDOKU!</h1>
        <div class="wrapper fadeInDown">
            <div id="formContent">
                <!-- Icon -->
                <div class="fadeIn first">
                    <div class="divImage"></div>
                </div>

                <!-- Login Form -->
                <form action="access-servlet" class="formStyle" method="post">
                    <label for="username"></label><input type="text" id="username" class="fadeIn second" placeholder="Enter Username" name="username" required>
                    <label for="password"></label><input type="text" id="password" class="fadeIn third" placeholder="Enter Password" name="password" required>
                    <input type="submit" class="fadeIn fourth" name="loginButton" value="login">
                </form>

                <!-- Remind Passowrd -->
                <div id="formFooter">
                    <a class="underlineHover" href="signUp.jsp">Sign Up</a>
                </div>

            </div>
        </div>
    </body>
</html>
