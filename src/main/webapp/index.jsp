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
<!-- Archivo CSS unificado -->
<link rel="stylesheet" href="css/index.css">
<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<!-- Estructura Flex: el body se divide en nav, main (contenido) y footer -->
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


	<!-- Contenido principal -->
	<main>
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
					<img src="imagenes/ahorrar.jpg" class="d-block w-100"
						alt="Imagen 1">
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
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Anterior</span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#carruselBanco" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Siguiente</span>
			</button>
		</div>
	</main>

	<!-- Footer de InnovaBank -->
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
				<a href="">Términos</a> <a href="">Privacidad</a> <a href="">Contacto</a>
			</div>
		</div>
	</footer>

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		// Inicializar el carrusel
		document.addEventListener("DOMContentLoaded", function() {
			var myCarousel = new bootstrap.Carousel(document
					.querySelector("#carruselBanco"), {
				interval : 10000,
				ride : "carousel"
			});
		});
	</script>
</body>
</html>
