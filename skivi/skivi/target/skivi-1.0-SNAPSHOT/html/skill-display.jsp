<%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 5/4/2021
  Time: 3:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Skivi - ${sectionName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css?version=4">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/timeTraveling.css?version=20">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css?version=7">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors.css?version=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modal.css?version=7">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp" />

<main>

    <div id="skillSubsectionNavBar">
        <c:forEach var="subsection" items="${subsectionList}">

                <div class="skillSubsectionNavBarLinkWrapper">
                    <a href="/skivi_war_exploded/courses/${fn:escapeXml(courseId)}/${fn:escapeXml(sectionName)}/${fn:escapeXml(subsection.name)}">${fn:escapeXml(subsection.name)}</a>
                </div>

        </c:forEach>
    </div>

    <aside>
        <form method="POST">
        <ul>
            <c:if test="${user.admin == false}">
                <c:forEach var="subsection" items="${subsectionList}">
                    <li>
                        <div class="skillSubsectionLinkWrapper">
                            <a href="${pageContext.request.contextPath}/courses/${fn:escapeXml(courseId)}/${fn:escapeXml(sectionName)}/${fn:escapeXml(subsection.name)}">${fn:escapeXml(subsection.name)}</a>
                        </div>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${user.admin == true}">

                    <c:forEach var="subsection" items="${subsectionList}">

                        <li>
                            <div class="skillSubsectionLinkWrapper">
                                <a href="${pageContext.request.contextPath}/courses/${fn:escapeXml(courseId)}/${fn:escapeXml(sectionName)}/${fn:escapeXml(subsection.name)}">${fn:escapeXml(subsection.name)}</a>

                                <input type="hidden" name="subsectionRemoveSkillId" value="${fn:escapeXml(courseId)}">
                                <input type="hidden" name="subsectionRemoveSectionId" value="${fn:escapeXml(sectionId)}">
                                <input type="hidden" name="subsectionRemoveSectionName" value="${fn:escapeXml(sectionName)}">
                                <button name="removeSubsectionButton" value="${fn:escapeXml(subsection.id)}" class="removeSubsectionButton" type="submit">
                                    <i class="fa fa-times"></i>
                                </button>
                            </div>
                        </li>
                    </c:forEach>
                    <li>
                        <div class="skillSubsectionLinkWrapper">
                            <input type="hidden" name="subsectionCreateSkillId" value="${fn:escapeXml(courseId)}">
                            <input type="hidden" name="subsectionCreateSectionId" value="${fn:escapeXml(sectionId)}">
                            <input type="hidden" name="subsectionCreateSectionName" value="${fn:escapeXml(sectionName)}">
                            <input type="text" name="newSubsection" class="createNewSubsectionInput" placeholder="Add new subsection">
                            <button type="submit" class="createNewSubsectionButton" name="addNewSubsectionButton" value="${fn:escapeXml(courseId)}">
                                <i class="fa fa-plus"></i>
                            </button>
                        </div>
                    </li>

            </c:if>
        </ul>
        </form>
    </aside>

    <section id="skillsDisplay">
        <c:if test="${errorMessage != null}">
            <section class="errorWrapper skillsWrapper">
                <h2 class="errorHeader">Something went wrong</h2>
                <hr>
                <p>${fn:escapeXml(errorMessage)}</p>
            </section>
        </c:if>

        <h1 class="skillChooserHeader">${fn:escapeXml(sectionName)}</h1>
        <c:forEach var="step" items="${stepList}">
            <c:if test="${user.admin == true}">
                <form method="POST" onsubmit="copyContenteditablesToForm(${fn:escapeXml(step.stepNumber)})">
                    <label>
                        <textarea name="updateStepHeader${fn:escapeXml(step.stepNumber)}" id="updateStepHeaderTextarea${fn:escapeXml(step.stepNumber)}" style="display:none"></textarea>
                    </label>

                    <input type="hidden" name="updateStepId${fn:escapeXml(step.stepNumber)}" value="${fn:escapeXml(step.id)}">

                    <label>
                        <textarea name="updateStepDescription${fn:escapeXml(step.stepNumber)}" id="updateStepDescriptionTextarea${fn:escapeXml(step.stepNumber)}" style="display:none"></textarea>
                    </label>

                    <label>
                        <textarea name="updateStepNumber${fn:escapeXml(step.stepNumber)}" id="updateStepNumberTextarea${fn:escapeXml(step.stepNumber)}" style="display:none"></textarea>
                    </label>
                    <input type="hidden" name="skillIdNewStepBox${fn:escapeXml(step.stepNumber)}" value="${fn:escapeXml(courseId)}">
                    <input type="hidden" name="sectionIdNewStepBox${fn:escapeXml(step.stepNumber)}" value="${fn:escapeXml(sectionId)}">
                    <input type="hidden" name="subsectionIdNewStepBox${fn:escapeXml(step.stepNumber)}" value="${fn:escapeXml(subsectionId)}">
                    <div class="tutorialContentBox">
                        <c:if test="${step.stepNumber == 0}">
                            <div class="tutorialBoxDecoration">
                                <h2 class="tutorialSubsectionTitle">
                                    <c:out value="${subsectionName}"/>
                                </h2>
                                <img class="tutorialSakuraMotif" src="${pageContext.request.contextPath}/resources/images/tutorialcontentborder.png" alt="sakura border">
                            </div>
                        </c:if>

                        <span id="stepNumber${fn:escapeXml(step.stepNumber)}" class="number"><c:out value="${step.stepNumber}"/></span>

                        <h3 id="stepHeader${fn:escapeXml(step.stepNumber)}" class="stepHeader" contenteditable="true"><c:out value="${step.stepHeader}"/></h3>
                        <p id="stepDescription${fn:escapeXml(step.stepNumber)}" contenteditable="true">
                                ${fn:escapeXml(step.stepDescription)}
                        </p>
                        <img class="stepPresentationImage"
                             src="${fn:escapeXml(step.contentLink)}" alt="${fn:escapeXml(step.contentDescription)}">

                        <c:if test="${step.contentTypeId == 1}">
                            <iframe width="400" height="300" src="${fn:escapeXml(step.contentLink)}" title="YouTube video player"
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                    allowfullscreen>
                            </iframe>
                        </c:if>

                        <input type="text" id="updateStepContentLink${fn:escapeXml(step.stepNumber)}" name="newStepContentLink${fn:escapeXml(step.stepNumber)}" placeholder="Update step content Link" value="${fn:escapeXml(step.contentLink)}">
                        <div class="stepManipulationButtons">
                            <button class="updateStepButton" type="submit" name="updateStepButton" value="${fn:escapeXml(step.stepNumber)}">
                                <span>Update step</span>
                            </button>

                            <input type="hidden" name="skillIdRemoveStepBox${fn:escapeXml(step.id)}" value="${fn:escapeXml(courseId)}">
                            <button name="removeStepButton" type="submit" class="removeStepButton" value="${fn:escapeXml(step.id)}">
                                <i class="fa fa-times"></i>
                            </button>
                        </div>

                    </div>
                </form>
            </c:if>

            <c:if test="${user.admin == false}">
                <div class="tutorialContentBox">
                    <c:if test="${step.stepNumber == 0}">
                        <div class="tutorialBoxDecoration">
                            <h2 class="tutorialSubsectionTitle">
                                <c:out value="${subsectionName}"/>
                            </h2>
                            <img class="tutorialSakuraMotif" src="${pageContext.request.contextPath}/resources/images/tutorialcontentborder.png" alt="sakura border">
                        </div>
                    </c:if>

                    <c:if test="${step.stepNumber > 0}">
                        <span class="number"><c:out value="${step.stepNumber}"/></span>
                    </c:if>

                    <h3 class="stepHeader"><c:out value="${step.stepHeader}"/></h3>
                    <p>
                        <c:out value="${step.stepDescription}"/>
                    </p>
                    <img class="stepPresentationImage"
                         src="${step.contentLink}" alt="${step.contentDescription}">
                </div>
            </c:if>

            <c:set var="resources" value="${stepResourcesMap[step.id]}"/>
            <c:if test="${resources.size() > 0 && user.admin == false}">
                <div class="productsNeededShowcase">

                    <h3>You will need</h3>
                    <c:forEach var="resource" items="${resources}">
                        <div class="productNeeded">
                            <img src="${resource.resourceImageLink}" alt="${resource.resourceDescription}">
                            <button type="button"
                                    onclick="location = '${resource.resourceLink}'">Go
                                to product</button>
                            <span><c:out value="${resource.resourceDescription}"/></span>
                        </div>
                    </c:forEach>

                </div>
            </c:if>

            <c:if test="${user.admin == true}">
                <div class="productsNeededShowcase">

                    <h3>You will need</h3>
                    <c:forEach var="resource" items="${resources}">
                        <div class="productNeeded">
                            <form method="post">
                                <input type="hidden" name="skillIdRemoveResourceBox" value="${fn:escapeXml(courseId)}">
                                <button name="removeResourceButton" type="submit" class="removeSkillButton" value="${fn:escapeXml(resource.id)}">
                                    <i class="fa fa-times"></i>
                                </button>
                            </form>

                            <img src="${fn:escapeXml(resource.resourceImageLink)}" alt="${fn:escapeXml(resource.resourceDescription)}">
                            <button type="button" class="goToProductButton"
                                    onclick="location = '${fn:escapeXml(resource.resourceLink)}'">Go
                                to product</button>
                            <span class="imageTitleSpan"><c:out value="${resource.resourceDescription}"/></span>
                        </div>
                    </c:forEach>
                    <div class="productNeeded insertionForm">
                        <form method="post" class="newResourceAddingBox">
                            <div>
                                <input type="hidden" name="skillIdNewResourceBox${fn:escapeXml(step.id)}" value="${fn:escapeXml(courseId)}">
                                <input type="url" name="newResourceLinkBox${fn:escapeXml(step.id)}" placeholder="Link to where to buy">
                                <input type="url" name="newResourceImageLinkBox${fn:escapeXml(step.id)}" placeholder="Link to resource image">
                                <input type="text" name="newResourceDescriptionBox${fn:escapeXml(step.id)}" placeholder="Describe the resource">
                            </div>
                            <button type="submit" class="createNewResourceButton" name="createNewResourceButton" value="${fn:escapeXml(step.id)}">
                                <i class="fa fa-plus"></i>
                            </button>
                        </form>
                    </div>
                    <div class="productNeeded insertionForm">
                        <form method="post" class="newResourceAddingBox">
                            <span>or add existing by id: </span>
                            <div>
                                <input type="hidden" name="skillIdNewResourceBox${fn:escapeXml(step.id)}" value="${fn:escapeXml(courseId)}">
                                <input type="number" class="newIdResourceId" name="newResourceIdBox${fn:escapeXml(step.id)}" placeholder="What is the id?">
                            </div>
                            <button type="submit" class="createNewResourceButton" name="createNewResourceButton" value="${fn:escapeXml(step.id)}">
                                <i class="fa fa-plus"></i>
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>

        </c:forEach>

        <c:if test="${user.admin == true && subsectionsNr == 0}">
            <h2 class="addNewSubsectionWarning">Add a new subsection!</h2>
        </c:if>

        <!--
        <c:if test="${user.admin == true && subsectionsNr > 0}">
            <div class="tutorialContentBox">
                Submit a new step:
                <form id="postNewStep" novalidate method="POST">
                    <div>
                        <input type="hidden" id="skillIdNewStepBox${courseId}" name="skillIdNewStepBox" value="${courseId}">
                        <input type="hidden" id="sectionIdNewStepBox${sectionId}" name="sectionIdNewStepBox" value="${sectionId}">
                        <input type="hidden" id="subsectionIdNewStepBox${subsectionId}" name="subsectionIdNewStepBox" value="${subsectionId}">
                        <input type="text" id="newStepHeaderBox" name="newStepHeaderBox" placeholder="New step header">
                        <input type="number" id="newStepNumberBox" name="newStepNumberBox" placeholder="Step number">
                        <input type="text" id="newStepDescriptionBox" name="newStepDescriptionBox" placeholder="Describe the step">
                        <input type="url" id="newStepContentLink" name="newStepContentLinkBox" placeholder="Add step content Link">
                    </div>
                    <button class="stepSubmitButton" name="newStepButton" type="submit">Submit new step</button>
                </form>
            </div>
        </c:if>-->
        <c:if test="${user.admin == true && subsectionsNr > 0}">
            <span id="stepSubmissionError"></span>
            <div class="tutorialContentBox">
                <h3>Submit a new step:</h3>
                <form id="postNewStep" method="POST" novalidate>
                    <div>
                        <input type="hidden" id="skillIdNewStepBox${fn:escapeXml(courseId)}" name="skillIdNewStepBox" value="${fn:escapeXml(courseId)}">
                        <input type="hidden" id="sectionIdNewStepBox${fn:escapeXml(sectionId)}" name="sectionIdNewStepBox" value="${fn:escapeXml(sectionId)}">
                        <input type="hidden" id="subsectionIdNewStepBox${fn:escapeXml(subsectionId)}" name="subsectionIdNewStepBox" value="${fn:escapeXml(subsectionId)}">
                        <input type="text" id="newStepHeaderBox" name="newStepHeaderBox" placeholder="New step header">
                        <input type="number" id="newStepNumberBox" name="newStepNumberBox" placeholder="Step number">
                        <input type="text" id="newStepDescriptionBox" name="newStepDescriptionBox" placeholder="Describe the step">
                        <input type="url" id="newStepContentLink" name="newStepContentLinkBox" placeholder="Add step content Link">
                    </div>
                    <button class="updateStepButton" type="submit" name="newStepButton">Submit new step</button>
                </form>
            </div>
        </c:if>

        <!--
            Aici este testul cu raspunsuri, care poate sa lipseasca, depinde de curs
        -->
        <c:if test="${questionsAnswers != null}">
            <input type="hidden" name="hasTest" value="true">
            <div class="tutorialContentBox">
                <div class="tutorialBoxDecoration">
                    <div class="cardImage">
                        ✍️
                    </div>
                </div>
                <h3 class="stepHeader">Test</h3>
                <hr>
                <form>
                    <c:set var="count" value="1" scope="page" />
                    <c:forEach var="entry" items="${questionsAnswers}">
                        <p>${count}) ${fn:escapeXml(entry.key.questionText)}</p>
                        <c:forEach var="answer" items="${entry.value}">
                            <div class="testAnswer">
                                <label for="q${fn:escapeXml(entry.key.id)}-a${fn:escapeXml(answer.id)}">
                                        ${fn:escapeXml(answer.answerText)}
                                </label>
                                <div class="checkboxDiv">
                                    <input type="radio" id="q${fn:escapeXml(entry.key.id)}-a${fn:escapeXml(answer.id)}" class="questionAnswer" name="questionAnswer${count}" value="${answer.id}" required>
                                </div>
                            </div>
                        </c:forEach>
                        <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:forEach>
                </form>
            </div>
        </c:if>

        <div class="finishedTutorial">
            <form method="post">
                <c:if test="${count == null}">
                    <c:set var="count" value="0" scope="page"/>
                </c:if>
                <button onclick="submitAnswers(${fn:escapeXml(subsectionId)}, '${fn:escapeXml(courseUrl)}', ${fn:escapeXml(courseId)}, ${fn:escapeXml(count)}, '${fn:escapeXml(sessionId)}', ${fn:escapeXml(userId)})" name="finishTutorialButton" class="modalWindowOpener" type="button" data-modal="progressMadeModal"><img src="${pageContext.request.contextPath}/resources/images/ui/donebutton.png" alt="Pink round button for finishing tutorial">
                    <span id="doneButtonText">Done</span>
                </button>
            </form>
        </div>
    </section>

   <div id="progressMadeModal" class="modalWindow">
        <div id="warningPopup">
            <form>
                <div class="warningMessage">
                    <p>Congrats!</p><p> You have made progress in this skill. You have earned the <span id="achievementTitleSpan"></span> achievement.</p>
                </div>

                <div class="modalNavButtons">
                    <button type="button" id="achievementButton" class="cancelRemovalButton modalWindowCloser" data-modal="progressMadeModal">Ok</button>
                </div>
            </form>

        </div>
    </div>

    <div id="alreadyExistingAchievement" class="modalWindow">
        <div id="warningPopupConflict">
            <form>
                <div class="warningMessage">
                    <p>You have already earned the <span id="achievementTitleSpanConflict"></span> achievement.</p>
                </div>

                <div class="modalNavButtons">
                    <button type="button" id="achievementButtonConflict" class="cancelRemovalButton modalWindowCloser" data-modal="alreadyExistingAchievement">Ok</button>
                </div>
            </form>

        </div>
    </div>

    <div id="tryAgainAchievementModal" class="modalWindow">
        <div id="warningPopupTryAgain">
            <form>
                <div class="warningMessage">
                    <p>Try again! Score: <span id="numberOfCorrectAnswers"></span> out of <span id="numberOfQuestions"></span></p>
                </div>

                <div class="modalNavButtons">
                    <button type="button" id="achievementButtonTryAgain" class="cancelRemovalButton modalWindowCloser" data-modal="tryAgainAchievementModal">Ok</button>
                </div>
            </form>

        </div>
    </div>

    <div id="notImplementingAchievementsPopup" class="modalWindow">
        <div id="warningPopupNotImplementing">
            <form>
                <div class="warningMessage">
                    <p>Congrats, but no achievement is available for this tutorial!</p>
                </div>

                <div class="modalNavButtons">
                    <button type="button" id="achievementButtonNotImplemented" class="cancelRemovalButton modalWindowCloser" data-modal="notImplementingAchievementsPopup">Ok</button>
                </div>
            </form>

        </div>
    </div>

</main>

<jsp:include page="footer.jsp" />

<!--<script src="${pageContext.request.contextPath}/js/modalwindowpop.js"></script>-->
<script src="${pageContext.request.contextPath}/js/modalwindowclose.js"></script>
<script src="${pageContext.request.contextPath}/js/usermenutoggle.js"></script>
<script src="${pageContext.request.contextPath}/js/fetchanddisplaypush.js"></script>
<script src="${pageContext.request.contextPath}/js/submitnewstep.js?version=19"></script>
<script src="${pageContext.request.contextPath}/js/messagegetter.js"></script>

</body>

</html>