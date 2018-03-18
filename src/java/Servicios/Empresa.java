/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import DAO.EmpresaDAO;
import Entities.EmpresaEntity;
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
@Path("empresas")
@Stateless
@WebService
public class Empresa {

    /**
     * *
     * Metodo para consultar todas las empresas parametrizadas
     *
     * @return
     */
    @GET
    @Path("consultarEmpresas")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarEmpresas() {
        String objJson = "";
        try {
            EmpresaDAO dao = new EmpresaDAO();
            List<EmpresaEntity> lista = dao.consultarEmpresas();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
