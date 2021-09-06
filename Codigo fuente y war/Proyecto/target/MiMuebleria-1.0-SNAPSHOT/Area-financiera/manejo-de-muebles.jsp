<%-- 
    Document   : manejo-de-piezas
    Created on : 5/09/2021, 11:48:07
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
        <title>Manejo de muebles</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Area financiera")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            }
            String confimacionCreacion = String.valueOf(request.getAttribute("confimacionCreacion"));
            String confimacionCreacionInstruccion = String.valueOf(request.getAttribute("confimacionCreacionInstruccion"));
            ResultSet tablaMuebles = (ResultSet) request.getAttribute("tablaMuebles");
        %>
        <jsp:include page="nav-regresar.jsp"/>

        <div class="container mt-5" style="float: left; width: 470px; height: 150px;  margin: 30px;">
            <div class="row">
                <div class="col-sm">
                    <form method="post" action="/MiMuebleria/ControladorConsultaMueble">
                        <label  class="col-form-label">Crear un nuevo mueble</label>
                        <input type="text" name="txtNombreMueble" class="form-control mt-2" placeholder="Nombre del nuevo mueble" required="">
                        <input type="number" step="0.01" name="txtPrecio" class="form-control mt-2" placeholder="Precio del nuevo mueble" required="">
                        <button type="submit" class="btn btn-success mt-3" name="btnIngresar">Crear nuevo mueble</button>                        </form>
                        <%if (!confimacionCreacion.equals("null")) {
                        %>
                    <div style="float: left;"class="alert alert-info mt-2" role="alert"><%=confimacionCreacion%></div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <div class="container mt-5" style="float: left; width: 470px; height: 300px;  margin: 30px;">
            <div class="row">
                <div class="col-sm">
                    <form method="post" action="/MiMuebleria/ControladorConsultaMueble">
                        <label  class="col-form-label">Crear instruccion para ensamble de mueble</label>
                        <input type="text" name="txtNombreMueble" class="form-control mt-2" placeholder="Nombre del mueble" required="">
                        <input type="text" name="txtNombrePieza" class="form-control mt-2" placeholder="Nombre de la pieza necesitada" required="">              
                        <input type="number" step="0.01" name="txtPrecioPieza" class="form-control mt-2" placeholder="Precio de la pieza necesitada" required="">
                        <input type="number"  name="txtCantidad" class="form-control mt-2" placeholder="Piezas que se necesitan" required="">
                        <button type="submit" class="btn btn-success mt-3" name="btnInstruccion">Crear nueva instruccion</button>                        
                    </form>
                    <%if (!confimacionCreacionInstruccion.equals("null")) {
                    %>
                    <div style="float: left;"class="alert alert-info mt-2" role="alert"><%=confimacionCreacionInstruccion%></div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <div class="container mt-5" style="float: left; width: 470px; height: 150px;  margin: 30px;">
            <div class="row">
                <div class="col-sm">
                    <form method="post" action="/MiMuebleria/ControladorConsultaMueble">
                        <label  class="col-form-label">Buscar mueble por nombre</label>
                        <input type="text" name="txtBuscar" class="form-control" placeholder="Nombre del mueble" required="">
                        <button type="submit" class="btn btn-success mt-3" name="btnBuscar">Buscar</button>                        
                    </form>
                </div>
            </div>
        </div>
        <div class="container mt-5" style="float: left; width: 470px; height: 150px;  margin: 30px;">
            <div class="row">
                <div class="col-sm">
                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Nombre del mueble</th>
                            <th scope="col">Precio del mueble</th>
                        </tr>
                        <%
                            try {
                                while (tablaMuebles.next()) {//exploramos el resultset con los parametros que sabemos que vendran
                                    out.print("<tr>");                  //creamos las nuevas tuplas
                                    out.print("<td>" + tablaMuebles.getString("nombre") + "</td>");
                                    out.print("<td>" + tablaMuebles.getDouble("precio") + "</td>");
                                    out.print("</tr>");
                                }
                            } catch (NullPointerException ex) {//el resultset puede estar nulo asi capturamos esa excepsion

                            }
                        %>
                    </table>      
                </div>
            </div>
        </div>
    </body>
</html>
