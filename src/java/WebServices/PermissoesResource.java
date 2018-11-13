/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

/**
 *
 * @author hook
 */


import DAO.PermissoesDAO;
import bean.Permissoes;
import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("permissoes")
public class PermissoesResource{

    private PermissoesDAO dao = new PermissoesDAO();
    private Gson gson = new Gson();
    
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    public void create(Permissoes entity) {
       dao.create(entity);
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Permissoes entity) {
        dao.edit(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        dao.remove(dao.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String find(@PathParam("id") Integer id) {
        return gson.toJson(dao.find(id));
    }


//    @GET
//    @Path("buscarTodos")
//    @Produces({MediaType.APPLICATION_JSON})
//    public String findAll() {
//        return gson.toJson(dao.findAll());
//    }
//    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return gson.toJson(dao.findRange(from,to));
    }
    

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(dao.count());
    }
    
    
}
    