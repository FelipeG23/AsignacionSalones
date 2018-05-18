/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.AsignacionManualDAO;
import DAO.MateriasDAO;
import Entities.AsignacionEntity;
import Entities.MateriasEntity;
import Utiles.DeserializaObjeto;
import java.util.ArrayList;
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

    @GET
    @Path("consultarAsignacionxMateria/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarAsignacion(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            JSONObject json = new JSONObject(datos);
            AsignacionManualDAO dao = new AsignacionManualDAO();
            ArrayList<AsignacionEntity> lista = dao.consultarAsignaxMateria(json.getString("fecha").replaceAll("-", "/"), "" + json.getLong("materia"));
            objJson = DeserializaObjeto.creaObjetoJson("Ok", lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("actualizarSalonHorario/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String actualizarSalonHorario(@PathParam("datos") String datos) {
        String objJson = "";
        try {
            AsignacionManualDAO dao = new AsignacionManualDAO();
            JSONObject json = new JSONObject(datos);
            AsignacionEntity aux = new AsignacionEntity();
            aux.setCodigoMateria(json.getString("codigoMateria"));
            aux.setFechaAsignada(json.getString("fechaAsignada"));
            aux.setHoraInicio(json.getString("horaInicio"));
            aux.setHoraFin(json.getString("horaFin"));
            aux.setSalonCodigo(json.getString("salonCodigo"));
            String rta = dao.actualizarSalonXMateria(aux);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
