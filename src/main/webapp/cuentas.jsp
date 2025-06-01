<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="vistaProyectoFinal.DWS_DIW.dtos.CuentaDto"%>

<%
String rolUsuario = (String) session.getAttribute("rolUsuario");
if (!"usuario".equals(rolUsuario)) {
	response.sendRedirect("index.jsp"); // Redirige si no es usuario
	return;
}

String emailUsuario = (String) session.getAttribute("emailUsuario");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Mis Cuentas</title>

<!-- ✅ Corrección de rutas absolutas -->
<link rel="stylesheet" href="css/cuentas.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
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
						<li><a class="dropdown-item" href="cuentas.jsp">Cuentas</a></li>
						<li><a class="dropdown-item" href="transferencias.jsp">Transferencias</a></li>
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

	<main>
		<div class="container mt-4">
			<h1 class="mb-3">Cuentas</h1>

			<%
			if (request.getAttribute("mensaje") != null) {
			%>
			<div class="alert alert-danger">
				<%=request.getAttribute("mensaje")%>
			</div>
			<%
			}
			%>

			<!-- ✅ Contenedor donde se cargarán las cuentas dinámicamente -->
			<div id="cuentasContainer">Cargando cuentas...</div>
		</div>
		<div class="mb-3">
			<a href="<%=request.getContextPath()%>/crearCuenta.jsp"
				class="btn btn-primary d-block mx-auto mt-4"
				style="max-width: 300px;">Crear Cuenta</a>

		</div>
	</main>
	<!-- FOOTER -->
	<footer>
		<div class="container text-center">
			<h5>InnovaBank</h5>
			<p>
				&copy;
				<%=java.time.Year.now()%>
				InnovaBank. Todos los derechos reservados.
			</p>
			<p>C/ Futuro Financiero, 123 · Madrid, España · Tel: +34 900 123
				456</p>
			<div>
				<a href="#">Términos</a> <a href="#">Privacidad</a> <a href="#">Contacto</a>
			</div>
		</div>
	</footer>

	<!-- ✅ Corrección de rutas absolutas para JS -->
	<script src="<%=request.getContextPath()%>/js/cuenta.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


</body>
</html>
