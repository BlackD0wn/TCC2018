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
import Connection.ConnectionFactory;
import DAO.ProdutoDAO;
import bean.Produto;
import com.google.gson.Gson;
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

@Path("produto")
public class ProdutoResource {

    private EntityManager em = ConnectionFactory.getConnection();
    private ProdutoDAO dao = new ProdutoDAO();
    private Gson gson = new Gson();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Produto entity) {
        System.out.println(new Gson().toJson(entity));
        entity.setDiasEstoqueDisponivel(-1);
        dao.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Produto entity) {
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
        return gson.toJson(dao.procurarTodos());
    }

    @GET
    @Path("buscarTodosOrdenadoCurvaABC")
    @Produces({MediaType.APPLICATION_JSON})
    public String findAllABC() {
        return gson.toJson(dao.procurarTodosOrdenadoCurvaABC());
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return gson.toJson(dao.findRange(from, to));
    }

    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByName(@PathParam("name") String busca) {
        return gson.toJson(dao.findByName(busca));
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(dao.count());
    }

    @GET
    @Path("partName/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByPartName(@PathParam("name") String busca) {
        return gson.toJson(dao.findByPartName(busca));
    }

}
