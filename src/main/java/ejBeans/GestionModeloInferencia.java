/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejBeans;

import java.util.HashMap;
import javax.ejb.Singleton;
import org.apache.jena.rdf.model.InfModel;

/**
 *
 * @author Carlos Sánchez López
 */
@Singleton
public class GestionModeloInferencia {
   //Variables miembro de la clase
    
    // Primer argumento HashMap principal contiene el token
    // asociado a cada usuario. El segundo argumento del HashMap
    // es a su vez otro HashMap, en el que el primer argumento contiene
    // el nombre del modelo y el segundo el modelo Jena.
    
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, HashMap<String, InfModel>> modelosInferenciaToken;
    private HashMap<String, InfModel> modelInferenciaPorNombre;
    
    // Constructor sin parametros
    public GestionModeloInferencia() {
        modelosInferenciaToken = new HashMap<>();
    }

    // Métodos de la Clase
    
    //Añadir modelo de inferencia al usuario identificado por su token
    public void addDefaultModel(String token, String nombreModelo, InfModel modelo) {

        if (modelosInferenciaToken.containsKey(token)) {
            modelInferenciaPorNombre = modelosInferenciaToken.get(token);
        } else {
            modelInferenciaPorNombre = new HashMap();
        }
        modelInferenciaPorNombre.put(nombreModelo, modelo);
        modelosInferenciaToken.put(token, modelInferenciaPorNombre);
    }

    // Recuperar modelo por nombre
    public InfModel getModel(String token, String nombreModelo) {
        InfModel infModelo;
        if (modelosInferenciaToken.containsKey(token)) {
            modelInferenciaPorNombre = modelosInferenciaToken.get(token);
            infModelo = modelInferenciaPorNombre.get(nombreModelo);
        } else {
            infModelo = null;
        }
        return infModelo;
    } 
}
