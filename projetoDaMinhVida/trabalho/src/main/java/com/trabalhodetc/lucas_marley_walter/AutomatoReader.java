package com.trabalhodetc.lucas_marley_walter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AutomatoReader {

    public static Automato loadAutomatoFromJff(String path){
        AutomatoReader reader = new AutomatoReader();
        reader.setPath(path);
        return reader.load();
    }

    Document document;

    private AutomatoReader() {
        document = null;
    }
    private void setPath(String path){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(path);
        } catch (ParserConfigurationException e) {
            System.out.println("Erro desconhecido");
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }
    }
    private Automato load() {
        Automato automato = new Automato();
        Set<String> alfabeto = new HashSet<>();
        loadStates(automato);
        loadTransitions(automato, alfabeto);
        automato.setAlfabeto(alfabeto);
        return automato;

    }

    private void loadTransitions(Automato automato, Set<String> alfabeto) {
        NodeList transitions = document.getElementsByTagName("transition");

        for (int i = 0; i < transitions.getLength(); i++) {
            Element transition = (Element) transitions.item(i);
            loadTransitionChilds(automato, alfabeto, transition);
        }
    }

    private void loadTransitionChilds(Automato automato, Set<String> alfabeto, Element transition) {
        NodeList childs = transition.getChildNodes();
        Integer from = null;
        Integer to = null;
        String read = null;

        for (int i = 0; i < childs.getLength(); i++) {
            Node no = childs.item(i);
            if (no.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element tag = (Element) no;
            String name = tag.getTagName();
            
            if (name.equals("from")) {
                from = Integer.parseInt(tag.getTextContent());
            } else if (name.equals("to")) {
                to = Integer.parseInt(tag.getTextContent());
            } else if (name.equals("read")) {
                read = tag.getTextContent();
            }

        }

        alfabeto.add(read);
        if(from != null)
            automato.adicionarTransicao(from, read, to);
    }

    private void loadStates(Automato automato) {
        NodeList estados = document.getElementsByTagName("state");

        for (int i = 0; i < estados.getLength(); i++) {
            Element estado = (Element) estados.item(i);
            Integer id = Integer.parseInt(estado.getAttribute("id"));
            automato.adicionarEstado(loadStateChild(automato, estado, id));
        }
    }

    private Estado loadStateChild(Automato automato, Element estado, Integer id) {
        Estado e = new Estado(id);
        NodeList childs = estado.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            Node no = childs.item(i);
            if (no.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element tag = (Element) no;
            String nome = tag.getTagName();

            if (nome.equals("x")) {
                e.setPositionX(Float.parseFloat(tag.getTextContent()));
            } else if (nome.equals("y")) {
                e.setPositionY(Float.parseFloat(tag.getTextContent()));
            } else if (nome.equals("final")) {
                e.setFinal(true);
            } else if (nome.equals("initial")) {
                automato.setInicial(id);
            }
        }
        e.setNome("q" + e.getId());
        return e;
    }

}
