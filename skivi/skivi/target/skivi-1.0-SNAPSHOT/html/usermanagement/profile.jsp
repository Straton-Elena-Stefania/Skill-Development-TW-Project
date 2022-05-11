<%@ page import="com.timetraveling.models.users.UserHibernateRepository" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css?version=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css?version=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pageProfile.css?version=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Page-Profile</title>
</head>

<body>

<jsp:include page="../header.jsp" />

<main>
    <div class="wrapper">
        <div class="profileNavigation">
            <img src="<%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getProfilePicture() %>" alt="user" width="100">
            <form id="profilePictureForm" action="profile" method="post" enctype="multipart/form-data" data-id="<%=request.getSession().getAttribute("id")%>">
                <!--<input id="profilePictureInput" type="file" name="file" accept="image/*">-->
                <div class="profilePictureUploadSpace">
                    <input type="file" name="file" size="60" />
                    <input name="submitPicture" type="submit" value="Upload" />
                </div>
            </form>
            <span><%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getUsername()%></span>
            <nav id="settingsBar">
                <a id="profileInfoButton" class="tabButton active" href="">Info</a>
                <a id="achievementReference" class="tabButton" href="">Achievements</a>
            </nav>
        </div><!-- endLeft -->

        <div class="profileInfoTab">
            <div id="infoTab" class="tab">
                <div class="info">
                    <h3>Information</h3>
                    <div class="userProfileData">
                        <div class="data">
                            <h4>Email</h4>
                            <p><%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getEmail()%></p>
                        </div>
                        <div class="data">
                            <h4>Description</h4>
                            <p><%=new UserHibernateRepository().findByID((int) request.getSession().getAttribute("id")).getDescription()%></p>
                        </div>
                    </div>
                </div>

                <div class="projects">
                    <h3>Skills</h3>
                    <div class="userSkillsProfileData">

                        <div class="data">
                            <form id="skillForm" action="profile" method="post" data-id="<%=request.getSession().getAttribute("id")%>">
                                <label>
                                    <span>Add new Skill:</span>
                                    <input type="text" placeholder="Add skills" class="addSkillInput" id="skillInput" name="skillModel" list="skillModel-list" autocomplete="off">

                                    <datalist data-name="skillsToAdd" id="skillModel-list">
                                        <c:forEach var="skill" items="${skills}">
                                            <option class="skillAddingOption" data-value="${skill.id}">${skill.name}</option>
                                        </c:forEach>
                                    </datalist>
                                </label>

                                <input id="addSkill" name="skillAddButton" class="addButton" type="submit">
                            </form>
                        </div>

                        <div class="data">
                            <ul id="skillList" class="list">
                                <c:forEach var="userSkill" items="${userSkills}">
                                    <li>
                                        <c:out value="${userSkill.name}"/>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                    </div>
                </div>
            </div>

            <div id="scoresTab" class="tab hideTab">
                <h4>My achievements:</h4>
                <ul>
                    <c:forEach var="userAchievement" items="${userAchievements}">
                        <li>
                            <c:out value="${userAchievement.title}"/>
                        </li>
                    </c:forEach>
                </ul>

                <h4>Favorite skills:</h4>
                <ul>
                    <c:forEach var="favoritedSkill" items="${favoritedSkills}">
                        <li>
                            <c:out value="${favoritedSkill.name}"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </div> <!-- endRight -->

    </div>
</main>

<jsp:include page="../footer.jsp" />

<script src="${pageContext.request.contextPath}/js/modalwindowpop.js"></script>
<script src="${pageContext.request.contextPath}/js/modalwindowclose.js"></script>
<script src="${pageContext.request.contextPath}/js/usermenutoggle.js"></script>
<script src="${pageContext.request.contextPath}/js/pageProfile.js?version=2"></script>
<script src="${pageContext.request.contextPath}/js/fetchanddisplaypush.js"></script>
<script src="${pageContext.request.contextPath}/js/messagegetter.js"></script>

</body>
</html>