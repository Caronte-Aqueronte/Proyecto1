/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AreaFinanciera;

import AreaDeFabrica.GestorDePieza;
import java.sql.*;
import static ConexionMysql.Conexion.CONEXION;

/**
 *
 * @author Luis Monterroso
 */
public class ConsultaMueble {

    public ResultSet mostrarMuebles() {
        try {
            PreparedStatement query = CONEXION.prepareCall(
                    "SELECT * FROM mueble;");//esta query devolvera todo de la tabla mueble
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (SQLException ex) {
            return null;
        }
    }

    public ResultSet buscarMueblePorNombre(String nombre) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM mueble WHERE nombre like ?");//Esta query busca por el nombre
            query.setString(1, "%"+nombre+"%");//damos valor al ?
            ResultSet resultado = query.executeQuery();
            return resultado;
        } catch (SQLException ex) {
            return null;
        }
    }

    public String crearNuevoMueble(String nombreMueble, double precio) {
        PreparedStatement query;
        try {
            if (saberSiExisteMueble(nombreMueble) == false) {
                query = CONEXION.prepareStatement(
                        "INSERT INTO mueble VALUES (?,?);"); //esto insertara los valores enviados
                query.setString(1, nombreMueble);//damos valores a los ?
                query.setDouble(2, precio);
                if (query.executeUpdate() > 0) {//si esto se cumple entonces se agrego con exito
                    CONEXION.commit();
                    return "Mueble creado con exito";
                }
                //si llega aqui entonces no entro al if y no se creo el usuario
                throw new SQLException("No se creo el usuario, revise que los parametros sean validos");
            } else {
                return "No se creo mueble, el mueble indicado ya existe en la base de datos";
            }
        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex2) {

            }
            return ex.getMessage();
        } catch (NullPointerException ex) {
            return "Error parametros vacios";
        }
    }

    public boolean saberSiExisteMueble(String nombreMueble) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM mueble WHERE UPPER(nombre) = UPPER(?);");//esta consulta muestra los muebles con el nombre que se manda
            query.setString(1, nombreMueble);//le damos valor a los ?
            ResultSet respuesta = query.executeQuery();//ejecutamos la query
            if (respuesta.next()) {//si esntra aqui entonces existe ese mueble
                return true;//
            }
            //si llega aqui entonces el mueble no existe y retornamos false
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean saberSiExisteIndicacion(String nombreMueble, String nombrePieza, double precio) {
        try {
            PreparedStatement query = CONEXION.prepareStatement(
                    "SELECT * FROM formula WHERE UPPER(codigo_de_mueble) = UPPER(?) AND UPPER(nombre_de_pieza) = UPPER(?) "
                    + "AND precio_de_pieza = ;?");//busca por medio de los parametros
            query.setString(1, nombreMueble);//damos valores a los ?
            query.setString(2, nombrePieza);
            query.setDouble(3, precio);
            ResultSet resultado = query.executeQuery();//ejecutamos la query
            while (resultado.next()) {//si entra entonces ya existe la instruccion
                return true;
            }
            return false;//si lllea aqui no existe la instruccion
        } catch (SQLException ex) {
            return false;
        }
    }

    public String crearIndicaciones(String nombreMueble, String nombrePieza, double precio, int piezasNecesarias) {
        try {
            boolean existeMueble = saberSiExisteMueble(nombreMueble);
            if (existeMueble) {//si esto es true entonce vemos si existe la pieza
                if (saberSiExisteIndicacion(nombreMueble, nombrePieza, precio) == false) {//si esto es false entonces no hay una instruccion para eso entonces podmeos seguir
                    if (GestorDePieza.saberSiExistePieza(nombrePieza, precio)) {//si existe la pieza eontces hacemos el rgistro
                        CONEXION.setAutoCommit(false);
                        PreparedStatement query = CONEXION.prepareStatement(""
                                + "INSERT INTO formula VALUES (?,?,?,?)");//insertamos la instruccion
                        query.setString(1, nombreMueble);//damos valores a los ?
                        query.setString(2, nombrePieza);
                        query.setDouble(3, precio);
                        query.setInt(4, piezasNecesarias);
                        if (query.executeUpdate() > 0) {//si la insercion afecta una fila entonces notificamos con exio
                            CONEXION.commit();
                            return "Se agrego con exito la indicacion";
                        } else {//si no entonces notificamos que no se guardo
                            return "No se guardo la indicacion debido a un error inesperado";
                        }
                    } else {//si no existe la pieza notificamos
                        return "La pieza especificada no existe, no se guardo la indicacion";
                    }
                } else {
                    return "Ya hay una instruccion igual para este mueble";
                }
            } else {//si no entonces generamos la excepcion
                return "El mueble especificado no existe";
            }
        } catch (SQLException ex) {
            try {
                CONEXION.rollback();
            } catch (SQLException ex1) {
            }
            return ex.getMessage();
        }
    }
}
