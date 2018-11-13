/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Slaves.CalculaABC;
import Slaves.CalculaEstoque;
import bean.Movimentacao;
import bean.Produto;
import bean.Usuario;
import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author hook
 */
public class MovimentacaoDAO extends AbstractDAO<Movimentacao>{

    public MovimentacaoDAO() {
        super(Movimentacao.class);
    }
    @Override
    public boolean create(Movimentacao entity) {
        boolean retorno;
        
        Produto p1 = new ProdutoDAO().find(entity.getProduto().getId());
        entity.setProduto(p1);
        
        
        Date d = new Date();
        entity.setData(d);
        
        System.out.println("Fim data");
        Produto p = entity.getProduto();
        
        if (entity.getTipo() == 1) {
          p.adicionaSaldo(entity.getQuantidade());

        }else if(entity.getTipo() == 0){
          p.removeSaldo(entity.getQuantidade());
        }
        
        new ProdutoDAO().edit(p);
        new UsuarioDAO().edit(entity.getUsuario());
       
        retorno =  super.create(entity);
        
        if (entity.getTipo() == 1) {
            System.out.println("Calculando ABC");
            CalculaABC calculo = new CalculaABC();
            calculo.calcular();
        }else if (entity.getTipo() == 0) {
            System.out.println("Calculando estoque");
            CalculaEstoque calculo = new CalculaEstoque();
            calculo.calcular(p);
        }
        
        return retorno;
    }
    
    public List<Movimentacao> findByProduct(Produto p){
        List<Movimentacao> list = null;
        System.out.println(p.getId());
        try{
            list = getEntityManager().createQuery("from Movimentacao where produto_id = :busca").setParameter("busca", p.getId()).getResultList();         
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }
    
    public List<Movimentacao> findByUser(Usuario u){
        List<Movimentacao> list = null;
        try{System.out.println("id: "+u.getID());
            list = getEntityManager().createQuery("from Movimentacao m where usuario = :busca").setParameter("busca", u).getResultList();         
            System.out.println(new Gson().toJson(list));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }
   
    public List<Movimentacao> findByType(int type){
        List<Movimentacao> list = null;
        try{
            list = getEntityManager().createQuery("from Movimentacao where tipo = :busca").setParameter("busca", type).getResultList();         
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return list;
    }
   
    @Override
    public List<Movimentacao> findAll() {
        List<Movimentacao> list = null;
        list = getEntityManager().createQuery("From Movimentacao order by data").getResultList();
        return list;
    }
   
    @Override
    protected EntityManager getEntityManager() {
        return Connection.ConnectionFactory.getConnection();
    
    }
    
}
