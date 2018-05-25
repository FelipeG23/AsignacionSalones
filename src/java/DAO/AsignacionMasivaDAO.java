/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 *
 * @author Felipe
 */
public class AsignacionMasivaDAO {

    public String asignarMasivo(String codigoPrograma) {
        String rta = "";
        Connection conn = null;

        try {
            conn = ConexionDAO.GetConnection();
            CallableStatement cStmt = conn.prepareCall("{call PR_ASIGNACION_AUTOMATICA(?,?)}");
            cStmt.setString(1, codigoPrograma);
            cStmt.registerOutParameter(2, Types.VARCHAR);
            cStmt.execute();
            rta = ""+cStmt.getString(2);
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
        return rta;
    }
}
