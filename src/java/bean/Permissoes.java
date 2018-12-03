/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author hook
 */
@Entity
public class Permissoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private boolean email;
    private boolean usuario;
    private boolean produto;
    private boolean fornecedor;
    private boolean configuracao;
    private boolean estatistica;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }

    public boolean isProduto() {
        return produto;
    }

    public void setProduto(boolean produto) {
        this.produto = produto;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean isConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(boolean configuracao) {
        this.configuracao = configuracao;
    }

    public boolean isEstatistica() {
        return estatistica;
    }

    public void setEstatistica(boolean estatistica) {
        this.estatistica = estatistica;
    }

    public Permissoes() {
    }

}
