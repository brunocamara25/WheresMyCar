package br.com.wheresmycar.dto;


/**
 * Created by Bruno on 5/8/15.
 */

public class AndaresDTO {

    private String Id;
    private String Nome;
    private String QtdLivre;
    private String QtdVagas;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getQtdLivre() {
        return QtdLivre;
    }

    public void setQtdLivre(String qtdLivre) {
        QtdLivre = qtdLivre;
    }

    public String getQtdVagas() {
        return QtdVagas;
    }

    public void setQtdVagas(String qtdVagas) {
        QtdVagas = qtdVagas;
    }
}