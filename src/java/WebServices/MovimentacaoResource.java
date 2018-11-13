/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Connection.ConnectionFactory;
import DAO.MovimentacaoDAO;
import DAO.ProdutoDAO;
import DAO.UsuarioDAO;
import bean.Movimentacao;
import bean.Produto;
import bean.Usuario;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("movimentacao")
public class MovimentacaoResource{

    private EntityManager em = ConnectionFactory.getConnection();
    private MovimentacaoDAO dao = new MovimentacaoDAO();
    private Gson gson = new Gson();
    
        
    @GET
    @Path("name/produto/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByFornecedor(@PathParam("name") String name){
     String retorno = null;
        try{ 
             ProdutoDAO pdao = new ProdutoDAO();
             List<Produto> p = pdao.findByPartName(name);
             List<Movimentacao> m = new ArrayList<>(); 
             if (!p.isEmpty()) {
                 for (Iterator<Produto> iterator = p.iterator(); iterator.hasNext();) {
                     Produto next = iterator.next();
                     m.addAll(dao.findByProduct(next));
                 }
                retorno = gson.toJson(m);
            }
     }catch(Exception e){
         return null;
     }
     return retorno;
    }
    
    @GET
    @Path("name/usuario/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByUsuario(@PathParam("name") String name){
     String retorno = null;
        try{ 
             UsuarioDAO udao = new UsuarioDAO();
             List<Usuario> p = udao.findByPartName(name);
             List<Movimentacao> m = new ArrayList<>();
             if (!p.isEmpty()) {
                 for (Iterator<Usuario> iterator = p.iterator(); iterator.hasNext();) {
                     Usuario next = iterator.next();
                     m.addAll(dao.findByUser(next));
                 }
                 
                retorno = gson.toJson(m);
            }
     }catch(Exception e){
         return null;
     }
     return retorno;
    }
    
    
    
    @GET
    @Path("name/type/{type}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByTipo(@PathParam("type") int tipo){
     String retorno;
        try{ 
             retorno = gson.toJson(dao.findByName("a"));
     
     }catch(Exception e){
         return null;
     }
     return retorno;
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    public void create(Movimentacao entity) {
      System.out.println(entity.toString());
      dao.create(entity);
    }
    
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Movimentacao entity) {
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


    @GET
    @Path("buscarTodos")
    @Produces({MediaType.APPLICATION_JSON})
    public String findAll() {
        return gson.toJson(dao.findAll());
    }
    
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
    

