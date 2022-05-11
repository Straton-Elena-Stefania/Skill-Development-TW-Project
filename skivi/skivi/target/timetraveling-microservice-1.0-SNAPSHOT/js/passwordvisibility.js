const eyeButton = document.getElementById("eye");
eyeButton.addEventListener("click", function () {
    let passwordField = document.getElementById("password")
    if (passwordField.type == 'text') {
        passwordField.type = 'password';
        eyeButton.textContent = 'Show password';
        eyeButton.setAttribute('aria-label',
        'Show password as plain text. ');
    }
    else {
        passwordField.type = 'text';
        eyeButton.textContent = 'Hide password';
        eyeButton.setAttribute('aria-label', 'Hide password.');
    }
}, false);