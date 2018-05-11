/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.MateriasEntity;
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
public class MateriasDAO {

    public List<MateriasEntity> consultarTodasMaterias() {
        List<MateriasEntity> lista = null;
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder();
            conn = ConexionDAO.GetConnection();
            sql.append("SELECT CODIGO, NOMBRE FROM CLASE.MATERIAS");
            rs = conn.createStatement().executeQuery(sql.toString());
            while(rs.next()){
                if(lista == null){
                    lista = new ArrayList<>();
                }
                MateriasEntity aux = new MateriasEntity();
                aux.setCodigo(rs.getLong("CODIGO"));
                aux.setNombreMateria(rs.getString("NOMBRE"));
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
