<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    if (request.getSession(false) != null) { // Verifica si hay una sesión activa
        request.getSession(false).invalidate(); // Invalidar la sesión
        System.out.println("Sesión cerrada correctamente.");
    }
    response.sendRedirect("index.jsp"); // Redirigir al inicio
%>
