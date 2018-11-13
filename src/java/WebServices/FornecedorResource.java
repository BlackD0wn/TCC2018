    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Connection.ConnectionFactory;
import DAO.FornecedorDAO;
import DAO.ProdutoDAO;
import Slaves.CalculaEstoque;
import bean.Fornecedor;
import bean.Produto;
import com.google.gson.Gson;
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

@Path("fornecedor")
public class FornecedorResource{

    private EntityManager em = ConnectionFactory.getConnection();
    private FornecedorDAO dao = new FornecedorDAO();
    private Gson gson = new Gson();
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    public void create(Fornecedor entity) {
         dao.create(entity);
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Fornecedor entity) {
       
        if (entity.getPrazoDeEntrega() != dao.find(entity.getId()).getPrazoDeEntrega()) {
            CalculaEstoque calculo = new CalculaEstoque();
            List<Produto> produtos = new ProdutoDAO().findByFornecedor(entity);
            
            for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
                Produto next = iterator.next();
                calculo.calcular(next);
            }   
        }
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
        System.out.println();
        return gson.toJson(dao.findAll());
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return gson.toJson(dao.findRange(from,to));
    }
    
    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByName(@PathParam("name") String busca) {
        return gson.toJson(dao.findByName(busca));
    }
    
    @GET
    @Path("partName/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByPartName(@PathParam("name") String busca) {
        return gson.toJson(dao.findByPartName(busca));
    }
    
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(dao.count());
    }
    
   
    
    
}
