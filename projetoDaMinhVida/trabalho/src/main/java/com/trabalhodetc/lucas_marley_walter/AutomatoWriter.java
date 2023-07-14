package com.trabalhodetc.lucas_marley_walter;
import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AutomatoWriter {

    public static void saveInJff(String path, Automato automato){
        AutomatoWriter writer = new AutomatoWriter(automato);
        writer.save(path);
    }

    Document document;
    Automato automato;

    private AutomatoWriter(Automato automato) {
        this.automato = automato;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (Exception e) {
            System.out.println("ERRO DESCONHECIDO");
        }
    }

    private void save(String path) {
        Element raiz = document.createElement("structure");
        Element type = document.createElement("type");
        Element automato = document.createElement("automaton");
        raiz.appendChild(type);
        raiz.appendChild(automato);
        type.setTextContent("fa");
        document.appendChild(raiz);
        appendStates(automato);
        appendTransitions(automato);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult documentoFinal = new StreamResult(new File(path));
            try {
                transformer.transform(source, documentoFinal);
            } catch (TransformerException e) {

                e.printStackTrace();
            }

        } catch (TransformerConfigurationException e) {

            e.printStackTrace();
        }
    }

    private void appendTransitions(Element elementAutomato) {
        String alfabeto[] = automato.getAlfabetoAsArray();
        for (int i = 0; i < automato.getNumeroDeEstados(); i++) {

            Estado estado = automato.getEstado(i);
            for (String string : alfabeto) {
                Integer from = estado.getId();
                List<Integer> to = estado.getListaDeTransicao(string);
                if(to == null){
                    continue;
                }
                
                for (Integer transicaoInteger : to) {
                    
                    Element transicao = document.createElement("transition");
                    
                    Element fromElement = document.createElement("from");
                    Element toElement = document.createElement("to");
                    Element readElement = document.createElement("read");
                    
                    fromElement.setTextContent(Integer.toString(from));
                    toElement.setTextContent(transicaoInteger.toString());
                    readElement.setTextContent(string.toString());
                    
                    transicao.appendChild(fromElement);
                    transicao.appendChild(toElement);
                    transicao.appendChild(readElement);
                    
                    elementAutomato.appendChild(transicao);
                }

            }

        }
    }

    private void appendStates(Element elementAutomato) {

        for (int i = 0; i < automato.getNumeroDeEstados(); i++) {
            Estado estado = automato.getEstado(i);
            Element state = document.createElement("state");
            Attr id = document.createAttribute("id");
            id.setValue(estado.getId().toString());

            Attr name = document.createAttribute("name");
            name.setValue("q" + estado.getId());

            state.setAttributeNode(id);
            state.setAttributeNode(name);

            Element x = document.createElement("x");
            Element y = document.createElement("y");

            y.setTextContent(String.valueOf(estado.getPosition().y));
            x.setTextContent(String.valueOf(estado.getPosition().x));
            state.appendChild(x);
            state.appendChild(y);

            if (estado.getId() == automato.getInicial()) {
                Element f = document.createElement("initial");
                state.appendChild(f);
            }
            if (estado.IsFinal()) {
                Element f = document.createElement("final");
                state.appendChild(f);
            }
            elementAutomato.appendChild(state);
        }

    }

}
