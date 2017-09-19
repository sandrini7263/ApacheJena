/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionModeloInferencia;
import ejBeans.GestionSparql;
import ejBeans.GestionSparqlEjecucion;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.InfModel;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/createQueryExecution")
public class CreateQueryExecution {

// Inyección de EJB's necesarios
    @EJB
    GestionSparql gestionsparql;
    @EJB
    GestionSparqlEjecucion gestionsparqlejecucion;
    @EJB
    GestionModeloInferencia gestionmodeloinferencia;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createQueryExecution(@FormParam("token") String token, @FormParam("nameQuery") String nameQuery, 
            @FormParam("nameModelInference") String nameModelInference,
            @Context HttpServletResponse servletResponse) {
        ResponseBuilder response;

        try {
            // Recuperar Consulta
            Query sparqlQuery = gestionsparql.getQuery(token, nameQuery);

            // Recuperar modelo de inferencia
            InfModel infModel2 = gestionmodeloinferencia.getModel(token, nameModelInference);
            QueryExecution qe = QueryExecutionFactory.create(sparqlQuery, infModel2);

            //Guardar ejecución Query 
            gestionsparqlejecucion.addQuery(token, nameQuery, qe);

            response = Response.ok();
            response.header("Result:", "Created Query Execution");
            return response.build();

        } catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result:", "There is no Inference Model or Query");
            return response.build();
        }
    }
}
