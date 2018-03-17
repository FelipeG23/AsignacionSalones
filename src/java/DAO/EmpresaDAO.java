/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Felipe
 */
public class EmpresaDAO {

    /**
     * Metodo para consulta el codigo de una empresa por su nombre
     *
     * @param nombreEmpresa
     * @return
     */
    public Long consultarEmpresaXNombre(String nombreEmpresa) {
        Long rta = new Long("0");
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT ID_EMPRESA FROM CLASE.EMPRESA        ");
            sql.append("    WHERE NOMBRE_EMPRESA = ?                    ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, nombreEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
                rta = rs.getLong("ID_EMPRESA");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rta;
    }
}
