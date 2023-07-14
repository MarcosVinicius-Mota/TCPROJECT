/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.trabalhodetc.AutomatoG3;
public class Estados {  //classe destinada à definição do autômato, seguindo padrão da estrutura xml.
    private String id; //corresponde à numeração do estado
    private String name; //corresponde ao nome dado ao autômato
    private String x; //coordenada usada para localidade do estado em um plano
    private String y; //coordenada usada para localidade do estado em um plano
    boolean estadoFinal;
    
    public Estados() {
       estadoFinal = false;
   }

   public Estados(String id, String name, String x, String y) {
       this.id = id;
       this.name = name;
       this.x = x;
       this.y = y;
       estadoFinal = false;
   }
   
   public String getId() {
       return id;
   }

   public void setId(String id) {
       this.id = id;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public String getX() {
       return x;
   }

   public void setX(String x) {
       this.x = x;
   }

   public String getY() {
       return y;
   }

   public void setY(String y) {
       this.y = y;
   }
}
