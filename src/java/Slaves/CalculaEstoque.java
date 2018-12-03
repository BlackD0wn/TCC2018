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
        System.out.println("Tempo de datas = " + diferencaDias);
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
//        System.out.println("Divisor: "+divisor);

        for (Iterator<Movimentacao> iterator = movimentacoesFiltradas.iterator(); iterator.hasNext();) {
            Movimentacao next = iterator.next();
            if (next.getTipo() == 0) {
                media += next.getQuantidade();
//                System.out.println("Data: "+next.getData()+" Quantidade: "+next.getQuantidade()+" Media no momento: "+media);

            }
        }

        return media / divisor;
    }

    public void calcular(Produto p) {

        atualizaProduto(p);

        float mediaGastoPorDia = CalculaGastoEmDias(p);
        System.out.println("media gasta por dia:" + mediaGastoPorDia);
        float pontoDeRessuprimetoEmDias = p.getFornecedor().getPrazoDeEntrega() * margemSegurancaEntrega;
        pontoDeRessuprimetoEmDias = Math.round(pontoDeRessuprimetoEmDias);
        float estoqueSegurancaEmDias = pontoDeRessuprimetoEmDias - p.getFornecedor().getPrazoDeEntrega();
        float estoqueIdeal = mediaGastoPorDia * p.getDiasEstoqueDesejavel();
        int diasEstoqueDisponivel = (int) (p.getSaldo() / mediaGastoPorDia);
        diasEstoqueDisponivel = (int) Math.floor(diasEstoqueDisponivel);

        p.setEstoqueDeSeguranca(p.getFornecedor().getPrazoDeEntrega() / 2 + 1);
        p.setPontoDeRessuprimento(p.getFornecedor().getPrazoDeEntrega() + p.getEstoqueDeSeguranca());
        p.setEstoqueIdeal(p.getPontoDeRessuprimento() + p.getDiasEstoqueDesejavel());
        p.setDiasEstoqueDisponivel(diasEstoqueDisponivel);
        p.setMediaConsumo(mediaGastoPorDia);

        System.out.println("Dias de Estoque disponivel: " + p.getDiasEstoqueDisponivel());
        System.out.println("Ponto de Ressuprimento: " + p.getPontoDeRessuprimento());

        PedidoDAO pdao = new PedidoDAO();
        if (p.getDiasEstoqueDisponivel() <= p.getPontoDeRessuprimento()) {

            if (!pdao.pedidosEmAberto(p)) {
                Pedido em = new Pedido();
                em.setConfig(new ConfiguracaoDAO().procurarTodos().get(0));
//                em.setF(p.getFornecedor());
                em.setP(p);
                em.setAssunto("Pedido do produto " + em.getP().getNome());
                em.setCorpo("Produto:" + em.getP().getNome() + "\nQuantidade: " + estoqueIdeal + "\nPrazo de Entrega: " + em.getProduto().getFornecedor().getPrazoDeEntrega() + "\n\nAtenciosamente,\n" + em.getConfig().getNomeFantasia());
                em.setQuantidade((int) estoqueIdeal);
                PedidoDAO emDAO = new PedidoDAO();
                System.out.println("Criando pedido:" + new Gson().toJson(em));
                emDAO.create(em);

            } else {
                List<Pedido> naoEnviados = pdao.procurarNaoEnviadosPorProduto(p);

                if (!naoEnviados.isEmpty()) {
                    Pedido em = naoEnviados.get(0);
                    em.setConfig(new ConfiguracaoDAO().procurarTodos().get(0));
                    em.setP(p);
                    em.setQuantidade((int) estoqueIdeal);
                    System.out.println("Editando pedido");
                    pdao.edit(em);
                }
            }
        }

        atualizaProduto(p);

    }

}
