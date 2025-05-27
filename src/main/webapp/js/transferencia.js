// Mostrar mensaje si hay ?success=true o ?error=true
const params = new URLSearchParams(window.location.search);

if (params.has("success")) {
    alert("✅ Transferencia realizada correctamente.");
} else if (params.has("error")) {
    alert("❌ Error al realizar la transferencia. Verifica los datos.");
}

// Enviar datos al controlador Spring desde JS (opcional si quieres fetch en lugar de submit tradicional)
document.getElementById("transferencia").addEventListener("submit", function(event) {
    // Este bloque es opcional: solo si deseas manejarlo completamente con fetch
    // Si prefieres mantener el submit tradicional con redirección, puedes eliminarlo
    event.preventDefault();

    const ibanOrigen = document.getElementById("ibanOrigen").value;
    const ibanDestino = document.getElementById("ibanDestino").value;
    const cantidad = document.getElementById("cantidadTransferencia").value;

    fetch("transferencia", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `ibanOrigen=${encodeURIComponent(ibanOrigen)}&ibanDestino=${encodeURIComponent(ibanDestino)}&cantidadTransferencia=${encodeURIComponent(cantidad)}`
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        } else {
            alert("❌ Error inesperado al procesar la transferencia.");
        }
    })
    .catch(error => {
        console.error("Error en la solicitud:", error);
        alert("❌ Error de red al enviar la transferencia.");
    });
});
