package com.trabalhodetc.uniao_afds_afns;

import java.io.IOException;
import java.io.FileWriter;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;

//import uniao_afds_afns.Estado;
//import uniao_afds_afns.Transicao;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLOutputFactory;

public class AutomatoWriter {
    public static void escreveAutomato(Automato automato, String pathAutomato)
            throws IOException, XMLStreamException {
        escreveAutomato(automato, new FileWriter(pathAutomato));
    }

    public static void escreveAutomato(Automato automato, FileWriter arquivo)
            throws XMLStreamException {
        XMLStreamWriter arquivoSaida = XMLOutputFactory.newFactory()
            .createXMLStreamWriter(arquivo);

        //arquivoSaida.writeStartDocument("UTF-8" ,"1.0");
        arquivoSaida.writeComment("Criado com uniao_afd_afn");
        arquivoSaida.writeStartElement("structure");

        // Tipo do automato
        arquivoSaida.writeStartElement("type");
        arquivoSaida.writeCharacters("fa");
        arquivoSaida.writeEndElement();

        // Automato
        arquivoSaida.writeStartElement("automaton");

        // Escreve a lista de estado
        arquivoSaida.writeComment("Lista de estados");
        escreveEstados(automato.getEstados(), arquivoSaida);

        // Escreve a lista de transições
        arquivoSaida.writeComment("Lista de transicoes");
        escreveTransicoes(automato.getTransicoes(), arquivoSaida);

        arquivoSaida.writeEndDocument();

        arquivoSaida.close();
    }

    private static void escreveEstados(List<Estado> estados,
                                      XMLStreamWriter arquivoSaida)
            throws XMLStreamException {
        for (Estado estado : estados) {
            arquivoSaida.writeStartElement("state");
            arquivoSaida.writeAttribute("id", String.valueOf(estado.getId()));
            arquivoSaida.writeAttribute("name", estado.getNome());

            arquivoSaida.writeStartElement("x");
            arquivoSaida.writeCharacters("100"); // Posição dummy
            arquivoSaida.writeEndElement();

            arquivoSaida.writeStartElement("y");
            arquivoSaida.writeCharacters("100"); // Posição dummy
            arquivoSaida.writeEndElement();

            if (estado.isInicial())
                arquivoSaida.writeEmptyElement("initial");

            if (estado.isFinal())
                arquivoSaida.writeEmptyElement("final");

            arquivoSaida.writeEndElement();
        }
    }

    private static void escreveTransicoes(List<Transicao> transicoes,
                                          XMLStreamWriter arquivoSaida)
            throws XMLStreamException {
        for (Transicao transicao : transicoes) {
            arquivoSaida.writeStartElement("transition");
                arquivoSaida.writeStartElement("from");
                arquivoSaida.writeCharacters(String.valueOf(transicao.getOrigem()));
                arquivoSaida.writeEndElement();

                arquivoSaida.writeStartElement("to");
                arquivoSaida.writeCharacters(String.valueOf(transicao.getDestino()));
                arquivoSaida.writeEndElement();

                // Transições lambda ou vazia têm uma tag read vazia.
                if (transicao.getSimbolo().isEmpty()) {
                    arquivoSaida.writeEmptyElement("read");
                } else {
                    arquivoSaida.writeStartElement("read");
                    arquivoSaida.writeCharacters(transicao.getSimbolo());
                    arquivoSaida.writeEndElement();
                }
            arquivoSaida.writeEndElement();
        }
    }
}

