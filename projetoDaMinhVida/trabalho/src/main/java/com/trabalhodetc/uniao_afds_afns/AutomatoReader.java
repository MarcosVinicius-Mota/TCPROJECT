package com.trabalhodetc.uniao_afds_afns;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AutomatoReader extends DefaultHandler {
    private Automato automato;
    private Estado estado;
    private Transicao transicao;

    private boolean lendoFrom;
    private boolean lendoTo;
    private boolean lendoRead;

    AutomatoReader(Automato automato) {
        this.automato = automato;
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        switch (qName) {
            // Lê os dados de um estado
            case "state":
                this.estado = new Estado(attributes.getValue("name"),
                    Integer.parseInt(attributes.getValue("id")));
                break;

            case "initial":
                this.estado.setInicial(true);
                break;

            case "final":
                this.estado.setFinal(true);
                break;

            // Lê as transições
            case "transition":
                this.transicao = new Transicao();
                break;

            case "from":
                this.lendoFrom = true;
                break;

            case "to":
                this.lendoTo = true;
                break;

            case "read":
                this.lendoRead = true;
                break;
        }
    }

    @Override
    public void characters(char ch[],
                           int start,
                           int length) throws SAXException {
        String valor = new String(ch, start, length);

        // Ignora toda ocorrência de caractéres em branco para garantir que as
        // transições vazias realmente sejam vazias.
        if (valor.isBlank())
            return;

        if (this.lendoFrom) {
            this.transicao.setOrigem(Integer.parseInt(valor));
            this.lendoFrom = false;
        } else if (this.lendoTo) {
            this.transicao.setDestino(Integer.parseInt(valor));
            this.lendoTo = false;
        } else if (this.lendoRead) {
            // Quando ocorre uma transição lambda esse if nunca
            // vai ocorrer fazendo o símbolo ser "". Isso
            // acontece porque a tag <read> está vazia então
            // essa função não será chamada para ler os
            // caractéres entre as tags de abertura e
            // fechamento.
            this.transicao.setSimbolo(valor);
            this.lendoRead = false;
        }
    }

    @Override
    public void endElement(String uri,
                             String localName,
                             String qName) throws SAXException {
        switch (qName) {
            case "state":
                this.automato.addEstado(this.estado);
                break;

            case "transition":
                this.automato.addTransicao(this.transicao);
                break;
        }
    }
}

