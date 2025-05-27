<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registro</title>
<link rel="icon" type="image/png" href="imagenes/favicon.png">
<link rel="stylesheet" href="css/registro.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
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


	<div class="container Formulario" style="max-width: 800px;">
		<h3 class="text-center mb-4">
			<b>Alta nueva cuenta</b>
		</h3>

		<!-- Formulario de registro -->
		<form id="registro" action="registro" method="POST"
			enctype="multipart/form-data">

			<div class="row">
				<!-- Primera columna -->
				<div class="col-md-6">
					<div class="mb-3">
						<label>Nombre completo:</label> <input type="text"
							id="nombreCompletoUsuario" name="nombreCompletoUsuario"
							class="form-control" placeholder="Introduzca su nombre" required>
					</div>
					<div class="mb-3">
						<label>Telefono:</label> <input type="text" id="telefonoUsuario"
							name="telefonoUsuario" class="form-control"
							placeholder="Introduzca su telefono" required>
					</div>
					<div class="mb-3">
						<label>Email:</label> <input type="email" id="emailUsuario"
							name="emailUsuario" class="form-control"
							placeholder="Introduzca su email" required>
					</div>
					<div class="mb-3">
						<label>Repetir email:</label> <input type="email"
							id="confirmEmailUsuario" name="confirmEmailUsuario"
							class="form-control" placeholder="Introduzca su email" required>
					</div>
					<div class="mb-3">
						<label>Contrasena:</label> <input type="password"
							id="passwordUsuario" name="passwordUsuario" class="form-control"
							placeholder="Introduce la contrasena" required>
					</div>
					<div class="mb-3">
						<label>Repetir contrasena:</label> <input type="password"
							id="confirmPasswordUsuario" name="confirmPasswordUsuario"
							class="form-control" placeholder="Repetir contrasena" required>
					</div>
				</div>

				<!-- Segunda columna -->

				<div class="col-md-6">
					<div class="mb-3">
						<label>Ciudad:</label> <select id="ciudadUsuario"
							name="ciudadUsuario" class="form-control" required>
							<option value="">Seleccione una ciudad</option>
							<option value="Madrid">Madrid</option>
							<option value="Barcelona">Barcelona</option>
							<option value="Valencia">Valencia</option>
							<option value="Sevilla">Sevilla</option>
						</select>
					</div>
					<div class="mb-3">
						<label>DNI:</label> <input type="text" id="dniUsuario"
							name="dniUsuario" class="form-control"
							placeholder="Introduzca su DNI" required>
					</div>
					<div class="mb-3">
						<label>Seleccione foto de su DNI por delante:</label> <input
							type="file" id="fotoDniFrontalUsuario"
							name="fotoDniFrontalUsuario" class="form-control"
							accept=".jpg,.png" required>
					</div>
					<div class="mb-3">
						<label>Seleccione foto de su DNI por detras:</label> <input
							type="file" id="fotoDniTraseroUsuario"
							name="fotoDniTraseroUsuario" class="form-control"
							accept=".jpg,.png" required>
					</div>
					<div class="mb-3">
						<label>Seleccione foto de su rostro:</label> <input type="file"
							id="fotoUsuario" name="fotoUsuario" class="form-control"
							accept=".jpg,.png" required>
					</div>
				</div>
			</div>

			<!-- BotÃ³n de registro -->
			<button type="submit" class="btn btn-dark w-100">Registrarse</button>
		</form>

		<!-- Contenedor de mensajes de resultado -->
		<div id="result" class="text-center mt-3 text-danger"></div>
	</div>
	<script src="js/registro.js"></script>

</body>

</html>
