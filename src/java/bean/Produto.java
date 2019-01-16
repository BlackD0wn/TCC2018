/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.ColumnDefault;

/**
 *
 * @author hook
 */
@Entity
@XmlRootElement
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String ean;
    private String nome;
    private String unidade;
    private int saldo;
    @ColumnDefault("-1")
    private int diasEstoqueDisponivel;
    @ColumnDefault("-1")
    private int diasEstoqueDesejavel;
    @ColumnDefault("N")
    private String curvaABC;
    private int estoqueDeSeguranca; // 50% do prazo de entrega
    private int pontoDeRessuprimento; // estoqueSeguranca + prazoDeEntrega
    private int estoqueIdeal;
    @OneToOne(cascade = CascadeType.MERGE)
    private Fornecedor fornecedor;
    private float valorMovimentado;
    private float porcMovimentacao;
    private float mediaConsumo;
    private int diasParaCalcularEstoque;

    public int getDiasParaCalcularEstoque() {
        return diasParaCalcularEstoque;
    }

    public void setDiasParaCalcularEstoque(int diasParaCalcularEstoque) {
        this.diasParaCalcularEstoque = diasParaCalcularEstoque;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    public float getMediaConsumo() {
        return mediaConsumo;
    }

    public void setMediaConsumo(float mediaConsumo) {
        this.mediaConsumo = mediaConsumo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getDiasEstoqueDisponivel() {
        return diasEstoqueDisponivel;
    }

    public void setDiasEstoqueDisponivel(int diasEstoqueDisponivel) {
        this.diasEstoqueDisponivel = diasEstoqueDisponivel;
    }

    public int getDiasEstoqueDesejavel() {
        return diasEstoqueDesejavel;
    }

    public void setDiasEstoqueDesejavel(int diasEstoqueDesejavel) {
        this.diasEstoqueDesejavel = diasEstoqueDesejavel;
    }

    public String getCurvaABC() {
        return curvaABC;
    }

    public void setCurvaABC(String curvaABC) {
        this.curvaABC = curvaABC;
    }

    public int getEstoqueDeSeguranca() {
        return estoqueDeSeguranca;
    }

    public void setEstoqueDeSeguranca(int estoqueDeSeguranca) {
        this.estoqueDeSeguranca = estoqueDeSeguranca;
    }

    public int getPontoDeRessuprimento() {
        return pontoDeRessuprimento;
    }

    public void setPontoDeRessuprimento(int pontoDeRessuprimento) {
        this.pontoDeRessuprimento = pontoDeRessuprimento;
    }

    public int getEstoqueIdeal() {
        return estoqueIdeal;
    }

    public void setEstoqueIdeal(int estoqueIdeal) {
        this.estoqueIdeal = estoqueIdeal;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public float getValorMovimentado() {
        return valorMovimentado;
    }

    public void setValorMovimentado(float valorMovimentado) {
        this.valorMovimentado = valorMovimentado;
    }

    public float getPorcMovimentacao() {
        return porcMovimentacao;
    }

    public void setPorcMovimentacao(float porcMovimentacao) {
        this.porcMovimentacao = porcMovimentacao;
    }

    public void adicionaSaldo(int quantidade) {
        this.saldo += quantidade;
    }

    public void removeSaldo(int quantidade) {
        this.saldo -= quantidade;
    }

}
