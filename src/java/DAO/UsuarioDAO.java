/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.PersonaEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class UsuarioDAO {

    /**
     * Metodo para insertar un usuario
     *
     * @param json
     * @return
     */
    public String insertarUsuario(PersonaEntity usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    INSERT INTO clase.usuario ");
            sql.append("    (NOMBRES,                 ");
            sql.append("    APELLIDOS,                ");
            sql.append("    DOCUMENTO,                ");
            sql.append("    EMAIL,                    ");
            sql.append("    SEXO,                     ");
            sql.append("    ID_EMPRESA)               ");
            sql.append("    VALUES(                   ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?                         ");
            sql.append("    )                         ");
            ps = conn.prepareStatement(sql.toString());
           
            ps.executeUpdate();
            rta = "OK";
        } catch (Exception e) {
            e.printStackTrace();
            rta = e.getMessage();
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return rta;
    }

   
}
