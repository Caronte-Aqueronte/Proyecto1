<%-- 
    Document   : nav-regresar
    Created on : 24/08/2021, 22:29:08
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Regresar</title>
    </head>
    <body>
        <% //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0")|| !sesion.getAttribute("puesto").equals("Punto de venta")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            }%>
         <nav class="navbar navbar-light" style="background-color: #393e41;">
            <a href="/MiMuebleria/Area-de-ventas/menu-principal-ventas.jsp" class="btn btn-outline-light me-3"><i class='bx bx-log-out-circle' style='color:#ffffff'  ></i> HOME</a>
        </nav> 
    </body>
</html>
