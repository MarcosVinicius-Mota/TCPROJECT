package com.trabalhodetc.lucas_marley_walter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Automato {

    public static Automato loadFromJff(String path){
        return AutomatoReader.loadAutomatoFromJff(path);
    }

    public static void saveInJff(String path, Automato automato){
        AutomatoWriter.saveInJff(path, automato);
    }

    private List<Estado> estados;
    private Set<String> alfabeto;
    private int inical;

    public Automato() {
        estados = new ArrayList<>();
        alfabeto = new HashSet<>();
    }

    public int getNumeroDeEstados() {
        return estados.size();
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public int getInicial() {
        return inical;
    }

    public void setInicial(int inical) {
        this.inical = inical;
    }

    public void setAlfabeto(Set<String> set) {
        this.alfabeto = set;
    }

    public boolean alfabetoContains(String c) {
        return alfabeto.contains(c);
    }

    public void adicionarNoAlfabeto(String c) {
        alfabeto.add(c);
    }

    public String[] getAlfabetoAsArray() {
        String[] array = new String[alfabeto.size()];
        alfabeto.toArray(array);
        return array;
    }

    public Automato(int inical) {
        this();
        this.inical = inical;
    }

    void adicionarEstado(Estado estado) {
        if (estados.contains(estado)) {
            throw new AutomatoException("ESTADO JÁ EXISTE NO AUTOMATO");
        }
        estados.add(estado);
    }

    void removerEstado(Integer id) {
        for (String string : alfabeto) {
            for (Estado x : estados) {
                Set<Integer> set = new HashSet<>();
                List<Integer> l = x.getListaDeTransicao(string);
                if(l != null){
                    for (Integer integer : l) {
                        if(integer == id){
                            set.add(integer);
                        }
                    }
                    l.removeAll(set);
                }
            }
        }
        estados.removeIf((x) -> x.getId() == id);
    }

    void adicionarTransicao(int elemento, String simbolo, Integer to) {
        buscaSeq(elemento).adicionarTransicao(simbolo, to);
    }

    public Estado getEstado(int i) {
        return estados.get(i);
    }

    int getId(int i) {
        return estados.get(i).getId();
    }

    public void sort() {
        estados.sort((e1, e2) -> e1.getId() - e2.getId());
    }

    public int binarySearch(int ID) {
        return Collections.binarySearch(estados, new Estado(ID), (e1, e2) -> e1.getId() - e2.getId());
    }

    public Estado buscarNome(String nome){
        for (Estado sEstado : estados) {

            if(nome.hashCode() == sEstado.getNome().hashCode()){
                if(nome.equals(sEstado.getNome())){
                    return sEstado;
                }
            }
        }

        return null;
    }

    public void atualizarNomes(){
        for (Estado estado : estados) {
            estado.setNome("q" + estado.getId());
        }
    }

    public Estado buscaSeq(Integer ID){
        for (Estado e : estados) {
            if(e.getId() == ID){
                return e;
            }
        }
        return null;
    }


}