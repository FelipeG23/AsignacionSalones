/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.PersonaEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class UsuarioDAO {

    /**
     * Metodo para insertar un usuario
     *
     * @param json
     * @return
     */
    public String insertarUsuario(PersonaEntity usuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        String rta = "";
        try {
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("    INSERT INTO clase.usuario ");
            sql.append("    (NOMBRES,                 ");
            sql.append("    APELLIDOS,                ");
            sql.append("    DOCUMENTO,                ");
            sql.append("    EMAIL,                    ");
            sql.append("    SEXO,                     ");
            sql.append("    ID_EMPRESA)               ");
            sql.append("    VALUES(                   ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?,                        ");
            sql.append("    ?                         ");
            sql.append("    )                         ");
            ps = conn.prepareStatement(sql.toString());
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setLong(3, usuario.getDocumento());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getSexo());
            ps.setLong(6, usuario.getEmpresa());
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

    public ArrayList<PersonaEntity> consultarTodasPersonas() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        ArrayList<PersonaEntity> rta = null;
        try {
            EmpresaDAO daoEmp = new EmpresaDAO();
            conn = ConexionDAO.GetConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("select ID_USUARIO, NOMBRES, APELLIDOS, DOCUMENTO, EMAIL, SEXO, ID_EMPRESA from usuario order by id_usuario");
            st = conn.createStatement();
            rs = st.executeQuery(sql.toString());
            while (rs.next()) {
                if (null == rta) {
                    rta = new ArrayList<>();
                }
                PersonaEntity aux = new PersonaEntity();
                aux.setId_Persona(rs.getLong("ID_USUARIO"));
                aux.setNombre(rs.getString("NOMBRES"));
                aux.setApellido(rs.getString("APELLIDOS"));
                aux.setDocumento(rs.getLong("DOCUMENTO"));
                aux.setEmail(rs.getString("EMAIL"));
                aux.setSexo(rs.getString("SEXO"));
                aux.setEmpresa(rs.getLong("ID_EMPRESA"));
                aux.setEmpresaNombre(daoEmp.consultarEmpresaXID(aux.getEmpresa()));
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
//
//    public PersonaEntity consultarEspecifico(String id) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        PersonaEntity respuesta = null;
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT NOMBRE,APELLIDOS,EDAD FROM PROPIETARIO WHERE ID = ? ");
//            ps = conn.prepareStatement(sql.toString());
//            ps.setInt(1, Integer.parseInt(id));
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                respuesta = new PersonaEntity();
//                respuesta.setNombre_P(rs.getString("NOMBRE"));
//                respuesta.setApellido(rs.getString("APELLIDOS"));
//                respuesta.setEdad(rs.getInt("EDAD"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return respuesta;
//    }
//    
//    public String actualizar(PersonaEntity persona) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        String respuesta = "";
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//            sql.append("UPDATE PROPIETARIO  ");
//            sql.append("SET NOMBRE = ? ,APELLIDOS = ? ,EDAD = ? WHERE ID = ?");
//            ps = conn.prepareStatement(sql.toString());
//            ps.setString(1, persona.getNombre_P());
//            ps.setString(2, persona.getApellido());
//            ps.setInt(3, persona.getEdad());
//            ps.setInt(4, Integer.parseInt(persona.getId()));
//            ps.executeUpdate();
//            respuesta = "OK";
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return respuesta;
//    }
//    public String eliminar(String id) {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        String respuesta = "";
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//            sql.append("DELETE FROM PROPIETARIO  ");
//            sql.append("WHERE ID = ?");
//            ps = conn.prepareStatement(sql.toString());
//            ps.setInt(1, Integer.parseInt(id));
//            ps.executeUpdate();
//            respuesta = "OK";
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return respuesta;
//    }
}
