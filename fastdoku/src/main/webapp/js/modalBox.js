// Get the modal
var modal = document.getElementById('myModal');

// When the user clicks the button, open the modal
function showModal(points) {
    modal.style.display = "block";
    console.log(points);
    //lost points
    if (parseInt(points) < 0) {
        document.getElementById("result").textContent = "You have lost " + points + " points.";
        document.getElementsByClassName("modal-header")[0].style.backgroundColor = "red";
        document.getElementById("endImg").setAttribute("src", "./images/lost.png");
    } else {
        document.getElementById("result").textContent = "You have gained " + Math.abs(parseInt(points)) + " points.";
        document.getElementsByClassName("modal-header")[0].style.backgroundColor = "green";
        document.getElementById("endImg").setAttribute("src", "./images/win.png");
    }
}

function hideModal(){
    modal.style.display = "none";
}

function mainPage(){
    location.href = "";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}