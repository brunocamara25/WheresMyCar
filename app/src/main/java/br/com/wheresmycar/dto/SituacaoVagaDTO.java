package br.com.wheresmycar.dto;


/**
 * Created by Bruno on 20/8/15.
 */

public class SituacaoVagaDTO {

    private boolean ReservaConcluidaComSucesso;
    private boolean VagaAindaReservada;

    public boolean isReservaConcluidaComSucesso() {
        return ReservaConcluidaComSucesso;
    }

    public void setReservaConcluidaComSucesso(boolean reservaConcluidaComSucesso) {
        ReservaConcluidaComSucesso = reservaConcluidaComSucesso;
    }

    public boolean isVagaAindaReservada() {
        return VagaAindaReservada;
    }

    public void setVagaAindaReservada(boolean vagaAindaReservada) {
        VagaAindaReservada = vagaAindaReservada;
    }
}