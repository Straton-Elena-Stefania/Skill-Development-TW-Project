
const form = document.getElementById('registerForm');
const usernameRegex = /^[a-zA-Z0-9]+$/;
const passwordRegex = /^[A-Za-z0-9!@#$%^&*()_]+$/;

const username = document.getElementById('username');
const email = document.getElementById('registrationEmail');
const password = document.getElementById('registerPassword');
const usernameError = document.querySelector('#username + span.error');
const emailError = document.querySelector('#registrationEmail + span.error');
const passwordError = document.querySelector('#passwordInput + span.error');

username.addEventListener('input', function (event) {
    /**
     * De fiecare data cand utilizatorul scrie ceva,
     * noi validam acel input
     */
    if (!username.validity.valid) {
        showUsernameError();
    } else {
        if (usernameRegex.test(username.value) == false) {
            showUsernameError();
        } else {
            /**
             * Resetam, in cazul in care nu avem nicio eroare,
             * elementul ce descrie mesajul de eroare pentru a-l
             * ascunde.
             */
            usernameError.textContent = ''; 
            usernameError.className = 'error'; 
        }
    }
});

email.addEventListener('input', function (event) {
    // Each time the user types something, we check if the
    // form fields are valid.

    if (email.validity.valid) {
        emailError.textContent = '';
        emailError.className = 'error'; 
    } else {
        // If there is still an error, show the correct error
        showEmailError();
    }
});

password.addEventListener('input', function (event) {
    // Each time the user types something, we check if the
    // form fields are valid.

    /**
     * Daca a fost de dinainte setat vreun mesaj de eroare, il stergem
     * inainte de a reseta stilul pentru spanul cu eroare.
     */
    if (password.validity.valid) {
        passwordError.textContent = '';
        passwordError.className = 'error';
    } else {
        // If there is still an error, show the correct error
        showPasswordError();
    }
});


form.addEventListener('submit', function (event) {
    // if the email field is valid, we let the form submit

    if (!username.validity.valid) {
        showUsernameError();

        event.preventDefault();
    }

    if (!email.validity.valid) {
        showEmailError();
        event.preventDefault();
    }

    if (!password.validity.valid) {
        /**
         * Afisam ce mesaj de eroare am gasit cand parola nu e valida
         */
        showPasswordError();
        /**
         * Nu lasam sa dea submit
         */
        event.preventDefault();
    }

});

function showEmailError() {
    if (email.validity.valueMissing) {
        emailError.textContent = 'You need to enter an e-mail address.';
    } else if (email.validity.typeMismatch) {
        emailError.textContent = 'Entered value needs to be an e-mail address.';
    }

    emailError.className = 'error active';
    emailError.style.backgroundColor = "rgb(114, 173, 149)";
    emailError.style.right = "10em";
    emailError.style.top = "-1em";
    emailError.style.borderRadius = "1em 1em 0em 1em";
}

function showUsernameError() {
    if (!usernameRegex.test(username)) {
        //console.log(":):)");
        usernameError.textContent = 'Username contains invalid characters.';
    }

    usernameError.className = 'error active';
}

function showPasswordError() {
    /**
     * Daca nu avem nimic in casuta avem eroare
     */
    if (password.validity.valueMissing) {
        passwordError.textContent = 'You need to enter your password.';
    } else if (!passwordRegex.test(password.value)) {
        passwordError = "Invalid character detected in password.";
    } else if (password.validity.tooShort) {
        passwordError.textContent = `Password should be at least ${password.minLength} characters; you entered ${password.value.length}.`;
    }

    /**
     * Modificam clasa pentru mesajul de eroare pentru a-l afisa
     */
    passwordError.className = 'error active';
    
    passwordError.style.backgroundColor = "rgb(220, 127, 200)";
    passwordError.style.borderRadius = "0em 1em 1em 1em";
    passwordError.style.top = "4em";
}