/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.ProgramasDAO;
import Entities.ProgramasEntity;
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
@Path("Programas")
@Stateless
@WebService
public class ProgramasService {

    @GET
    @Path("insertarPrograma/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertarPrograma(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(datos);
            ProgramasDAO dao = new ProgramasDAO();
            ProgramasEntity ent = new ProgramasEntity();
            ent.setNombre(json.getString("nombre"));
            String rta = dao.insertarSalon(ent);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    @GET
    @Path("actualizarPrograma/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String actualizarPrograma(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(datos);
            ProgramasDAO dao = new ProgramasDAO();
            ProgramasEntity ent = new ProgramasEntity();
            ent.setNombre(json.getString("nombre"));
            ent.setCodigo(json.getLong("codigo"));
            String rta = dao.actualizarPrograma(ent);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    @GET
    @Path("eliminarPrograma/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminarPrograma(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(datos);
            ProgramasDAO dao = new ProgramasDAO();
            String rta = dao.eliminarPrograma(json.getLong("codigo"));
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    @GET
    @Path("consultarProgramas/")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarProgramas() {
        String objJson = "";
        try {
            ProgramasDAO dao = new ProgramasDAO();
            List<ProgramasEntity> lista = dao.consultarTodosProgramas();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
