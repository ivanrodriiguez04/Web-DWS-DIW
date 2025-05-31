<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
String rolUsuario = (String) session.getAttribute("rolUsuario");
if (!"superAdmin".equals(rolUsuario)) {
	response.sendRedirect("index.jsp");
	return;
}

String emailUsuario = (String) session.getAttribute("emailUsuario");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Administración de Usuarios</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" type="image/png" href="imagenes/favicon.png">
<link rel="stylesheet" href="css/superAdministrador.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
</head>
<body>
	<!-- NAV -->
	<nav>
		<div class="container-fluid">
			<div class="nav-left">
				<a href="index.jsp" class="logo">InnovaBank</a>
			</div>
			<div class="nav-right">
				<%
				if (emailUsuario == null) {
				%>
				<a href="registro.jsp" class="hazteCliente">Hazte cliente</a> <a
					href="inicioSesion.jsp" class="login-cuentas">Login</a>
				<%
				} else {
				%>
				<div class="dropdown">
					<button class="btn btn-link dropdown-toggle email-link"
						type="button" id="userDropdown" data-bs-toggle="dropdown"
						aria-expanded="false">
						<%=emailUsuario%>
					</button>
					<ul class="dropdown-menu dropdown-menu-end"
						aria-labelledby="userDropdown">
						<li><a class="dropdown-item"
							href="<%=request.getContextPath()%>/logout.jsp">Cerrar Sesión</a></li>
					</ul>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</nav>

	<!-- CONTENIDO -->
	<div class="container mt-4">
		<h1 class="mb-4">Administración de Usuarios</h1>

		<h2>Administradores</h2>
		<table class="table table-striped" id="tablaAdmins">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nombre</th>
					<th>Email</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody id="adminTable"></tbody>
		</table>

		<h2>Usuarios</h2>
		<table class="table table-striped" id="tablaUsuarios">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nombre</th>
					<th>Email</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody id="userTable"></tbody>
		</table>

		<!-- Botones de descarga -->
		<div class="container mt-4">
			<div class="row justify-content-center">
				<div class="col-md-4 text-center mb-2">
					<button class="btn btn-primary w-100" onclick="descargarPDF()">Descargar
						PDF</button>
				</div>
				<div class="col-md-4 text-center mb-2">
					<button class="btn btn-success w-100" onclick="descargarExcel()">Descargar
						Excel</button>
				</div>
			</div>
		</div>


	</div>

	<!-- Scripts -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.28/jspdf.plugin.autotable.min.js"></script>
	
	<script src="js/superAdministrador.js"></script>
</body>
</html>
