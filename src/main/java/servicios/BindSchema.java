/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionModelo;
import ejBeans.GestionRazonador;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.Reasoner;

/**
 *
 * @author Carlos Sánchez López
 *
 */
@Path("/v1/bindSchema")
public class BindSchema {

    @EJB
    GestionRazonador gestionrazonador;

    @EJB
    GestionModelo gestionmodelo;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)

    public Response bindSchema(@FormParam("token") String token, @FormParam("nameReasoner") String nameReasoner, @FormParam("nameModel") String nameModel) {

        ResponseBuilder response;
        //Recuperar modelo para enlace
        Model modeloEnlace = gestionmodelo.getModel(token, nameModel);
        //Recuperar razonador para enlace
        Reasoner reasonerEnlace = gestionrazonador.getReasoner(token, nameReasoner);

        try {
            // Crear Razonador y guardarlo
            Reasoner reasoner = reasonerEnlace.bindSchema(modeloEnlace);

            // Guarda nuevo razonador enlazado
            gestionrazonador.addReasoner(token, nameReasoner, reasoner);
            response = Response.ok();
            response.header("Result", "Create bind schema");
            return response.build();

        } catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result", "There is no model or reasoner");
            return response.build();
        }
    }
}
