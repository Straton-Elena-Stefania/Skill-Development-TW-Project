<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SkiVI - Invalid Configuration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css?version=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors.css?version=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css?version=2">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<header>
    <div>
        <a class="headerLogo" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/resources/images/skividark.png" alt="SkiVI Logo">
        </a>
    </div>


    <div class="authButtons">
        <button type="button" class="homeButton"><img class="bowOverlay" src="${pageContext.request.contextPath}/resources/images/bow.png"
                                                      alt="bow overlay">Home</button>

    </div>
</header>
<main>
    <div class="errorWrapper">

        <h1 class="statusCodeHeader"></h1>
        <hr>
        <p>Invalid configuration</p>
    </div>
</main>
<jsp:include page="footer.jsp" />
</body>
</html>