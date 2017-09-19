/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ejBeans.GestionRazonador;
import ejBeans.GestionReglas;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/genericRulesReasoner")
public class GenericRulesReasoner {

    @EJB
    GestionRazonador gestionrazonador;

    @EJB
    GestionReglas gestionreglas;

    @POST
    public Response genericRulesReasoner(@FormParam("token") String token, @FormParam("nameReasoner") String nameReasoner,
            @FormParam("nameRules") String nameRules) {
        ResponseBuilder response;
        try{
        // Buscar reglas por Nombre
        List rules = gestionreglas.getRules(token, nameRules);
        // Crear razonador a partir de reglas 
        Reasoner reasoner = new GenericRuleReasoner(rules);

        // Guardar Razonador creado
        gestionrazonador.addReasoner(token, nameReasoner, reasoner);
        response = Response.ok();
        response.header("Content-Disposition", "attachment;filename=" );
        return response.build();
        } catch (NullPointerException e) {
            response = Response.ok();
            response.header("Result:", "There is no rules");
            return response.build();
        }
    }
}
