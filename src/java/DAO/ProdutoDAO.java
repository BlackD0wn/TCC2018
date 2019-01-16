/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Util.CalculaEstoque;
import bean.Fornecedor;
import bean.Produto;
import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class ProdutoDAO extends AbstractDAO<Produto> {

    public ProdutoDAO() {
        super(Produto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return Connection.ConnectionFactory.getConnection();
    }

    @Override
    public boolean create(Produto entity) {

        Date d = new Date();
        entity.setDataCadastro(d);

        return super.create(entity);

    }

    @Override
    public boolean edit(Produto entity) {

        System.out.println("Antes: "+new Gson().toJson(entity));
        entity.setDataCadastro(this.find(entity.getId()).getDataCadastro());

        boolean retorno;
        retorno = super.edit(entity);
        System.out.println("passou edit");
        entity = this.find(entity.getId());
        try {
            CalculaEstoque ce = new CalculaEstoque();
//        ce.calcular(entity);
        } catch (Exception e) {
            //todo
            e.printStackTrace();

        }
        return retorno;

    }

    @Override
    public List<Produto> procurarTodos() {

        List<Produto> list = null;
        list = getEntityManager().createQuery("From Produto order by nome").getResultList();

        return list;
    }

    public List<Produto> procurarTodosOrdenadoCurvaABC() {

        List<Produto> list = null;
        list = getEntityManager().createQuery("From Produto order by curvaABC").getResultList();

        return list;
    }

    public List<Produto> procurarPorFornecedor(Fornecedor f) {
        List<Produto> list = null;
        list = getEntityManager().createQuery("from Produto where fornecedor_id = :busca").setParameter("busca", f.getId()).getResultList();
        return list;
    }

    public List<Produto> procurarOrdenadoPorcentagem() {
        List<Produto> list = null;
        list = getEntityManager().createQuery("From Produto order by porcMovimentacao desc").getResultList();
        return list;
    }

}
