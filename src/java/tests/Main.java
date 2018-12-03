/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import DAO.FornecedorDAO;
import DAO.MovimentacaoDAO;
import DAO.PermissoesDAO;
import DAO.ProdutoDAO;
import DAO.UsuarioDAO;
import Slaves.CalculaABC;
import Slaves.CalculaEstoque;
import Slaves.CalculadorDiferencaDatas;
import WebServices.MovimentacaoResource;
import bean.Fornecedor;
import bean.Movimentacao;
import bean.Permissoes;
import bean.Produto;
import bean.Usuario;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
     *
 * @author hook
 */
public class Main {
    public static void main(String[] args) throws ParseException {
//      
//        MovimentacaoDAO mdao = new MovimentacaoDAO();
//        CalculadorDiferencaDatas cdd = new CalculadorDiferencaDatas();
//        Movimentacao m1=  mdao.find(1);
//        
//        Movimentacao m2 = new Movimentacao();
//        
//        m2.setData(new Date());
//        m2.setProduto(new ProdutoDAO().find(1));
//        m2.setUsuario(new UsuarioDAO().find(1));
//        
//        mdao.create(m2);
//        System.out.println(cdd.diferencaDeDatas(new Date(), m2.getData()));
//        
//         UsuarioDAO udao = new UsuarioDAO();
//         Usuario u  = new Usuario();
//         u.setAtivo(true);
//         u.setNome("admin");
//         u.setSenha("admin");
//         PermissoesDAO pdao = new PermissoesDAO();
//         Permissoes p = new Permissoes();
//         
//         p.setConfiguracao(true);
//         p.setEmail(true);
//         p.setEstatistica(true);
//         p.setFornecedor(true);
//         p.setProduto(true);
//         p.setUsuario(true);
//         u.setPermissoes(p);
//         udao.create(u);
//         
//         
//    Fornecedor f1 = new Fornecedor();
//    f1.setPrazoDeEntrega(20);
//    f1.setBairro("sadas");
//    new FornecedorDAO().create(f1);
//
//    Produto p1 = new Produto();
//    p1.setFornecedor(f1);
//    
//    new ProdutoDAO().create(p1);
//    
//    Movimentacao m1 = new Movimentacao();
//    
//    m1.setProduto(p1);
//    m1.setUsuario(new UsuarioDAO().find(4));
//    
//    m1.setTipo(1);
//    m1.setQuantidade(1000);
//  
//    new MovimentacaoDAO().create(m1);
//    
//    Movimentacao m2 = new Movimentacao();
//    m2.setProduto(p1);
//    m2.setUsuario(new UsuarioDAO().find(4));
//    
//    m2.setTipo(0);
//    m2.setQuantidade(20);
//  
//    
//    new MovimentacaoDAO().create(m2);
//    
////    
//  
//    ProdutoDAO pdao = new ProdutoDAO();
//    
//    Produto p1 = pdao.find(2);
//    
//
//    MovimentacaoResource mreResource = new MovimentacaoResource();
//        
//        
//
//    Usuario u = new UsuarioDAO().find(1);
//    Produto p = new ProdutoDAO().find(7);
////    
//    Movimentacao m = new Movimentacao();
//    
//    m.setProduto(p);
//    m.setQuantidade(1);
//    m.setTipo(0);
//    m.setUsuario(u);
//
//    new MovimentacaoDAO().create(m);

//        CalculaABC c = new CalculaABC();
//        c.calcular();
        


//    mreResource.create(m);
//    ProdutoDAO pdao = new ProdutoDAO();
//    List<Produto> lista = pdao.procurarTodos();
//    
//        for (Iterator<Produto> iterator = lista.iterator(); iterator.hasNext();) {
//            Produto next = iterator.next();
//            next.setDataCadastro(new Date());
//            pdao.edit(next);
//                System.out.println(next.getDataCadastro().toString());
 
//        }

    CalculaABC ca = new CalculaABC();
    ca.calcular();
    
    ProdutoDAO pdao = new ProdutoDAO();
    List<Produto> produtos = pdao.procurarTodos();
    CalculaEstoque ce = new CalculaEstoque();
    
        for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
            Produto next = iterator.next();
            ce.calcular(next);
        }

        System.out.println(new MovimentacaoDAO().find(14).getProduto().getDataCadastro().toString());

    }



}
