/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Felipe
 */
public class ConexionDAO {

    public static void main(String[] args) {
        try {
            Connection dao = ConexionDAO.GetConnection();
            dao.close();
        } catch (Exception e) {
        }
    }

    public static Connection GetConnection() {
        Connection conexion = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
//          conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Clase", "root", "root"); //DRIVER MYSQL
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.190.43.117:1521:ORCL", "CLASE", "CLASE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }
}
