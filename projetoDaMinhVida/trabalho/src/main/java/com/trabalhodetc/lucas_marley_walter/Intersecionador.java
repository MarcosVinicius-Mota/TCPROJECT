package com.trabalhodetc.lucas_marley_walter;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Intersecionador {
    
    public static Automato Intersecionar(Automato automato1, Automato automato2) {
        
        automato1.sort();
        automato2.sort();
        automato1.atualizarNomes();
        automato2.atualizarNomes();

        String alfabeto1[] = automato1.getAlfabetoAsArray();
        String alfabeto2[] = automato2.getAlfabetoAsArray();

        Automato resultado = new Automato();
        Set<String> alfa = new HashSet<>();

        for (int i = 0; i < alfabeto1.length; i++) {
            alfa.add(alfabeto1[i]);
        }
        for (int i = 0; i < alfabeto2.length; i++) {
            alfa.add(alfabeto2[i]);
        }

        resultado.setAlfabeto(alfa);

        List<String> novosEstados = new ArrayList<>();

        for (int i = 0; i < automato1.getNumeroDeEstados(); i++) {
            Estado estadoAtual1 = automato1.getEstado(i);
            for (int j = 0; j < automato2.getNumeroDeEstados(); j++) {
                Estado estadoAtual2 = automato2.getEstado(j);
                String r = estadoAtual1.getNome() + "-" + estadoAtual2.getNome();
                novosEstados.add(r);
            }
        }

        for (int i = 0; i < novosEstados.size(); i++) {
            Estado e = new Estado(i);
            e.setNome(novosEstados.get(i));
            Random r = new Random();
            e.setPositionX((float) r.nextInt(200));
            e.setPositionY((float) r.nextInt(200));
            resultado.adicionarEstado(e);
        }

        for (int i = 0; i < resultado.getNumeroDeEstados(); i++) {
            Estado e = resultado.getEstado(i);
            String estados[] = e.getNome().split("-");
            Estado e1 = automato1.buscarNome(estados[0]);
            Estado e2 = automato2.buscarNome(estados[1]);
            for (String simbolo : resultado.getAlfabetoAsArray()) {

                Integer transicao1 = e1.getTransicao(simbolo);
                Integer transicao2 = e2.getTransicao(simbolo);

                if (transicao1 == null || transicao2 == null) {
                    continue;
                }

                Estado t1 = automato1.buscaSeq(transicao1);
                Estado t2 = automato2.buscaSeq(transicao2);

                String result = t1.getNome() + "-" + t2.getNome();
                e.adicionarTransicao(simbolo, novosEstados.indexOf(result));

            }

        }


        Estado inicial1 = automato1.getEstado(automato1.getInicial());
        Estado inicial2 = automato2.getEstado(automato2.getInicial());
        String inicial3 = inicial1.getNome() + "-" + inicial2.getNome();
        resultado.setInicial(novosEstados.indexOf(inicial3));

        for (int i = 0; i < resultado.getNumeroDeEstados(); i++) {

            Estado e = resultado.getEstado(i);
            String estados[] = e.getNome().split("-");

            e.setFinal(automato1.buscarNome(estados[0]).IsFinal() && automato2.buscarNome(estados[1]).IsFinal());

        }
        Minimizador.removerInaceciveis(resultado);
        Minimizador.adicionarEstadoConsumidor(resultado);
        return resultado;
    }

}