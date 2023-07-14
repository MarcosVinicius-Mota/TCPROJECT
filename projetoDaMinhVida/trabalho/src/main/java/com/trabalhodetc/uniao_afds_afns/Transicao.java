package com.trabalhodetc.uniao_afds_afns;

public class Transicao {
    private int origem; // from
    private int destino; // to

    // Quando "" representa uma transição
    // vazia, ou seja, uma transição lambda.
    private String simbolo = ""; // read

    Transicao() {}

    Transicao(int origem) {
        this.origem = origem;
    }

    Transicao(int origem, int destino) {
        this.origem = origem;
        this.destino = destino;
    }

    Transicao(int origem, int destino, String simbolo) {
        this.origem = origem;
        this.destino = destino;
        this.simbolo = simbolo;
    }

    public int getOrigem() {
        return this.origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
    }

    public int getDestino() {
        return this.destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getSimbolo() {
        return this.simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
