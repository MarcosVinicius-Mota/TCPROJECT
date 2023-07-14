package com.trabalhodetc.uniao_afds_afns;

public class Estado {
    private int id;

    private boolean eInicial;
    private boolean eFinal;

    private String nome;


    Estado(String nome, int id) {
        this.nome = nome;
        this.id = id;

        this.eInicial = false;
        this.eFinal = false;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isInicial() {
        return this.eInicial;
    }

    public void setInicial(boolean eInicial) {
        this.eInicial = eInicial;
    }

    public boolean isFinal() {
        return this.eFinal;
    }

    public void setFinal(boolean eFinal) {
        this.eFinal = eFinal;
    }
}

