/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Conexion.ConexionDAO;
import Entities.AptoEntity;
import Entities.PersonaEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Felipe
 */
public class JoinsDAO {

//    public ArrayList<Object> leftJoin() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT  apto.nombre as nombreApto,apto.n_habitaciones as habitaciones ,  propietario.nombre as nombreP ,propietario.apellidos as apellidoP ,propietario.edad as edad  FROM apto ");
//            sql.append("LEFT JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("LEFT JOIN propietario ON propietario.id = apto_propiet.id_pro");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                AptoEntity apto = new AptoEntity();
//                PersonaEntity persona = new PersonaEntity();
//                apto.setNombre(rs.getString("nombreApto"));
//                apto.setnHabitaciones(rs.getInt("habitaciones"));
//                persona.setNombre_P(rs.getString("nombreP"));
//                persona.setApellido(rs.getString("apellidoP"));
//                persona.setEdad(rs.getInt("edad"));
//                aux.add(apto);
//                aux.add(persona);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
//    public ArrayList<Object> leftJoin2() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT  apto.nombre as nombreApto,apto.n_habitaciones as habitaciones  FROM apto ");
//            sql.append("LEFT JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("LEFT JOIN propietario ON propietario.id = apto_propiet.id_pro ");
//            sql.append("WHERE apto_propiet.id_apto IS NULL");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                AptoEntity apto = new AptoEntity();
//                apto.setNombre(rs.getString("nombreApto"));
//                apto.setnHabitaciones(rs.getInt("habitaciones"));
//                aux.add(apto);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
//    
//    public ArrayList<Object> fullOuterJoin() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT apto.nombre as nombreApto,apto.n_habitaciones habitaciones ,  propietario.nombre as nombreP,propietario.apellidos as apellidoP ,propietario.edad FROM apto ");
//            sql.append("FULL OUTER JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("FULL OUTER JOIN propietario ON propietario.id = apto_propiet.id_pro");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                AptoEntity apto = new AptoEntity();
//                PersonaEntity persona = new PersonaEntity();
//                apto.setNombre(rs.getString("nombreApto"));
//                apto.setnHabitaciones(rs.getInt("habitaciones"));
//                persona.setNombre_P(rs.getString("nombreP"));
//                persona.setApellido(rs.getString("apellidoP"));
//                persona.setEdad(rs.getInt("edad"));
//                aux.add(apto);
//                aux.add(persona);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
//    public ArrayList<Object> fullOuterJoin2() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT apto.nombre as nombreApto,apto.n_habitaciones habitaciones ,  propietario.nombre as nombreP,propietario.apellidos as apellidoP ,propietario.edad FROM apto  ");
//            sql.append("FULL OUTER JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("FULL OUTER JOIN propietario ON propietario.id = apto_propiet.id_pro ");
//            sql.append("WHERE apto_propiet.id_apto IS NULL OR apto_propiet.id_apto IS NULL");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                AptoEntity apto = new AptoEntity();
//                PersonaEntity persona = new PersonaEntity();
//                apto.setNombre(rs.getString("nombreApto"));
//                apto.setnHabitaciones(rs.getInt("habitaciones"));
//                persona.setNombre_P(rs.getString("nombreP"));
//                persona.setApellido(rs.getString("apellidoP"));
//                persona.setEdad(rs.getInt("edad"));
//                aux.add(apto);
//                aux.add(persona);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
//    public ArrayList<Object> innerJoin() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT apto.nombre as nombreApto,apto.n_habitaciones habitaciones ,  propietario.nombre as nombreP,propietario.apellidos as apellidoP ,propietario.edad FROM apto ");
//            sql.append("INNER JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("INNER JOIN propietario ON propietario.id = apto_propiet.id_pro");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                AptoEntity apto = new AptoEntity();
//                PersonaEntity persona = new PersonaEntity();
//                apto.setNombre(rs.getString("nombreApto"));
//                apto.setnHabitaciones(rs.getInt("habitaciones"));
//                persona.setNombre_P(rs.getString("nombreP"));
//                persona.setApellido(rs.getString("apellidoP"));
//                persona.setEdad(rs.getInt("edad"));
//                aux.add(apto);
//                aux.add(persona);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
//    public ArrayList<Object> rightJoin() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT apto.nombre as nombreApto,apto.n_habitaciones habitaciones ,  propietario.nombre as nombreP,propietario.apellidos as apellidoP ,propietario.edad FROM apto ");
//            sql.append("RIGHT JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("RIGHT JOIN propietario ON propietario.id = apto_propiet.id_pro");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                AptoEntity apto = new AptoEntity();
//                PersonaEntity persona = new PersonaEntity();
//                apto.setNombre(rs.getString("nombreApto"));
//                apto.setnHabitaciones(rs.getInt("habitaciones"));
//                persona.setNombre_P(rs.getString("nombreP"));
//                persona.setApellido(rs.getString("apellidoP"));
//                persona.setEdad(rs.getInt("edad"));
//                aux.add(apto);
//                aux.add(persona);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
//    public ArrayList<Object> rightJoin2() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        ArrayList<Object> rta = new ArrayList<>();
//        try {
//            conn = ConexionDAO.GetConnection();
//            StringBuilder sql = new StringBuilder();
//
//            sql.append("SELECT propietario.nombre AS nombreP ,propietario.apellidos AS apellidoP,propietario.edad AS edad FROM apto ");
//            sql.append("RIGHT JOIN apto_propiet ON apto.id = apto_propiet.id_apto ");
//            sql.append("RIGHT JOIN propietario ON propietario.id = apto_propiet.id_pro ");
//            sql.append("WHERE apto_propiet.id_pro IS NULL");
//            ps = conn.prepareStatement(sql.toString());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                ArrayList<Object> aux = new ArrayList<>();
//                PersonaEntity persona = new PersonaEntity();
//                persona.setNombre_P(rs.getString("nombreP"));
//                persona.setApellido(rs.getString("apellidoP"));
//                persona.setEdad(rs.getInt("edad"));
//                aux.add(persona);
//                rta.add(aux);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (null != conn) {
//                    conn.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return rta;
//
//    }
    

}
