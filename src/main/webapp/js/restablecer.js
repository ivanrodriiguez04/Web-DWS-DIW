document.addEventListener("DOMContentLoaded", function () {
    // Obtener los elementos del formulario
    const passwordInput = document.getElementById("passwordUsuario");
    const confirmPasswordInput = document.getElementById("confirmPasswordUsuario");
    const submitButton = document.querySelector("button[type='submit']");
    
    // Crear el mensaje de error
    const errorMessage = document.createElement("div");
    errorMessage.style.color = "red";
    errorMessage.style.marginTop = "5px";
    errorMessage.textContent = "⚠️ Las contraseñas no coinciden.";
    errorMessage.style.display = "none"; // Ocultarlo por defecto
    
    // Insertar el mensaje de error debajo del campo de confirmación
    confirmPasswordInput.parentNode.appendChild(errorMessage);

    // Función para validar si las contraseñas coinciden
    function validatePasswords() {
        if (passwordInput.value !== confirmPasswordInput.value) {
            errorMessage.style.display = "block"; // Mostrar mensaje de error
            submitButton.disabled = true; // Deshabilitar el botón
        } else {
            errorMessage.style.display = "none"; // Ocultar mensaje de error
            submitButton.disabled = false; // Habilitar el botón
        }
    }

    // Escuchar los eventos de entrada en los campos de contraseña
    passwordInput.addEventListener("input", validatePasswords);
    confirmPasswordInput.addEventListener("input", validatePasswords);
});
