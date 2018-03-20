/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.EmpresaEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Felipe
 */
public class EmpresaDAO {

    /**
     * Metodo para consulta el codigo de una empresa por su nombre
     *
     * @param nombreEmpresa
     * @return
     */
    public Long consultarEmpresaXNombre(String nombreEmpresa) {
        Long rta = new Long("0");
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT ID_EMPRESA FROM CLASE.EMPRESA        ");
            sql.append("    WHERE NOMBRE_EMPRESA = ?                    ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, nombreEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
                rta = rs.getLong("ID_EMPRESA");
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
        return rta;
    }

    /**
     * Metodo para consulta el nombre de una empresa por su codigo
     *
     * @param nombreEmpresa
     * @return
     */
    public String consultarEmpresaXID(Long idEmpresa) {
        String rta = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    SELECT  NOMBRE_EMPRESA FROM CLASE.EMPRESA        ");
            sql.append("    WHERE ID_EMPRESA = ?                    ");
            ps = conn.prepareStatement(sql.toString());
            ps.setLong(1, idEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
                rta = rs.getString("NOMBRE_EMPRESA");
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
        return rta;
    }

    /**
     * Metodo para buscar todas las empresas en la BD
     *
     * @return
     */
    public List<EmpresaEntity> consultarEmpresas() {
        List<EmpresaEntity> lista = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("select ID_EMPRESA,NOMBRE_EMPRESA from empresa");
            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                EmpresaEntity aux = new EmpresaEntity();
                aux.setId_Empresa(rs.getLong("ID_EMPRESA"));
                aux.setNombre_Empresa(rs.getString("NOMBRE_EMPRESA"));
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
