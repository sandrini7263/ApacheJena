/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejBeans;

import java.util.HashMap;
import javax.ejb.Singleton;
import org.apache.jena.reasoner.Reasoner;

/**
 *
 * @author Carlos Sánchez López
 */
@Singleton
public class GestionRazonador {

    //Variables miembro de la clase

    // Primer argumento HashMap principal contiene el token
    // asociado a cada usuario. El segundo argumento del HashMap
    // es a su vez otro HashMap, en el que el primer argumento contiene
    // el nombre del razonador y el segundo el razonador Jena.
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, HashMap<String, Reasoner>> razonadoresToken;
    private HashMap<String, Reasoner> reasonerPorNombre;

    // Constructor sin parametros
    public GestionRazonador() {
        razonadoresToken = new HashMap<>();
    }

    // Métodos de la Clase
    //Añadir modelo al usuario identificado por su token
    public void addReasoner(String token, String nombreRazonador, Reasoner razonador) {

        if (razonadoresToken.containsKey(token)) {
            reasonerPorNombre = razonadoresToken.get(token);
        } else {
            reasonerPorNombre = new HashMap();
        }
        reasonerPorNombre.put(nombreRazonador, razonador);
        razonadoresToken.put(token, reasonerPorNombre);
    }

    // Recuperar modelo por nombre
    public Reasoner getReasoner(String token, String nombreRazonador) {
        Reasoner razonador;
        if (razonadoresToken.containsKey(token)) {
            reasonerPorNombre = razonadoresToken.get(token);
            razonador = reasonerPorNombre.get(nombreRazonador);
        } else {
            razonador = null;
        }
        return razonador;
    }
}
