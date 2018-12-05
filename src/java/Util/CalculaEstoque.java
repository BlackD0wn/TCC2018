/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

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

    private int diasParaCalcular = 90;
    private float margemSegurancaEntrega = (float) 1.5;

    private List<Movimentacao> buscaMovimentacoesPorProduto(Produto p) {
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

    private void atualizaProduto(Produto p) {
        ProdutoDAO dao = new ProdutoDAO();
        dao.edit(p);
    }

    private float CalculaGastoEmDias(Produto p) {
        float media = 0;
        int divisor = 90;
        CalculadorDiferencaDatas cdd = new CalculadorDiferencaDatas();

        List<Movimentacao> movimentacoes = buscaMovimentacoesPorProduto(p);
        List<Movimentacao> movimentacoesFiltradas = new ArrayList<>();

        int diferencaDias = cdd.diferencaDeDatas(new Date(), p.getDataCadastro());
        if (diferencaDias < diasParaCalcular) {
            divisor = diferencaDias;
        } else {
            divisor = diasParaCalcular;
        }

        for (Iterator<Movimentacao> iterator = movimentacoes.iterator(); iterator.hasNext();) {
            Movimentacao next = iterator.next();
            if (next.getTipo() == 0) {
                if (cdd.diferencaDeDatas(new Date(), next.getData()) <= diasParaCalcular) {
                    movimentacoesFiltradas.add(next);
                }

            }
        }
//    
        for (Iterator<Movimentacao> iterator = movimentacoesFiltradas.iterator(); iterator.hasNext();) {
            Movimentacao next = iterator.next();
            if (next.getTipo() == 0) {
                media += next.getQuantidade();
//    
            }
        }

        return media / divisor;
    }

    public void calcular(Produto p) {

        atualizaProduto(p);

        float mediaGastoPorDia = CalculaGastoEmDias(p);
        float pontoDeRessuprimetoEmDias = p.getFornecedor().getPrazoDeEntrega() * margemSegurancaEntrega;
        pontoDeRessuprimetoEmDias = Math.round(pontoDeRessuprimetoEmDias); 
        float estoqueSegurancaEmDias = pontoDeRessuprimetoEmDias - p.getFornecedor().getPrazoDeEntrega();
        float estoqueIdeal = mediaGastoPorDia * (p.getDiasEstoqueDesejavel()+estoqueSegurancaEmDias);
        int diasEstoqueDisponivel = (int) (p.getSaldo() / mediaGastoPorDia);
        diasEstoqueDisponivel = (int) Math.floor(diasEstoqueDisponivel);

        p.setEstoqueDeSeguranca((int) estoqueSegurancaEmDias); // arrumar
        p.setPontoDeRessuprimento((int) pontoDeRessuprimetoEmDias);
        p.setEstoqueIdeal((int) estoqueIdeal);
        p.setDiasEstoqueDisponivel(diasEstoqueDisponivel);
        p.setMediaConsumo(mediaGastoPorDia);

        MovimentacaoDAO mdao = new MovimentacaoDAO();
        PedidoDAO pdao = new PedidoDAO();
        if (p.getDiasEstoqueDisponivel() <= p.getPontoDeRessuprimento()) {

            if (!pdao.pedidosEmAberto(p)) {
                int quantidadeAPedir = (int) estoqueIdeal - (int)(estoqueSegurancaEmDias*mediaGastoPorDia);

                Pedido em = new Pedido();
                em.setConfig(new ConfiguracaoDAO().procurarTodos().get(0));
//                em.setF(p.getFornecedor());
                em.setP(p);
                em.setAssunto("Pedido do produto " + em.getP().getNome());
                em.setCorpo("Produto:" + em.getP().getNome() + "\nQuantidade: " + estoqueIdeal + "\nPrazo de Entrega: " + em.getProduto().getFornecedor().getPrazoDeEntrega() +"\n Preco: "+mdao.ultimoPrecoPorProduto(p)+"\n\nAtenciosamente,\n" + em.getConfig().getNomeFantasia());
                em.setQuantidade(quantidadeAPedir);
                PedidoDAO emDAO = new PedidoDAO();
                emDAO.create(em);

            } else {
                List<Pedido> naoEnviados = pdao.procurarNaoEnviadosPorProduto(p);

                if (!naoEnviados.isEmpty()) {
                    Pedido em = naoEnviados.get(0);
                    em.setConfig(new ConfiguracaoDAO().procurarTodos().get(0));
                    em.setP(p);
                    em.setQuantidade((int) estoqueIdeal);
                    pdao.edit(em);
                }
            }
        }

        atualizaProduto(p);

    }

}
