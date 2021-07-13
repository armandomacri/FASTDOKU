<%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 07/02/2021
  Time: 09:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Fastdoku</title>
        <link href="css/login.css" rel="stylesheet" type="text/css">
        <link rel="icon" type="image/png" href="images/sudoku.png"/>
    </head>
    <body>
        <%
            if (request.getParameter("error") != null) {
                out.print("<div style='text-align:center;'><h1 style='color:red;'>"+request.getParameter("error")+"</h1></div>");
                request.removeAttribute("error");
            }
        %>
        <div class="wrapper fadeInDown">
            <div id="formContent">
                <!-- Icon -->
                <div class="fadeIn first">
                    <div class="divImage"></div>
                </div>

                <!-- Login Form -->
                <form action="access-servlet" class="formStyle" method="post">
                    <label for="username"></label><input type="text" id="username" class="fadeIn second" placeholder="Enter Username" name="username" required>
                    <label for="password"></label><input type="password" id="password" class="fadeIn third" placeholder="Enter Password" name="password" required>
                    <input type="submit" class="fadeIn fourth" name="logIn" value="logIn">
                </form>

                <!-- Remind Passowrd -->
                <div id="formFooter">
                    <a class="underlineHover" href="signUp.jsp">Sign Up</a>
                </div>

            </div>
        </div>
    </body>
</html>
