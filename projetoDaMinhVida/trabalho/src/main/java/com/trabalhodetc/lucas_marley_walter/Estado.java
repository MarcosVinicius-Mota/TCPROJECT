package com.trabalhodetc.lucas_marley_walter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Estado {

    private Integer id;
    private Boolean _final;
    private Vector2f screenPosition;
    private String nome;
    private Map<String, List<Integer>> transicao;

    public Estado() {
        screenPosition = new Vector2f();
        screenPosition.x = 0f;
        screenPosition.y = 0f;
        transicao = new HashMap<>();

    }

    public Estado(int id) {
        this();
        this.id = id;
        this._final = false;
    }

    public Estado(int id, boolean _final) {
        this();
        this.id = id;
        this._final = _final;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean IsFinal() {
        return _final;
    }

    public void setFinal(boolean _final) {
        this._final = _final;
    }

    public boolean equals(Object other) {
        return id.equals(other);
    }

    public void setPositionX(Float x) {
        this.screenPosition.x = x;
    }

    public void setPositionY(Float y) {
        this.screenPosition.y = y;
    }

    public Vector2f getPosition() {
        return screenPosition;
    }

    public void adicionarTransicao(String simbolo, Integer proximoId) {
        List<Integer> l = transicao.get(simbolo);
        if (l == null) {
            l = new ArrayList<>();
            transicao.put(simbolo, l);
        }
        l.add(proximoId);
        transicao.put(simbolo, l);
    }

    public void removerTransicao(String simbolo) {
        transicao.remove(simbolo);
    }

    public void removerTransicao(String simbolo, Integer destino) {
        List<Integer> l = transicao.get(simbolo);

        try {
            l.removeIf(x -> x == destino);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lista não inicializada", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Integer getTransicao(String key) {
        List<Integer> l = transicao.get(key);
        return l == null ? null : l.size() == 0 ? null : l.get(0);
    }

    public List<Integer> getListaDeTransicao(String simbolo) {
        return transicao.get(simbolo);
    }

    public boolean temLambda() {
        return transicao.containsKey("");
    }

    public boolean apenasLambda(){
        return transicao.size() == 1 && (transicao.get("") != null);
    }

    boolean possuiTransicao() {
        return !transicao.isEmpty();
    }

    public void sobreescreverTransicao(String simbolo, Integer id){
        transicao.remove(simbolo);
        adicionarTransicao(simbolo, id);
    }

}