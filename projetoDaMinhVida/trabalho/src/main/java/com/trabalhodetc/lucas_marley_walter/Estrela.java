package com.trabalhodetc.lucas_marley_walter;

public class Estrela {
    public static void estrela(Automato automato){
        automato.sort();
        Estado novoInicial = new Estado(automato.getEstado(automato.getNumeroDeEstados() - 1).getId() + 1);
        Estado novofinal = new Estado(automato.getEstado(automato.getNumeroDeEstados() - 1).getId() + 2);
        Estado inicial = automato.getEstado(automato.getInicial());

        novoInicial.setFinal(false);
        novofinal.setFinal(true);

        automato.adicionarNoAlfabeto("");
        novoInicial.adicionarTransicao("", novofinal.getId());  
        novoInicial.adicionarTransicao("", inicial.getId());
        
        automato.setInicial(novoInicial.getId());
        automato.adicionarEstado(novoInicial);
        automato.adicionarEstado(novofinal);

        
        
        for(int i = 0; i < automato.getNumeroDeEstados() - 2; i++){
            Estado e = automato.getEstado(i);
            if(e.IsFinal()){
                e.adicionarTransicao("", novofinal.getId());
                e.adicionarTransicao("", inicial.getId());
                e.setFinal(false);
            }
        }
    }
}
