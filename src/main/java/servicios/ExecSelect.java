/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionSparqlEjecucion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import utilidades.GestionRutas;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/execSelect")
public class ExecSelect {

    @EJB
    GestionSparqlEjecucion gestionsparqlejecucion;

    @GET
    @Path("{token}/{nameQueryExecution}/{archive}")

    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)

    public Response execSelect(@PathParam("token") String token, @PathParam("nameQueryExecution") String nameQueryExecution, 
            @PathParam("archive") String archive) throws IOException {
        ResponseBuilder response;
        try {
            // Recuperar Ejecución Query
            QueryExecution qe = gestionsparqlejecucion.getQuery(token, nameQueryExecution);
            ResultSet rs = qe.execSelect();
            GestionRutas gr = new GestionRutas();
            String ruta = gr.getActualPath()+ token + "\\" + archive + ".txt";
            File file = new File(ruta);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            while (rs.hasNext()) {
                QuerySolution sol = rs.next();
                // Escribir en archivo, para posteriormente descargarlo en cliente 
                bw.write(sol.toString() + "\n");
            }
            bw.close();
            response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment;filename=" + archive);
            return response.build();
        } catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result:", "Query name does not exist");
            return response.build();
        }
    }
}
