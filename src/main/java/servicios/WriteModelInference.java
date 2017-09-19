/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

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
import org.apache.jena.rdf.model.InfModel;
import utilidades.GestionRutas;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/writeModelInference")
public class WriteModelInference {

    @EJB
    GestionModeloInferencia gestionmodeloinferencia;

    @GET
    @Path("{token}/{nameModelInference}")

    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)

    public Response escribirModeloInferencia(@PathParam("token") String token, @PathParam("nameModelInference") String nameModelInference) throws IOException {
        ResponseBuilder response;
        // Recuperar modelo de inferencia
        try {
            InfModel infmodel = gestionmodeloinferencia.getModel(token, nameModelInference);
            // Escribir modelo en fichero con el mismo nombre que el modelo
            GestionRutas gr = new GestionRutas();
            OutputStream outs = new FileOutputStream(gr.getActualPath() + token + "\\" + nameModelInference);
            infmodel.write(outs, "TTL");
            File file = new File(gr.getActualPath() + token + "\\"+ nameModelInference);
            response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment;filename=" + nameModelInference);
            return response.build();
            
        } catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result:", "There is no inference model");
            return response.build();
        }
    }
}
