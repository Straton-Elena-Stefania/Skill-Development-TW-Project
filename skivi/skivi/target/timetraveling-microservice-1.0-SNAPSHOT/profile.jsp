<%@ page import="com.timetraveling.models.users.UserOpenJPADAO" %><%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 4/23/2021
  Time: 4:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>skivi - profile</title>
</head>
<body>
    <h1>My profile - <%= new UserOpenJPADAO().findByID((int) request.getSession().getAttribute("id")).getUsername() %></h1>
</body>
</html>
