/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionModelo;
import ejBeans.GestionModeloInferencia;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.jena.rdf.model.Model;
import utilidades.GestionRutas;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/writeModel")
public class WriteModel {

    @EJB
    GestionModelo gestionmodelo;
    @EJB
    GestionModeloInferencia gestionmodeloinferencia;
    
    @GET
    @Path("{token}/{nameModel}")

    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)

    public Response writeModel(@PathParam("token") String token, @PathParam("nameModel") String nameModel) throws IOException {
        ResponseBuilder response;
        try{
        // Recuperar modelo RDF
        Model model = gestionmodelo.getModel(token, nameModel);
        GestionRutas gr = new GestionRutas();
        // Escribir modelo en fichero
        
        OutputStream outs = new FileOutputStream(gr.getActualPath()+ token + "\\"  + nameModel);//
        model.write(outs, "TTL");
        File file = new File(gr.getActualPath() +  token + "\\" + nameModel);
        response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=" + nameModel);
        return response.build();
         } catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result:", "There is no model");
            return response.build();
        }
        
    }
}
