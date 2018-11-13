/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import DAO.UsuarioDAO;
import bean.Usuario;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author hook
 */
@Path("usuario")
public class UsuarioResource {
    private Gson gson = new Gson();
    private UsuarioDAO dao = new UsuarioDAO();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
    }
    
    
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
          dao.create(entity);
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Usuario entity) {
        System.out.println(entity.toString());
        dao.edit(entity);
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String find(@PathParam("id") Integer id){
        Usuario u = dao.find(id);
        return gson.toJson(u);
    }
    
    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByName(@PathParam("name") String name){
     String retorno;
        try{ 
             retorno = gson.toJson(dao.findByName(name));
     
     }catch(Exception e){
         return null;
     }
     return retorno;
    }
    
    @GET
    @Path("namePart/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByPartName(@PathParam("name") String busca) {
        return gson.toJson(dao.findByPartName(busca));
    }
    
    
    
    //delete não esta funcionando aqui, verificar depois
    @GET
    @Path("remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String remove(@PathParam("id") Integer id) {
       Usuario busca = dao.find(id);
       dao.remove(busca);
       
       return gson.toJson(busca);
    }
    
    @GET
    @Path("findAll")
    @Produces({MediaType.APPLICATION_JSON})
    public String findAll() {
       List<Usuario> u = dao.findAll();
       return gson.toJson(u);
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
       List<Usuario> u = dao.findRange(from, to);
       return gson.toJson(u);
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String count() {
       List<Usuario> u = dao.findAll();
       return gson.toJson(u.size());
    }
}

