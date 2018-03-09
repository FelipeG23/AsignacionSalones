/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.AptoDAO;
import Entities.AptoEntity;
import Utiles.DeserializaObjeto;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

/**
 *
 * @author Felipe
 */
@Path("Apto")
@Stateless
@WebService
public class Apto {
    @GET
    @Path("insertarApto/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String operation(@PathParam("datos") String datos) {
        JSONObject json = new JSONObject(datos);
        String objJson = "";
        try {
            AptoDAO dao = new AptoDAO();
            String rta = dao.insertarApto(json);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    @GET
    @Path("consultarTodos/")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarTodos() {
        String objJson = "";
        try {
            AptoDAO dao = new AptoDAO();
            ArrayList<AptoEntity> rta = dao.consultarTodosAptos();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    
    @GET
    @Path("consultarEspecifico/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarEspecifico(@PathParam("id") String id) {
        String objJson = "";
        try {
            AptoDAO dao = new AptoDAO();
            AptoEntity rta = dao.consultarEspecifico(id);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    @GET
    @Path("actualizarApto/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String actualizarEspecifico(@PathParam("datos") String datos) {
        String objJson = "";
        JSONObject json = new JSONObject(datos);
        try {
            AptoDAO dao = new AptoDAO();
            AptoEntity apto = new AptoEntity();
            apto.setId(json.getInt("ID"));
            apto.setNombre(json.getString("nombre"));
            apto.setnHabitaciones(json.getInt("nHabitaciones"));
            String rta = dao.actualizar(apto);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
     @GET
    @Path("eliminarApto/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminar(@PathParam("id") String id) {
             

        String objJson = "";
        try {
            AptoDAO dao = new AptoDAO();
            String rta = dao.eliminar(id);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    
}
