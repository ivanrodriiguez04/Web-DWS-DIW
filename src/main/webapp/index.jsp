<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Página Principal - InnovaBank</title>
<link rel="icon" type="image/png" href="imagenes/favicon.png">
<link rel="stylesheet" href="css/nav.css">
<link rel="stylesheet" href="css/carrusel.css">
<!-- Archivo CSS del carrusel -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
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

	<!-- Texto de Bienvenida -->
	<div class="container text-center mt-4">
		<h1>Bienvenido a InnovaBank</h1>
		<p>Tu banco de confianza, innovando para brindarte los mejores
			servicios financieros.</p>
	</div>

	<!-- Carrusel de imágenes -->
	<div id="carruselBanco" class="carousel slide" data-bs-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active" data-bs-interval="10000">
				<img src="imagenes/ahorrar.jpg" class="d-block w-100" alt="Imagen 1">
			</div>
			<div class="carousel-item" data-bs-interval="10000">
				<img src="imagenes/asesoria.jpg" class="d-block w-100"
					alt="Imagen 2">
			</div>
			<div class="carousel-item" data-bs-interval="10000">
				<img src="imagenes/personal.jpg" class="d-block w-100"
					alt="Imagen 3">
			</div>
		</div>
		<!-- Botones de control manual -->
		<button class="carousel-control-prev" type="button"
			data-bs-target="#carruselBanco" data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Anterior</span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#carruselBanco" data-bs-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Siguiente</span>
		</button>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	<script>
		// Inicializar manualmente el carrusel
		document.addEventListener("DOMContentLoaded", function() {
			var myCarousel = new bootstrap.Carousel(document
					.querySelector("#carruselBanco"), {
				interval : 10000, // 10 segundos
				ride : "carousel"
			});
		});
	</script>

</body>
</html>
