let favoriteButton = document.getElementsByClassName('heartSkillButton');
let floatingTexts = document.getElementsByClassName('floatingFavoritedText');

for (let i = 0; i < favoriteButton.length; i++) {
    favoriteButton[i].addEventListener("click", function () {
        floatingTexts[i].style.display = "block";
        window.setTimeout(function () { stopFavoritedText(i); }, 2500);

        console.log(i);
    }, false);
}


function stopFavoritedText(i) {
    floatingTexts[i].style.display = " none";
}

function addToFavorites(session, userId, skillId) {
    let userUrl = "/skivi_war_exploded/users/" + userId;

    /*xhr.open("POST", testSubmissionUrl, true);

    xhr.setRequestHeader("Content-Type", "application/json");*/

    let patchBody = JSON.stringify({  "operation": "add",
        "attribute": "favorites",
        "value": skillId
    });

    fetch(userUrl, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': session,
            'userId': userId,
            'patch': "yes"
        },
        body: patchBody
    } )
        .then(resp => {
            if (resp.ok) {
                console.log("ok");
                resp.text().then(function (textResponse) {

                })
            } else {
                if (resp.status === 409) {
                    console.log("Duplicate");

                } else {
                    console.log("error in processing ajax call!");
                }
            }
        })
        .catch(err => console.log(err))
}