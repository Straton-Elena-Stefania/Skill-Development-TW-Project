<%@ page import="com.timetraveling.models.users.UserHibernateRepository" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css?version=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pageSettings.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
    <title>Setting-Page</title>
</head>

<body>
<jsp:include page="../header.jsp" />

<main>

    <section class="container">

        <div class="leftbox">
            <nav id="settingsBar">
                <a class="tabButton active"><i class="fa fa-user"><span class="hideButtonContent">Profile</span></i></a>
                <a class="tabButton"><i class="fa fa-cog"><span class="hideButtonContent">Settings</span></i></a>
            </nav>
        </div> <!--  end leftbox-->

        <div class="rectangleToPlayWith">
            <span class="rectangleToPlayDecoration">SkiVI</span>
        </div>

        <div id="settingsContainer" class="rightbox">
            <div id="profileTab" class="profile tab">
                <h1>Personal Info</h1>
                <div>
                    <h2>Account</h2>
                    <form novalidate method="POST" action="settings">
                        <label class="label">
                            <span class="labelTitle">Full name</span>
                            <input type="text" id="username" name="username" value="<%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getUsername()%>"/>
                            <span class="error" aria-live="polite"></span>
                        </label>
                        <label class="label">
                            <span class="labelTitle">Email</span>
                            <input type="email" id="email" name="email" value="<%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getEmail()%>"/>
                            <span class="error" aria-live="polite"></span>
                        </label>
                        <label class="description">
                            <span class="labelTitle">Description</span>
                            <input type="text" id="description" name="description" value="<%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getDescription()%>"/>
                            <span class="error" aria-live="polite"></span>
                        </label>

                        <button class="updateSettingsButton" name="infoButton" type="submit">Update</button>
                    </form>
                </div>
                <div>
                    <h2>
                        Change Password
                    </h2>
                    <form id="passwordChangeForm" novalidate method="POST" action="settings">
                        <div class="passwordArea">
                            <label class="label">
                                <span class="labelTitle">Old Password</span>
                                <input type="password" id="oldPassword" name="oldPassword">
                            </label>
                            <button type="button" class="eye" onclick="if (oldPassword.type == 'text') oldPassword.type = 'password';
      else oldPassword.type = 'text';"><img
                                    src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.iconsdb.com%2Ficons%2Fpreview%2Fpink%2Feye-xxl.png&f=1&nofb=1"
                                    alt="Eye button for showing password"></button>
                            <span class="error" aria-live="polite"></span>
                        </div>

                        <div class="passwordArea">
                            <label class="label">
                                <span class="labelTitle">New Password</span>
                                <input type="password" id="newPassword" name="newPassword">

                            </label>
                            <button type="button" class="eye" onclick="if (newPassword.type == 'text') newPassword.type = 'password';
      else newPassword.type = 'text';"><img
                                    src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.iconsdb.com%2Ficons%2Fpreview%2Fpink%2Feye-xxl.png&f=1&nofb=1"
                                    alt="Eye button for showing password"></button>
                            <span class="error" aria-live="polite"></span>
                        </div>

                        <button class="updateSettingsButton" name="passwordButton" value="userInfo" type="submit">Update</button>
                    </form>
                </div>

            </div> <!--  end rightbox-->

            <div id="settingsTab" class="profile tab hideTab">
                <!--  al doilea tab-->
                <h1>Notification Settings</h1>
                <h2>Email Notifications</h2>
                <form>
                    <div>
                        <input type="radio" id="allow" name="notification" value="yes">
                        <label for="allow">Yes</label>

                        <input type="radio" id="deny" name="notification" value="no">
                        <label for="deny">No</label>
                    </div>

                    <button class="updateSettingsButton">Update</button>
                </form>
            </div>
        </div>
    </section>
</main>


<jsp:include page="../footer.jsp" />

<script src="${pageContext.request.contextPath}/js/modalwindowpop.js"></script>
<script src="${pageContext.request.contextPath}/js/modalwindowclose.js"></script>
<script src="${pageContext.request.contextPath}/js/usermenutoggle.js"></script>
<script src="${pageContext.request.contextPath}/js/pageSettings.js"></script>
<script src="${pageContext.request.contextPath}/js/fetchanddisplaypush.js"></script>
<script src="${pageContext.request.contextPath}/js/messagegetter.js"></script>

</body>

</html>
