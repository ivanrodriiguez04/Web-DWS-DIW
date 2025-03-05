const urlParams = new URLSearchParams(window.location.search);

if (urlParams.has("registro")) {
	alert("✅ Registro confirmado con éxito. Ya puedes iniciar sesión.");
} else if (urlParams.has("error")) {
	alert("⚠️ El enlace de confirmación es inválido o ha expirado.");
}
document.getElementById("login").addEventListener("submit", function(event) {
    event.preventDefault();

    let email = document.getElementById("emailUsuario").value;
    let password = document.getElementById("passwordUsuario").value;

    fetch("http://localhost:8081/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: email, password: password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.idUsuario) {
            // Enviar idUsuario al servidor usando fetch para almacenar en sesión
            fetch("guardarSesion.jsp", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: `idUsuario=${encodeURIComponent(data.idUsuario)}&emailUsuario=${encodeURIComponent(email)}`
            })
            .then(() => {
                window.location.href = "cuentas.jsp";  // Redirigir a cuentas.jsp
            })
            .catch(error => console.error("Error al guardar sesión:", error));
        } else {
            console.error("Error en el login: ID de usuario no recibido");
            document.getElementById("result").textContent = "Credenciales incorrectas";
        }
    })
    .catch(error => console.error("Error en la autenticación:", error));
});
