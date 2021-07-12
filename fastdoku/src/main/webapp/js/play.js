
class Request{
    constructor(sender, receiver, type, text) {
        this.sender = sender; //username of the logged user
        this.receiver = receiver;
        this.type = type; //type of the request
        this.text = text; //payload
    }
}

waitForSocketConnection(ws, registerUser);

function registerUser () {
    var username = document.getElementById("loggedUsername").textContent;
    sendWebSocket(JSON.stringify(new Request(username, "WebSocket", "register_me", "")));
    waitForSocketConnection(ws, registerOpponent);
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
        case "lose":
            alert(text);
            location.href = "./update-servlet?points=-2";
            break;
        case "surrender":
            gameOn = false;
            alert(sender + " has given up!");
            location.href = "./update-servlet?points=1";
            break;
        default:
            break;
    }
}