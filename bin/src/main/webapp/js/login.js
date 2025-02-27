const urlParams = new URLSearchParams(window.location.search);

    if (urlParams.has("registro")) {
        alert("✅ Registro confirmado con éxito. Ya puedes iniciar sesión.");
    } else if (urlParams.has("error")) {
        alert("⚠️ El enlace de confirmación es inválido o ha expirado.");
    }