/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Estudiante 2018
 */
@WebServlet(urlPatterns = {"/subirArchivo"})
public class SubirArchivo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Part filePart = request.getPart("anexos"); // Obtiene el archivo
            System.out.println(""+filePart);
            System.out.println("");
            System.out.println("");
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileName(Part part) {
        String respuesta = "";
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                respuesta = token.substring(token.lastIndexOf("."));
            }
        }
        return respuesta;
    }

}
