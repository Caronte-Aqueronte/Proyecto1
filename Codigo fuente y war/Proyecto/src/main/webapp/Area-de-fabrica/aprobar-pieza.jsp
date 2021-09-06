<%-- 
    Document   : aprobar-pieza
    Created on : 23/08/2021, 01:30:14
    Author     : Luis Monterroso
--%>

<%@page import="AreaDeFabrica.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Fabrica")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            } else {
                GestorDeEnsamble gestor = new GestorDeEnsamble();
                gestor.registrarMuebleEnSalaDeVentas(request.getParameter("codigo"));
                response.sendRedirect("informacion-de-ensambles.jsp");
            }
        %>
    </body>
</html>
