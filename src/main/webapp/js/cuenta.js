document.addEventListener("DOMContentLoaded", function () {
    // Guardar ID de usuario en localStorage y sessionStorage
    const idUsuario = document.body.getAttribute("data-usuario-id");
    if (idUsuario) {
        localStorage.setItem("idUsuario", idUsuario);
        sessionStorage.setItem("idUsuario", idUsuario);
        cargarCuentas();
    } else {
        console.error("ID de usuario no encontrado en la sesión.");
    }

    configurarFormularioCreacion();
});

// Función para obtener el ID del usuario
function obtenerUsuarioId() {
    let usuarioId = sessionStorage.getItem("idUsuario") || localStorage.getItem("idUsuario");
    if (!usuarioId) {
        console.error("ID de usuario no encontrado.");
        return null;
    }
    return parseInt(usuarioId);
}

// Función para cargar las cuentas del usuario desde la API
function cargarCuentas() {
    let usuarioId = obtenerUsuarioId();
    if (!usuarioId) {
        console.error("Error: ID de usuario no encontrado.");
        return;
    }

    fetch(`http://localhost:8081/api/cuentas/usuario/${usuarioId}`)
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
                            <th>ID</th>
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
                            <td>${cuenta.idCuenta}</td>
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

// Función para eliminar una cuenta
function eliminarCuenta(idCuenta) {
    if (!confirm("¿Seguro que deseas eliminar esta cuenta?")) return;

    fetch(`http://localhost:8081/api/cuentas/eliminar/${idCuenta}`, {
        method: "DELETE",
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al eliminar la cuenta");
        return response.json();
    })
    .then(data => {
        alert("Cuenta eliminada con éxito.");
        cargarCuentas();
    })
    .catch(error => console.error("Error al eliminar cuenta:", error));
}

// Configurar el formulario de creación de cuentas
function configurarFormularioCreacion() {
    const form = document.getElementById("crearCuentaForm");
    if (!form) return;

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        let usuarioId = obtenerUsuarioId();
        if (!usuarioId) {
            alert("Error: ID de usuario no encontrado.");
            return;
        }

        let cuenta = {
            nombreCuenta: document.getElementById("nombreCuenta").value,
            tipoCuenta: document.getElementById("tipoCuenta").value,
            idUsuario: usuarioId
        };

        fetch("http://localhost:8081/api/cuentas/crear", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(cuenta)
        })
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                alert("Error: " + data.error);
            } else {
                alert("Cuenta creada con éxito.");
                window.location.href = "cuentas.jsp";
            }
        })
        .catch(error => console.error("Error al crear cuenta:", error));
    });
}
