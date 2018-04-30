/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.SalonEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class SalonesDAO {

    public List<SalonEntity> consultarSalones() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String insertarSalon(SalonEntity ent) {
        String rta = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            boolean ban = false;
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) FROM SALONES  WHERE CODIGO = ?");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, ent.getNombre());
            rs = ps.executeQuery();
            while (rs.next()) {
                ban = rs.getLong(1) <= 0;
            }
            if (ban) {
                sql = new StringBuilder();
                sql.append("    INSERT INTO CLASE.SALONES (CODIGO,                ");
                sql.append("                               NOMBRE,                ");
                sql.append("                               ESTADO,                ");
                sql.append("                               EDI_CODIGO,            ");
                sql.append("                               CAPACIDAD)             ");
                sql.append("         VALUES (?,                                   ");
                sql.append("                 ?,                                   ");
                sql.append("                 'A',                                 ");
                sql.append("                 ?,                                   ");
                sql.append("                  ?                                   ");
                sql.append("                 )                                    ");
                ps.setString(1,ent.getCodigo());
                ps.setString(2, ent.getNombre());
                ps.setLong(3, ent.getCodigoEdificio());
                ps.setLong(4, ent.getCapacidad());
                ps.executeQuery();
                rta ="OK";
            } else {
                rta = "Ya existe un salon con codigo/nombre " + ent.getNombre();
            }

        } catch (Exception e) {
            e.printStackTrace();
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
