<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Skills</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css?version=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css?version=3">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/skillsStyle.css?version=8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css?version=6">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<!--<header>
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
</header>-->
<jsp:include page="html/header.jsp" />

<main>
    <section id="skillsDisplay">
        <div class="skillChooserBaner">

            <h1 class="skillChooserHeader">Welcome</h1>
            <hr>
            <p>Select a skill to continue learning or start learning another one</p>
        </div>
        <div class="sectionMenu">

            <c:if test="${skillSectionsMap != null}">
                <c:set var="count2" value="1" scope="page" />
                <c:forEach var="skillModel" items="${skillSectionsMap}">
                    <div class="imageContainer">
                        <div class="skillContainerUpperBar">
                            <button onclick="addToFavorites('${session}', ${userId}, ${skillModel.key.id});" class="heartSkillButton">
                                <i class="fa fa-heart-o"></i>
                            </button>
                            <span class="floatingFavoritedText">Added to favorite skills</span>
                                <button onclick="setSkillToRemove(${skillModel.key.id})" class="removeSkillButton modalWindowOpener" data-modal="skillRemovalWarningModal">
                                    <i class="fa fa-times"></i>
                                </button>
                        </div>
                        <c:if test="${fn:length(skillModel.value) == 0}">
                            <span class="unavailableCourseWarning">
                                Course unavailable
                            </span>
                            <img class="modalWindowOpener blurryImage" src="${pageContext.request.contextPath}/${skillModel.key.imagePresentationLink}"
                                 data-modal="modalWindow${count2}"
                                 alt="${skillModel.key.name} skill preview photo">
                            <span class="skillInfoText">${skillModel.key.name}</span>
                        </c:if>
                        <c:if test="${fn:length(skillModel.value) > 0}">
                            <img class="modalWindowOpener" src="${pageContext.request.contextPath}/${skillModel.key.imagePresentationLink}"
                                 data-modal="modalWindow${count2}"
                                 alt="${skillModel.key.name} skill preview photo">
                            <span class="skillInfoText">${skillModel.key.name}</span>
                        </c:if>
                    </div>
                    <c:set var="count2" value="${count2 + 1}" scope="page"/>
                </c:forEach>
            </c:if>-->

            <!--<c:if test="${timetravelingSections != null}">
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
                    <img class="modalWindowOpener" src="${pageContext.request.contextPath}/resources/images/timetravelpreview.gif" data-modal="modalWindow1" alt="Timetraveling skillModel preview photo">
                    <span class="skillInfoText">Timetraveling</span>
                </div>
            </c:if>

            <c:if test="${firstaidSections != null}">
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
                    <img class="modalWindowOpener" src="resources/images/firstaid/stetho.png" data-modal="modalWindow2" alt="First aid skillModel preview photo">
                    <span class="skillInfoText">This is the skillModel!</span>
                </div>
            </c:if>

            <c:if test="${cookingSections != null}">
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
                    <img class="modalWindowOpener" src="resources/images/cooking/cooking2.png" data-modal="modalWindow3" alt="Cooking skillModel preview photo">
                    <span class="skillInfoText">This is the skillModel!</span>
                </div>
            </c:if>-->



        </div>

    </section>

    <!--<c:if test="${timetravelingSections != null}">
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
                                <c:forEach var="section" items="${timetravelingSections}">
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
    </c:if>-->

    <c:if test="${skillSections != null}">
        <c:set var="count" value="1" scope="page" />
        <c:forEach var="entry" items="${skillSections}">
            <div id="modalWindow${count}" class="modalWindow">
                <div class="skillModal">
                    <div class="previewMotif">
                        <img class="timeTravelMotifImg" src="${entry.key.sideimageLink}" alt="Cooking photo with cherries and macaroons">
                    </div>

                    <section class="skillDescription">
                        <div class="skillDescriptionHeader">
                            <h1>
                                ${entry.key.name} secrets!
                            </h1>
                        </div>
                        <hr>
                        <div class="skillDescriptionDescription">
                            <div>
                                <p>
                                    ${entry.key.description}
                                </p>
                            </div>

                            <c:if test="${user.admin == true}">
                                <form method="POST" class="addNewSectionForm">

                                    <c:if test="${fn:length(entry.value) > 0}">
                                        <input type="hidden" name="sectionCreateSkillId" value="${entry.key.id}">
                                        <input type="text" name="newSectionNameText" placeholder="Add new section">
                                        <button type="submit" class="addNewSectionButton" name="addNewSectionButton">
                                            <i class="fa fa-plus-square"></i>
                                        </button>
                                    </c:if>

                                </form>
                            </c:if>

                            <form method="get" class="selectSectionForm">
                                <div>
                                    <c:if test="${fn:length(entry.value) == 0}">
                                        Add a new section to continue
                                    </c:if>
                                    <c:forEach var="section" items="${entry.value}">

                                        <input type="hidden" name="section${section.name}" value="${section.id}">
                                        <label class="yearLabel">
                                            <input type="radio" name="year" value="${entry.key.id}/${section.name}">
                                            <c:out value="${section.name}" />
                                        </label>

                                    </c:forEach>
                                </div>

                                <div class="modalNavButtons">
                                    <button type="button" class="closeModalButton modalWindowCloser"
                                            data-modal="modalWindow${count}">close</button>
                                    <input type="hidden" value="${fn:length(entry.value)}">
                                    <c:if test="${fn:length(entry.value) == 0}">
                                        <button class="goToSkillButton goToSkillIsDeactivated" data-modal="modalWindow${count}" disabled>start</button>
                                    </c:if>
                                    <c:if test="${fn:length(entry.value) > 0}">
                                        <button type="submit" class="goToSkillButton" data-modal="modalWindow${count}">start</button>
                                    </c:if>
                                </div>
                            </form>

                        </div>

                    </section>

                </div>
            </div>
            <c:set var="count" value="${count + 1}" scope="page"/>
        </c:forEach>
    </c:if>

    <div id="skillRemovalWarningModal" class="modalWindow">
        <div id="warningPopup">
            <form method="get">
                <div class="warningMessage">
                    <p>This action will remove the skill from the skills you are interested in.</p>
                    <p>Your achievements and progress will be lost.</p>
                    <p>You may sign up for this skill again afterwards, but you will have to start from scratch.</p>
                    <p>Proceed?</p>
                </div>

                <div class="modalNavButtons">
                    <input id="whatToRemove" name="skillRemovalOption" type="hidden">
                    <button type="submit" class="confirmRemovalButton " data-modal="skillRemovalWarningModal">Ok</button>
                    <button onclick="unsetSkillToRemove()" type="button" class="cancelRemovalButton modalWindowCloser" data-modal="skillRemovalWarningModal">Go
                        back</button>
                </div>
            </form>

        </div>
    </div>


</main>

<jsp:include page="html/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/sanitizeagainstxss.js"></script>
<script src="js/modalwindowpop.js"></script>
<script src="js/modalwindowclose.js"></script>
<script src="js/usermenutoggle.js"></script>
<script src="js/floatingFavoritedText.js?version=3"></script>
<script src="js/fetchanddisplaypush.js?version=2"></script>
<script src="js/removeskillhomepage.js"></script>

<!--<script>
    function postMessage() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", "shoutServlet?t=" + new Date(), false);
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        var nameText = escape(document.getElementById("name").value);
        var messageText = escape(document.getElementById("message").value);
        document.getElementById("message").value = "";
        xmlhttp.send("name=" + nameText + "&message=" + messageText);
    }
</script>-->

<script src="${pageContext.request.contextPath}/js/messagegetter.js"></script>

</body>

</html>