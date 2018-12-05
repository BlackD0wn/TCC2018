/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import Connection.ConnectionFactory;
import DAO.MovimentacaoDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import Util.EnviarEmail;
import bean.Movimentacao;
import com.google.gson.Gson;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import bean.Pedido;
import bean.Produto;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.mail.MessagingException;
import javax.ws.rs.POST;

/**
 *
 * @author hook
 */
@Path("email")
public class PedidoResource {

    private EntityManager em = ConnectionFactory.getConnection();
    private PedidoDAO dao = new PedidoDAO();
    private Gson gson = new Gson();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Pedido pedido) {

        ProdutoDAO pdao = new ProdutoDAO();
        pedido.getProduto().setDataCadastro(pdao.find(pedido.getProduto().getId()).getDataCadastro());
        pedido.setDataEnvio(new Date());
        dao.create(pedido);
        this.send(pedido);

    }

    @GET
    @Path("name/produto/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findByProduto(@PathParam("name") String name) {
        String retorno = null;
        try {
            ProdutoDAO pdao = new ProdutoDAO();
            List<Produto> p = pdao.findByPartName(name);

            List<Pedido> m = new ArrayList<>();
            if (!p.isEmpty()) {
                for (Iterator<Produto> iterator = p.iterator(); iterator.hasNext();) {
                    Produto next = iterator.next();
                    m.addAll(dao.procurarPorProduto(next));
                }
                retorno = gson.toJson(m);
            }
        } catch (Exception e) {
            return null;
        }
        return retorno;
    }

    @GET
    @Path("findEnviados")
    @Produces({MediaType.APPLICATION_JSON})
    public String findEnviados() {
        return gson.toJson(dao.procurarEnviados());
    }

    @GET
    @Path("findRecebidos")
    @Produces({MediaType.APPLICATION_JSON})
    public String findRecebidos() {
        return gson.toJson(dao.procurarRecebido());
    }

    @GET
    @Path("findNaoEnviados")
    @Produces({MediaType.APPLICATION_JSON})
    public String findNaoEnviados() {
        return gson.toJson(dao.procurarNaoEnviados());
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(Pedido entity) {
        dao.edit(entity);
    }

    @PUT
    @Path("send")
    @Consumes({MediaType.APPLICATION_JSON})
    public void send(Pedido entity) {

        try {

            ProdutoDAO pdao = new ProdutoDAO();
            entity.getProduto().setDataCadastro(pdao.find(entity.getProduto().getId()).getDataCadastro());
//            EnviarEmail ee;
//            ee = new EnviarEmail(entity.getConfig(), entity.getProduto().getFornecedor());
            //         ee.enviarEmail(entity);
            entity.setEnviado(true);
            entity.setDataEnvio(new Date());
            dao.edit(entity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @PUT
    @Path("receber")
    @Consumes({MediaType.APPLICATION_JSON})
    public void receberPedido(Pedido entity) {
        
        entity.setDataEnvio(dao.find(entity.getID()).getDataEnvio());
        entity.getP().setDataCadastro(dao.find(entity.getID()).getP().getDataCadastro());
        //entity.getP().adicionaSaldo((int) entity.getQuantidade());
        entity.setDataRecebido(new Date());
        entity.setRecebido(true);
        dao.edit(entity);
        
        Movimentacao m = new Movimentacao();
        MovimentacaoDAO mdao = new MovimentacaoDAO();
        m.setPreco(mdao.ultimoPrecoPorProduto(entity.getProduto()));
        m.setData(new Date());
        m.setProduto(entity.getP());
        m.setQuantidade(entity.getQuantidade());
        m.setTipo(1);        
        m.setUsuario(entity.getUsuario());
        mdao.create(m);

        
        
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
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public String findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return gson.toJson(dao.findRange(from, to));
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(dao.count());
    }

}
