document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("formRegistrazione");

    if (!form) {
        return;
    }

    const nome = document.getElementById("nome");
    const cognome = document.getElementById("cognome");
    const email = document.getElementById("email");
    const password = document.getElementById("password");

    const erroreNome = document.getElementById("erroreNome");
    const erroreCognome = document.getElementById("erroreCognome");
    const erroreEmail = document.getElementById("erroreEmail");
    const errorePassword = document.getElementById("errorePassword");

    const regexNome = /^[A-Za-zÀ-ÿ\s]{2,50}$/;
    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const regexPassword = /^.{4,}$/;

    function validaNome() {
        if (!regexNome.test(nome.value.trim())) {
            erroreNome.textContent = "Il nome deve contenere almeno 2 caratteri e solo lettere.";
            return false;
        }

        erroreNome.textContent = "";
        return true;
    }

    function validaCognome() {
        if (!regexNome.test(cognome.value.trim())) {
            erroreCognome.textContent = "Il cognome deve contenere almeno 2 caratteri e solo lettere.";
            return false;
        }

        erroreCognome.textContent = "";
        return true;
    }

    function validaEmail() {
        if (!regexEmail.test(email.value.trim())) {
            erroreEmail.textContent = "Inserisci un indirizzo email valido.";
            return false;
        }

        erroreEmail.textContent = "";
        return true;
    }

    function validaPassword() {
        if (!regexPassword.test(password.value.trim())) {
            errorePassword.textContent = "La password deve contenere almeno 4 caratteri.";
            return false;
        }

        errorePassword.textContent = "";
        return true;
    }

    nome.addEventListener("change", validaNome);
    cognome.addEventListener("change", validaCognome);
    email.addEventListener("change", validaEmail);
    password.addEventListener("change", validaPassword);

    form.addEventListener("submit", function (event) {
        const nomeValido = validaNome();
        const cognomeValido = validaCognome();
        const emailValida = validaEmail();
        const passwordValida = validaPassword();

        if (!nomeValido || !cognomeValido || !emailValida || !passwordValida) {
            event.preventDefault();
        }
    });
});