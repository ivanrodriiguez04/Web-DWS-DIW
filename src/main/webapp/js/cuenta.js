document.addEventListener("DOMContentLoaded", function () {
    cargarCuentas();
    configurarEliminacion();
});

// Función para cargar las cuentas del usuario
function cargarCuentas() {
    let usuarioId = obtenerUsuarioId();

    if (!usuarioId) {
        console.error("Error: No se pudo obtener el ID del usuario.");
        return;
    }

    fetch(`http://localhost:8081/api/cuentas/usuario/${usuarioId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            let cuentasContainer = document.getElementById("cuentasContainer");
            cuentasContainer.innerHTML = "";

            if (data.length === 0) {
                cuentasContainer.innerHTML = "<p>No tienes cuentas creadas.</p>";
            } else {
                const tablaCuentas = document.createElement("table");
                tablaCuentas.classList.add("table", "table-bordered");

                const encabezado = document.createElement("thead");
                encabezado.innerHTML = `
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Tipo de Cuenta</th>
                        <th>IBAN</th>
                        <th>Dinero</th>
                        <th>Acciones</th>
                    </tr>
                `;
                tablaCuentas.appendChild(encabezado);

                const cuerpo = document.createElement("tbody");

                data.forEach(cuenta => {
                    const fila = document.createElement("tr");
                    fila.innerHTML = `
                        <td>${cuenta.idCuenta}</td>
                        <td>${cuenta.nombreCuenta}</td>
                        <td>${cuenta.tipoCuenta}</td>
                        <td>${cuenta.ibanCuenta}</td>
                        <td>${cuenta.dineroCuenta} €</td>
                        <td>
                            <button class="btn btn-danger eliminarCuenta" data-id="${cuenta.idCuenta}">Eliminar</button>
                        </td>
                    `;
                    cuerpo.appendChild(fila);
                });

                tablaCuentas.appendChild(cuerpo);
                cuentasContainer.appendChild(tablaCuentas);

                configurarEliminacion();
            }
        })
        .catch(error => console.error("Error al cargar cuentas:", error));
}

// Configurar eventos de eliminación de cuentas
function configurarEliminacion() {
    document.querySelectorAll(".eliminarCuenta").forEach(button => {
        button.addEventListener("click", function () {
            let idCuenta = this.getAttribute("data-id");

            if (confirm("¿Estás seguro de que deseas eliminar esta cuenta?")) {
                eliminarCuenta(idCuenta);
            }
        });
    });
}

// Función para eliminar una cuenta
function eliminarCuenta(idCuenta) {
    fetch(`http://localhost:8081/api/cuentas/eliminar/${idCuenta}`, {
        method: "DELETE",
    })
    .then(response => response.json())
    .then(data => {
        if (data.mensaje) {
            alert(data.mensaje);
            cargarCuentas(); // Recargar las cuentas después de eliminar
        } else {
            alert("Error: " + data.error);
        }
    })
    .catch(error => console.error("Error al eliminar la cuenta:", error));
}

// Función para obtener el usuarioId
function obtenerUsuarioId() {
    return document.body.getAttribute("data-usuario-id");
}
