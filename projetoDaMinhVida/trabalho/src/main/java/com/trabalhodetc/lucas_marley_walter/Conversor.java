package com.trabalhodetc.lucas_marley_walter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Conversor {

    public static Automato converter(Automato afn) {
        afn.atualizarNomes();
        afn.sort();

        Automato afd = new Automato();
        Set<String> alfabeto = new HashSet<>();
        for (String string : afn.getAlfabetoAsArray()) {
           alfabeto.add(string);
        }
        alfabeto.removeIf(x -> x.equals(""));
        afd.setAlfabeto(alfabeto);


        String inicial = pularLambdas(afn, afn.getEstado(afn.getInicial()));
        List<String> estados = new ArrayList<>();
        estados.add(inicial);
        int pilha = 1;
        while(true){
            for (String simbolo : alfabeto) {
                String r = praOndeVai(afn, inicial, simbolo);
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

        
        for(int i = 0; i < estados.size(); i++){
            Estado e = new Estado(i);
            e.setNome(estados.get(i));
            e.setFinal((Conversor.isFinal(afn, e.getNome())));
            Random r = new Random();
            e.setPositionX((float)r.nextInt(200));
            e.setPositionY((float)r.nextInt(200));

            afd.adicionarEstado(e);
        }
        
        afd.setInicial(0);


        for (int i = 0; i < afd.getNumeroDeEstados(); i++) {
            
            Estado e = afd.getEstado(i);
            for (String simbolo : alfabeto) {
                int index = estados.indexOf(praOndeVai(afn, e.getNome(), simbolo));
                if(index >= 0 )
                    e.adicionarTransicao(simbolo, index);
            }
        }

        Minimizador.adicionarEstadoConsumidor(afd);

        return afd;
    }

    private static boolean isFinal(Automato afn, String estado){
        String estados[] = estado.split("-");
        for (String string : estados) {
            if(afn.buscarNome(string).IsFinal()){
                return true;
            }
        }
        return false;
    }

    private static String praOndeVai(Automato afn, String estado, String simbolo){

        if(estado == null){
            return null;
        }
        
        String estados[] = estado.split("-");
        StringBuilder builder = new StringBuilder();
        for (String string : estados) {
            Estado e = afn.buscarNome(string);
            List<Integer> list = e.getListaDeTransicao(simbolo);
            if(list == null){
                continue;
            }
            for (Integer integer : list) {
                Estado tp = afn.getEstado(afn.binarySearch(integer));
                if(tp.temLambda()){
                    String mp = pularLambdas(afn, tp) + "-";
                    if(!builder.toString().contains(mp)){
                        builder.append(mp);
                    }
                }
                if(tp.apenasLambda() == false){
                    if(!builder.toString().contains(tp.getNome()))
                        builder.append(tp.getNome() + "-");
                }
            }
        }   
        String result = builder.toString();
        if(result.length() == 0){
            return null;
        }
        if(result.charAt(result.length() - 1) == '-')
            result = result.substring(0, result.length() - 1);
        return result;

    }
    
    private static String pularLambdas(Automato afn, Estado estado) {
        StringBuilder stringBuilder = new StringBuilder();
        pularLambdas(afn, estado, stringBuilder);
        String result = stringBuilder.toString();
        if(result.length() == 0){
            return null;
        }
        if(result.charAt(result.length() - 1) == '-')
            result = result.substring(0, result.length() - 1);
        return result;
    }

    private static void pularLambdas(Automato afn, Estado estado, StringBuilder builder) {

        if (!estado.temLambda()) {
            builder.append(estado.getNome() + "-");
            return;
        }

        if (estado.temLambda() && !estado.apenasLambda()) {
            builder.append(estado.getNome() + "-");
        }

        List<Integer> transicoes = estado.getListaDeTransicao("");
        if (transicoes != null) {
            for (Integer integer : transicoes) {
                Estado e = afn.getEstado(afn.binarySearch(integer));
                if(e.getId() != estado.getId() && !builder.toString().contains(e.getNome()))
                    pularLambdas(afn, e, builder);
            }
        }

    }

}
