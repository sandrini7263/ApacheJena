/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejBeans;

import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.Singleton;

/**
 *
 * @author Carlos Sánchez López
 */
@Singleton
public class GestionReglas {

    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, HashMap<String, ArrayList<String>>> reglasToken;
    private HashMap<String, ArrayList<String>> reglasPorNombre;

    // Constructor sin parametros
    public GestionReglas() {
        reglasToken = new HashMap<>();
    }

    // Métodos de la Clase
    //Añadir modelo al usuario identificado por su token
    public void addRules(String token, String nombreReglas, ArrayList<String> reglas) {

        if (reglasToken.containsKey(token)) {
            reglasPorNombre = reglasToken.get(token);
        } else {
            reglasPorNombre = new HashMap();
        }
        reglasPorNombre.put(nombreReglas, reglas);
        reglasToken.put(token, reglasPorNombre);
    }

    // Recuperar modelo por nombre
    public ArrayList getRules(String token, String nombreReglas) {
        ArrayList<String> listaReglas;
        if (reglasToken.containsKey(token)) {
            reglasPorNombre = reglasToken.get(token);
            listaReglas = reglasPorNombre.get(nombreReglas);
        } else {
            listaReglas = null;
        }
        return listaReglas;
    }
}
