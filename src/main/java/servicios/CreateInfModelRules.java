/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionModelo;
import ejBeans.GestionModeloInferencia;
import ejBeans.GestionRazonador;
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
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/createInfModelRules")
public class CreateInfModelRules {

    // Inyección de EJB's necesarios
    @EJB
    GestionModeloInferencia gestionmodeloinferencia;
    @EJB
    GestionRazonador gestionrazonador;
    @EJB
    GestionModelo gestionmodelo;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createInfModelRules(@FormParam("token") String token, @FormParam("nameReasoner") String nameReasoner, @FormParam("nameModelInference") String nameModelInference,
            @FormParam("nameModelInferenceRules") String nameModelInferenceRules,
            @Context HttpServletResponse servletResponse) {
        ResponseBuilder response;
        
        try{
        InfModel modeloInferencia = gestionmodeloinferencia.getModel(token, nameModelInference);
        Reasoner razonador = gestionrazonador.getReasoner(token, nameReasoner);
        
        // Crear Modelo de inferencia a partir de un razonador y un modelo 
        InfModel infModel2 = ModelFactory.createInfModel(razonador, modeloInferencia);

        // Guardar Modelo de Inferencia
        gestionmodeloinferencia.addDefaultModel(token, nameModelInferenceRules, infModel2);
        
        response = Response.ok();
        response.header("Result:", "Inference model created");
        return response.build();
        
        }catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result:", "There is no inference model or reasoner");
            return response.build();
        }
    }
}
