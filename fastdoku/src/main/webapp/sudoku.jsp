<%@ page import="it.unipi.dii.inginf.dsmt.fastdoku.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: arman
  Date: 16/02/2021
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/sudoku.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <script src="js/sudoku.js"></script>
</head>

<body>
    <%String username = ((User)session.getAttribute("loggedUser")).getUsername();%>

    <nav class="app-bar">
        <div class="bar-font title">Sudoku</div>
        <div class="bar-font" id="loggedUsername"><%out.print(username);%></div>

        <button id="pause-btn" onclick="pauseGameButtonClick()" class="button bar-button more-button">
            <span id="pause-icon" class="material-icons">pause</span>
            <span id="pause-text">Pause</span>
        </button>
        <button id="check-btn" onclick="checkButtonClick()" class="button bar-button more-button">
            <span class="material-icons">done_all</span>
            <span>Check</span>
        </button>
        <button id="solve-btn" onclick="solveButtonClick()" class="button bar-button more-button">
            <span class="material-icons">border_color</span>
            <span>Solve</span>
        </button>
    </nav>

    <div class="body" id="sudoku">
        <div class="card game">
            <table id="puzzle-grid">
                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>

                <tr>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>

                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                    <td>
                        <input type="text" maxlength="1" onchange="checkInput(this)" disabled/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="card status">
            <div id="game-number"><%out.print(username);%> <b>VS</b> <span id="opponentUsername"><%out.print(request.getParameter("opponent"));%></span></div>
            <ul class="game-status">
                <li>
                    <div class="vertical-adjust">
                        <span class="material-icons">timer</span>
                        <span id="timer-label">Time</span>
                    </div>
                    <div id="timer" class="timer">00:00</div>
                </li>

                <li>
                    <div class="vertical-adjust">
                        <span class="material-icons">network_check</span>
                        <span id="game-difficulty-label">Game difficulty</span>
                    </div>
                    <div id="game-difficulty" class="timer" style="text-transform: uppercase;"><% out.print(request.getParameter("difficulty"));%></div>
                </li>
                <li>
                    <div class="vertical-adjust">
                        <span class="material-icons">grid_on</span>
                        <span>Remaining numbers</span>
                    </div>
                    <div class="remain-table">
                        <div class="remain-column">
                            <div class="remain-cell">
                                <div class="remain-cell-header">1</div>
                                <div onchange="document.write('Hello');" id="remain-1" class="remain-cell-content">0</div>
                            </div>
                            <div class="remain-cell">
                                <div class="remain-cell-header">4</div>
                                <div id="remain-4" class="remain-cell-content">0</div>
                            </div>
                            <div class="remain-cell">
                                <div class="remain-cell-header">7</div>
                                <div id="remain-7" class="remain-cell-content">0</div>
                            </div>
                        </div>
                        <div class="remain-column">
                            <div class="remain-cell">
                                <div class="remain-cell-header">2</div>
                                <div id="remain-2" class="remain-cell-content">0</div>
                            </div>
                            <div class="remain-cell">
                                <div class="remain-cell-header">5</div>
                                <div id="remain-5" class="remain-cell-content">0</div>
                            </div>
                            <div class="remain-cell">
                                <div class="remain-cell-header">8</div>
                                <div id="remain-8" class="remain-cell-content">0</div>
                            </div>
                        </div>
                        <div class="remain-column">
                            <div class="remain-cell">
                                <div class="remain-cell-header">3</div>
                                <div id="remain-3" class="remain-cell-content">0</div>
                            </div>
                            <div class="remain-cell">
                                <div class="remain-cell-header">6</div>
                                <div id="remain-6" class="remain-cell-content">0</div>
                            </div>
                            <div class="remain-cell">
                                <div class="remain-cell-header">9</div>
                                <div id="remain-9" class="remain-cell-content">0</div>
                            </div>
                        </div>

                    </div>
                </li>
            </ul>
        </div>
    </div>
    <script src="js/webSocket.js" type="text/javascript"></script>
    <script src="js/play.js" type="text/javascript"></script>
</body>
</html>
