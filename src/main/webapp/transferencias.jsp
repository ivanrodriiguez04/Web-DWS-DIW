<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
    String emailUsuario = (String) session.getAttribute("emailUsuario");
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Transferencias</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="imagenes/favicon.png" type="image/png">
    <link rel="stylesheet" href="css/transferencias.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
				<a href="registro.jsp" class="hazteCliente">Hazte cliente</a>
				<a href="inicioSesion.jsp" class="login-cuentas">Login</a>
			<%
			} else {
			%>
				<div class="dropdown">
					<button class="btn btn-link dropdown-toggle email-link" type="button"
						id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
						<%= emailUsuario %>
					</button>
					<ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
						<li><a class="dropdown-item" href="cuentas.jsp">Cuentas</a></li>
						<li><a class="dropdown-item" href="transferencias.jsp">Transferencias</a></li>
						<li><a class="dropdown-item" href="<%=request.getContextPath()%>/logout.jsp">Cerrar Sesión</a></li>
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
        <h3 class="text-center mb-4"><b>Transferencia</b></h3>
        <form id="transferencia" action="transferencia" method="post">
            <div class="mb-3">
                <label>Desde qué cuenta desea hacer la transferencia:</label>
                <input type="text" id="ibanOrigen" name="ibanOrigen" class="form-control" placeholder="Introduzca el IBAN de la cuenta" required>
            </div>
            <div class="mb-3">
                <label>A qué cuenta desea realizar la transferencia:</label>
                <input type="text" id="ibanDestino" name="ibanDestino" class="form-control" placeholder="Introduce el IBAN de destino" required>
            </div>
            <div class="mb-3">
                <label>Cantidad:</label>
                <input type="number" step="0.01" id="cantidadTransferencia" name="cantidadTransferencia" class="form-control" placeholder="Introduce la cantidad a transferir" required>
            </div>
            <button type="submit" class="btn btn-dark w-100">Realizar transferencia</button>
        </form>
        <div id="result" class="text-center mt-3 fw-bold">
            <% if ("true".equals(success)) { %>
                <span class="text-success">✅ Transferencia realizada correctamente.</span>
            <% } else if ("true".equals(error)) { %>
                <span class="text-danger">❌ Error al realizar la transferencia. Verifica los datos.</span>
            <% } else if ("sesion".equals(error)) { %>
                <span class="text-danger">❌ Sesión inválida: vuelve a iniciar sesión.</span>
            <% } %>
        </div>
    </div>
</main>

<!-- FOOTER -->
<footer>
    <div class="container text-center">
        <h5>InnovaBank</h5>
        <p>&copy; <%= java.time.Year.now() %> InnovaBank. Todos los derechos reservados.</p>
        <p>C/ Futuro Financiero, 123 · Madrid, España · Tel: +34 900 123 456</p>
        <div>
            <a href="terminos.jsp">Términos</a>
            <a href="privacidad.jsp">Privacidad</a>
            <a href="contacto.jsp">Contacto</a>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
