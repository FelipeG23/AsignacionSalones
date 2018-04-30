/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.EdificioDAO;
import Entities.EdificioEntity;
import Utiles.DeserializaObjeto;
import java.util.List;
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
@Path("EdificiosService")
@Stateless
@WebService
public class EdificioService {

    @GET
    @Path("insertarEdificio/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertarEdificio(@PathParam("nombre") String nombreEdificio) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(nombreEdificio);
            EdificioDAO dao = new EdificioDAO();
            String rta = dao.insertarEdificio(json.getString("nombre"));
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("consultarEdificios/")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarEdificios() {
        String objJson = "";
        try {
            EdificioDAO dao = new EdificioDAO();
            List<EdificioEntity> rta = dao.consultarEdificios();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("actualizarEdificio/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String actualizarEdificio(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(datos);
            EdificioDAO dao = new EdificioDAO();
            EdificioEntity edificio = new EdificioEntity();
            edificio.setNombre(json.getString("nombre"));
            edificio.setCodigo(json.getLong("idEdificio"));
            String rta = dao.actualizarEdificio(edificio);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    @GET
    @Path("eliminarEdificio/{idEdificio}")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminarEdificio(@PathParam("idEdificio") String id) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(id);
            EdificioDAO dao = new EdificioDAO();
            String rta = dao.eliminarEdificio(json.getLong("codigo"));
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

}
