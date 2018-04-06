/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.UsuarioEntity;
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
public class UsuarioDAO {

    /**
     * Metodo para insertar un usuario
     *
     * @param usuario
     * @param json
     * @return
     */
    public String insertarUsuario(UsuarioEntity usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("            INSERT INTO CLASE.USUARIOS (CODIGO,                         ");
            sql.append("                                        NOMBRE,                         ");
            sql.append("                                        APELLIDO,                       ");
            sql.append("                                        DOCUMENTO,                      ");
            sql.append("                                        CLAVE)                          ");
            sql.append("                VALUES ((SELECT NVL(MAX(CODIGO)+1,1) FROM USUARIOS),    ");
            sql.append("                        ?,                                              ");
            sql.append("                        ?,                                              ");
            sql.append("                        ?,                                              ");
            sql.append("                        ?                                               ");
            sql.append("                        )                                               ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setLong(3, usuario.getDocumento());
            ps.setString(4, usuario.getClave());
            ps.executeUpdate();
            rta = "OK";
        } catch (Exception e) {
            e.printStackTrace();
            rta = "ERROR" + e.getMessage();
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

    public List<UsuarioEntity> consultarTodosUsuarios() {
        List<UsuarioEntity> lista = null;
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT CODIGO, NOMBRE, APELLIDO, DOCUMENTO, CLAVE FROM CLASE.USUARIOS   ");
            st = conn.createStatement();
            rs = st.executeQuery(sql.toString());
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                UsuarioEntity aux =  new UsuarioEntity();
                aux.setCodigo(rs.getLong("CODIGO"));
                aux.setNombre(rs.getString("NOMBRE"));
                aux.setApellido(rs.getString("APELLIDO"));
                aux.setDocumento(rs.getLong("DOCUMENTO"));
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
