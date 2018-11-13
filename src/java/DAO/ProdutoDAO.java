/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public class ProdutoDAO extends AbstractDAO<Produto>{

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
        
        System.out.println("Criando: "+new Gson().toJson(entity.getDataCadastro()));
        return super.create(entity);
        
    }
    
    @Override
    public List<Produto> findAll() {
        
        List<Produto> list = null;
        list = getEntityManager().createQuery("From Produto order by nome").getResultList();
            
       
        return list;
    }
    
       public List<Produto> findByFornecedor(Fornecedor f) {
        List<Produto> list = null;
       list = getEntityManager().createQuery("from Produto where fornecedor_id = :busca").setParameter("busca", f.getId()).getResultList();         
             return list;
    }
    
    public List<Produto> findByPorc() {
        List<Produto> list = null;
        list = getEntityManager().createQuery("From Produto order by porcMovimentacao desc").getResultList();
        return list;
    }
    
}
