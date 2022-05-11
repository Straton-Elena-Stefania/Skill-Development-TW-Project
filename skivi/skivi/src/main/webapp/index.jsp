<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login to SkiVI</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/./css/header.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/./css/style.css?version=4" type="text/css">
</head>

<%
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Cache-Control","no-store");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0);
%>

<body>

<header>
    <div>
        <a class="headerLogo" href="index.jsp">
            <img alt="Homepage" src="resources/images/skividark.png">
        </a>
    </div>
    <div class="authButtons">
        <button type="button" class="homeButton" onclick="toggleLoginWindow()"><img class="bowOverlay" src="${pageContext.request.contextPath}/resources/images/bow.png" alt="bow overlay">Login</button>
        <button type="button" class="homeButton" onclick="toggleRegisterWindow()"><img class="bowOverlay" src="${pageContext.request.contextPath}/resources/images/bow.png" alt="bow overlay">Register</button>
    </div>
</header>

<main>
    <aside id="sideNavigation">
        <section id="aboutUsDescription">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <div id="aboutPageHeader">
                <h2>We are SkiVI</h2>
                <span class="aboutPageSubtitle">Skill Virtual Instructor</span>
            </div>

            <hr>
            <p>
                We believe that being a jack of all trades doesn't imply
                being a master of none. We seek expertise from professionals
                in the field to procure you only the coolest and the weirdest
                tutorials.
            </p>
            <p>
                Learn weird and cool skills and impress your friends with
                <span>SkiVI</span>!
            </p>

        </section>
    </aside>

    <section id="loginHome">

        <h1 id="loginHeader">Login or Register to SkiVI</h1>
        <a class="topnav" href="javascript:void(0)" onclick="openNav()">
            About Us
        </a>
        <div id="signUpBoxDesign">
            <div class="backgroundGrid">
                <div class="backgoundGridComponent"></div>
                <div class="backgoundGridComponent"></div>
                <div class="backgoundGridComponent"></div>
                <div class="backgoundGridComponent"></div>
                <div id="loginForm">

                    <img class="bearMotif" alt="bear" src="resources/images/bear.png">
                    <form id="registerForm" novalidate method="POST" action="sign-up">

                        <span id="okToLoginNotifier"></span>
                        <div>
                            <input type="text" id="username" name="username" placeholder="Username" required>
                            <span class="error" aria-live="polite"></span>
                            <span id="usernameErrorSpan"></span>
                            <input type="email" id="registrationEmail" name="registrationEmail" placeholder="Email address" required>
                            <span class="error" aria-live="polite"></span>
                            <span id="emailErrorSpan"></span>
                            <div class="passwordInput">
                                <input type="password" id="registerPassword" name="password" placeholder="Password" required minlength="8">
                                <button type="button" class="eye" onclick="if (password.type == 'text') password.type = 'password';
    else password.type = 'text';"><img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.iconsdb.com%2Ficons%2Fpreview%2Fpink%2Feye-xxl.png&f=1&nofb=1" alt="Eye for showing password"></button>
                            </div>
                            <span class="error" aria-live="polite"></span>
                            <span id="passwordErrorSpan"></span>

                        </div>

                        <button id="mainSignInButton" class="mainSignInButton" type="submit">Register</button>

                    </form>

                    <a id="loginTrigger" href="javascript:void(0)" onclick="toggleLoginWindow()">Already have an account?</a>
                    <form id="localSignIn" novalidate method="POST" action="login">
                        <div>
                            <input type="email" id="loginEmail" name="loginEmail" placeholder="Email address" required minlength="8">
                            <span class="error" aria-live="polite"></span>
                            <div class="passwordInput">
                                <input type="password" id="password" name="password" placeholder="Password" required>
                                <button type="button" class="eye" onclick="if (password.type == 'text') password.type = 'password';
    else password.type = 'text';"><img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.iconsdb.com%2Ficons%2Fpreview%2Fpink%2Feye-xxl.png&f=1&nofb=1" alt="Eye for showing password"></button>
                            </div>
                        </div>
                        <a id="forgotPassword" href="/forgotPassword.html">Forgot password?</a>
                        <button class="mainSignInButton" type="submit">Sign In</button>
                    </form>
                    <a id="registerTrigger" href="javascript:void(0)" onclick="toggleRegisterWindow()">
                        No account? Click here!
                    </a>
                </div>
            </div>

            <div id="rotatedBorder"></div>


        </div>
    </section>
</main>

<script src="js/loginwindowhandler.js?version=15"></script>
<script src="js/formvalidation.js?"></script>

</body>

</html>