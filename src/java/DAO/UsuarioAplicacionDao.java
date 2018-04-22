/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.UsuarioAplicacionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class UsuarioAplicacionDao {

    public List<UsuarioAplicacionEntity> consultarPermisosXUsuario(String idUsuario) {
        Connection conn = null;
        List<UsuarioAplicacionEntity> lista = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("        SELECT US.CODIGO USUARIO, AP.CODIGO                                         ");
            sql.append("          FROM USUARIOS US, APLICACIONES AP, APL_USUARIOS INTER                     ");
            sql.append("         WHERE US.CODIGO = INTER.COD_USUARIO AND INTER.COD_APLICACION = AP.CODIGO   ");
            sql.append("          AND US.CODIGO  = ? ");
            conn = ConexionDAO.GetConnection();
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                UsuarioAplicacionEntity aux = new UsuarioAplicacionEntity();
                aux.setCodAplicacion(rs.getLong("CODIGO"));
                aux.setCodUsuario(rs.getLong("USUARIO"));
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

    public String actualizarPermisos(String idUsuario, String listaPermisos) {
        String rta = "";
        Connection conn = null;

        PreparedStatement ps = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    DELETE FROM APL_USUARIOS    ");
            sql.append("    WHERE COD_USUARIO = ?       ");
            ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, Long.parseLong(idUsuario));
            ps.executeUpdate();

            String[] lista = listaPermisos.split(",");
            for (String item : lista) {
                sql = new StringBuilder();
                sql.append("    INSERT INTO CLASE.APL_USUARIOS (CODIGO, COD_APLICACION, COD_USUARIO) ");
                sql.append("         VALUES ((SELECT NVL(MAX(CODIGO)+1,1) FROM APL_USUARIOS),        ");
                sql.append("                 ?,                                                      ");
                sql.append("                  ?                                                      ");
                sql.append("                     )                                                   ");
                ps = conn.prepareStatement(sql.toString());
                ps.setLong(1, Long.parseLong(item));
                ps.setLong(2, Long.parseLong(idUsuario));
                ps.executeUpdate();
            }
            rta = "OK";
        } catch (Exception e) {
            rta = e.getMessage();
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
