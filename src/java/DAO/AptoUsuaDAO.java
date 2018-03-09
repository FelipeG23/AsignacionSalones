/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.json.JSONObject;

/**
 *
 * @author Felipe
 */
public class AptoUsuaDAO {

    public String insertarProApto(JSONObject json) {
        Connection conn = null;
        PreparedStatement ps = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO apto_propiet (id_pro, id_apto) VALUES (?,?) ");
            ps = conn.prepareCall(sql.toString());
            ps.setInt(1, json.getInt("prop_id"));
            ps.setInt(2, json.getInt("apto_id"));
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

    public String borrarTodo() {
        Connection conn = null;
        Statement st = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM apto_propiet ");
            st = conn.createStatement();
            st.executeUpdate(sql.toString());
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
