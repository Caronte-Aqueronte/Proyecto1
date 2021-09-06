<%-- 
    Document   : libro-de-indicaciones
    Created on : 5/09/2021, 14:39:40
    Author     : Luis Monterroso
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Libro de indicaciones</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Fabrica")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            }
            ResultSet tablaIndicacion = (ResultSet) request.getAttribute("tablaIndicacion");
        %>
        <nav class="navbar navbar-light" style="background-color: #393e41;">
            <a href="Area-de-fabrica/menu-principal-fabrica.jsp" class="btn btn-outline-light me-3"><i class='bx bx-log-out-circle' style='color:#ffffff'  ></i> HOME</a>
        </nav>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <div style="width: 300px; height: 170px">
                        <form action="/MiMuebleria/BuscarEnElLibroDeIndicacion" method="POST">
                            <label  class="col-form-label">Buscar instrucciones con nombre del mueble</label>
                            <input type="text" class="form-control" name="txtNombreMueble" placeholder="Nombre del mueble" required="">
                            <input type="submit" value="Buscar" name="btnBuscar" class="btn btn-success mt-2" />
                        </form>
                    </div>
                    <table class="table table-striped">
                        <thead>
                            <tr>                               
                                <th scope="col">Nombre del mueble</th>
                                <th scope="col">Nombre de la pieza</th>
                                <th scope="col">Precio de la pieza</th>
                                <th scope="col">Piezas que se necesitan</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                try {
                                    while (tablaIndicacion.next()) {
                                        out.print("<tr>");
                                        out.print("<td>" + tablaIndicacion.getString("codigo_de_mueble") + "</td>");
                                        out.print("<td>" + tablaIndicacion.getString("nombre_de_pieza") + "</td>");
                                        out.print("<td>" + tablaIndicacion.getDouble("precio_de_pieza") + "</td>");
                                        out.print("<td>" + tablaIndicacion.getInt("piezas_necesarias") + "</td>");
                                        out.print("</tr>");
                                    }
                                } catch (NullPointerException ex) {

                                }
                            %>
                        </tbody>
                    </table>        
                </div>
            </div>
        </div>
    </body>
</html>
