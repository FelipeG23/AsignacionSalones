package Servicios;

import DAO.UsuarioDAO;
import Entities.UsuarioEntity;
import Utiles.Correo;
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
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNombre(json.getString("nombre"));
            usuario.setApellido(json.getString("apellido"));
            usuario.setDocumento(json.getLong("documento"));
            usuario.setClave(json.getString("clave"));
            String rta = dao.insertarUsuario(usuario);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
    /**
     * Web service operation
     *
     * @param datos
     * @return
     */
    @GET
    @Path("actualizarUsuario/{datos}")
    @Produces(MediaType.APPLICATION_JSON)
    public String actualizarUsuario(@PathParam("datos") String datos) {
        JSONObject json = new JSONObject(datos);
        String objJson = "";
        try {
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNombre(json.getString("nombre"));
            usuario.setApellido(json.getString("apellido"));
            usuario.setDocumento(json.getLong("documento"));
            usuario.setClave(json.getString("clave"));
            usuario.setCodigo(json.getLong("codigo"));
            String rta = dao.actualizarUsuario(usuario);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    /**
     * *
     * Metodo para consultar todos los usuarios
     *
     * @return
     */
    @GET
    @Path("consultarTodos/")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarTodos() {
        String objJson = "";
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<UsuarioEntity> rta = dao.consultarTodosUsuarios();
            objJson = DeserializaObjeto.creaObjetoJson("Ok", rta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }

    @GET
    @Path("envioCorreos/{correos}/{mensaje}")
    @Produces(MediaType.APPLICATION_JSON)
    public String consultarEspecifico(@PathParam("correos") String correos,@PathParam("mensaje") String mensaje) {
        String objJson = "";
        try {
            Correo logica = new Correo();
            String[] lista = correos.split(",");
            logica.EnviarCorreo("siadocpruebas@gmail.com", "Pruebas123", mensaje, "Correo clase POO", lista);
            objJson = DeserializaObjeto.creaObjetoJson("Ok", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
