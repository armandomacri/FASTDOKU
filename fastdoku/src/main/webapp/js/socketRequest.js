const diff = ["very easy", "easy", "normal", "hard", "expert"];

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
}

function addUserAmongOnlineOne () {
    sendWebSocket(JSON.stringify(new Request("", "WebSocket", "add_online", "")));
}

ws.onmessage = function (event) {
    var jsonString = JSON.parse(event.data);
    var type = jsonString.type;
    var text = jsonString.text;
    var sender = jsonString.sender;
    var myUsername = document.getElementById("loggedUsername").textContent;
    console.log("Message received from: " + sender + " [Type = "+type+"]");
    var ul = document.getElementById("onlineUserList");
    var ul1 = document.getElementById("requestRecived");

    switch (type){

        case "user_list":
            for(var i = 0; i < text.length; i++) {
                ul.append(createUserLi(myUsername, text[i]));
            }
            waitForSocketConnection(ws, addUserAmongOnlineOne);
            break;

        case "new_user":
            ul.append(createUserLi(myUsername, text));
            break;

        case "remove_user":
            document.getElementById(text+"_online").remove();
            if (document.getElementById(text+"_request"))
                document.getElementById(text+"_request").remove();
            hideDialogButtonClick('dialog');
            break;

        case "send_request":
            ul1.appendChild(requestUserLi(myUsername, sender, text));
            break;

        case "refuse_request":
            var b = document.getElementById(sender+"request");
            b.disabled = false;
            b.textContent = "Send Request";
            b.style.cursor = "pointer";
            b.style.color = "green";
            break;

        case "info":
            showAlert(text);
            break;

        case "error":
            alert(text);
            location.href = "./logout-servlet";
            break;

        case "play_start":
            location.href = "./sudoku.jsp?difficulty=" + diff[parseInt(text)-1] + "&opponent=" + sender;
            break;

        default:
            break;
    }
}

function createUserLi (mySelf, username) {
    var li = document.createElement("li");
    li.setAttribute("class", "table-row");
    li.setAttribute("id", username + "_online");
    var div1 = document.createElement("div");
    div1.setAttribute("class","col col-1");
    div1.setAttribute("data-label", "Username");
    var usernameText = document.createTextNode(username);
    div1.append(usernameText);

    var div2 = document.createElement("div");
    div2.setAttribute("class","col col-2");
    div2.setAttribute("data-label", "Status");
    var image = document.createElement("IMG");
    image.setAttribute("src", "./images/online_point.png");
    image.setAttribute("width", "20");
    image.setAttribute("height", "20");
    image.setAttribute("alt", "User Online");
    div2.appendChild(image);

    var div3 = document.createElement("div");
    div3.setAttribute("class","col col-3");
    div3.setAttribute("data-label", "Action");
    var sendRequestButton = document.createElement("Button");
    sendRequestButton.setAttribute("class", "requestButton");
    sendRequestButton.setAttribute("id", username+"request");
    sendRequestButton.appendChild(document.createTextNode("Send Request"));
    sendRequestButton.onclick = function() {
                                            //sendRequest(mySelf, username);
                                            showDialogClick('dialog', username);
                                            };
    div3.append(sendRequestButton);
    li.append(div1, div2, div3);
    return li;
}

function requestUserLi (username, sender, difficulty) {
    var li = document.createElement("li");
    li.setAttribute("class", "table-row");
    li.setAttribute("id", sender + "_request");

    var div1 = document.createElement("div");
    div1.setAttribute("class","col col-1");
    div1.setAttribute("data-label", "Username");
    var usernameText = document.createTextNode(sender);
    div1.append(usernameText);

    var div2 = document.createElement("div");
    div2.setAttribute("class","col col-2");
    div2.setAttribute("data-label", "Difficulty");
    var difficultyText = document.createTextNode(diff[parseInt(difficulty)-1]);
    div2.append(difficultyText);

    var div3 = document.createElement("div");
    div3.setAttribute("class","col col-3");
    div3.setAttribute("data-label", "Action");

    var acceptButton = document.createElement("Button");
    acceptButton.setAttribute("class", "acceptButton");
    acceptButton.appendChild(document.createTextNode("Accept"));

    var refuseButton = document.createElement("Button");
    refuseButton.setAttribute("class", "acceptButton");
    refuseButton.appendChild(document.createTextNode("Refuse"));
    refuseButton.style.color = "red";

    acceptButton.onclick = function () { acceptRequest(username, sender, difficulty); };
    refuseButton.onclick = function () { refuseRequest(username, sender); };
    div3.append(acceptButton, refuseButton);
    li.append(div1, div2, div3);
    return li;
}

function sendRequest (mySelf, username, difficulty) {
    sendWebSocket(JSON.stringify(new Request(mySelf, username, "send_request", difficulty)));
}

function acceptRequest(username, sender, difficulty){
    sendWebSocket(JSON.stringify(new Request(username, sender, "play_start", difficulty)));
}

function refuseRequest(username, sender){
    sendWebSocket(JSON.stringify(new Request(username, sender, "refuse_request", "")));
    if (document.getElementById(sender+"_request"))
        document.getElementById(sender+"_request").remove();
}

function showAlert(text){
    var div = document.getElementById("alert");
    div.textContent = text;
    div.style.opacity = "1";
    var span = document.createElement("span");
    span.setAttribute("class", "closebtn");
    span.innerHTML = "&times;";
    span.onclick = function(){
            // Get the parent of <span class="closebtn"> (<div class="alert">)
            var div = this.parentElement;
            // Set the opacity of div to 0 (transparent)
            div.style.opacity = "0";
            // Hide the div after 600ms (the same amount of milliseconds it takes to fade out)
            setTimeout(function(){ div.style.opacity = "0"; }, 600);
        }
    div.appendChild(span);
}