
class Request{
    constructor(sender, receiver, type, text) {
        this.sender = sender; //username of the logged user
        this.receiver = receiver;
        this.type = type; //type of the request
        this.text = text; //payload
    }
}

waitForSocketConnection(ws, registerUser);
waitForSocketConnection(ws, registerOpponent);

function registerUser () {
    var username = document.getElementById("loggedUsername").textContent;
    sendWebSocket(JSON.stringify(new Request(username, "WebSocket", "register_me", "")));
}

function registerOpponent() {
    var opponent = document.getElementById("opponentUsername").textContent;
    sendWebSocket(JSON.stringify(new Request("", "WebSocket", "register_opponent", opponent)));
}

ws.onmessage = function (event) {
    var jsonString = JSON.parse(event.data);
    var type = jsonString.type;
    var text = jsonString.text;
    var sender = jsonString.sender;
    console.log("Message received from: " + sender + " [Type = "+type+"]");

    switch (type) {
        case "opponent_disconnected":
            alert(text);
            location.href = "./main.jsp";
            break;
        case "surrender":
            AjaxManager.updatePoints(1);
            gameOn = false;
            break;
        default:
            break;
    }
}