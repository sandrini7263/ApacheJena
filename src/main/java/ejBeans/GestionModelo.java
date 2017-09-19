/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejBeans;

import java.util.HashMap;
import javax.ejb.Singleton;
import org.apache.jena.rdf.model.Model;

/**
 *
 * @author Carlos Sánchez López
 */
@Singleton
public class GestionModelo {

    //Variables miembro de la clase
    // Primer argumento HashMap principal contiene el token
    // asociado a cada usuario. El segundo argumento del HashMap
    // es a su vez otro HashMap, en el que el primer argumento contiene
    // el nombre del modelo y el segundo el modelo Jena.
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, HashMap<String, Model>> modelosToken;
    private HashMap<String, Model> modelPorNombre;

    // Constructor sin parametros
    public GestionModelo() {
        modelosToken = new HashMap<>();
    }

    // Métodos de la Clase
    //Añadir modelo al usuario identificado por su token
    public void addDefaultModel(String token, String nombreModelo, Model modelo) {

        if (modelosToken.containsKey(token)) {
            modelPorNombre = modelosToken.get(token);
        } else {
            modelPorNombre = new HashMap();
        }
        modelPorNombre.put(nombreModelo, modelo);
        modelosToken.put(token, modelPorNombre);
    }

    // Recuperar modelo por nombre
    public Model getModel(String token, String nombreModelo) {
        Model modelo;
        if (modelosToken.containsKey(token)) {
            modelPorNombre = modelosToken.get(token);
            modelo = modelPorNombre.get(nombreModelo);
        } else {
            //Modelo no existe
            modelo = null;
        }
        return modelo;
    }

    //Modificar valor HashMap
    public void updateModel(String token, String nombreModelo, Model model) {

        modelPorNombre.put(nombreModelo, model);
        modelosToken.put(token, modelPorNombre);
    }
}
