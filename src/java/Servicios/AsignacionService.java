/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.MateriasDAO;
import Entities.MateriasEntity;
import Utiles.DeserializaObjeto;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Felipe
 */
@Path("AsignacionService")
@Stateless
@WebService
public class AsignacionService {

    @GET
    @Path("consultarMaterias/")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarMaterias() {
        String objJson = "";
        try {
            MateriasDAO dao = new MateriasDAO();
            List<MateriasEntity> rta = dao.consultarTodasMaterias();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
