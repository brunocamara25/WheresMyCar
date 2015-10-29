package br.com.wheresmycar.dto;


/**
 * Created by Bruno on 20/8/15.
 */

public class SituacaoVagaDTO {

    private String ReservaConcluidaComSucesso;
    private String VagaAindaReservada;

    public String getReservaConcluidaComSucesso() {
        return ReservaConcluidaComSucesso;
    }

    public void setReservaConcluidaComSucesso(String reservaConcluidaComSucesso) {
        ReservaConcluidaComSucesso = reservaConcluidaComSucesso;
    }

    public String getVagaAindaReservada() {
        return VagaAindaReservada;
    }

    public void setVagaAindaReservada(String vagaAindaReservada) {
        VagaAindaReservada = vagaAindaReservada;
    }
}