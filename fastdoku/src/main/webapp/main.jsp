<%@ page import="it.unipi.dii.inginf.dsmt.fastdoku.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 08/02/2021
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Main Page</title>
    <link href="css/onlineuser.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <link href="css/alert.css" rel="stylesheet" type="text/css">
    <link rel="icon" type="image/png" href="images/sudoku.png"/>
</head>
<body>
    <script src="js/webSocket.js" type="text/javascript"></script>
    <script src="js/onlineUser.js" type="text/javascript"></script>
    <%
        User user = (User)session.getAttribute("loggedUser");
        if(request.getAttribute("points") != null){
            int points = Integer.parseInt(request.getAttribute("points").toString());
            String msg;
            if (points > 0)
                msg = "CONGRATS you have won " + points + " points";
            else
                msg = "SORRY you have lost " + points + " points";
            out.print("<script type='text/javascript'>" + "alert(" + msg + ")</script>");
            request.removeAttribute("points");
        }
    %>
    <!-- Navigation -->
    <header class="header">
        <div class="logo"><span>FASTDOKU</span></div>
        <nav>
            <ul class="menu-items">
                <li>
                    <div class="menu-item select" >
                        <span class="material-icons">account_circle</span>
                        <span id="loggedUsername" style="margin-left: 5px"><%out.print(user.getUsername());%></span>
                    </div>
                </li>
                <li>
                    <div class="menu-item select">
                        <span class="material-icons">emoji_events</span>
                        <span style="margin-left: 5px"><%out.print(user.getPoints());%></span>
                    </div>
                </li>
                <li class="log-out"><a href="logout-servlet" class="menu-item">Log out</a></li>
            </ul>
        </nav>
    </header>
    <div style="display: inline-flex; width: 100%">
        <div class="box">
            <div class="container">
                <h2>Online Users <small>Send a request</small></h2>
                <ul class="responsive-table" id="onlineUserList">
                    <li class="table-header">
                        <div class="col col-1">Username</div>
                        <div class="col col-2">Status</div>
                        <div class="col col-3">Action</div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="box">
            <div class="container">
                <h2>Request <small>your request</small></h2>
                <ul class="responsive-table" id="requestRecived">
                    <li class="table-header">
                        <div class="col col-1">Username</div>
                        <div class="col col-2">Difficulty</div>
                        <div class="col col-3">Action</div>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <!-- select difficulty -->
    <div id="dialog" class="dialog">
        <div id="dialog-box" class="dialog-content">
            <div class="dialog-header">Play With <span id="userToChallenge"></span></div>

            <div class="dialog-body">
                <p>Select game difficulty to send a request.</p>
                <ul>
                    <li class="radio-option">
                        <label for="very-easy">
                            <input id="very-easy" value="very easy" type="radio" name="difficulty"> Very easy
                        </label>
                    </li>
                    <li class="radio-option">
                        <label for="easy">
                            <input id="easy" value="easy" type="radio" name="difficulty"> Easy
                        </label>
                    </li>
                    <li class="radio-option">
                        <label for="normal">
                            <input id="normal" value="normal" type="radio" name="difficulty"> Normal
                        </label>
                    </li>
                    <li class="radio-option">
                        <label for="hard">
                            <input id="hard" value="hard" type="radio" name="difficulty"> Hard
                        </label>
                    </li>
                    <li class="radio-option">
                        <label for="very-hard">
                            <input id="very-hard" value="expert" type="radio" name="difficulty"> Expert
                        </label>
                    </li>
                </ul>
            </div>

            <div class="dialog-footer">
                <button onclick="startGameButtonClick();" ripple-color="#003c8f" class="button dialog-btn vertical-adjust" >OK</button>
                <button onclick="hideDialogButtonClick('dialog');" ripple-color="#202020" class="button dialog-btn vertical-adjust" >Cancel</button>
            </div>
        </div>
    </div>
    <!------------------------------------------------------------------------------------------------------------------------------------------------>

    <!-- capture alert -->
    <div class="fixed-horizontal-center">
        <div id="alert">
            This is an alert box.
        </div>
    </div>
    <script src="js/socketRequest.js" type="text/javascript"></script>
</body>
</html>
