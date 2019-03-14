/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.AplicacionEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class AplicacionesDao {

    public List<AplicacionEntity> consultarAplicaciones() {
        Connection conn = null;
        List<AplicacionEntity> lista = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT CODIGO, NOMBRE FROM CLASE.APLICACIONES ");
            conn = ConexionDAO.GetConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql.toString());
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                AplicacionEntity aux = new AplicacionEntity();
                aux.setCodigo(rs.getInt("CODIGO"));
                aux.setNombre(rs.getString("NOMBRE"));
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

}
