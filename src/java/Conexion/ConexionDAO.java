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

    public static Connection GetConnection() {
        Connection conexion = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
//          conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Clase", "root", "root"); //DRIVER MYSQL
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "CLASE", "CLASE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }
}
