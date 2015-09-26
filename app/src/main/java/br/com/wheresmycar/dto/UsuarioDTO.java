package br.com.wheresmycar.dto;


import java.io.Serializable;

/**
 * Created by Bruno on 7/14/15.
 */

public class UsuarioDTO implements Serializable {
    private Long id;
    private String login;
    private String senha;
    private String ultimaAtualizacao;

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



}
