/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionSparql;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/createQuery")
public class CreateQuery {

    @EJB
    GestionSparql gestionsparql;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createQuery(@FormParam("token") String token, @FormParam("SPARQLsentence") String SPARQLsentence,
            @FormParam("nameQuery") String nameQuery, @Context HttpServletResponse servletResponse) {

        // Crear Consulta
        Query sparqlQuery = QueryFactory.create(SPARQLsentence);

        // Guardar Consulta sparql
        gestionsparql.addQuery(token, nameQuery, sparqlQuery);

    }
}
