package br.com.wheresmycar.dto;


/**
 * Created by Bruno on 20/8/15.
 */

public class VagasDTO {

    private String Deficiente;
    private String Disponivel;
    private String Id;
    private String Nome;

    public String getDeficiente() {
        return Deficiente;
    }

    public void setDeficiente(String deficiente) {
        Deficiente = deficiente;
    }

    public String getDisponivel() {
        return Disponivel;
    }

    public void setDisponivel(String disponivel) {
        Disponivel = disponivel;
    }

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
}