/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slaves;

import DAO.ConfiguracaoDAO;
import DAO.PedidoDAO;
import DAO.MovimentacaoDAO;
import DAO.ProdutoDAO;
import bean.Pedido;
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
public class CalculaEstoque {
 
    private int diasParaCalcular= 90;
    private float margemSegurancaEntrega = (float) 1.5;
    
    private List<Movimentacao> buscaMovimentacoesPorProduto(Produto p){
        MovimentacaoDAO dao = new MovimentacaoDAO();
        List<Movimentacao> movimentacoes = dao.findByProduct(p);
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
    
    private float CalculaGastoEmDias(Produto p){
       float media = 0;
       int divisor = 90;
       CalculadorDiferencaDatas cdd = new CalculadorDiferencaDatas();
       
       List<Movimentacao> movimentacoes = buscaMovimentacoesPorProduto(p);

//        System.out.println(new Gson().toJson(p));
       int diferencaDias = cdd.diferencaDeDatas(new Date(),p.getDataCadastro());
        System.out.println("Tempo de datas = "+diferencaDias);
        if (diferencaDias < diasParaCalcular) {
            divisor = diferencaDias;
        }else{
            divisor = diasParaCalcular;
        }
       
//        System.out.println(new Gson().toJson(movimentacoes));
//        System.out.println("Media 0 :"+ media);
        for (Iterator<Movimentacao> iterator = movimentacoes.iterator(); iterator.hasNext();) {
            Movimentacao next = iterator.next();
            if (next.getTipo() == 0) {
//                System.out.println("quantidade: "+ next.getQuantidade());
                media+= next.getQuantidade();
               
//                System.out.println("media for: "+media);
            }
        }
        
        
        return media/divisor;
    }
    
    public void calcular(Produto p){
        
        atualizaProduto(p);
        
        float mediaGastoPorDia = CalculaGastoEmDias(p);
        System.out.println("media gasta por dia:"+mediaGastoPorDia);
        float pontoDeRessuprimetoEmDias = p.getFornecedor().getPrazoDeEntrega() * margemSegurancaEntrega;
        pontoDeRessuprimetoEmDias = Math.round(pontoDeRessuprimetoEmDias);
        float estoqueSegurancaEmDias = pontoDeRessuprimetoEmDias - p.getFornecedor().getPrazoDeEntrega();
        float estoqueIdeal = mediaGastoPorDia * p.getDiasEstoqueDesejavel();
        int diasEstoqueDisponivel = (int) (p.getSaldo()/mediaGastoPorDia);
        diasEstoqueDisponivel = (int) Math.floor(diasEstoqueDisponivel);
        
        p.setEstoqueDeSeguranca(p.getFornecedor().getPrazoDeEntrega() / 2 + 1);
        p.setPontoDeRessuprimento(p.getFornecedor().getPrazoDeEntrega() + p.getEstoqueDeSeguranca());
        p.setEstoqueIdeal(p.getPontoDeRessuprimento() + p.getDiasEstoqueDesejavel());
        p.setDiasEstoqueDisponivel(diasEstoqueDisponivel);
        p.setMediaConsumo(mediaGastoPorDia);
//        
//        System.out.println("media gasto por dia:"+ mediaGastoPorDia);
//        
//        System.out.println("EstoqueDisponivel = "+p.getSaldo());
//        
//        System.out.println("Estoque de Segurança: " + p.getEstoqueDeSeguranca() + " dias");
//        System.out.println("Ponto de Ressuprimento: " + p.getPontoDeRessuprimento() + " dias");
//        System.out.println("Estoque ideal: " + p.getEstoqueIdeal() + " dias");
//        System.out.println("Estoque disponivel: " + p.getDiasEstoqueDisponivel()+ " dias");
      
        System.out.println("Dias de Estoque disponivel: "+p.getDiasEstoqueDisponivel());
        System.out.println("Ponto de Ressuprimento: "+p.getPontoDeRessuprimento());
       
        PedidoDAO pdao = new PedidoDAO();
        if (p.getDiasEstoqueDisponivel() <= p.getPontoDeRessuprimento()) {

            if (pdao.pedidosEmAberto(p)) {
                Pedido em = new Pedido();
                em.setConfig(new ConfiguracaoDAO().findAll().get(0));
                em.setF(p.getFornecedor());
                em.setP(p);
                em.setAssunto("Pedido do produto "+em.getP().getNome());
                em.setCorpo("Produto:"+em.getP().getNome()+"\nQuantidade: "+estoqueIdeal+"\nPrazo de Entrega: "+em.getF().getPrazoDeEntrega());
                em.setQuantidade((long) estoqueIdeal);
                PedidoDAO emDAO = new PedidoDAO();
                System.out.println("Criando pedido:"+new Gson().toJson(em));
                emDAO.create(em);

            }else{
                Pedido em = pdao.findNaoEnviadosPorProduto(p).get(0);
                em.setConfig(new ConfiguracaoDAO().findAll().get(0));
                em.setF(p.getFornecedor());
                em.setP(p);
                em.setQuantidade((long) estoqueIdeal);
                System.out.println("Editando pedido:"+new Gson().toJson(em));
                pdao.edit(em);
            }
            
        }
        
        atualizaProduto(p);
    
    }
    
    
}
