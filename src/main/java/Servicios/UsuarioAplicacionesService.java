/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.AplicacionesDao;
import DAO.UsuarioAplicacionDao;
import Entities.AplicacionEntity;
import Entities.UsuarioAplicacionEntity;
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
@Path("Aplicaciones")
@Stateless
@WebService
public class UsuarioAplicacionesService {

    @GET
    @Path("consultarAplicaciones/")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarAplicaciones() {
        String objJson = "";
        try {
            AplicacionesDao dao = new AplicacionesDao();
            List<AplicacionEntity> lista = dao.consultarAplicaciones();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("consultarPermisoXUsuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarPermisoXUsuario(@PathParam("idUsuario") String datos) {
        String objJson = "";
        JSONObject json = new JSONObject(datos);
        try {
            UsuarioAplicacionDao dao = new UsuarioAplicacionDao();
            List<UsuarioAplicacionEntity> lista = dao.consultarPermisosXUsuario(""+json.getString("codigo"));
            objJson = DeserializaObjeto.creaObjetoJson("Ok", lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("actualizarPermisos/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String actualizarPermisos(@PathParam("datos") String listaPermisos) {
        String objJson = "";
        JSONObject jsonPermisos = new JSONObject(listaPermisos);
        try {
            UsuarioAplicacionDao dao = new UsuarioAplicacionDao();
            String rta = dao.actualizarPermisos(jsonPermisos.getString("idUsuario"), jsonPermisos.getString("listaPermisos"));
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
