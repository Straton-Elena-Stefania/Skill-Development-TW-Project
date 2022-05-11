/*localStorage.setItem("messagesWaiting", false);
function getMessages(){
    if(!localStorage.getItem("messagesWaiting")){
        localStorage.setItem("messagesWaiting", true);
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange=function(){
            if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                localStorage.setItem("messagesWaiting", false);
                var contentElement = document.getElementById("notificationsDisplayBox");
                contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;

                const notificationButton = document.getElementById("notificationButton");
                notificationButton.classList.add("badge1");
            }
        }

        xmlhttp.open("GET", "shoutServlet", true);
        xmlhttp.send();
    }
}

setInterval(getMessages, 1000);*/

var messagesWaiting = false;
function getMessages() {
    if (messagesWaiting == false) {
        messagesWaiting = true;

        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                messagesWaiting = false;
                var contentElement = document.getElementById("notificationsDisplayBox");
                contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;

                const notificationButton = document.getElementById("notificationButton");
                notificationButton.classList.add("badge1");
            }
        }

        xmlhttp.open("GET", "/skivi_war_exploded/shoutServlet", true);
        xmlhttp.send();
    }
}

setInterval(getMessages, 1000);