/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejBeans;

import java.util.HashMap;
import javax.ejb.Singleton;
import org.apache.jena.query.Query;

/**
 *
 * @author Carlos Sánchez López
 */
@Singleton
public class GestionSparql {
    //Variables miembro de la clase

    // Primer argumento HashMap principal contiene el token
    // asociado a cada usuario. El segundo argumento del HashMap
    // es a su vez otro HashMap, en el que el primer argumento contiene
    // el nombre del razonador y el segundo el razonador Jena.
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, HashMap<String, Query>> consultasToken;
    private HashMap<String, Query> consultasPorNombre;

    // Constructor sin parametros
    public GestionSparql() {
        consultasToken = new HashMap<>();
    }

    // Métodos de la Clase
    //Añadir modelo al usuario identificado por su token
    public void addQuery(String token, String nombreQuery, Query consulta) {

        if (consultasToken.containsKey(token)) {
            consultasPorNombre = consultasToken.get(token);
        } else {
            consultasPorNombre = new HashMap();
        }
        consultasPorNombre.put(nombreQuery, consulta);
        consultasToken.put(token, consultasPorNombre);
    }

    // Recuperar modelo por nombre
    public Query getQuery(String token, String nombreConsulta) {
        Query consulta;
        if (consultasToken.containsKey(token)) {
            consultasPorNombre = consultasToken.get(token);
            consulta = consultasPorNombre.get(nombreConsulta);
        } else {
            consulta = null;
        }
        return consulta;
    } 
}
