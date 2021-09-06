package AreaFinanciera;

import java.io.*;
import java.util.Arrays;

public class LectorDeArchivo {

    public void leerArchivo(InputStream inputStream) {
        try {
            //Estas son las variables y objetos que serviran para leer el fichero de entrada
            String linea;
            String[] campos;
            InputStreamReader InputReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(InputReader);
            //
            while ((linea = bufferedReader.readLine()) != null) {
                campos = separarCampos(linea, "(");
                System.out.println(Arrays.toString(campos));
            }
        } catch (Exception ex) {

        }

    }

    public String[] separarCampos(String linea, String identificador) {
        //identificamos la instruccion sabemos que es la palabra que esta antes del (
        String[] inicioRegistro = linea.split("\\" + identificador);
        //la instruccion sera la primera posicion del arreglo devuelto
        String identificadorPeticion = inicioRegistro[0];
        //esta linea de campos sera la union de la peticion mas todo lo que le sigue
        String lineaDeCampos;
        //seteamos la linea de campos
        lineaDeCampos = identificadorPeticion + "," + linea.substring(identificadorPeticion.length() + 1, linea.length() - 1);
        //cambiamos todos los espacios entre coma y texto para evitar errores
        String lineaDeCamposSinEspacios = lineaDeCampos.replaceAll(", ", ",");
        //separamos las entradas por las comas
        String[] campos = lineaDeCamposSinEspacios.split(",");
        //construimos una nueva linea
        String lineaDeCamposSinComas = "";
        //con este for exploramo los campos y los unimos de nuevo quedaran separados por comillas
        for (int x = 0; x < campos.length; x++) {
            lineaDeCamposSinComas = lineaDeCamposSinComas + campos[x];
        }
        //puede que en la linea sin comas hayan dos pares de comillas juntas, asi que las quitamos
        String palabraSinDobleComilla = lineaDeCamposSinComas.replaceAll("\"\"", "\"");
        //separamos las palabras de string que estaran separadas por "
        String[] camposFinales = palabraSinDobleComilla.split("\"");
        return camposFinales;
    }
}
