<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    if (session != null) {
        session.invalidate(); // Invalidar la sesión
        System.out.println("Sesión cerrada correctamente.");
    }
    response.sendRedirect("index.jsp"); // Redirigir al inicio
%>
