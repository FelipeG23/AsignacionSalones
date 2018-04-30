/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.EdificioDAO;
import DAO.SalonesDAO;
import Entities.SalonEntity;
import Utiles.DeserializaObjeto;
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
/**
 *
 * @author Felipe
 */
@Path("SalonesService")
@Stateless
@WebService
public class SalonesService {

    @GET
    @Path("insertarSalones/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertarSalones(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(datos);
            SalonesDAO dao = new SalonesDAO();
            SalonEntity ent = new SalonEntity();
            ent.setCodigo(json.getString("nombre"));
            ent.setNombre(json.getString("nombre"));
            ent.setCodigoEdificio(json.getLong("edificio"));
            ent.setCapacidad(json.getLong("capacidad"));
            String rta = dao.insertarSalon(ent);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

}
