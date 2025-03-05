document.addEventListener("DOMContentLoaded", function () {
    cargarCuentas();
});

// Función para cargar las cuentas desde el controlador del proyecto Web
function cargarCuentas() {
    fetch("http://localhost:8080/cuentas/obtener")
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

// Función para eliminar una cuenta a través del controlador Web (CORREGIDO)
function eliminarCuenta(idCuenta) {
    if (!confirm("¿Seguro que deseas eliminar esta cuenta?")) return;

    fetch("http://localhost:8080/cuentas/eliminar", {
        method: "POST",  // 🔹 Ahora es POST en lugar de DELETE
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",  // 🔹 Necesario para enviar `form-data`
        },
        body: `idCuenta=${idCuenta}`  // 🔹 Enviado en el cuerpo de la petición
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al eliminar la cuenta");
        return response.text();
    })
    .then(data => {
        if (data === "success") {
            alert("Cuenta eliminada con éxito.");
            cargarCuentas();
        } else {
            alert("Error al eliminar la cuenta.");
        }
    })
    .catch(error => console.error("Error al eliminar cuenta:", error));
}
