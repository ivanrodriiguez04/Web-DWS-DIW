<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
String idUsuario = (String) session.getAttribute("idUsuario"); // Obtener idUsuario almacenado en la sesión
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Inicio de Sesion</title>
<link rel="icon" type="image/png" href="imagenes/favicon.png">
<link rel="stylesheet" href="css/inicioSesion.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script src="js/login.js"></script>

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
						role="button" data-bs-toggle="dropdown" aria-expanded="false">
						<%=emailUsuario%>
					</a>
					<ul class="dropdown-menu dropdown-menu-end"
						aria-labelledby="userDropdown">
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

	<div class="container Formulario" style="max-width: 600px;">
		<h3 class="text-center mb-4">
			<b>Iniciar Sesión</b>
		</h3>

		<!-- Formulario de inicio de sesion -->
		<form id="login" action="login" method="post">
			<!-- Nombre de club o correo -->
			<div class="mb-3">
				<label>Email:</label> <input type="text" id="emailUsuario"
					name="emailUsuario" class="form-control"
					placeholder="Introduzca su correo" required>
			</div>

			<!-- Contraseña -->
			<div class="mb-3">
				<label>Contraseña:</label> <input type="password"
					id="passwordUsuario" name="passwordUsuario" class="form-control"
					placeholder="Introduce la contrasena" required>
			</div>

			<!-- Boton de iniciar sesion -->
			<button type="submit" class="btn btn-dark w-100">Iniciar
				Sesión</button>
			<a href="registro.jsp" class="redireccion">¿Todavía no tienes
				cuenta?</a><br> <a href="recuperarPassword.jsp" class="redireccion">¿No
				recuerdas la contraseña?</a>
		</form>

		<!-- Contenedor para mostrar mensajes de resultado -->
		<div id="result" class="text-center mt-3 text-danger"></div>
	</div>
</body>
</html>