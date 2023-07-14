package com.trabalhodetc.lucas_marley_walter;
import java.util.ArrayList;
import java.util.List;

public class Coordenada {

    private Integer x;
    private Integer y;
    private Boolean equivalente;
    private List<Coordenada> head;

    public Coordenada(Integer x, Integer y) {
        this();
        this.x = x;
        this.y = y;

    }

    public Coordenada() {
        head = new ArrayList<>();
        equivalente = false;
    }

    public void destruirLista() {
        for (int i = 0; i < head.size(); i++) {
            if (head.get(i).isEquivalente()) {
                head.get(i).setEquivalente(false);
                head.get(i).destruirLista();
            }
        }
        while (head.size() > 0) {
            head.remove(0);
        }

    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public Boolean isEquivalente() {
        return equivalente;
    }

    public void setEquivalente(Boolean estadoFinal) {
        this.equivalente = estadoFinal;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void adicionarNaLista(Coordenada ID) {
        head.add(ID);
    }

    @Override
    public String toString() {
        return "Coordenada [x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        Coordenada c = (Coordenada) obj;
        return (c.x == x && c.y == y) || (c.y == x && c.x == y);

    }

}
