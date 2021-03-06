/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.PuntoDeVenta;

import AreaDeVentas.GestorDeStock;
import AreaDeVentas.ProductoEnVenta;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "DescargarCarrito", urlPatterns = {"/DescargarCarrito"})
public class DescargarCarrito extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //el siguiente codigo va en todos los bodyes de las paginas en donde queremos que no se entre si el usuario no esta registrado
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("log") == null || sesion.getAttribute("log").equals("0") || !sesion.getAttribute("puesto").equals("Punto de venta")) {
            response.sendRedirect("/MiMuebleria/index.jsp");
        } else {
            try ( PrintWriter out = response.getWriter()) {
                int codigoEnsamble = Integer.valueOf(request.getParameter("codigoEnsamble"));
                int conditadorObjeto = 0;
                GestorDeStock gestor = new GestorDeStock();
                gestor.cambiarEstadoDeArticulo(codigoEnsamble, "Disponible");
                for (ProductoEnVenta item : gestor.carrito) {
                    if (item.getCodigoDeEnsamble() == codigoEnsamble) {
                        gestor.carrito.remove(conditadorObjeto);
                        break;
                    }
                    conditadorObjeto++;
                }
                response.sendRedirect("/MiMuebleria/Area-de-ventas/nueva-venta.jsp");
            } catch (NumberFormatException ex) {

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
