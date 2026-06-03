/**
 * VALIDATION.JS - Validazione lato client (stile del professore)
 *
 * - Pattern e messaggi come costanti
 * - Funzione generica validateFormElem(formElem, pattern, span, message)
 * - classList.add("error") / classList.remove("error")
 * - Errori mostrati su evento "change" e su evento "submit"
 * - Errori visualizzati nel DOM (niente alert)
 */

/* ============================
   PATTERN (Espressioni Regolari)
   ============================ */
const nameOrLastnamePattern = /^[A-Za-zÀ-ÖØ-öø-ÿ' ]{2,50}$/;
const emailPattern = /^\S+@\S+\.\S+$/;
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
const addressPattern = /^.{5,255}$/;
const phonePattern = /^(\+39[\s]?)?[0-9]{3}[\s\-]?[0-9]{3,4}[\s\-]?[0-9]{3,4}$/;

/* ============================
   MESSAGGI DI ERRORE
   ============================ */
const nameErrorMessage = "Il nome deve contenere solo lettere (minimo 2 caratteri).";
const lastnameErrorMessage = "Il cognome deve contenere solo lettere (minimo 2 caratteri).";
const emailErrorMessage = "Inserisci un'email valida nel formato nome@dominio.ext";
const passwordErrorMessage = "Minimo 8 caratteri, almeno 1 maiuscola, 1 minuscola e 1 numero.";
const addressErrorMessage = "Inserisci un indirizzo valido (minimo 5 caratteri).";
const phoneErrorMessage = "Formato non valido (es. +39 333 1234567).";
const requiredMessage = "Questo campo è obbligatorio.";

/* ============================
   FUNZIONE GENERICA DI VALIDAZIONE
   (Stile identico alla slide del prof)
   ============================ */
function validateFormElem(formElem, pattern, span, message) {
    if (formElem.value.trim().match(pattern)) {
        formElem.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }
    formElem.classList.add("error");
    span.innerHTML = message;
    span.style.color = "red";
    return false;
}

/**
 * Controlla che un campo obbligatorio non sia vuoto.
 * Se vuoto mostra il messaggio, altrimenti valida con il pattern.
 */
function validateRequired(formElem, pattern, span, message) {
    if (formElem.value.trim() === "") {
        formElem.classList.add("error");
        span.innerHTML = requiredMessage;
        span.style.color = "red";
        return false;
    }
    return validateFormElem(formElem, pattern, span, message);
}

/**
 * Valida un campo facoltativo: se vuoto è OK, se compilato deve rispettare il pattern.
 */
function validateOptional(formElem, pattern, span, message) {
    if (formElem.value.trim() === "") {
        formElem.classList.remove("error");
        span.style.color = "black";
        span.innerHTML = "";
        return true;
    }
    return validateFormElem(formElem, pattern, span, message);
}


/* ============================================================
   INIZIALIZZAZIONE AL CARICAMENTO DELLA PAGINA
   ============================================================ */
document.addEventListener("DOMContentLoaded", function () {

    /* ----------------------------------------------------------
       FORM DI LOGIN
       ---------------------------------------------------------- */
    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        const email    = document.getElementById("email");
        const password = document.getElementById("password");
        const emailSpan    = document.getElementById("emailError");
        const passwordSpan = document.getElementById("passwordError");

        // Validazione su evento "change"
        email.addEventListener("change", function () {
            validateRequired(email, emailPattern, emailSpan, emailErrorMessage);
        });

        password.addEventListener("change", function () {
            if (password.value.trim() === "") {
                password.classList.add("error");
                passwordSpan.innerHTML = requiredMessage;
                passwordSpan.style.color = "red";
            } else {
                password.classList.remove("error");
                passwordSpan.innerHTML = "";
            }
        });

        // Validazione su evento "submit"
        loginForm.addEventListener("submit", function (e) {
            let isValid = true;

            if (!validateRequired(email, emailPattern, emailSpan, emailErrorMessage)) {
                isValid = false;
            }

            if (password.value.trim() === "") {
                password.classList.add("error");
                passwordSpan.innerHTML = requiredMessage;
                passwordSpan.style.color = "red";
                isValid = false;
            }

            if (!isValid) {
                e.preventDefault();
            }
        });
    }

    /* ----------------------------------------------------------
       FORM DI REGISTRAZIONE
       ---------------------------------------------------------- */
    const registerForm = document.getElementById("registerForm");

    if (registerForm) {
        const nome      = document.getElementById("nome");
        const cognome   = document.getElementById("cognome");
        const email     = document.getElementById("email");
        const password  = document.getElementById("password");
        const indirizzo = document.getElementById("indirizzo");
        const telefono  = document.getElementById("telefono");

        const nomeSpan      = document.getElementById("nomeError");
        const cognomeSpan   = document.getElementById("cognomeError");
        const emailSpan     = document.getElementById("emailError");
        const passwordSpan  = document.getElementById("passwordError");
        const indirizzoSpan = document.getElementById("indirizzoError");
        const telefonoSpan  = document.getElementById("telefonoError");

        // Validazione su evento "change" per ogni campo
        nome.addEventListener("change", function () {
            validateRequired(nome, nameOrLastnamePattern, nomeSpan, nameErrorMessage);
        });

        cognome.addEventListener("change", function () {
            validateRequired(cognome, nameOrLastnamePattern, cognomeSpan, lastnameErrorMessage);
        });

        email.addEventListener("change", function () {
            validateRequired(email, emailPattern, emailSpan, emailErrorMessage);
        });

        password.addEventListener("change", function () {
            validateRequired(password, passwordPattern, passwordSpan, passwordErrorMessage);
        });

        indirizzo.addEventListener("change", function () {
            validateRequired(indirizzo, addressPattern, indirizzoSpan, addressErrorMessage);
        });

        telefono.addEventListener("change", function () {
            validateOptional(telefono, phonePattern, telefonoSpan, phoneErrorMessage);
        });

        // Validazione completa su evento "submit"
        registerForm.addEventListener("submit", function (e) {
            let isValid = true;

            if (!validateRequired(nome, nameOrLastnamePattern, nomeSpan, nameErrorMessage))
                isValid = false;
            if (!validateRequired(cognome, nameOrLastnamePattern, cognomeSpan, lastnameErrorMessage))
                isValid = false;
            if (!validateRequired(email, emailPattern, emailSpan, emailErrorMessage))
                isValid = false;
            if (!validateRequired(password, passwordPattern, passwordSpan, passwordErrorMessage))
                isValid = false;
            if (!validateRequired(indirizzo, addressPattern, indirizzoSpan, addressErrorMessage))
                isValid = false;
            if (!validateOptional(telefono, phonePattern, telefonoSpan, phoneErrorMessage))
                isValid = false;

            if (!isValid) {
                e.preventDefault();
            }
        });
    }

});
