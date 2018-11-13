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

/**
 *
 * @author hook
 */
@Entity
public class Pedido {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Produto p;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Fornecedor f;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Configuracao config;

  
    public Date getDataRecebido() {
        return dataRecebido;
    }

    public void setDataRecebido(Date dataRecebido) {
        this.dataRecebido = dataRecebido;
    }

    public boolean isRecebido() {
        return recebido;
    }

    public void setRecebido(boolean recebido) {
        this.recebido = recebido;
    }
    
    private String corpo;
    private String assunto;
    private long quantidade;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRecebido;
    
    private boolean recebido;
    private boolean enviado;


    public Configuracao getConfig() {
        return config;
    }

    public void setConfig(Configuracao config) {
        this.config = config;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
   
    public Pedido() {
    }

    public void preparaEmail(){
        assunto = "Pedido - "+config.getNomeFantasia();
        corpo = "Produto: "+p.getNome()+
                "\nQuantidade:"+quantidade+
                "\nEntrega em:"+f.getPrazoDeEntrega();
    }
    
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Produto getP() {
        return p;
    }

    public void setP(Produto p) {
        this.p = p;
    }

    public Fornecedor getF() {
        return f;
    }

    public void setF(Fornecedor f) {
        this.f = f;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }
    
}
