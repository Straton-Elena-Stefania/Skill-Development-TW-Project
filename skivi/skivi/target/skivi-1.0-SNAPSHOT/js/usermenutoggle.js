function menuToggle() {
    const toggleMenu = document.querySelector('.menuUser');
    toggleMenu.classList.toggle('active');
}

function notificationsToggle() {
    const toggleMenu = document.querySelector('#notificationsDisplayBox');
    toggleMenu.classList.toggle('active');

    const notificationButton = document.getElementById("notificationButton");
    notificationButton.classList.remove("badge1");
}

function viewNotifications() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "shoutServlet?t="+new Date(), false);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var messageText = escape(document.getElementById("message").value);
    document.getElementById("message").value = "";
    xmlhttp.send("&message="+messageText);
}