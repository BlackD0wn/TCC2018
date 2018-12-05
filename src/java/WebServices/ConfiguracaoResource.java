/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import DAO.ConfiguracaoDAO;
import bean.Configuracao;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hook
 */
@Path("configuracao")
public class ConfiguracaoResource {

    private ConfiguracaoDAO dao = new ConfiguracaoDAO();
    private Gson gson = new Gson();

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String find(@PathParam("id") Integer id) {
        return gson.toJson(dao.find(id));
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Configuracao entity) {
        dao.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Configuracao entity) {
        dao.edit(entity);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(dao.count());
    }

}
