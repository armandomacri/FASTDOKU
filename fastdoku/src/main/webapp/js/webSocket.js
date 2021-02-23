


// Create WebSocket connection.
let ws = new Object;
if (!("WebSocket" in window)) {
    alert("This browser does not support WebSockets");
} else {
    initWebSocket();
}

function initWebSocket() {
    ws = new WebSocket("ws://localhost:8090/ws");

    ws.onopen = function(event) {
        console.log("Websocket opened.");
    };

    ws.onmessage = function(event) {
        console.log("Message received: "+ event.data);
    };

    ws.onclose = function() {
        console.log('Websocket closed');
    };
}

function waitForSocketConnection(socket, callback){
    setTimeout(
        function () {
            if (socket.readyState === 1) {
                if (callback != null){
                    callback();
                }
            } else {
                console.log("wait for connection...")
                waitForSocketConnection(socket, callback);
            }

        }, 5); // wait 5 milisecond for the connection
}


/**
 * Function used to send a message with the web socket
 * @param message   Message to send
 */
function sendWebSocket(message) {
    ws.send(message);
    console.log('Message sent');
}

/**
 * Function used to close the web socket
 */

window.onclose = function closeWebSocket () {
    ws.close();
}