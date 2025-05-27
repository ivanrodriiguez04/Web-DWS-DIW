<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
String mensaje = request.getParameter("mensaje"); // Obtiene mensaje de error o éxito
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Crear Cuenta</title>
<link rel="stylesheet" href="css/nav.css">
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
					<a class="dropdown-toggle email-link" id="userDropdown" href="#"
						role="button" data-bs-toggle="dropdown"> <%=emailUsuario%>
					</a>
					<ul class="dropdown-menu dropdown-menu-end">
						<li><a href="cuentas.jsp" class="dropdown-item">Cuentas</a></li>
						<li><a href="transferencias.jsp" class="dropdown-item">Transferencias</a></li>
						<li><a href="<%=request.getContextPath()%>/logout.jsp"
							class="dropdown-item">Cerrar Sesión</a></li>
					</ul>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</nav>

	<div class="container mt-4">
		<h1>Crear una Nueva Cuenta</h1>

		<%-- Muestra el mensaje si existe --%>
		<%
		if (mensaje != null && !mensaje.isEmpty()) {
		%>
		<div class="alert alert-warning"><%=mensaje%></div>
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
				<label for="tipoCuenta" class="form-label">Tipo de cuenta:</label> <select
					id="tipoCuenta" name="tipoCuenta" class="form-select" required>
					<option value="familiar">Familiar</option>
					<option value="personal">Personal</option>
					<option value="ahorro">Ahorro</option>
				</select>
			</div>
			<button type="submit" class="btn btn-primary">Crear cuenta</button>
		</form>

	</div>
</body>
</html>
