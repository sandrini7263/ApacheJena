/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionModelo;
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
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/createDefaultModel")

public class CreateDefaultModel {

    @EJB
    GestionModelo gestionmodelo;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createDefaultModel(@FormParam("token") String token, @FormParam("nameModel") String nameModel, @Context HttpServletResponse servletResponse) {

        // Crear Modelo por Defecto o vacio
        Model modeloDefecto = ModelFactory.createDefaultModel();
        // Guardar Modelo Creado
        gestionmodelo.addDefaultModel(token, nameModel, modeloDefecto);
        ResponseBuilder response = Response.ok();
        response.header("Result:", "Create default model");
        return response.build();
        
    }

}
