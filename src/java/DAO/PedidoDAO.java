/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import bean.Movimentacao;
import bean.Pedido;
import bean.Produto;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * asa
 *
 * @author hook
 */
public class PedidoDAO extends AbstractDAO<Pedido> {

    private EntityManager em;

    public PedidoDAO() {
        super(Pedido.class);
        em = Connection.ConnectionFactory.getConnection();
    }

    @Override
    public boolean create(Pedido pedido) {
//        ProdutoDAO pdao = new ProdutoDAO();
//        
//        pedido.getProduto().setDataCadastro(pdao.find(pedido.getProduto()).getDataCadastro());
//        pedido.setDataEnvio(new Date());
//        
        //enviar email

        return super.create(pedido);
    }

    public List<Pedido> procurarPorProduto(Produto p) {
        List<Pedido> list = null;
        System.out.println(p.getId());
        try {
            list = getEntityManager().createQuery("from Pedido where produto_id = :busca").setParameter("busca", p.getId()).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public boolean pedidosEmAberto(Produto p) {
        List<Pedido> list = new ArrayList<>();
        System.out.println(p.getId());
        try {
            list = getEntityManager().createQuery("from Pedido where produto_id = :busca and enviado = false and recebido = false").setParameter("busca", p.getId()).getResultList();
            if (list.isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return true;
    }

    public List<Pedido> procurarEnviados() {
        List<Pedido> list = null;
        try {
            list = getEntityManager().createQuery("from Pedido where enviado = true and recebido = false").getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Pedido> procurarNaoEnviados() {
        List<Pedido> list = null;
        try {
            list = getEntityManager().createQuery("from Pedido where enviado = false").getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Pedido> procurarNaoEnviadosPorProduto(Produto p) {
        List<Pedido> list = null;
        try {
            list = getEntityManager().createQuery("from Pedido where produto_id = :busca and enviado = false and recebido = false").setParameter("busca", p.getId()).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Pedido> procurarRecebido() {
        List<Pedido> list = null;
        try {
            list = getEntityManager().createQuery("from Pedido where recebido = true").getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
