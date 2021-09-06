/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.AreaFinanciera;

import AreaFinanciera.LectorDeArchivo;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Luis Monterroso
 */
@WebServlet(name = "CargaDeArchivo", urlPatterns = {"/CargaDeArchivo"})
@MultipartConfig(location = "org")
public class CargaDeArchivo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LectorDeArchivo carga = new LectorDeArchivo();
        Part parteDeArchivo = request.getPart("txtArchivo");
        InputStream inputStream = parteDeArchivo.getInputStream();
        carga.leerArchivo(inputStream);
        
        
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LectorDeArchivo carga = new LectorDeArchivo();
        Part parteDeArchivo = request.getPart("txtArchivo");
        InputStream inputStream = parteDeArchivo.getInputStream();
        carga.leerArchivo(inputStream);
        
        
    }
}
