/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Estudiante 2018
 */
@WebServlet(urlPatterns = {"/subirArchivo"})
public class SubirArchivo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String rutaTempFinal = "";
        try {
            String rutaTemp = System.getProperty("user.home");
            Part filePart = request.getPart("anexos"); // Obtiene el archivo
            String nombre = getFileName(filePart);
            File file = new File(rutaTemp + "/" + nombre);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            rutaTempFinal = file.toString();
            Reader reader = Files.newBufferedReader(Paths.get(file.toString()));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            
            for (CSVRecord csvRecord : csvRecords) {
                System.out.println(""+csvRecord.get(0));    
            }
            csvParser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                File f = new File(rutaTempFinal);
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileName(Part part) {
        String respuesta = "";
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                respuesta = token.split("=")[1];
                respuesta = respuesta.replaceAll("\"", "");
            }
        }
        return respuesta;
    }

}
