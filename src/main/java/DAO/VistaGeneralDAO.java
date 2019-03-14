/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.VistaGeneralEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class VistaGeneralDAO {

    public List<VistaGeneralEntity> consultarGeneralCalendario() {
        List<VistaGeneralEntity> lista = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT TO_CHAR (FECHA, 'yyyy-MM-dd') FECHA,                                                    ");
            sql.append("           TO_CHAR (FECHA, 'yyyy-MM-dd') ||'T'|| TO_CHAR (HORA_INICIO, 'HH24') ||':00:00' INICIO,  ");
            sql.append("           TO_CHAR (FECHA, 'yyyy-MM-dd') ||'T'|| TO_CHAR (HORA_FIN, 'HH24') ||':00:00' FIN,        ");
            sql.append("           MATER.NOMBRE NOMBRE_MATERIA,                                                            ");
            sql.append("           PROG.NOMBRE PROGRAMA,                                                                   ");
            sql.append("           SALON.NOMBRE SALON                                                                      ");
            sql.append("                                                                                                   ");
            sql.append("      FROM HORARIOS   HORARIO,                                                                     ");
            sql.append("           GRUPOS     GRUPO,                                                                       ");
            sql.append("           SALONES    SALON,                                                                       ");
            sql.append("           MATERIAS   MATER,                                                                       ");
            sql.append("           PROGRAMAS  PROG                                                                         ");
            sql.append("     WHERE     SAL_CODIGO IS NOT NULL                                                              ");
            sql.append("           AND HORARIO.GRU_CODIGO = GRUPO.CODIGO                                                   ");
            sql.append("           AND HORARIO.SAL_CODIGO = SALON.CODIGO                                                   ");
            sql.append("           AND MATER.CODIGO = GRUPO.COD_MATERIA                                                    ");
            sql.append("           AND GRUPO.PL_CODIGO = PROG.CODIGO                                                       ");
            rs = conn.createStatement().executeQuery(sql.toString());
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                VistaGeneralEntity aux = new VistaGeneralEntity();
                aux.setFecha(rs.getString("FECHA"));
                aux.setStart(rs.getString("INICIO"));
                aux.setEnd(rs.getString("FIN"));
                aux.setTitle(rs.getString("NOMBRE_MATERIA") +"-"+ rs.getString("PROGRAMA") +"-"+ rs.getString("SALON"));
                aux.setPrograma(rs.getString("PROGRAMA"));
                aux.setSalon(rs.getString("SALON"));
                lista.add(aux);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null){
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
    public List<VistaGeneralEntity> consultarGeneralCalendarioXPrograma(Long id) {
        List<VistaGeneralEntity> lista = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT TO_CHAR (FECHA, 'yyyy-MM-dd') FECHA,                                                    ");
            sql.append("           TO_CHAR (FECHA, 'yyyy-MM-dd') ||'T'|| TO_CHAR (HORA_INICIO, 'HH24') ||':00:00' INICIO,  ");
            sql.append("           TO_CHAR (FECHA, 'yyyy-MM-dd') ||'T'|| TO_CHAR (HORA_FIN, 'HH24') ||':00:00' FIN,        ");
            sql.append("           MATER.NOMBRE NOMBRE_MATERIA,                                                            ");
            sql.append("           PROG.NOMBRE PROGRAMA,                                                                   ");
            sql.append("           SALON.NOMBRE SALON                                                                      ");
            sql.append("                                                                                                   ");
            sql.append("      FROM HORARIOS   HORARIO,                                                                     ");
            sql.append("           GRUPOS     GRUPO,                                                                       ");
            sql.append("           SALONES    SALON,                                                                       ");
            sql.append("           MATERIAS   MATER,                                                                       ");
            sql.append("           PROGRAMAS  PROG                                                                         ");
            sql.append("     WHERE     SAL_CODIGO IS NOT NULL                                                              ");
            sql.append("           AND HORARIO.GRU_CODIGO = GRUPO.CODIGO                                                   ");
            sql.append("           AND HORARIO.SAL_CODIGO = SALON.CODIGO                                                   ");
            sql.append("           AND MATER.CODIGO = GRUPO.COD_MATERIA                                                    ");
            sql.append("           AND GRUPO.PL_CODIGO = PROG.CODIGO                                                       ");
            sql.append("           AND  PROG.CODIGO  = ?                                                     ");
            ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                VistaGeneralEntity aux = new VistaGeneralEntity();
                aux.setFecha(rs.getString("FECHA"));
                aux.setStart(rs.getString("INICIO"));
                aux.setEnd(rs.getString("FIN"));
                aux.setTitle(rs.getString("NOMBRE_MATERIA") +"-"+ rs.getString("PROGRAMA") +"-"+ rs.getString("SALON"));
                aux.setPrograma(rs.getString("PROGRAMA"));
                aux.setSalon(rs.getString("SALON"));
                lista.add(aux);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null){
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

}
