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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/nav.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
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
				<a href="registro.jsp" class="hazteCliente">Hazte cliente</a> <a href="inicioSesion.jsp" class="login-cuentas">Login</a>
				<%
				} else {
				%>
				<div class="dropdown">
					<a class="dropdown-toggle email-link" id="userDropdown" href="#"
						role="button" data-bs-toggle="dropdown"> <%=emailUsuario%>
					</a>
					<ul class="dropdown-menu dropdown-menu-end">
						<li><a href="cuentas.jsp" class="dropdown-item">Cuentas</a></li>
						<li><a href="logout.jsp" class="dropdown-item">Cerrar sesión</a></li>
					</ul>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</nav>

	<div class="container mt-4">
		<h1 class="mb-3">Mis Cuentas</h1>

		<div class="mb-3">
			<a href="<%=request.getContextPath()%>/crearCuenta.jsp" class="btn btn-primary">Crear Cuenta</a>
		</div>

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

	<!-- ✅ Corrección de rutas absolutas para JS -->
	<script src="<%=request.getContextPath()%>/js/cuenta.js"></script>
</body>
</html>
