/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.EmpresaDAO;
import DAO.UsuarioDAO;
import Entities.PersonaEntity;
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
        String respuesta = "";
        try {
            String rutaTemp = System.getProperty("user.home");
            Part filePart = null;
            try {
                filePart = request.getPart("anexos"); // Obtiene el archivo
            } catch (Exception e) {
                filePart = null;
            }
            if (filePart != null) {
                String nombre = getFileName(filePart);
                File file = new File(rutaTemp + "/" + nombre);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                rutaTempFinal = file.toString();
                Reader reader = Files.newBufferedReader(Paths.get(file.toString()));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                Iterable<CSVRecord> csvRecords = csvParser.getRecords();
                EmpresaDAO daoEmpresa = new EmpresaDAO();
                UsuarioDAO daoUsuario = new UsuarioDAO();
                for (CSVRecord csvRecord : csvRecords) {

                    if (csvRecord.size() == 5) {
                        PersonaEntity persona = new PersonaEntity();
                        persona.setNombre(csvRecord.get(0));
                        persona.setApellido(csvRecord.get(1));
                        persona.setDocumento(Long.parseLong(csvRecord.get(2)));
                        persona.setEmail(csvRecord.get(3));
                        Long idEmpresa = daoEmpresa.consultarEmpresaXNombre(csvRecord.get(4));
                        persona.setEmpresa(idEmpresa);
                        String rta = daoUsuario.insertarUsuario(persona);
                        if (!rta.equalsIgnoreCase("OK")) {
                            respuesta = rta;
                        }
                    }
                }
                csvParser.close();
                respuesta = "OK";
            } else {
                respuesta = "Error al subir el archivo , intente de nuevo";
            }
            response.getWriter().write(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
