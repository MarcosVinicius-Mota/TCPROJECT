/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalhodetc.AutomatoG3;


public class Transicao {
    private String from; //atributo que armazena o estado de onde a transição parte
    private String to; //atributo que armazena o estado para onde a transição vai
    private String read; //atributo que armazena o símbolo lido pela transição

    public Transicao() {       
    }

    public Transicao(String from, String to, String read) {
        this.from = from;
        this.to = to;              //Cria-se um construtor público e armazena o valor às variáveis de instância da classe
        this.read = read;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

   
}
