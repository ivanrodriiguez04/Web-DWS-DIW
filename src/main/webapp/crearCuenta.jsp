<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
String mensaje = request.getParameter("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Crear Cuenta</title>
<link rel="stylesheet" href="css/crearCuenta.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
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


	<!-- MAIN -->
	<main>
		<div class="Formulario">
			<h3 class="text-center mb-4">
				<b>Crear una Nueva Cuenta</b>
			</h3>

			<%
			if (mensaje != null && !mensaje.isEmpty()) {
			%>
			<div class="alert alert-warning text-center"><%=mensaje%></div>
			<%
			}
			%>

			<form id="crearCuentaForm" method="post"
				action="${pageContext.request.contextPath}/cuentas/crear">
				<div class="mb-3">
					<label for="nombreCuenta" class="form-label">Nombre de la
						cuenta:</label> <input type="text" id="nombreCuenta" name="nombreCuenta"
						class="form-control" required>
				</div>
				<div class="mb-3">
					<label for="tipoCuenta" class="form-label">Tipo de cuenta:</label>
					<select id="tipoCuenta" name="tipoCuenta" class="form-select"
						required>
						<option value="familiar">Familiar</option>
						<option value="personal">Personal</option>
						<option value="ahorro">Ahorro</option>
					</select>
				</div>
				<button type="submit" class="btn btn-dark w-100">Crear
					cuenta</button>
			</form>
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

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
