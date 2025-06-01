document.addEventListener("DOMContentLoaded", function () {
    cargarCuentas();
});

// Función para cargar las cuentas desde el nuevo endpoint JSON
function cargarCuentas() {
    fetch("/cuentas/obtener", {
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener las cuentas");
        }
        return response.json();
    })
    .then(data => {
        let cuentasContainer = document.getElementById("cuentasContainer");
        cuentasContainer.innerHTML = "";

        if (!Array.isArray(data) || data.length === 0) {
            cuentasContainer.innerHTML = "<p>No tienes cuentas creadas.</p>";
        } else {
            let tabla = `<table class="table table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>Nombre</th>
                        <th>Tipo</th>
                        <th>IBAN</th>
                        <th>Dinero</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>`;

            data.forEach(cuenta => {
                tabla += `
                    <tr>
                        <td>${cuenta.nombreCuenta}</td>
                        <td>${cuenta.tipoCuenta}</td>
                        <td>${cuenta.ibanCuenta}</td>
                        <td>${cuenta.dineroCuenta} €</td>
                        <td>
                            <button class="btn btn-danger" onclick="eliminarCuenta(${cuenta.idCuenta})">Eliminar</button>
                        </td>
                    </tr>`;
            });

            tabla += "</tbody></table>";
            cuentasContainer.innerHTML = tabla;
        }
    })
    .catch(error => {
        console.error("Error al cargar cuentas:", error);
        document.getElementById("cuentasContainer").innerHTML = "<p>Error al cargar las cuentas.</p>";
    });
}

// Función para eliminar una cuenta a través del controlador Web
function eliminarCuenta(idCuenta) {
    if (!confirm("¿Seguro que deseas eliminar esta cuenta?")) return;

    fetch("/cuentas/eliminar", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: `idCuenta=${encodeURIComponent(idCuenta)}`,
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al eliminar la cuenta");
        return response.text();
    })
    .then(data => {
        if (data.trim() === "success") {
            alert("Cuenta eliminada con éxito.");
            cargarCuentas();  // Recargar la lista de cuentas
        } else {
            alert("Error al eliminar la cuenta.");
        }
    })
    .catch(error => {
        console.error("Error al eliminar cuenta:", error);
        alert("Error al eliminar la cuenta.");
    });
}
