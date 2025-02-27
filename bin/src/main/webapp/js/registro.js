document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("registro");
    const email = document.getElementById("emailUsuario");
    const confirmEmail = document.getElementById("confirmEmailUsuario");
    const password = document.getElementById("passwordUsuario");
    const confirmPassword = document.getElementById("confirmPasswordUsuario");
    const submitButton = form.querySelector("button[type='submit']");
    const resultDiv = document.getElementById("result");

    function validarFormulario() {
        let errores = [];

        // Validar Email
        if (email.value.trim() !== confirmEmail.value.trim()) {
            errores.push("Los correos electrónicos no coinciden.");
            email.classList.add("is-invalid");
            confirmEmail.classList.add("is-invalid");
        } else {
            email.classList.remove("is-invalid");
            confirmEmail.classList.remove("is-invalid");
        }

        // Validar Contraseña
        if (password.value !== confirmPassword.value) {
            errores.push("Las contraseñas no coinciden.");
            password.classList.add("is-invalid");
            confirmPassword.classList.add("is-invalid");
        } else {
            password.classList.remove("is-invalid");
            confirmPassword.classList.remove("is-invalid");
        }

        // Mostrar errores y deshabilitar el botón si hay errores
        if (errores.length > 0) {
            resultDiv.innerHTML = errores.join("<br>");
            resultDiv.classList.remove("alert-success");
            resultDiv.classList.add("alert", "alert-danger"); // Rojo para errores
            submitButton.disabled = true;
        } else {
            resultDiv.innerHTML = "";
            resultDiv.classList.remove("alert", "alert-danger");
            submitButton.disabled = false;
        }
    }

    // Escuchar cambios en los campos necesarios
    [email, confirmEmail, password, confirmPassword].forEach(input => {
        input.addEventListener("input", validarFormulario);
    });

    // Enviar el formulario correctamente y mostrar el mensaje de confirmación en verde
    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Evita recargar la página inmediatamente

        if (!submitButton.disabled) {
            // Mostrar mensaje de confirmación en verde
            resultDiv.innerHTML = "✅ Revise el email para la confirmación de su cuenta.";
            resultDiv.classList.remove("alert-danger");
            resultDiv.classList.add("alert", "alert-success"); // Verde para éxito

            // Enviar el formulario después de mostrar el mensaje
            setTimeout(() => {
                form.submit(); // Ahora el formulario se enviará correctamente
            }, 5000); // Pequeño retraso para que el usuario vea el mensaje
        }
    });
});
