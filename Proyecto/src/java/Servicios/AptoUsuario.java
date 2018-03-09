/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.AptoUsuaDAO;
import DAO.JoinsDAO;
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
@Path("AptoUsuario")
@Stateless
@WebService
public class AptoUsuario {

    @GET
    @Path("eliminaRegistro/")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminaRegistro() {
        String objJson = "";
        try {
            String rta = "";
            AptoUsuaDAO dao = new AptoUsuaDAO();
            dao.borrarTodo();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("actualizarInfo/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String operation(@PathParam("datos") String datos) {
        JSONObject json = new JSONObject(datos);
        String objJson = "";
        try {
            AptoUsuaDAO dao = new AptoUsuaDAO();
            String rta = dao.insertarProApto(json);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("Consultajoin/{join}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultaJoin(@PathParam("join") String datos) {
        String objJson = "";
        try {
            ArrayList<Object> rta = new ArrayList<>();
            JoinsDAO dao = new JoinsDAO();
            switch (datos) {
                case "LeftJoin":
                    rta = dao.leftJoin();
                    break;
                case "LeftJoin2":
                    rta = dao.leftJoin2();
                    break;
                case "InnerJoin":
                    rta = dao.innerJoin();
                    break;
                case "FullOuterJoin":
                    rta = dao.fullOuterJoin();
                    break;
                case "FullOuterJoin2":
                    rta = dao.fullOuterJoin2();
                    break;
                case "RigthJoin":
                    rta = dao.rightJoin();
                    break;
                case "RigthJoin2":
                    rta = dao.rightJoin2();
                    break;
              
            }

            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

}
