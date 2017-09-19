/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionRazonador;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/getOwlReasoner")
public class GetOwlReasoner {

    @EJB
    GestionRazonador gestionrazonador;

    @POST
    public void getOwlReasoner(@FormParam("token") String token, @FormParam("nameReasoner") String nameReasoner, @Context HttpServletResponse servletResponse) {

        // Obtener Razonador OWL
        Reasoner razonador = ReasonerRegistry.getOWLReasoner();

        // Guardar Razonador OWL para uso posterior
        gestionrazonador.addReasoner(token, nameReasoner, razonador);
    }
}
