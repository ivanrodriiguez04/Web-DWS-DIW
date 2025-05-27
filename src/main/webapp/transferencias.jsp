<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>

<%
String emailUsuario = (String) session.getAttribute("emailUsuario");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Transferencias</title>
<link rel="icon" type="image/png" href="imagenes/favicon.png">
<link rel="stylesheet" href="css/nav.css">
<link rel="stylesheet" href="css/tansferencias.css">
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
	
	<div class="container Transferencia" style="max-width: 600px;">
		<h3 class="text-center mb-4">
			<b>Transferencia</b>
		</h3>

		<!-- Formulario de inicio de sesion -->
		<form id="transferencia" action="transferencia" method="post">
			<!-- IBAN mandatario -->
			<div class="mb-3">
				<label>Desde que cuenta desea hacer la transferencia:</label> <input type="text" id="ibanOrigen"
					name="ibanOrigen" class="form-control"
					placeholder="Introduzca el IBAN de la cuenta:" required>
			</div>
			<!-- IBAN receptor -->
			<div class="mb-3">
				<label>A que cuenta desea realizar la transferencia:</label> <input type="text"
					id="ibanDestino" name="ibanDestino" class="form-control"
					placeholder="Introduce el IBAN de la cuenta a la que le quiere hacer la transferencia:" required>
			</div>
			<!-- Cantidad -->
			<div class="mb-3">
				<label>Contraseña:</label> <input type="text"
					id="cantidadTransferencia" name="cantidadTransferencia" class="form-control"
					placeholder="Introduce la cantidad que desea mandar:" required>
			</div>

			<!-- Boton de transferencia -->
			<button type="submit" class="btn btn-dark w-100">Realizar transferencia</button>
		</form>

		<div id="result" class="text-center mt-3 fw-bold">
    <%
        String success = request.getParameter("success");
        String error = request.getParameter("error");

        if ("true".equals(success)) {
    %>
            <span class="text-success">✅ Transferencia realizada correctamente.</span>
    <%
        } else if ("true".equals(error)) {
    %>
            <span class="text-danger">❌ Error al realizar la transferencia. Verifica los datos.</span>
    <%
        } else if ("sesion".equals(error)) {
    %>
            <span class="text-danger">❌ Sesión inválida: vuelve a iniciar sesión.</span>
    <%
        }
    %>
</div>

	</div>
</body>
</html>