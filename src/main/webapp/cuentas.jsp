<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="vistaProyectoFinal.DWS_DIW.dtos.CuentaDto" %>

<%
    String emailUsuario = (String) session.getAttribute("emailUsuario");
    List<CuentaDto> cuentas = (List<CuentaDto>) request.getAttribute("cuentas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Cuentas</title>
    <link rel="stylesheet" href="css/nav.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
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
                        <a class="dropdown-toggle email-link" id="userDropdown" href="#" role="button" data-bs-toggle="dropdown">
                            <%= emailUsuario %>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a href="cuentas.jsp" class="dropdown-item">Cuentas</a></li>
                            <li><a href="logout.jsp" class="dropdown-item">Cerrar sesión</a></li>
                        </ul>
                    </div>
                <% } %>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="mb-3">Mis Cuentas</h1>

        <!-- Botón para ir a crearCuenta.jsp -->
        <div class="mb-3">
            <a href="crearCuenta.jsp" class="btn btn-primary">Crear Cuenta</a>
        </div>

        <!-- Mostrar mensaje si existe -->
        <% if (request.getAttribute("mensaje") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("mensaje") %>
            </div>
        <% } %>

        <!-- Mostrar cuentas -->
        <div id="cuentasContainer">
            <% if (cuentas == null || cuentas.isEmpty()) { %>
                <p>No tienes cuentas creadas.</p>
            <% } else { %>
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Tipo de Cuenta</th>
                            <th>IBAN</th>
                            <th>Dinero</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (CuentaDto cuenta : cuentas) { %>
                            <tr>
                                <td><%= cuenta.getIdCuenta() %></td>
                                <td><%= cuenta.getNombreCuenta() %></td>
                                <td><%= cuenta.getTipoCuenta() %></td>
                                <td><%= cuenta.getIbanCuenta() %></td>
                                <td><%= cuenta.getDineroCuenta() %> €</td>
                                <td>
                                    <button class="btn btn-danger eliminarCuenta" data-id="<%= cuenta.getIdCuenta() %>">Eliminar</button>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } %>
        </div>
    </div>

    <script src="js/cuenta.js"></script>
</body>
</html>
