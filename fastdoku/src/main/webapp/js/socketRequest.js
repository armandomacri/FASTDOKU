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
    sendWebSocket(JSON.stringify(new Request(username, "", "register_me", "")));
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
            break;

        case "new_user":
            ul.append(createUserLi(myUsername, text));
            break;

        case "remove_user":
            document.getElementById(text+"_online").remove();
            document.getElementById(text+"_request").remove();
            break;

        case "send_request":
            ul1.appendChild(requestUserLi(myUsername, sender, text));
            break;

        case "info":
            showAlert(text);
            break;

        case "error":
            alert(text);
            break;

        case "play_start":
            location.href = "./sudoku.jsp?difficulty=" + diff[parseInt(text)] + "&opponent=" + sender;
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
    sendRequestButton.appendChild(document.createTextNode("Send Request"));
    sendRequestButton.onclick = function() {
                                            //sendRequest(mySelf, username);
                                            showDialogClick('dialog', username);
                                            };
    div3.append(sendRequestButton);
    li.append(div1, div2, div3);
    return li;
}

function requestUserLi (mySelf, username, difficulty) {
    var li = document.createElement("li");
    li.setAttribute("class", "table-row");
    li.setAttribute("id", username + "_request");

    var div1 = document.createElement("div");
    div1.setAttribute("class","col col-1");
    div1.setAttribute("data-label", "Username");
    var usernameText = document.createTextNode(username);
    div1.append(usernameText);

    var div2 = document.createElement("div");
    div2.setAttribute("class","col col-2");
    div2.setAttribute("data-label", "Difficulty");
    var difficultyText = document.createTextNode(diff[parseInt(difficulty)]);
    div2.append(difficultyText);

    var div3 = document.createElement("div");
    div3.setAttribute("class","col col-3");
    div3.setAttribute("data-label", "Action");
    var acceptButton = document.createElement("Button");
    acceptButton.setAttribute("class", "acceptButton");
    acceptButton.appendChild(document.createTextNode("Accept Request"));
    acceptButton.onclick = function() { acceptRequest(mySelf, username, difficulty); };
    div3.append(acceptButton);
    li.append(div1, div2, div3);
    return li;
}

function sendRequest (mySelf, username, difficulty) {
    sendWebSocket(JSON.stringify(new Request(mySelf, username, "send_request", difficulty)));

}

function acceptRequest(mySelf, username, difficulty){
    sendWebSocket(JSON.stringify(new Request(mySelf, username, "play_start", difficulty)));
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