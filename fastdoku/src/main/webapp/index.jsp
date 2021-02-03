<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>FASTDOKU</title>

        <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css">
        <!--
        <link rel="icon" type="image/png" href="resources/images/icon.png"/>
        -->
    </head>
    <body>
        <!--
        <h1>Welcome to FASTDOKU!</h1>

        <div class="first_div">
            <form action="access-servlet" class="form_style" method="post">

                <div class="imgcontainer">
                    <img src='resources/images/icon.png' alt="Icon not available" width="75%">
                </div>

                <div class="container">
                    <label><b>Username</b></label>
                    <label>
                        <input type="text" class="inputWidth" placeholder="Enter Username" name="username" required>
                    </label>

                    <label><b>Password</b></label>
                    <label>
                        <input type="password" class="inputWidth" placeholder="Enter Password" name="password" required>
                    </label>

                    <button class="mainButton" type="submit" name="loginButton" value="login">Login</button>
                    <button class="mainButton" type="submit" name="registerButton" value="register">Register</button>
                </div>
            </form>
        </div>
        -->
        <div class="jumbotron">
            <div class="container">
                <span class="glyphicon glyphicon-list-alt"></span>
                <h2>Calendar</h2>
                <div class="box">
                    <input type="text" placeholder="username">
                    <input type="password" placeholder="password">
                    <button class="btn btn-default full-width"><span class="glyphicon glyphicon-ok"></span></button>
                </div>
            </div>
        </div>
    </body>
</html>
