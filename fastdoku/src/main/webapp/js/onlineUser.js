
function showDialogClick(dialogId, opponent) {
    var dialog = document.getElementById(dialogId);
    var dialogBox = document.getElementById(dialogId + "-box");
    document.getElementById("userToChallenge").innerHTML = opponent;
    dialogBox.focus();
    dialog.style.opacity = "0";
    dialogBox.style.marginTop = "-500px";
    dialog.style.display = "block";
    dialog.style.visibility = "visible";

    // to view and move the dialog to the correct position after it set visible
    setTimeout(function () {
        dialog.style.opacity = "1";
        dialogBox.style.marginTop = "64px";
    }, 200);
}

// start new game
function startGameButtonClick() {
    var difficulties = document.getElementsByName('difficulty');
    // difficulty:
    //  0 expert
    //  1 hard
    //  2 normal
    //  3 easy
    //  4 very easy

    var difficulty = false;

    // get difficulty value
    for (var i = 0; i < difficulties.length; i++) {
        if (difficulties[i].checked) {
            difficulty = i;
            difficulties[i].checked = false;
            break;
        }
    }

    if (!difficulty){
        alert("Select difficulty.");
        return ;
    }

    hideDialogButtonClick('dialog');
    sendRequest(document.getElementById("loggedUsername").textContent, document.getElementById("userToChallenge").textContent, difficulty);
    //disable request button
    var buttons = document.getElementsByClassName("requestButton");
    for (var i = 0; i < buttons.length; i++) {
        if (!buttons[i].disabled && buttons[i].click){
            buttons[i].disabled = true;
            buttons[i].textContent = "Request Pending";
            buttons[i].style.color = "gray";
            break;
        }
    }
}

function hideDialogButtonClick(dialogId) {
    var dialog = document.getElementById(dialogId);
    var dialogBox = document.getElementById(dialogId + "-box");
    dialog.style.opacity = "0";
    dialogBox.style.marginTop = "-500px";

    setTimeout(function () {
        dialog.style.visibility = "collapse";
        //dialog.style.display = "none";
    }, 500);
}