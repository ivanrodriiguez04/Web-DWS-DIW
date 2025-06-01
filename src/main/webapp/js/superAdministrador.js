document.addEventListener("DOMContentLoaded", function () {
    cargarUsuarios();
});

function obtenerFechaActual() {
    var fecha = new Date();
    var año = fecha.getFullYear();
    var mes = ("0" + (fecha.getMonth() + 1)).slice(-2);
    var dia = ("0" + fecha.getDate()).slice(-2);
    return dia + "-" + mes + "-" + año;
}

function cargarUsuarios() {
    fetch("/superadmin/usuarios")
        .then(function(response) { return response.json(); })
        .then(function(data) {
            var usuarios = Array.isArray(data) ? data : [data];
            var tablaAdmins = document.getElementById("adminTable");
            var tablaUsuarios = document.getElementById("userTable");
            tablaAdmins.innerHTML = "";
            tablaUsuarios.innerHTML = "";

            usuarios.forEach(function(usuario) {
                var fila = document.createElement("tr");
                fila.innerHTML =
                    "<td>" + usuario.idUsuario + "</td>" +
                    "<td>" + usuario.nombreCompletoUsuario + "</td>" +
                    "<td>" + usuario.emailUsuario + "</td>" +
                    "<td><button class='btn btn-danger' onclick='confirmarEliminacion(" + usuario.idUsuario + ")'>Eliminar</button></td>";
                if (usuario.rolUsuario === "admin") {
                    tablaAdmins.appendChild(fila);
                } else if (usuario.rolUsuario === "usuario") {
                    tablaUsuarios.appendChild(fila);
                }
            });
        })
        .catch(function(error) { console.error("Error al cargar usuarios:", error); });
}

function confirmarEliminacion(id) {
    var confirmacion = confirm("¿Estás seguro de que deseas eliminar este usuario?");
    if (confirmacion) {
        eliminarUsuario(id);
    }
}

function eliminarUsuario(id) {
    fetch("/superadmin/eliminar/" + id, {
        method: "DELETE"
    })
    .then(function(response) { return response.json(); })
    .then(function(data) {
        if (data.mensaje) {
            alert(data.mensaje);
            location.reload();
        } else {
            alert("Error: " + (data.error || "No se pudo eliminar el usuario."));
        }
    })
    .catch(function(error) { console.error("Error en la solicitud:", error); });
}

function descargarPDF() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    fetch("/superadmin/usuarios")
        .then(response => response.json())
        .then(data => {
            const usuarios = Array.isArray(data) ? data : [data];
            const fecha = obtenerFechaActual();

            doc.setFontSize(18);
            doc.text("Listado de Usuarios", 105, 20, { align: "center" });
            doc.setFontSize(10);
            doc.text(`Generado el: ${fecha}`, 14, 28);

            const headers = [["ID", "Nombre", "Email", "Rol"]];
            const rows = usuarios.map(u => [
                u.idUsuario.toString(),
                u.nombreCompletoUsuario,
                u.emailUsuario,
                u.rolUsuario
            ]);

            doc.autoTable({
                startY: 35,
                head: headers,
                body: rows,
                theme: "striped",
                headStyles: { fillColor: [51, 102, 204], textColor: 255, fontSize: 12 },
                bodyStyles: { fontSize: 10 },
                margin: { left: 14, right: 14 }
            });

            doc.save(`usuarios_${fecha}.pdf`);
        })
        .catch(error => {
            console.error("Error al generar el PDF:", error);
            alert("No se pudo generar el PDF.");
        });
}

function descargarExcel() {
    var fecha = obtenerFechaActual();
    fetch("/superadmin/usuarios")
        .then(function(response) { return response.json(); })
        .then(function(data) {
            var usuarios = Array.isArray(data) ? data : [data];
            var worksheetData = usuarios.map(function(u) {
                return {
                    ID: u.idUsuario,
                    Nombre: u.nombreCompletoUsuario,
                    Email: u.emailUsuario,
                    Rol: u.rolUsuario
                };
            });

            var worksheet = XLSX.utils.json_to_sheet(worksheetData);
            var workbook = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(workbook, worksheet, "Usuarios");

            XLSX.writeFile(workbook, "usuarios_" + fecha + ".xlsx");
        })
        .catch(function(error) {
            console.error("Error al generar el Excel:", error);
            alert("No se pudo generar el Excel.");
        });
}
