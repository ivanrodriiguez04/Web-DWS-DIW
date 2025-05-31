<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8">
    <title>Restablecer Contraseña</title>
    <link rel="icon" type="image/png" href="imagenes/favicon.png">
    <link rel="stylesheet" href="css/restablecer.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/restablecer.js"></script>
</head>
<body>

<%
    // Obtener el token y el email de la URL
    String token = request.getParameter("token");
    String email = request.getParameter("email");

    if (token == null || token.isEmpty() || email == null || email.isEmpty()) {
        response.sendRedirect("inicioSesion.jsp");
%>
    <div class="container text-center mt-5">
        <div class="alert alert-danger">Error: El enlace de recuperación es inválido o ha expirado.</div>
        <a href="inicioSesion.jsp" class="btn btn-dark">Volver al inicio</a>
    </div>
<%
    } else {
%>

<div class="container-fluid d-flex justify-content-center align-items-center vh-100">
    <div class="Restablecer p-4">
        <h3 class="text-center mb-4"><b>Restablecer contraseña:</b></h3>

        <form id="restablecer" action="restablecer" method="post">
            <!-- Campo oculto para el token -->
            <input type="hidden" name="token" value="<%= token %>">

            <!-- Campo oculto para el email -->
            <input type="hidden" name="emailUsuario" value="<%= email %>">

            <div class="mb-3">
                <label for="passwordUsuario">Nueva contraseña:</label>
                <input type="password" id="passwordUsuario" name="passwordUsuario"
                       class="form-control" placeholder="Introduzca la nueva contraseña" required>
            </div>

            <div class="mb-3">
                <label for="confirmPasswordUsuario">Repita la contraseña:</label>
                <input type="password" id="confirmPasswordUsuario" name="confirmPasswordUsuario"
                       class="form-control" placeholder="Vuelva a introducir la nueva contraseña" required>
            </div>

            <button type="submit" class="btn btn-dark w-100">Cambiar contraseña</button>
        </form>

        <div id="result" class="text-center mt-3">
            <% if (request.getAttribute("successMessage") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("successMessage") %></div>
            <% } %>
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
            <% } %>
        </div>
    </div>
</div>

<% } %>

</body>
</html>
