<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SkiVI - 404</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css?version=2">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorpage.css?version=3">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors.css?version=3">
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
    <section class="errorWrapper">

        <h1 class="statusCodeHeader">404</h1>
        <hr>
        <p>Resource not found</p>
    </section>
</main>
<jsp:include page="footer.jsp" />
</body>
</html>