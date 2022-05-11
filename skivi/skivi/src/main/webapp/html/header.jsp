<%@ page import="com.timetraveling.models.users.UserHibernateRepository" %>
<header>
    <div>
        <a class="headerLogo" href="${pageContext.request.contextPath}/home">
            <img src="${pageContext.request.contextPath}/resources/images/skividark.png" alt="SkiVI Logo">
        </a>
    </div>


    <div class="authButtons">
        <button type="button" class="homeButton"><img class="bowOverlay" src="${pageContext.request.contextPath}/resources/images/bow.png"
                                                      alt="bow overlay">Home</button>

        <button type="button" id="notificationButton" class="homeButton" data-badge="" onclick="notificationsToggle();"><img class="bowOverlay" src="${pageContext.request.contextPath}/resources/images/bow.png"
                                                                                       alt="bow overlay">Notifications</button>

        <!--<h1 onload="getMessages();"></h1>-->

        <div id="notificationsDisplayBox">
            <ul>
                <li>
                    <% if (application.getAttribute("messages") != null) {%>
                    <%= application.getAttribute("messages")%>
                    <% }%>
                </li>
            </ul>
        </div>

        <div class="userNav">
            <div class="profilePictureIcon" onclick="menuToggle();">
                <img src="<%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getProfilePicture() %>" alt="Profile Picture of ${userName}">
            </div>
            <div class="menuUser">
                <a class="userName" href="${pageContext.request.contextPath}/profile"><strong>${userName}</strong></a>
                <ul>
                    <li>
                        <a class="tabButton" href="${pageContext.request.contextPath}/profile"><i class="fa fa-user">My profile</i></a>
                    </li>
                    <li>
                        <a class="tabButton" href="${pageContext.request.contextPath}/settings"><i class="fa fa-cog">Settings</i></a>
                    </li>
                    <li>
                        <a class="tabButton" href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out">Logout</i></a>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</header>


