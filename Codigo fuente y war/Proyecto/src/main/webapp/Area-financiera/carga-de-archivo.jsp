<%-- 
    Document   : tipos-de-muebles
    Created on : 4/09/2021, 19:16:16
    Author     : Luis Monterroso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
        <title>Tipos de muebles</title>
    </head>
    <body>
        <%
            //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
            HttpSession sesion = request.getSession();
            if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Area financiera")) {
                response.sendRedirect("/MiMuebleria/index.jsp");
            }
        %>
        <jsp:include page="nav-regresar.jsp"/>
        <div class="container mt-5">
            <div class="row">
                <div class="col-sm">
                    <form method="get" action="/MiMuebleria/CargaDeArchivo" enctype="multipart/form-data">
                        <div class="mb-3" style="width: 400px">
                            <label class="form-label" for="txtArchivo">Click para seleccionar archivo</label>
                            <input class="form-control" type="file" name="txtArchivo" id="txtArchivo">
                        </div>
                        <button type="submit" name="btnCargar" class="btn btn-info"><i class='bx bxs-cloud-upload' style='color:#ffffff'  ></i> Cargar archivo</button>
                    </form>
                    <div class="form-floating mt-5" style="height: 800px; width: 1000px">
                        <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 700px"></textarea>
                        <label for="floatingTextarea2">Completados</label>
                    </div>
                    <div class="form-floating" style="height: 800px; width: 1000px">
                        <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 700px"></textarea>
                        <label for="floatingTextarea2">Errores</label>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
