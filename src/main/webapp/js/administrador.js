document.addEventListener("DOMContentLoaded", function () {
    cargarUsuarios();
});

function cargarUsuarios() {
    fetch("http://localhost:8081/api/administrador/usuarios")
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            let usuarios = Array.isArray(data) ? data : [data]; 
            
            let tablaUsuarios = document.getElementById("userTable");
            tablaUsuarios.innerHTML = ""; 

            usuarios.forEach(usuario => {
                let fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${usuario.idUsuario}</td>
                    <td>${usuario.nombreCompletoUsuario}</td>
                    <td>${usuario.emailUsuario}</td>
                    <td>
                        <button class="btn btn-danger" onclick="confirmarEliminacion(${usuario.idUsuario})">Eliminar</button>
                    </td>
                `;
                tablaUsuarios.appendChild(fila);
            });
        })
        .catch(error => console.error("Error al cargar usuarios:", error));
}

function confirmarEliminacion(id) {
    let confirmacion = confirm("¿Estás seguro de que deseas eliminar este usuario?");
    if (confirmacion) {
        eliminarUsuario(id);
    }
}

function eliminarUsuario(id) {
    fetch(`/admin/eliminar/${id}`, {
        method: "DELETE",
    })
    .then(response => response.json())
    .then(data => {
        if (data.mensaje) {
            alert(data.mensaje);
            location.reload(); // Recargar la página para actualizar la lista
        } else {
            alert("Error: " + data.error);
        }
    })
    .catch(error => console.error("Error en la solicitud:", error));
}
