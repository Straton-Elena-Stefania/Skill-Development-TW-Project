function submitNewStep(courseUrl, subsectionId) {
    let stepNumber = document.getElementById("newStepNumberBox").value;
    let stepHeader = document.getElementById("newStepHeaderBox").value;
    let stepDescription = document.getElementById("newStepDescriptionBox").value;
    let stepContentLink = document.getElementById("newStepContentLink").value;
    let errorTextBox = document.getElementById("stepSubmissionError");

    if (!Number.isInteger(stepNumber) || stepNumber < 0) {
        document.getElementById("stepSubmissionError").innerHtml = "The step number has to be integer and positive";
    }

    let xhr = new XMLHttpRequest();
    let url = courseUrl + "/steps";

    xhr.open("POST", url, true);

    xhr.setRequestHeader("Content-Type", "application/json");

    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 409) {
            errorTextBox.innerHTML = "There already is a step with this number on the page";
        } else if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.reload(false);
        } else {
            errorTextBox.innerHTML = "Error from the course provider";
        }
    };

    // Converting JSON data to string
    let stepJsonInformation = JSON.stringify({  "subsectionID": subsectionId,
                                                "stepHeader": stepHeader,
                                                "stepNumber": stepNumber,
                                                "stepDescription": stepDescription,
                                                "contentLink": stepContentLink,
                                                "contentDescription": stepHeader,
                                                "stepContentType": 2
                                            });

    // Sending data with the request
    xhr.send(stepJsonInformation);
}

function submitAnswers(subsectionId, skillUrl, skillId, questionsCount, sessionId, userId) {
    event.preventDefault();
    event.stopPropagation();

    let answers = [];

    for (let i = 1; i < questionsCount; i++) {
        //let currentAnswer = document.getElementById("questionsAnswers" + i).value;
        let nameOfAnswer = "questionAnswer" + i;
        let currentAnswer = document.querySelector('input[name=' + nameOfAnswer + ']:checked').value;
        answers.push(currentAnswer);
    }

    let xhr = new XMLHttpRequest();
    let testSubmissionUrl = skillUrl + "/scoring";

    xhr.open("POST", testSubmissionUrl, true);

    xhr.setRequestHeader("Content-Type", "application/json");
    //xhr.setRequestHeader("Access-Control-Request-Headers", "*");
    xhr.setRequestHeader("Authorization", sessionId);
    xhr.setRequestHeader("userId", userId);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 404) {
            let achievementButton = document.getElementById("achievementButtonNotImplemented");
            let modal = document.getElementById(achievementButton.dataset.modal);

            modal.style.display = "block";
        }
        if (xhr.readyState === 4 && xhr.status === 409) {
            console.log("asf");
        } else if (xhr.readyState === 4 && xhr.status === 200) {
            let jsonResponse = JSON.parse(xhr.responseText);
            let achievement = jsonResponse.achievement;

            if (achievement !== "null") {
                let userUrl = "/skivi_war_exploded/users/" + userId;

                /*xhr.open("POST", testSubmissionUrl, true);

                xhr.setRequestHeader("Content-Type", "application/json");*/

                let patchBody = JSON.stringify({  "operation": "add",
                    "attribute": "achievements",
                    "value": skillId + "-" + achievement
                });

                fetch(userUrl, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': sessionId,
                        'userId': userId,
                        'patch': "yes"
                    },
                    body: patchBody
                } )
                    .then(resp => {
                        if (resp.ok) {
                            console.log("ok");
                                resp.text().then(function (textResponse) {
                                    let achievementButton = document.getElementById("achievementButton");
                                    let modal = document.getElementById(achievementButton.dataset.modal);
                                    let achievementTitle = document.getElementById("achievementTitleSpan");
                                    achievementTitle.innerHTML = achievement;
                                    modal.style.display = "block";
                                })
                        } else {
                            if (resp.status === 409) {
                                console.log("Duplicate");
                                let achievementButton = document.getElementById("achievementButtonConflict");
                                let modal = document.getElementById(achievementButton.dataset.modal);
                                let achievementTitle = document.getElementById("achievementTitleSpanConflict");
                                achievementTitle.innerHTML = achievement;
                                modal.style.display = "block";
                            } else {
                                console.log("error in processing ajax call!");
                            }
                        }
                    })
                    .catch(err => console.log(err))
            } else {
                let nrCorrectAnswers = jsonResponse.correctAnswers;
                let nrQuestions = jsonResponse.totalQuestions;

                let achievementButton = document.getElementById("achievementButtonTryAgain");
                let modal = document.getElementById(achievementButton.dataset.modal);

                let correctQuestionsSpan = document.getElementById("numberOfCorrectAnswers");
                correctQuestionsSpan.innerHTML = nrCorrectAnswers;
                let totalQuestionsSpan = document.getElementById("numberOfQuestions");
                totalQuestionsSpan.innerHTML = nrQuestions;

                modal.style.display = "block";
            }
        } else {
            console.log("abcdefghijklmnopqrst");
        }
    };

    let answer = JSON.stringify({  "subsectionID": subsectionId,
        "answers": answers
    });

    xhr.send(answer);
}

function copyContenteditablesToForm(stepNumber) {
    let contenteditable = "stepHeader" + stepNumber;
    let updateTextareaField = "updateStepHeaderTextarea" + stepNumber;
    document.getElementById(updateTextareaField).value = document.getElementById(contenteditable).innerHTML;

    contenteditable = "stepNumber" + stepNumber;
    updateTextareaField = "updateStepNumberTextarea" + stepNumber;
    document.getElementById(updateTextareaField).value = document.getElementById(contenteditable).innerHTML;

    contenteditable = "stepDescription" + stepNumber;
    updateTextareaField = "updateStepDescriptionTextarea" + stepNumber;
    document.getElementById(updateTextareaField).value = document.getElementById(contenteditable).innerHTML;
}