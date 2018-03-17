package Servicios;

import DAO.UsuarioDAO;
import Entities.PersonaEntity;
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

@Path("Usuario")
@Stateless
@WebService
public class Usuario {

    /**
     * Web service operation
     *
     * @param datos
     * @return
     */
    @GET
    @Path("insertarUsuario/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String operation(@PathParam("datos") String datos) {
        JSONObject json = new JSONObject(datos);
        String objJson = "";
        try {
      
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
//    @GET
//    @Path("consultarTodos/")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String consultarTodos() {
//        String objJson = "";
//        try {
//            UsuarioDAO dao = new UsuarioDAO();
//            ArrayList<PersonaEntity> rta = dao.consultarTodasPersonas();
//            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return objJson;
//    }
//    @GET
//    @Path("consultarEspecifico/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String consultarEspecifico(@PathParam("id") String id) {
//        String objJson = "";
//        try {
//            UsuarioDAO dao = new UsuarioDAO();
//            PersonaEntity rta = dao.consultarEspecifico(id);
//            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return objJson;
//    }
//    @GET
//    @Path("actualizarPersona/{datos}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String actualizar(@PathParam("datos") String datos) {
//                JSONObject json = new JSONObject(datos);
//
//        String objJson = "";
//        try {
//            UsuarioDAO dao = new UsuarioDAO();
//            PersonaEntity persona = new PersonaEntity();
//            persona.setId(""+json.getInt("ID"));
//            persona.setNombre_P(json.getString("nombre"));
//            persona.setApellido(json.getString("apellidos"));
//            persona.setEdad(json.getInt("edad"));
//            String rta = dao.actualizar(persona);
//            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return objJson;
//    }
//    @GET
//    @Path("eliminarPersona/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String eliminar(@PathParam("id") String id) {
//             
//
//        String objJson = "";
//        try {
//            UsuarioDAO dao = new UsuarioDAO();
//            String rta = dao.eliminar(id);
//            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return objJson;
//    }
}
