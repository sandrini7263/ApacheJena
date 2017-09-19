/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import ejBeans.GestionReglas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.apache.jena.reasoner.rulesys.Rule;
import utilidades.GestionRutas;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/parseRules")
public class ParseRules {

    @EJB
    GestionReglas gestionreglas;
        
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response parseRules(@FormDataParam("file") InputStream ips,
            @FormDataParam("file") FormDataContentDisposition file, @FormDataParam("token") String token, @FormDataParam("nameResource") String nameResource, @Context HttpServletResponse servletResponse) throws FileNotFoundException {

        //Obtención de la ruta donde se guardará el fichero subido al servidor
        GestionRutas gr = new GestionRutas();

        // Creación de directorio donde se guardarán los archivos subidos
        File directorio = new File(gr.getActualPath() + token);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        String ruta = directorio + "\\";

        try {
            int read;
            byte[] bytes = new byte[1024];

            OutputStream os = new FileOutputStream(new File(ruta + file.getFileName()));
            while ((read = ips.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            os.close();
        } catch (IOException e) {
            PrintStream s = null;
           e.printStackTrace(s);
        }

        BufferedReader knowledgeBase_rules = new BufferedReader(new FileReader(ruta + "knowledgeBase_rules.txt"));
        List rules = Rule.parseRules(Rule.rulesParserFromReader(knowledgeBase_rules));
        gestionreglas.addRules(token, nameResource, (ArrayList<String>) rules);
        return Response.ok("File uploaded correctly").build();
    }
}