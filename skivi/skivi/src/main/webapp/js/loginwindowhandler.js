let mql = window.matchMedia('(max-width: 600px)');

function openNav() {
    if (mql.matches) {
        document.getElementById("sideNavigation").style.width = "100vw";
        document.getElementById("loginHome").style.marginLeft = "100vw";
    } else {
        document.getElementById("sideNavigation").style.width = "50vw";
        document.getElementById("loginHome").style.marginLeft = "50vw";
        document.getElementById("signUpBoxDesign").style.transform += "translate(-50%, 0)";
        var ret = document.getElementById("signUpBoxDesign").style.transform.replace('translate(0px)','');
        document.getElementById("signUpBoxDesign").style.transform = ret;
        document.getElementById("loginHeader").style.textAlign = "right";
        document.getElementById("loginHeader").style.width = "70%";
        document.getElementById("loginHeader").style.left = "0";
    }
}

function closeNav() {
    if (mql.matches) {
        document.getElementById("sideNavigation").style.width = "0";
        document.getElementById("loginHome").style.margin = "auto";
    } else {
        document.getElementById("sideNavigation").style.width = "0";
        document.getElementById("loginHome").style.margin = "auto";
        document.getElementById("signUpBoxDesign").style.transform += "translate(0, 0)";
        var ret = document.getElementById("signUpBoxDesign").style.transform.replace('translate(-50%)','');
    
        console.log(ret);
        document.getElementById("signUpBoxDesign").style.transform = ret;
        document.getElementById("loginHeader").style.textAlign = "left";
        document.getElementById("loginHeader").style.width = "100%";
        document.getElementById("loginHeader").style.left = "15%";
    }
    
}
/*
    POATE FI IMBUNATATIT!!!
*/
function toggleRegisterWindow() {
    var old = document.getElementById("localSignIn");
    var newdiv = document.getElementById("registerForm");

    old.style.display = "none";
    newdiv.style.display = "flex";
    newdiv.style.flexDirection = "column";
    if (!mql.matches) {
        signUpBoxDesign.style.transform = "scale(1, 1.2)";
        console.log("alknslsln");
    } else {
        signUpBoxDesign.style.transform = "scale(0.6, 0.8)";
    }
    newdiv.style.justifyContent = "center";

    document.getElementById("loginTrigger").style.display = "block";
    document.getElementById("registerTrigger").style.display = "none";
}

function toggleLoginWindow() {
    const old = document.getElementById("registerForm");
    const newdiv = document.getElementById("localSignIn");

    old.style.display = "none";
    newdiv.style.display = "flex";
    newdiv.style.flexDirection = "column";
    if (!mql.matches) {
        signUpBoxDesign.style.transform = "scale(1, 1)";
    } else {
        signUpBoxDesign.style.transform = "scale(0.6, 0.6)";
    }
    newdiv.style.justifyContent = "center";

    document.getElementById("registerTrigger").style.display = "block";
    document.getElementById("loginTrigger").style.display = "none";
}

document.getElementById("mainSignInButton").addEventListener("click", function(event){
    let username = document.getElementById("username").value;
    let email = document.getElementById("registrationEmail").value;
    let password = document.getElementById("registerPassword").value;

    event.preventDefault();

    //let xhr = new XMLHttpRequest();
    let testSubmissionUrl = "/skivi_war_exploded/validation";

    /*xhr.open("POST", testSubmissionUrl, true);

    xhr.setRequestHeader("Content-Type", "application/json");*/

    let answer = JSON.stringify({  "username": username,
        "email": email,
        "password": password
    });

    fetch(testSubmissionUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "sessionId",
            'userId': "userId",
        },
        body: answer
    } )
        .then(resp => {
            if (resp.ok) {
                console.log("ok");
                resp.text().then(function (textResponse) {
                    console.log("ugiyudytdyttydty");
                    let jsonResponse = JSON.parse(textResponse);
                    let emailError = jsonResponse.emailError;
                    let usernameError = jsonResponse.usernameError;
                    let passwordError = jsonResponse.passwordError;

                    if (emailError !== "null") {
                        let emailErrorSpan = document.getElementById("emailErrorSpan");
                        event.preventDefault();
                        emailErrorSpan.innerHTML = emailError;
                    } else {
                        emailErrorSpan.innerHTML = null;
                    }

                    if (usernameError !== "null") {
                        let usernameErrorSpan = document.getElementById("usernameErrorSpan");
                        usernameErrorSpan.innerHTML = usernameError;
                        event.preventDefault();
                    } else {
                        usernameErrorSpan.innerHTML = null;
                    }

                    if (passwordError !== "null") {
                        let passwordErrorSpan = document.getElementById("passwordErrorSpan");
                        passwordErrorSpan.innerHTML = passwordError;
                        event.preventDefault();
                    } else {
                        passwordErrorSpan.innerHTML = null;
                    }

                    if (passwordError === "null" && emailError === "null" && usernameError === "null") {
                        let okToLoginSpan = document.getElementById("okToLoginNotifier");
                        okToLoginSpan.innerHTML = "Ok to login";
                    } else {
                        let okToLoginSpan = document.getElementById("okToLoginNotifier");
                        okToLoginSpan.innerHTML = null;
                    }
                })
            } else {
                console.log("error in processing ajax call!");
            }
        })
        .catch(err => {
            event.preventDefault();
            console.log(err);
        })

    /*xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("ugiyudytdyttydty");
            let jsonResponse = JSON.parse(xhr.responseText);
            let emailError = jsonResponse.emailError;
            let usernameError = jsonResponse.usernameError;
            let passwordError = jsonResponse.passwordError;

            if (emailError !== "null") {
                let emailErrorSpan = document.getElementById("emailErrorSpan");
                okEverything = false;
                emailErrorSpan.innerHTML = emailError;
            } else {
                emailErrorSpan.innerHTML = null;
            }

            if (usernameError !== "null") {
                let usernameErrorSpan = document.getElementById("usernameErrorSpan");
                usernameErrorSpan.innerHTML = usernameError;
                okEverything = false;
            } else {
                usernameErrorSpan.innerHTML = null;
            }

            if (passwordError !== "null") {
                let passwordErrorSpan = document.getElementById("passwordErrorSpan");
                passwordErrorSpan.innerHTML = passwordError;
                okEverything = false;
            } else {
                passwordErrorSpan.innerHTML = null;
            }

            if (okEverything) {
                let evt = document.createEvent("HTMLEvents");
                evt.initEvent(event.type, event.bubbles, event.cancelable);
                evt["useDefault"] = true;
                //Add other "e" attributes like screenX, pageX, etc...
                this.dispatchEvent(evt);
            }
        } else {
            console.log("!!");
        }
    };*/

    //xhr.send(answer);
});

function findDuplicatesInForm() {
    let email = document.getElementById("username").value;
    let username = document.getElementById("registrationEmail").value;
    let password = document.getElementById("registerPassword").value;

    let xhr = new XMLHttpRequest();
    let testSubmissionUrl = "http://localhost:8088/skivi_war_exploded/validation";

    xhr.open("POST", testSubmissionUrl, true);

    xhr.setRequestHeader("Content-Type", "application/json");
    //xhr.setRequestHeader("Access-Control-Request-Headers", "*");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let jsonResponse = JSON.parse(xhr.responseText);
            let emailError = jsonResponse.emailError;
            let usernameError = jsonResponse.usernameError;
            let passwordError = jsonResponse.passwordError;

            if (emailError !== "null") {
                let emailErrorSpan = document.getElementById("emailErrorSpan");
                event.preventDefault();
                emailErrorSpan.innerHTML = emailError;
            } else {
                emailErrorSpan.innerHTML = null;
            }

            if (usernameError !== "null") {
                let usernameErrorSpan = document.getElementById("usernameErrorSpan");
                event.preventDefault();
                usernameErrorSpan.innerHTML = usernameError;
            } else {
                usernameErrorSpan.innerHTML = null;
            }

            if (passwordError !== "null") {
                let passwordErrorSpan = document.getElementById("passwordErrorSpan");
                event.preventDefault();
                passwordErrorSpan.innerHTML = passwordError;
            } else {
                passwordErrorSpan.innerHTML = null;
            }
        }
    };

    let answer = JSON.stringify({  "username": username,
        "email": email,
        "password": password
    });

    // Sending data with the request
    xhr.send(answer);
}

/*const mq = window.matchMedia( "(min-width: 500px)" );
if (mq.matches) {
    document.getElementById("signUpBoxDesign").style.backgroundColor = "green";
    } else {
        document.getElementById("signUpBoxDesign").style.backgroundColor = "red";
    }*/

