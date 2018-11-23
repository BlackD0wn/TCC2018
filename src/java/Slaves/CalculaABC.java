/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slaves;

import DAO.MovimentacaoDAO;
import DAO.ProdutoDAO;
import bean.Movimentacao;
import bean.Produto;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author hook
 */
public class CalculaABC {
    
    private int diasParaCalcular = 90;
    
    private boolean dentroDoPrazoDoCalculo(Movimentacao m){
        Date d1 = m.getData(); 
        Date d2 = new Date(); 
        
        long dt = (d2.getTime() - d1.getTime());      
        long dias = (dt / 86400000L);
    
        if (dias <= diasParaCalcular) {
            return true;
        }
        return false;
    }
    
    private List<Movimentacao> buscaMovimentacoesPorProduto(Produto p){
        MovimentacaoDAO dao = new MovimentacaoDAO();
        List<Movimentacao> movimentacoes = dao.procurarPorProduto(p);
        List<Movimentacao> movimentacoesFiltradas = new ArrayList<Movimentacao>();
        CalculadorDiferencaDatas cdd = new CalculadorDiferencaDatas();
        for (Iterator<Movimentacao> iterator = movimentacoes.iterator(); iterator.hasNext();) {
            Movimentacao next = iterator.next();
              if (cdd.diferencaDeDatas(next.getData(), new Date()) < diasParaCalcular) {
                 movimentacoesFiltradas.add(next);
            }

        }
        return movimentacoesFiltradas;
    }
    
    private void atualizaProduto(Produto p){
        ProdutoDAO dao = new ProdutoDAO();
        dao.edit(p);
    }
    
    private List<Produto> buscarProdutos(){
        return new ProdutoDAO().procurarTodos();
    }
    
    private float CalculaValorTotalMovimentado(){
        List<Produto> produtos = buscarProdutos();
        List<Movimentacao> movimentacoes;
        float valorTotalMovimentado = 0;
        System.out.println("Produtos: "+ new Gson().toJson(produtos));
        
        for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
            Produto next = iterator.next();
            movimentacoes = buscaMovimentacoesPorProduto(next);
            for (Iterator<Movimentacao> iterator1 = movimentacoes.iterator(); iterator1.hasNext();) {       
                Movimentacao next1 = iterator1.next();
                if(next1.getTipo() == 1){
                   valorTotalMovimentado += next1.getPreco() * next1.getQuantidade();
                }
            }
        }
        return valorTotalMovimentado;
    }
    
    private float CalculaValorMovimentadoPorProduto(Produto p){

        float valorMovimetado = 0;
        
        MovimentacaoDAO dao = new MovimentacaoDAO();
        List<Movimentacao> movimentacoes = dao.procurarPorProduto(p);

        for (Iterator<Movimentacao> iterator = movimentacoes.iterator(); iterator.hasNext();) {
            Movimentacao next = iterator.next();
         
            if (next.getTipo() == 1) {
                valorMovimetado += next.getPreco() * next.getQuantidade();
            }
        }
        return valorMovimetado;
    }
    
    public void calcular(){
        
        float valorTotal = CalculaValorTotalMovimentado();
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.procurarTodos();
        float teste = 0;
        
        for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
            Produto next = iterator.next();
            float valorMovimentadoProduto = CalculaValorMovimentadoPorProduto(next);
            next.setValorMovimentado(valorMovimentadoProduto);
            float porcMovimentacao = valorMovimentadoProduto/valorTotal;
            
            next.setPorcMovimentacao(porcMovimentacao*100);
            atualizaProduto(next);
        }

        calculaABC();
        
    }
  
    private void calculaABC(){
        
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.procurarOrdenadoPorcentagem();
        System.out.println(new Gson().toJson(produtos));

        int a = 75;
        int b = a + 15;
        
        float total = 0;
        
        for (Iterator<Produto> iterator = produtos.iterator(); iterator.hasNext();) {
            Produto next = iterator.next();
            
            
            if (total < a){
                next.setCurvaABC("A");
            }else if(total < b){
                next.setCurvaABC("B");
            }else{
                next.setCurvaABC("C");
            }
            
            total+= next.getPorcMovimentacao();
            
            atualizaProduto(next);
            
        }
        System.out.println("Total Movimentado = "+total);
        
        
        
        
    }

}
