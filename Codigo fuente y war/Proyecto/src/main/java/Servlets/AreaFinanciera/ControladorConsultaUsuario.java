/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaFinanciera;

import AreaFinanciera.ConsultaUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "ControladorConsultaUsuario", urlPatterns = {"/ControladorConsultaUsuario"})
public class ControladorConsultaUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Area financiera")) {
            response.sendRedirect("/MiMuebleria/index.jsp");
        } else {
            PrintWriter out = response.getWriter();
            ResultSet resultado = null;
            ConsultaUsuario consulta = new ConsultaUsuario();
            try {
                if (request.getParameter("btnBuscar") != null) {//si esto no es null entonces se preciono el boton de buscar usuario
                    String nombreUsuario = request.getParameter("txtNombreUsuario");//obtenemos el valor del nombre de usuario
                    resultado = consulta.buscarUsuarioPorNombre(nombreUsuario);
                    request.setAttribute("tablaUsuarios", resultado);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-usuarios.jsp").forward(request, response);
                } else if (request.getParameter("activar") != null) {//si esto se cumple entonces
                    String nombreUsuario = request.getParameter("usuario");//obtenemos el nombre de usuario
                    String confirmacion = consulta.cambiarEstadoDeUsuario(nombreUsuario, "Activo");//cambiamos su estado
                    resultado = consulta.mostrarTodosLosUsuarios();//mostramos todos los usuarios
                    request.setAttribute("tablaUsuarios", resultado);//mandamos la tabla
                    request.setAttribute("confirmacion", confirmacion);//mandamos la tabla
                    request.getRequestDispatcher("/Area-financiera/manejo-de-usuarios.jsp").forward(request, response);
                } else if (request.getParameter("desactivar") != null) {
                    String nombreUsuario = request.getParameter("usuario");//obtenemos el nombre de usuario
                    String confirmacion = consulta.cambiarEstadoDeUsuario(nombreUsuario, "Inactivo");//cambiamos su estado
                    resultado = consulta.mostrarTodosLosUsuarios();//mostramos todos los usuarios
                    request.setAttribute("tablaUsuarios", resultado);//mandamos la tabla
                    request.setAttribute("confirmacion", confirmacion);//mandamos la tabla
                    request.getRequestDispatcher("/Area-financiera/manejo-de-usuarios.jsp").forward(request, response);
                } else if (request.getParameter("btnNuevoUsuario") != null) {
                    String nombreTrabajador = request.getParameter("txtNombreEmpleado");
                    String nombreUsuario = request.getParameter("txtNombreUsuario");
                    String password = request.getParameter("txtContra");
                    String rol = request.getParameter("opcion");
                    String confimacion = "";
                    if (nombreTrabajador != null && !nombreTrabajador.equals("")
                            && nombreUsuario != null && !nombreUsuario.equals("")
                            && password != null && !password.equals("")
                            && rol != null && !rol.equals("")) {//nada puede ser nulo o estar vacio
                        confimacion = consulta.insertarNuevoUsuario(nombreTrabajador, password, nombreUsuario, rol);
                        request.setAttribute("confirmacionNuevoUsuario", confimacion);
                        request.getRequestDispatcher("/Area-financiera/manejo-de-usuarios.jsp").forward(request, response);
                    } else {//si no esntra al if algo es nulo o vacio entonces notificamos al usuario
                        confimacion = "Error parametros vacios";
                        request.setAttribute("confirmacionNuevoUsuario", confimacion);
                        request.getRequestDispatcher("/Area-financiera/manejo-de-usuarios.jsp").forward(request, response);
                    }
                } else {
                    resultado = consulta.mostrarTodosLosUsuarios();
                    request.setAttribute("tablaUsuarios", resultado);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-usuarios.jsp").forward(request, response);
                }
            } catch (Exception ex) {

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
