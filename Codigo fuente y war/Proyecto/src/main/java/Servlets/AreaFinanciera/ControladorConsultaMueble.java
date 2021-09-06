/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaFinanciera;

import AreaFinanciera.ConsultaMueble;
import java.io.IOException;
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
@WebServlet(name = "ControladorConsultaMueble", urlPatterns = {"/ControladorConsultaMueble"})
public class ControladorConsultaMueble extends HttpServlet {

    /**
     * Con este metodo porcesamos los get y los post es necesario pues usaremos
     * los dos tipos de rquest
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ConsultaMueble consulta = new ConsultaMueble();
        //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Area financiera")) {
            response.sendRedirect("/MiMuebleria/index.jsp");
        } else {
            if (request.getParameter("btnBuscar") != null) {//si esto se cumple el usuario quiere buscar
                String nombreMueble = request.getParameter("txtBuscar");//obtenmos el valor ingresado en textbox
                if (nombreMueble != null && !nombreMueble.equals("")) {//vemos que no esta vacio o nulo
                    ResultSet tablaMuebles = consulta.buscarMueblePorNombre(nombreMueble);
                    request.setAttribute("tablaMuebles", tablaMuebles);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
                } else {//si esto se cumple entoces mostramos todos los muebles
                    ResultSet tablaMuebles = consulta.mostrarMuebles();
                    request.setAttribute("tablaMuebles", tablaMuebles);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
                }
            } else if (request.getParameter("btnIngresar") != null) {//si esto se cumple el usuairo quiere ingresar algo nuevo
                //tomamos los valores necesarios
                String nombreMueble = request.getParameter("txtNombreMueble");
                double precioMueble = 0;
                String confimacionCreacion = "";
                try {//intenatmos ocnvertir el numero
                    precioMueble = Double.parseDouble(request.getParameter("txtPrecio"));
                    if (precioMueble <= 0) {//si el numeo convertido es menor o igual a 0
                        throw new NumberFormatException();//lanzamos una excepcion
                    }
                } catch (NumberFormatException ex) {//proceduimiento para manejo de excepcion
                    confimacionCreacion = "No se creo el mueble, se esperaba un precio valido";
                    request.setAttribute("confimacionCreacion", confimacionCreacion);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
                }//si llega aqui entonces no hay problema de parseo
                if (nombreMueble != null && !nombreMueble.equals("")) {//si no esta vacio o nuklo este campo entramos
                    confimacionCreacion = consulta.crearNuevoMueble(nombreMueble, precioMueble);
                    request.setAttribute("confimacionCreacion", confimacionCreacion);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
                } else {
                    confimacionCreacion = "No se creo el mueble, parametros vacios";
                    request.setAttribute("confimacionCreacion", confimacionCreacion);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
                }
            } else if (request.getParameter("btnInstruccion") != null) {//si se cumple esto el usuario quiere ingresar una nueva instruccion
                String nombreMueble = request.getParameter("txtNombreMueble");
                String nombrePieza = request.getParameter("txtNombrePieza");
                double precioPieza = 0;
                int cantidadNecesitada = 0;
                String confimacionCreacionInstruccion = "";
                try {
                    precioPieza = Double.parseDouble(request.getParameter("txtPrecioPieza"));
                    cantidadNecesitada = Integer.valueOf(request.getParameter("txtCantidad"));
                } catch (NumberFormatException ex) {//si hay problema con la conversion entonces lanzamos la el mensaje de error
                    confimacionCreacionInstruccion = "No se creo instruccion, parametros numericos con formato incorrcto";
                    request.setAttribute("confimacionCreacionInstruccion", confimacionCreacionInstruccion);
                    ResultSet tablaMuebles = consulta.mostrarMuebles();//queremos mostrar siempre los muvbles creados
                    request.setAttribute("tablaMuebles", tablaMuebles);
                    request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
                }//si llega aqui etnonces no hay problema de parseo
                confimacionCreacionInstruccion = consulta.crearIndicaciones(nombreMueble, nombrePieza, precioPieza, cantidadNecesitada);//intentamos insertar la instruccion
                request.setAttribute("confimacionCreacionInstruccion", confimacionCreacionInstruccion);//mandamos el mensaje de confimacion
                ResultSet tablaMuebles = consulta.mostrarMuebles();//queremos mostrar siempre los muvbles creados
                request.setAttribute("tablaMuebles", tablaMuebles);//mandmaos los muebles creados
                request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);//redirigimmos a la pagina

            } else {
                ResultSet tablaMuebles = consulta.mostrarMuebles();
                request.setAttribute("tablaMuebles", tablaMuebles);
                request.getRequestDispatcher("/Area-financiera/manejo-de-muebles.jsp").forward(request, response);
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
