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

/*const mq = window.matchMedia( "(min-width: 500px)" );
if (mq.matches) {
    document.getElementById("signUpBoxDesign").style.backgroundColor = "green";
    } else {
        document.getElementById("signUpBoxDesign").style.backgroundColor = "red";
    }*/

