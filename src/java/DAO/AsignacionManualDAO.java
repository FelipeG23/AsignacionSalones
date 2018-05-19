/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.AsignacionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author JUAN
 */
public class AsignacionManualDAO {

    public String eliminarSalonXMateria(AsignacionEntity ent) {
        String rta = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    UPDATE CLASE.HORARIOS                                                               ");
            sql.append("       SET SAL_CODIGO = NULL                                                               ");
            sql.append("    WHERE GRU_CODIGO = ? AND TO_CHAR(FECHA,'dd/MM/yyyy') = ? AND  TO_CHAR (HORA_INICIO, 'HH12') = ? AND TO_CHAR (HORA_FIN, 'HH12')  = ?            ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, ent.getGrupoCodigo());
            ps.setString(2, ent.getFechaAsignada());
            ps.setString(3, ent.getHoraInicio());
            ps.setString(4, ent.getHoraFin());
            ps.executeUpdate();
            rta = "OK";
        } catch (Exception e) {
            rta = "ERROR " + e.getMessage();
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

    public String actualizarSalonXMateria(AsignacionEntity ent) {
        String rta = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    UPDATE CLASE.HORARIOS                                                               ");
            sql.append("       SET SAL_CODIGO = ?                                                               ");
            sql.append("    WHERE GRU_CODIGO = ? AND TO_CHAR(FECHA,'dd/MM/yyyy') = ? AND  TO_CHAR (HORA_INICIO, 'HH12') = ? AND TO_CHAR (HORA_FIN, 'HH12')  = ?            ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, ent.getSalonCodigo());
            ps.setString(2, ent.getGrupoCodigo());
            ps.setString(3, ent.getFechaAsignada());
            ps.setString(4, ent.getHoraInicio());
            ps.setString(5, ent.getHoraFin());
            ps.executeUpdate();
            rta = "OK";
        } catch (Exception e) {
            rta = "ERROR " + e.getMessage();
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

    public ArrayList<AsignacionEntity> consultarAsignaxMateria(String fecha, String materia) {
        ArrayList<AsignacionEntity> lista = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("	  SELECT MATER.*,                                          ");
            sql.append("	         TO_CHAR(HORARIO.FECHA,'dd/MM/yyyy') FECHA,                                    ");
            sql.append("	         TO_CHAR (HORARIO.HORA_INICIO, 'HH12 PM') INICIO,  ");
            sql.append("	         TO_CHAR (HORARIO.HORA_FIN, 'HH12 PM')  FIN,       ");
            sql.append("	         TO_CHAR (HORARIO.FECHA, 'DAY') DIA,               ");
            sql.append("	         HORARIO.SAL_CODIGO SALON,                          ");
            sql.append("	         HORARIO.GRU_CODIGO GRU_CODIGO                          ");
            sql.append("	    FROM HORARIOS HORARIO, GRUPOS GRUPO, MATERIAS MATER    ");
            sql.append("	   WHERE     GRUPO.CODIGO = HORARIO.GRU_CODIGO             ");
            sql.append("	         AND GRUPO.COD_MATERIA = MATER.CODIGO              ");
            sql.append("	         AND FECHA = TO_DATE (?, 'dd/MM/yyyy')             ");
            sql.append("	         AND MATER.CODIGO = ?                              ");
            sql.append("	ORDER BY HORARIO.FECHA ASC                    ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, fecha);
            ps.setString(2, materia);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                AsignacionEntity aux = new AsignacionEntity();
                aux.setCodigoMateria(rs.getString("CODIGO"));
                aux.setGrupoCodigo(rs.getString("GRU_CODIGO"));
                aux.setNombreMateria(rs.getString("NOMBRE"));
                aux.setFechaAsignada(rs.getString("FECHA"));
                aux.setHoraInicio(rs.getString("INICIO"));
                aux.setHoraFin(rs.getString("FIN"));
                aux.setDiaSemana(rs.getString("DIA"));
                aux.setSalonCodigo(rs.getString("SALON"));
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
}
