<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    List<UsuarioDto> usuarios = (List<UsuarioDto>) request.getAttribute("usuarios");
    String mensaje = (String) request.getAttribute("mensaje");
    String emailUsuario = (String) session.getAttribute("emailUsuario");

    if (usuarios == null) {
        usuarios = List.of(); // Evita NullPointerException
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administración de Usuarios</title>
    <link rel="icon" type="image/png" href="imagenes/favicon.png">
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
</head>
<body>
    <nav>
        <div class="container-fluid">
            <div class="nav-left">
                <a href="index.jsp" class="logo">InnovaBank</a>
            </div>
            <div class="nav-right">
                <% if (emailUsuario == null) { %>
                    <a href="registro.jsp" class="hazteCliente">Hazte cliente</a> 
                    <a href="inicioSesion.jsp" class="login-cuentas">Login</a>
                <% } else { %>
                    <div class="dropdown">
                        <a class="dropdown-toggle email-link" id="userDropdown" href="#" 
                           role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <%= emailUsuario %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <li><a href="logout.jsp" class="dropdown-item">Cerrar Sesión</a></li>
                        </ul>
                    </div>
                <% } %>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="mb-4">Usuarios</h1>

        <% if (mensaje != null) { %>
            <div class="alert alert-info"><%= mensaje %></div>
        <% } %>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody id="userTable"> <!-- Se corrigió el ID -->
                <c:choose>
                    <c:when test="${not empty usuarios}">
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td>${usuario.idUsuario}</td>
                                <td>${usuario.nombreCompletoUsuario}</td>
                                <td>${usuario.emailUsuario}</td>
                                <td>
                                    <button class="btn btn-danger" onclick="eliminarUsuario(${usuario.idUsuario})">Eliminar</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4" class="text-center">No hay usuarios disponibles.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/administrador.js"></script>
</body>
</html>
