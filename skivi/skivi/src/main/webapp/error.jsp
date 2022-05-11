<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>skivi - error</title>
</head>
<body>
    <h1>Error</h1>
    <a href="http://localhost:8081/">Back home</a>

    <h3> Status code: <%= request.getParameter("status") %></h3>
    <h3> Servlet name: <%= request.getParameter("servletName") %></h3>
    <h3> request URI: <%= request.getParameter("rwquestUri") %></h3>
</body>
</html>
