/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Felipe
 */
public class DeserializaObjeto {
    /**
     * Funcion con la cual creo un objeto Json apartir de un objeto
     *
     * @return
     */
    public static String creaObjetoJson(String respuesta, Object objeto) {
        String objJson = null;
        try {
            Gson gson = new Gson();
            Map<String, Object> mapaRta = new HashMap<String, Object>();
            if (objeto == null) {
                mapaRta.put("respuesta", "Objeto Nulo");
            } else {
                mapaRta.put("respuesta", respuesta);
                mapaRta.put("objeto", objeto);
            }
            objJson = gson.toJson(mapaRta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objJson;
    }
}
