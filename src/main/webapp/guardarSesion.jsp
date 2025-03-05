<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String idUsuario = request.getParameter("idUsuario");
    String emailUsuario = request.getParameter("emailUsuario");

    if (idUsuario != null && emailUsuario != null) {
        session.setAttribute("idUsuario", idUsuario);
        session.setAttribute("emailUsuario", emailUsuario);
    }
%>
