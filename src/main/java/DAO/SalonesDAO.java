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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class SalonesDAO {

    public List<SalonEntity> consultarSalonesDisponibles(String fecha, String inicio, String fin) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SalonEntity> lista = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT SAL.CODIGO,                                                                       ");
            sql.append("           SAL.NOMBRE,                                                                       ");
            sql.append("           SAL.ESTADO,                                                                       ");
            sql.append("           EDI_CODIGO,                                                                       ");
            sql.append("           CAPACIDAD                                                                         ");
            sql.append("      FROM SALONES SAL, EDIFICIOS EF                                                         ");
            sql.append("     WHERE  1=1                                                           ");
            sql.append("           AND SAL.CODIGO NOT IN                                                             ");
            sql.append("                   (SELECT SAL_CODIGO                                                        ");
            sql.append("                      FROM CLASE.HORARIOS                                                    ");
            sql.append("                     WHERE     TO_CHAR (FECHA, 'dd/MM/yyyy') = ?                             ");
            sql.append("                           AND EXTRACT (                                                     ");
            sql.append("                                   HOUR FROM CAST (HORA_INICIO AS TIMESTAMP)) BETWEEN ?      ");
            sql.append("                                                                                  AND ?      ");
            sql.append("                           AND SAL_CODIGO IS NOT NULL)                                       ");
            sql.append("           AND SAL.CODIGO NOT IN                                                             ");
            sql.append("                   (SELECT SAL_CODIGO                                                        ");
            sql.append("                      FROM clase.horarios                                                    ");
            sql.append("                     WHERE     TO_CHAR (fecha, 'dd/MM/yyyy') = ?                             ");
            sql.append("                           AND EXTRACT (HOUR FROM CAST (hora_fin AS TIMESTAMP)) BETWEEN ?    ");
            sql.append("                                                                                    AND ?    ");
            sql.append("                           AND sal_codigo IS NOT NULL)                                       ");
            sql.append("           AND SAL.ESTADO = 'A'                                                              ");
            sql.append("           AND SAL.EDI_CODIGO = EF.CODIGO                                                    ");
            sql.append("           ORDER BY SAL.CODIGO                                                     ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, fecha);
            ps.setString(2, inicio);
            ps.setString(3, fin);
            ps.setString(4, fecha);
            ps.setString(5, inicio);
            ps.setString(6, fin);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                SalonEntity aux = new SalonEntity();
                aux.setCodigo(rs.getString("CODIGO"));
                aux.setNombre(rs.getString("NOMBRE"));
                aux.setCodigoEdificio(rs.getLong("EDI_CODIGO"));
                aux.setCapacidad(rs.getLong("CAPACIDAD"));
                lista.add(aux);
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
        return lista;
    }

    public List<SalonEntity> consultarSalones() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SalonEntity> lista = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT CODIGO, NOMBRE, ESTADO, EDI_CODIGO, CAPACIDAD FROM CLASE.SALONES WHERE ESTADO = 'A' ");
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                SalonEntity aux = new SalonEntity();
                aux.setCodigo(rs.getString("CODIGO"));
                aux.setNombre(rs.getString("NOMBRE"));
                aux.setCodigoEdificio(rs.getLong("EDI_CODIGO"));
                aux.setCapacidad(rs.getLong("CAPACIDAD"));
                lista.add(aux);
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
        return lista;
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
                ps = conn.prepareStatement(sql.toString());
                ps.setString(1, ent.getCodigo());
                ps.setString(2, ent.getNombre());
                ps.setLong(3, ent.getCodigoEdificio());
                ps.setLong(4, ent.getCapacidad());
                ps.executeQuery();
                rta = "OK";
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

    public String actualizarSalon(SalonEntity ent) {
        String rta = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    UPDATE CLASE.SALONES      ");
            sql.append("       SET CODIGO = ?,        ");
            sql.append("           NOMBRE = ?,        ");
            sql.append("           EDI_CODIGO = ?,    ");
            sql.append("           CAPACIDAD = ?      ");
            sql.append("     WHERE CODIGO = ?         ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, ent.getNombre());
            ps.setString(2, ent.getNombre());
            ps.setLong(3, ent.getCodigoEdificio());
            ps.setLong(4, ent.getCapacidad());
            ps.setString(5, ent.getCodigo());
            ps.executeUpdate();
            rta = "OK";
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

    public String eliminarSalon(String codigoSalon) {
        String rta = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    UPDATE CLASE.SALONES      ");
            sql.append("       SET ESTADO = 'I'        ");
            sql.append("     WHERE CODIGO = ?         ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, codigoSalon);
            ps.executeUpdate();
            rta = "OK";
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
