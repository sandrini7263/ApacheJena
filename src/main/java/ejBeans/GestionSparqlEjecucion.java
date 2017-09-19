/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejBeans;

import java.util.HashMap;
import javax.ejb.Singleton;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;

/**
 *
 * @author Carlos Sánchez López
 */
@Singleton
public class GestionSparqlEjecucion {
    //Variables miembro de la clase

    // Primer argumento HashMap principal contiene el token
    // asociado a cada usuario. El segundo argumento del HashMap
    // es a su vez otro HashMap, en el que el primer argumento contiene
    // el nombre del razonador y el segundo el razonador Jena.
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, HashMap<String, QueryExecution>> consultasToken;
    private HashMap<String, QueryExecution> consultasPorNombre;

    // Constructor sin parametros
    public GestionSparqlEjecucion() {
        consultasToken = new HashMap<>();
    }

    // Métodos de la Clase
    //Añadir modelo al usuario identificado por su token
    public void addQuery(String token, String nombreQuery, QueryExecution consulta) {

        if (consultasToken.containsKey(token)) {
            consultasPorNombre = consultasToken.get(token);
        } else {
            consultasPorNombre = new HashMap();
        }
        consultasPorNombre.put(nombreQuery, consulta);
        consultasToken.put(token, consultasPorNombre);
    }

    // Recuperar modelo por nombre
    public QueryExecution getQuery(String token, String nombreConsulta) {
        QueryExecution consulta;
        if (consultasToken.containsKey(token)) {
            consultasPorNombre = consultasToken.get(token);
            consulta = consultasPorNombre.get(nombreConsulta);
        } else {
            consulta = null;
        }
        return consulta;
    } 
}
