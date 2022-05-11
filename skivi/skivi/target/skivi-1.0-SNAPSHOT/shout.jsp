<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/notification.css">
    <title>JSP Page</title>
</head>
<!--<h1 onload="getMessages();">SHOUT-OUT!</h1>
<form>
    <table>
        <tr>
            <td>Your name:</td>
            <td><input type="text" id="name" name="name"/></td>
        </tr>
        <tr>
            <td>Your shout:</td>
            <td><input type="text" id="message" name="message" /></td>
        </tr>
        <tr>
            <td><input type="button" onclick="postMessage();" value="SHOUT" /></td>
        </tr>
    </table>
</form>
<button class="badge1" data-badge="6">Badge Notification Example</button>
<br>
<a href="" class="badge1" data-badge="27">Badge Notification Example</a>
<h2> Current Shouts </h2>

<div id="content">
    <% if (application.getAttribute("messages") != null) {%>
    <%= application.getAttribute("messages")%>
    <% }%>
</div>
<script>
    function postMessage() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "shoutServlet?t="+new Date(), false);
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        var nameText = escape(document.getElementById("name").value);
        var messageText = escape(document.getElementById("message").value);
        document.getElementById("message").value = "";
        xmlhttp.send("name="+nameText+"&message="+messageText);
    }
    var messagesWaiting = false;
    function getMessages(){
        if(!messagesWaiting){
            messagesWaiting = true;
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange=function(){
                if (xmlhttp.readyState==4 && xmlhttp.status==200) {
                    messagesWaiting = false;
                    var contentElement = document.getElementById("content");
                    contentElement.innerHTML = xmlhttp.responseText + contentElement.innerHTML;
                }
            }
            xmlhttp.open("GET", "shoutServlet?t="+new Date(), true);
            xmlhttp.send();
        }
    }
    setInterval(getMessages, 1000);
</script>
</html>
