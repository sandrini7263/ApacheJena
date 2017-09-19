/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import ejBeans.GestionModelo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.jena.rdf.model.Model;
import utilidades.GestionRutas;

/**
 *
 * @author Carlos Sánchez López
 */
@Path("/v1/readModel")

public class ReadModel {

    @EJB
    GestionModelo gestionmodelo;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)

    public Response readModel(@FormDataParam("file") InputStream ips,
            @FormDataParam("file") FormDataContentDisposition file, @FormDataParam("token") String token, @FormDataParam("nameResource") String nameResource, @Context HttpServletResponse servletResponse) throws IOException {
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
          //  throw new WebApplicationException("Error uploading file");
        }

        // Recuperar el modelo creado cuyo nombre viene indicado en el parametro file
        // Se ha debido crear anteriormen como modelo vacio
        Model model = gestionmodelo.getModel(token, nameResource);
        // Una vez subido el fichero leer con JENA
        Model modelActualizado = model.read(ruta + file.getFileName());
        //Actualizar el modelo recuperado al que se ha añadido el fichero subido
        gestionmodelo.updateModel(token, nameResource, modelActualizado);

        return Response.ok("File uploaded correctly").build();
    }

}
