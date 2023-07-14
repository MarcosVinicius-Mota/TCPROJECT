package com.trabalhodetc.lucas_marley_walter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Minimizador {

    public static Automato minimizar(Automato automato){

        Minimizador m = new Minimizador(automato);
        m.minimizar();
        return automato;

    }

    private Automato automato;
    private List<Coordenada> tabela;

    private Minimizador(Automato automato) {
        this.automato = automato;
        this.automato.sort();
        tabela = new ArrayList<>();

        
    }
    
    public static void adicionarEstadoConsumidor(Automato automato) {
        automato.sort();
        String alfabeto[] = automato.getAlfabetoAsArray();

        int id = automato.getEstado(automato.getNumeroDeEstados() - 1).getId() + 1;
        Estado estado = new Estado(id);
        automato.adicionarEstado(estado);
        
        boolean remover = true;
        for (String string : alfabeto) {
            for (int i = 0; i < automato.getNumeroDeEstados() - 1; i++) {
                Estado e = automato.getEstado(i);
                
                if (e.getTransicao(string) == null) {
                    e.adicionarTransicao(string, id);
                    remover = false;
                }

            }
        }

        if (remover) {
            automato.removerEstado(id);
        } else {
            for (String string : alfabeto) {
                Estado e = automato.getEstado(automato.getNumeroDeEstados() - 1);
                e.adicionarTransicao(string, id);
                
            }
        }
        
    }
    
    private void minimizar() {
        
        tabela.clear();
        removerInaceciveis(automato);
        int tam = automato.getNumeroDeEstados();
        
        for (int i = 0; i < tam; i++) {
    
            for (int j = i + 1; j < tam; j++) {
                int e1 = automato.getId(i);
                int e2 = automato.getId(j);
                tabela.add(new Coordenada(e1, e2));
            }
            
        }
        
        automato.sort();
        
        for (Coordenada coordenada : tabela) {
            
            Estado e1 = automato.getEstado(automato.binarySearch(coordenada.getX()));
            Estado e2 = automato.getEstado(automato.binarySearch(coordenada.getY()));
            coordenada.setEquivalente(!(e1.IsFinal() ^ e2.IsFinal()));
        }
        
        
        for (Coordenada coordenada : tabela) {
            if (coordenada.isEquivalente() == false)
            continue;
            Estado e1 = automato.getEstado(automato.binarySearch(coordenada.getX()));
            Estado e2 = automato.getEstado(automato.binarySearch(coordenada.getY()));
            
            String alfabeto[] = automato.getAlfabetoAsArray();
            Boolean equivale = true;
            for (String String : alfabeto) {
                
                Integer transicao1 = e1.getTransicao(String);
                Integer transicao2 = e2.getTransicao(String);
                
                if (transicao1 == transicao2) {
                    equivale = true;
                } else {
                    Coordenada temp = new Coordenada(transicao1, transicao2);
                    for (int i = 0; i < tabela.size(); i++) {
                        if (tabela.get(i).equals(temp)) {
                            temp = tabela.get(i);
                            break;
                        }
                    }
                    
                    if (!temp.isEquivalente()) {
                        equivale = false;
                        coordenada.destruirLista();
                        break;
                    } else {
                        temp.adicionarNaLista(coordenada);
                    }
                    
                }
            }

            coordenada.setEquivalente(equivale);
            
        }
        
        unificar();
        Minimizador.adicionarEstadoConsumidor(automato);
        
    }
    
    public static void removerInaceciveis(Automato automato) {
        Integer inicial = automato.getInicial();
        List<Integer> estados = new ArrayList<>();
        estados.add(inicial);
        int pilha = 1;
        
        while(true){
            
            for (String simbolo : automato.getAlfabetoAsArray()) {
                Integer r = automato.buscaSeq(inicial).getTransicao(simbolo);
                if(r == null){
                    continue;
                }
                if(estados.contains(r) == false){
                    estados.add(r);
                }
            }

            if(pilha == estados.size()){
                break;
            }
            inicial = estados.get(pilha++);
        }

        Set<Integer> estadosASeremRemovidos = new HashSet<>();
        for (int i = 0; i < automato.getNumeroDeEstados(); i++) {
            Integer id = automato.getEstado(i).getId();
            if(!estados.contains(id)){
                estadosASeremRemovidos.add(id);
            }
        }

        for (Integer integer : estadosASeremRemovidos) {
            automato.removerEstado(integer);
        }

    }

    private void unificar() {

        for (Coordenada coordenada : tabela) {

            // automato.sort();
            if (coordenada.isEquivalente()) {
                // automato.sort();
                int index = automato.binarySearch(coordenada.getX());
                if (index < 0) {
                    continue;
                }
                Estado e1 = automato.getEstado(index);
                index = automato.binarySearch(coordenada.getY());
                if (index < 0) {
                    continue;
                }
                Estado e2 = automato.getEstado(index);

                if (e2.getId() == automato.getInicial()) {
                    Estado temp = e1;
                    e1 = e2;
                    e2 = temp;
                }

                String alfabeto[] = automato.getAlfabetoAsArray();
                for (int i = 0; i < automato.getNumeroDeEstados(); i++) {

                    for (String String : alfabeto) {

                        if (automato.getEstado(i).getTransicao(String) == e2.getId()) {
                            automato.getEstado(i).adicionarTransicao(String, e1.getId());
                        }

                    }

                }

                automato.removerEstado(e2.getId());
            }
        }
    }

}
