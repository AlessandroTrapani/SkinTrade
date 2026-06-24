// Attende che il DOM sia completamente caricato prima di accedere agli elementi della pagina.
document.addEventListener("DOMContentLoaded", function () {
 
// Recupera il form e interrompe lo script se la pagina corrente non contiene il form di registrazione.   
	const form = document.getElementById("formRegistrazione");
    if (!form) {
        return;
    }

    const contextPath = form.getAttribute("data-context-path");

    const nome = document.getElementById("nome");
    const cognome = document.getElementById("cognome");
    const email = document.getElementById("email");
    const password = document.getElementById("password");

    const erroreNome = document.getElementById("erroreNome");
    const erroreCognome = document.getElementById("erroreCognome");
    const erroreEmail = document.getElementById("erroreEmail");
    const errorePassword = document.getElementById("errorePassword");

    let emailDisponibile = false;

// Espressioni regolari usate per validare i campi del form.
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

    function validaEmailFormato() {
        if (!regexEmail.test(email.value.trim())) {
            erroreEmail.textContent = "Inserisci un indirizzo email valido.";
            emailDisponibile = false;
            return false;
        }

        erroreEmail.textContent = "";
        return true;
    }

// Esegue una richiesta AJAX alla Servlet per verificare se l'email è già registrata.	
    function verificaEmailAjax() {
        if (!validaEmailFormato()) {
            return;
        }

        fetch(contextPath + "/verifica-email?email=" + encodeURIComponent(email.value.trim()))
            .then(function (response) {
                return response.json();
            })
            .then(function (data) {
                erroreEmail.textContent = data.messaggio;

                if (data.valida) {
                    erroreEmail.classList.remove("errore-campo");
                    erroreEmail.classList.add("messaggio-ok-campo");
                    emailDisponibile = true;
                } else {
                    erroreEmail.classList.remove("messaggio-ok-campo");
                    erroreEmail.classList.add("errore-campo");
                    emailDisponibile = false;
                }
            })
            .catch(function () {
                erroreEmail.textContent = "Errore durante la verifica dell'email.";
                erroreEmail.classList.remove("messaggio-ok-campo");
                erroreEmail.classList.add("errore-campo");
                emailDisponibile = false;
            });
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
    email.addEventListener("change", verificaEmailAjax);
    password.addEventListener("change", validaPassword);

// Impedisce l'invio del form se almeno un campo non è valido.	
    form.addEventListener("submit", function (event) {
        const nomeValido = validaNome();
        const cognomeValido = validaCognome();
        const emailFormatoValido = validaEmailFormato();
        const passwordValida = validaPassword();

        if (!nomeValido || !cognomeValido || !emailFormatoValido || !passwordValida || !emailDisponibile) {
            event.preventDefault();

            if (emailFormatoValido && !emailDisponibile) {
                erroreEmail.textContent = "Verifica che l'email sia disponibile prima di registrarti.";
                erroreEmail.classList.remove("messaggio-ok-campo");
                erroreEmail.classList.add("errore-campo");
            }
        }
    });
});