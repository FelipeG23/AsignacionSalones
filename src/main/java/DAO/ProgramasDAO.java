/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.ProgramasEntity;
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
public class ProgramasDAO {

    public String insertarSalon(ProgramasEntity ent) {
        StringBuilder sql = new StringBuilder();
        Connection conn = null;
        String rta = "";
        PreparedStatement ps = null;
        try {
            conn = ConexionDAO.GetConnection();
            sql.append("    INSERT INTO CLASE.PROGRAMAS (CODIGO, NOMBRE,ESTADO)               ");
            sql.append("         VALUES ((SELECT NVL(MAX(CODIGO)+1,1) FROM PROGRAMAS), ");
            sql.append("                 ? , 'A'                                             ");
            sql.append("                   )                                           ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, ent.getNombre());
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

    public String actualizarPrograma(ProgramasEntity ent) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            StringBuilder sql = new StringBuilder();
            conn = ConexionDAO.GetConnection();
            sql.append("    UPDATE CLASE.PROGRAMAS  ");
            sql.append("       SET NOMBRE = ?       ");
            sql.append("     WHERE CODIGO = ?       ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, ent.getNombre());
            ps.setLong(2, ent.getCodigo());
            ps.executeUpdate();
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
        return "";
    }

    public String eliminarPrograma(Long idPrograma) {
        Connection conn = null;
        PreparedStatement ps = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    UPDATE CLASE.PROGRAMAS  ");
            sql.append("       SET ESTADO = 'I'       ");
            sql.append("     WHERE CODIGO = ?       ");
            ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, idPrograma);
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

    public List<ProgramasEntity> consultarTodosProgramas() {
        List<ProgramasEntity> lista = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT CODIGO, NOMBRE, ESTADO FROM CLASE.PROGRAMAS   ");
            sql.append("    WHERE ESTADO = 'A'                                   ");
            sql.append("    ORDER BY   CODIGO                                 ");
            st = conn.createStatement();
            rs = st.executeQuery(sql.toString());
            while(rs.next()){
                if(lista == null){
                    lista = new ArrayList<>();
                }
                ProgramasEntity aux = new ProgramasEntity();
                aux.setCodigo(rs.getLong("CODIGO"));
                aux.setNombre(rs.getString("NOMBRE"));
                lista.add(aux);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}
