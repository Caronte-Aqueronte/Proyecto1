/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaDeFabrica;

import AreaDeFabrica.GestorDeEnsamble;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "BuscarEnElLIbroDeIndicacion", urlPatterns = {"/BuscarEnElLibroDeIndicacion"})
public class BuscarEnElLibroDeIndicacion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorDeEnsamble gestor = new GestorDeEnsamble();
        if (request.getParameter("btnBuscar") != null) {//si esto se cumple entonces se quiere buscar las intrucciones
            String nombreMueble = request.getParameter("txtNombreMueble");
            if (nombreMueble != null && !nombreMueble.equals("")) {//si no esta bacio o nulo el parametro
                ResultSet tablaIndicacion = gestor.buscarInstrucciones(nombreMueble);//buscamos dentro de las instrucciones
                request.setAttribute("tablaIndicacion", tablaIndicacion);//pasamos esre parametro que es la tabla
                request.getRequestDispatcher("/Area-de-fabrica/libro-de-indicaciones.jsp").forward(request, response);//avanzamos
            } else {
                response.sendRedirect("/MiMuebleria/Area-de-fabrica/libro-de-indicaciones.jsp");
            }
        } else {//si no hay nada solo mostramos la tabla de indicaciones
            ResultSet tablaIndicacion = gestor.mostrarTodasLasInstrucciones();//buscamos dentro de las instrucciones
            request.setAttribute("tablaIndicacion", tablaIndicacion);//pasamos esre parametro que es la tabla
            request.getRequestDispatcher("/Area-de-fabrica/libro-de-indicaciones.jsp").forward(request, response);//avanzamos
        }

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GestorDeEnsamble gestor = new GestorDeEnsamble();
        if (request.getParameter("btnBuscar") != null) {//si esto se cumple entonces se quiere buscar las intrucciones
            String nombreMueble = request.getParameter("txtNombreMueble");
            if (nombreMueble != null && !nombreMueble.equals("")) {//si no esta bacio o nulo el parametro
                ResultSet tablaIndicacion = gestor.buscarInstrucciones(nombreMueble);//buscamos dentro de las instrucciones
                request.setAttribute("tablaIndicacion", tablaIndicacion);//pasamos esre parametro que es la tabla
                request.getRequestDispatcher("/Area-de-fabrica/libro-de-indicaciones.jsp").forward(request, response);//avanzamos
            } else {
                response.sendRedirect("/MiMuebleria/Area-de-fabrica/libro-de-indicaciones.jsp");
            }
        } else {//si no hay nada solo mostramos la tabla de indicaciones
            ResultSet tablaIndicacion = gestor.mostrarTodasLasInstrucciones();//buscamos dentro de las instrucciones
            request.setAttribute("tablaIndicacion", tablaIndicacion);//pasamos esre parametro que es la tabla
            request.getRequestDispatcher("/Area-de-fabrica/libro-de-indicaciones.jsp").forward(request, response);//avanzamos
        }

    }
}
