<%-- 
    Document   : manejo-de-usuarios
    Created on : 2/09/2021, 20:48:06
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
        <title>Manejo de usuarios</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Area financiera")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            }
            ResultSet usuarios = (ResultSet) request.getAttribute("tablaUsuarios");
            String confimacionEstado = String.valueOf(request.getAttribute("confirmacion"));
            String confimacionNuevoUsuario = String.valueOf(request.getAttribute("confirmacionNuevoUsuario"));
        %>
        <jsp:include page="nav-regresar.jsp"/>
        <div class="container mt-5" style="border-style: dotted;">
            <div class="row">
                <div class="col-sm">

                    <div style=" float: left; width: 470px; height: 150px;  margin: 30px;">
                        <form method="post" action="/MiMuebleria/ControladorConsultaUsuario">
                            <label  class="col-form-label">Buscar empleado por nombre de usuario o nombre del empleado</label>
                            <input type="text" name="txtNombreUsuario" class="form-control" placeholder="">
                            <button type="submit" class="btn btn-success mt-3 me-3" name="btnBuscar">Buscar usuario/empleado</button>
                            <button type="submit" class="btn btn-success mt-3" name="btnTodos">Mostrar todos los usuarios</button>
                        </form>
                        <%if (!confimacionEstado.equals("null")) {
                        %>
                        <div style="float: left;"class="alert alert-info mt-2" role="alert"><%=confimacionEstado%></div>
                        <%
                            }
                        %>
                    </div>
                    <div style="float: right; width: 470px; height: 370px;  margin: 30px">
                        <form method="post" action="/MiMuebleria/ControladorConsultaUsuario">
                            <label  class="col-form-label">Crear nuevo usuario</label>
                            <input type="text" name="txtNombreEmpleado" class="form-control" placeholder="Nombre del empleado" required="">
                            <input type="text" name="txtNombreUsuario" class="form-control mt-2" placeholder="Nombre de usuario" required="">
                            <input type="text" name="txtContra" class="form-control mt-2" placeholder="Password" required="">
                            <div class="form-check mt-2">
                                <input class="form-check-input" type="radio" name="opcion" id="flexRadioDefault1" value="Fabrica" checked="">
                                <label class="form-check-label">
                                    Área de Fábrica
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="opcion" id="flexRadioDefault2" value="Punto de venta">
                                <label class="form-check-label">
                                    Punto de venta
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="opcion" id="flexRadioDefault2" value=" Area financiera">
                                <label class="form-check-label">
                                    Área financiera
                                </label>
                            </div>
                            <button type="submit" class="btn btn-success mt-3" name="btnNuevoUsuario">Crear nuevo usuario</button>
                        </form>
                        <%
                            if (!confimacionNuevoUsuario.equals("null")) {
                        %>
                        <div style="float: left;"class="alert alert-info mt-2" role="alert"><%=confimacionNuevoUsuario%></div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
        <div class="container mt-3">
            <div class="row">
                <div class="col-sm">
                    <table class="table table-striped">
                        <tr>                               
                            <th scope="col">Nombre del trabajador</th>
                            <th scope="col">Nombre de usuario</th>
                            <th scope="col">Puesto del trabajador</th>
                            <th scope="col">Estado del usuario</th>
                            <th scope="col">Activar / Cancelar Usuario</th>
                        </tr>
                        <%                            try {
                                while (usuarios.next()) {
                                    out.print("<tr>");
                                    out.print("<td>" + usuarios.getString("nombre_de_trabajador") + "</td>");
                                    out.print("<td>" + usuarios.getString("usuario") + "</td>");
                                    out.print("<td>" + usuarios.getString("puesto") + "</td>");
                                    out.print("<td>" + usuarios.getString("estado") + "</td>");
                                    //este es el boton para activar un usuario
                                    out.print("<td><a href=\"/MiMuebleria/ControladorConsultaUsuario?activar=&usuario=" + usuarios.getString("usuario") + "\">" + "<i class='bx bx-checkbox-checked' style='color:#16d520'></i>");
                                    //boton para desactivvar un usuario
                                    out.print("<a href=\"/MiMuebleria/ControladorConsultaUsuario?desactivar=&usuario=" + usuarios.getString("usuario") + "\">" + "<i class='bx bxs-user-minus' style='color:#f60407'></i><td>");
                                    out.print("</tr>");
                                }
                            } catch (NullPointerException ex) {

                            }
                        %>
                    </table>            
                </div>
            </div>
        </div>
    </body>
</html>
