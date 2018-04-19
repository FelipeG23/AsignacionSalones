/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.AplicacionesDao;
import DAO.UsuarioDAO;
import Entities.AplicacionEntity;
import Entities.UsuarioEntity;
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
public class UsuarioAplicaciones {

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
}
