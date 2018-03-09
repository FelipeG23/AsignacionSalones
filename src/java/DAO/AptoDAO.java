/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.AptoEntity;
import Entities.PersonaEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Felipe
 */
public class AptoDAO {
    public String insertarApto(JSONObject json) {
        Connection conn = null;
        PreparedStatement ps = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO APTO (nombre, n_habitaciones) VALUES (?,?) ");
            ps = conn.prepareCall(sql.toString());
            ps.setString(1, json.getString("nombre"));
            ps.setInt(2, json.getInt("nHabitaciones"));
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
    
    
    public ArrayList<AptoEntity> consultarTodosAptos() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<AptoEntity> rta = null;
        try {

            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id ,nombre, n_habitaciones FROM apto ORDER BY ID ASC");
            st = conn.createStatement();
            rs = st.executeQuery(sql.toString());
            while (rs.next()) {
                if (null == rta) {
                    rta = new ArrayList<>();
                }
                AptoEntity aux = new AptoEntity();
                aux.setId(rs.getInt("id"));
                aux.setNombre(rs.getString("nombre"));
                aux.setnHabitaciones((rs.getInt("n_habitaciones")));
                rta.add(aux);
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
    public AptoEntity consultarEspecifico(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AptoEntity respuesta = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id ,nombre, n_habitaciones FROM APTO  WHERE ID = ? ");
            ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, Integer.parseInt(id));
            rs = ps.executeQuery();
            while (rs.next()) {
                respuesta = new AptoEntity();
                respuesta.setNombre(rs.getString("nombre"));
                respuesta.setnHabitaciones(rs.getInt("n_habitaciones"));
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
        return respuesta;
    }
     public String actualizar(AptoEntity apto) {
        Connection conn = null;
        PreparedStatement ps = null;
        String respuesta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE APTO  ");
            sql.append("SET NOMBRE = ? ,N_HABITACIONES = ? WHERE ID = ?");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, apto.getNombre());
            ps.setInt(2, apto.getnHabitaciones());
            ps.setInt(3, apto.getId());
            ps.executeUpdate();
            respuesta = "OK";
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
        return respuesta;
    }
      public String eliminar(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        String respuesta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM APTO  ");
            sql.append("WHERE ID = ?");
            ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
            respuesta = "OK";
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
        return respuesta;
    }
}
