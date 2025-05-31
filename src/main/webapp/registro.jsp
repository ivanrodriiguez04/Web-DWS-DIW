<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
%>
<!DOCTYPE html>
<html lang="es">
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



	<!-- FORMULARIO CENTRADO -->
	<main>
		<div class="Formulario container">
			<h3 class="text-center mb-4">
				<b>Alta nueva cuenta</b>
			</h3>
			<form id="registro" action="registro" method="POST"
				enctype="multipart/form-data">
				<div class="row">
					<!-- Columna izquierda -->
					<div class="col-md-6">
						<div class="mb-3">
							<label>Nombre completo:</label> <input type="text"
								id="nombreCompletoUsuario" name="nombreCompletoUsuario"
								class="form-control" placeholder="Introduzca su nombre" required>
						</div>
						<div class="mb-3">
							<label>Teléfono:</label> <input type="text" id="telefonoUsuario"
								name="telefonoUsuario" class="form-control"
								placeholder="Introduzca su teléfono" required>
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
							<label>Contraseña:</label> <input type="password"
								id="passwordUsuario" name="passwordUsuario" class="form-control"
								placeholder="Introduce la contraseña" required>
						</div>
						<div class="mb-3">
							<label>Repetir contraseña:</label> <input type="password"
								id="confirmPasswordUsuario" name="confirmPasswordUsuario"
								class="form-control" placeholder="Repetir contraseña" required>
						</div>
					</div>

					<!-- Columna derecha -->
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
							<label>Foto DNI frontal:</label> <input type="file"
								id="fotoDniFrontalUsuario" name="fotoDniFrontalUsuario"
								class="form-control" accept=".jpg,.png" required>
						</div>
						<div class="mb-3">
							<label>Foto DNI trasera:</label> <input type="file"
								id="fotoDniTraseroUsuario" name="fotoDniTraseroUsuario"
								class="form-control" accept=".jpg,.png" required>
						</div>
						<div class="mb-3">
							<label>Foto del rostro:</label> <input type="file"
								id="fotoUsuario" name="fotoUsuario" class="form-control"
								accept=".jpg,.png" required>
						</div>
					</div>
				</div>

				<button type="submit" class="btn btn-dark w-100">Registrarse</button>
			</form>
			<div id="result" class="text-center mt-3 text-danger"></div>
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

	<script src="js/registro.js"></script>
</body>
</html>
