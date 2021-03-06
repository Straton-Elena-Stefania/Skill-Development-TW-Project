<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Skills</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/skillsStyle.css">
    <link rel="stylesheet" href="css/modal.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<header>
    <div>
        <a class="headerLogo" href="">
            <img src="resources/images/skividark.png" alt="SkiVI Logo">
        </a>
    </div>
    <div class="authButtons">
        <button type="button" class="homeButton"><img class="bowOverlay" src="resources/images/bow.png"
                                                      alt="bow overlay">Home</button>
        <div class="userNav">
            <div class="profilePictureIcon" onclick="menuToggle();">
                <img src="resources/images/cv.jpg" alt="Profile Picture of ${userName}">
            </div>
            <div class="menuUser">
                <a class="userName" href="profile.jsp"><strong>${userName}</strong></a>
                <ul>
                    <li>
                        <a class="tabButton" href="profile.jsp"><i class="fa fa-user">My profile</i></a>
                    </li>
                    <li>
                        <a class="tabButton" href="page.jsp"><i class="fa fa-cog">Settings</i></a>
                    </li>
                    <li>
                        <a class="tabButton" href="logout"><i class="fa fa-sign-out">Logout</i></a>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</header>

<main>
    <section id="skillsDisplay">
        <div class="skillChooserBaner">

            <h1 class="skillChooserHeader">Welcome</h1>
            <hr>
            <p>Select a skill to continue learning or start learning another one</p>
        </div>
        <div class="sectionMenu">

            <div class="imageContainer">
                <div class="skillContainerUpperBar">
                    <button class="heartSkillButton">
                        <i class="fa fa-heart-o"></i>
                    </button>
                    <span class="floatingFavoritedText">Added to favorite skills</span>
                    <button class="removeSkillButton modalWindowOpener" data-modal="skillRemovalWarningModal">
                        <i class="fa fa-times"></i>
                    </button>
                </div>
                <img class="modalWindowOpener" src="resources/images/timetravelpreview.gif" data-modal="modalWindow1" alt="Timetraveling skill preview photo">
                <span class="skillInfoText">Timetraveling</span>
            </div>

            <div class="imageContainer">
                <div class="skillContainerUpperBar">
                    <button class="heartSkillButton">
                        <i class="fa fa-heart-o"></i>
                    </button>
                    <span class="floatingFavoritedText">Added to favorite skills</span>
                    <button class="removeSkillButton modalWindowOpener" data-modal="skillRemovalWarningModal">
                        <i class="fa fa-times"></i>
                    </button>
                </div>
                <img class="modalWindowOpener" src="resources/images/firstaid/stetho.png" data-modal="modalWindow2" alt="First aid skill preview photo">
                <span class="skillInfoText">This is the skill!</span>
            </div>

            <div class="imageContainer">
                <div class="skillContainerUpperBar">
                    <button class="heartSkillButton">
                        <i class="fa fa-heart-o"></i>
                    </button>
                    <span class="floatingFavoritedText">Added to favorite skills</span>
                    <button class="removeSkillButton modalWindowOpener" data-modal="skillRemovalWarningModal">
                        <i class="fa fa-times"></i>
                    </button>
                </div>
                <img class="modalWindowOpener" src="resources/images/cooking/cooking2.png" data-modal="modalWindow3" alt="Cooking skill preview photo">
                <span class="skillInfoText">This is the skill!</span>
            </div>

        </div>

    </section>
    <div id="modalWindow1" class="modalWindow">
        <div class="skillModal">
            <div class="previewMotif">
                <img class="timeTravelMotifImg" src="resources/images/timetraveling/timetravelmotif.png" alt="Pink cogs and hourglasses motif photo">
            </div>

            <section class="skillDescription">
                <div class="skillDescriptionHeader">
                    <h1>
                        Learn Time Traveling
                    </h1>
                </div>
                <hr>
                <div class="skillDescriptionDescription">
                    <div>
                        <p>
                            If you were to travel back in time, where would you go? While we can't (surprise) actually
                            transport you through time and space, we can teach you how to trick your friends that you
                            are from another period by showing you how to style yourself like you are.
                        </p>
                        <p>
                            Where would you like to go?
                        </p>
                    </div>

                    <form method="get">
                        <div>
                            <!--<label class="yearLabel">
                                <input type="radio" id="year_1" name="year" value="1920">
                                1920
                            </label>
                            <label class="yearLabel">
                                <input type="radio" id="year_2" name="year" value="1950">
                                1950
                            </label>
                            <label class="yearLabel">
                                <input type="radio" id="year_3" name="year" value="1970">
                                1970
                            </label>-->
                            <c:forEach var="section" items="${sectionsList}">
                                <label class="yearLabel">
                                    <input type="radio" id="${section.id}" name="year" value="${section.name}">
                                    <c:out value="${section.name}" />
                                </label>
                            </c:forEach>
                        </div>

                        <div class="modalNavButtons">
                            <button type="button" class="closeModalButton modalWindowCloser"
                                    data-modal="modalWindow1">close</button>
                            <button type="submit" class="goToSkillButton" data-modal="modalWindow1">start</button>
                        </div>
                    </form>

                </div>

            </section>

        </div>
    </div>

    <div id="modalWindow3" class="modalWindow">
        <div class="skillModal">
            <div class="previewMotif">

                <img class="timeTravelMotifImg" src="resources/images/cooking/cookingpreview1.png" alt="Cooking photo with cherries and macaroons">
            </div>

            <section class="skillDescription">
                <div class="skillDescriptionHeader">
                    <h1>
                        Cooking secrets!
                    </h1>
                </div>
                <hr>
                <div class="skillDescriptionDescription">
                    <div>
                        <p>
                            Like any artistic field, culinary art is for those who
                            have discovered their passion for cooking and want to
                            exhibit it, to display it, presenting dishes in unique
                            forms, but it is also for novices and amateurs, people
                            who want to acquire more and more this type of qualities.
                            Going to restaurants or ordering food online from the
                            internet is not always the best idea. It's a good idea
                            when we invite someone and we want to surprise
                            them, to do something unusual (pizza evening
                            with friends or dinner with a special person),
                            but most of the time it is very useful to cook.
                            Knowing how to cook is a necessity. It is not always safe to order
                            food online, it is not always healthy. The cooking
                            course will help you how to have dishes prepared by yourself.
                        </p>
                        <p>
                            Where would you like to go?
                        </p>
                    </div>

                    <form>
                        <div>
                            <label class="yearLabel">
                                <input type="radio" id="cooking_1" name="cooking" value="CookingClassesForBeginners!">
                                Cooking classes!
                            </label>
                            <label class="yearLabel">
                                <input type="radio" id="cooking_2" name="cooking" value="CookingRecipesForBeginners!">
                                Cooking recipes!
                            </label>
                        </div>

                        <div class="modalNavButtons">
                            <button type="button" class="closeModalButton modalWindowCloser"
                                    data-modal="modalWindow3">close</button>
                            <button type="submit" class="goToSkillButton" data-modal="modalWindow3">start</button>
                        </div>
                    </form>

                </div>

            </section>

        </div>
    </div>

    <div id="skillRemovalWarningModal" class="modalWindow">
        <div id="warningPopup">
            <form>
                <div class="warningMessage">
                    <p>This action will remove the skill from the skills you are interested in.</p>
                    <p>Your achievements and progress will be lost.</p>
                    <p>You may sign up for this skill again afterwards, but you will have to start from scratch.</p>
                    <p>Proceed?</p>
                </div>

                <div class="modalNavButtons">
                    <button type="submit" class="confirmRemovalButton " data-modal="skillRemovalWarningModal">Ok</button>
                    <button type="button" class="cancelRemovalButton modalWindowCloser" data-modal="skillRemovalWarningModal">Go
                        back</button>
                </div>
            </form>

        </div>
    </div>

    <div id="modalWindow2" class="modalWindow">
        <div class="skillModal">
            <div class="previewMotif">
                <img class="timeTravelMotifImg" src="resources/images/firstaid/firstaidpreview2.png" alt="Small heart with pulse pattern">
            </div>

            <section class="skillDescription">
                <div class="skillDescriptionHeader">
                    <h1>
                        First Aid
                    </h1>
                </div>
                <hr>
                <div class="skillDescriptionDescription">
                    <div>
                        <p>
                            It's important to know how to intervene, but it's just as
                            important to know when it's best to stand aside. Any first
                            aid intervention starts from a common scenario that later
                            branches into several possible situations - the victim may
                            or may not be conscious, respectively, if he is unconscious,
                            he may breathe. In our course we will teach you how to treat
                            unconscious people, more precisely 2 medical cases:
                        </p>
                        <p>
                            Where would you like to go?
                        </p>
                    </div>

                    <form>
                        <div>
                            <label class="yearLabel">
                                <input type="radio" id="aid_1" name="aid" value="CardioRespiratoryArrest">
                                Cardio-Respiratory Arrest
                            </label>
                            <label class="yearLabel">
                                <input type="radio" id="aid_2" name="aid" value="LossOfConsciousness">
                                Loss of consciousness
                            </label>
                        </div>

                        <div class="modalNavButtons">
                            <button type="button" class="closeModalButton modalWindowCloser"
                                    data-modal="modalWindow2">close</button>
                            <button type="submit" class="goToSkillButton" data-modal="modalWindow2">start</button>
                        </div>
                    </form>

                </div>

            </section>

        </div>
    </div>

</main>
<footer>
    <section class="footerLeft">
        <img src="resources/images/skivi.png" alt="White logo of SkiVI">
        <h3>About<span> SkiVi</span></h3>

        <p class="footerLinks">
            <a href="#">Home</a>|
            <a href="#">About</a>|
            <a href="#">Contact</a>
        </p>

        <p class="footerProjectName">?? 2021 SkiVi</p>
    </section>

    <section class="footerCenter">
        <h3>
            Contact data
        </h3>
        <div>
            <i class="fa fa-phone"></i>
            <p>0752128223</p>
        </div>
        <div>
            <i class="fa fa-inbox"></i>
            <p><a href="#">elena.straton@info.uaic.ro</a></p>
        </div>
    </section>
    <section class="footerRight">
        <h3>About the project</h3>
        <p class="footerProjectAbout">
            To design a modular Web application with the role of virtual instructor to offer sets of activities and training
            resources aimed at learning / deepening skills such as wire dancing, dead languages (eg, Latin, Aramaic),
            self-defense styles, singing to a musical instrument (harpsichord, Teremin, tuba), survival (in the jungle, in
            the home, cosmic vehicle, polluted neighborhood, ...), resuscitation procedures, making origami and others.

            Each skill will be implemented by an independent Web microservice that will be updated automatically or upon
            request. At least 3 such microservices will be developed as case studies.

            The basic functionalities of the system (instruction management, users interested in learning skills, etc.) will
            be offered via an API adopting the REST or GraphQL paradigm.
        </p>

    </section>
</footer>


<script src="js/modalwindowpop.js"></script>
<script src="js/modalwindowclose.js"></script>
<script src="js/usermenutoggle.js"></script>
<script src="js/floatingFavoritedText.js"></script>
</body>

</html>