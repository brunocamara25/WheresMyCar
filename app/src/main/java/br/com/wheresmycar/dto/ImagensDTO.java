package br.com.wheresmycar.dto;


/**
 * Created by Bruno on 10/10/15.
 */

public class ImagensDTO {

    private String Imagem;


    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }

    @Override
    public String toString() {
        return "ImagensDTO{" +
                "Imagem='" + Imagem + '\'' +
                '}';
    }
}