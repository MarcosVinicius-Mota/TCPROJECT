package com.trabalhodetc.lucas_marley_walter;

public class Complemento {
    public static void complemento(Automato at) {
        for(int i = 0; i < at.getNumeroDeEstados(); i++){
            at.getEstado(i).setFinal(!at.getEstado(i).IsFinal());
        }
    }
}
