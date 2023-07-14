 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.trabalhodetc.AutomatoG3;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Automato{ //automaton
    private ArrayList<Estados> state = new ArrayList<Estados>();
    private ArrayList<Transicao> transition = new ArrayList<Transicao>();
    private File localArquivo;
    public String estadoInicial;
    //public String estadoFinal; // Coloquei os estados inial e final na classe Automato por conta de sua facilidade na hora de consultar tais estados;

    
    public Automato() {

    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    
    
    public File getLocalArquivo() {
        return localArquivo;
    }
    

    public void setLocalArquivo(File localArquivo) {
        this.localArquivo = localArquivo;
        carregarDados();
    }
    public void setLocalArquivo(String ur) {
        this.localArquivo = new File(ur);
        carregarDados();
    }

    public ArrayList<Estados> getState() {
        return state;
    }

    

    public ArrayList<Transicao> getTransition() {
        return transition;
    }

    
    
    private void carregarDados(){     // criei classe private com a logica da classe de transition e estados 
        pegarEstados(getLocalArquivo());
        pegarTransicoes(getLocalArquivo());
    }
    private void pegarEstados(File localArquivo){
        
        
        try {
             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
             DocumentBuilder builder = factory.newDocumentBuilder();
             Document doc = builder.parse(localArquivo);
             
             NodeList listaEstados = doc.getElementsByTagName("state");
             for(int i=0; i<listaEstados.getLength(); i++){
                 Node noState = listaEstados.item(i);
                 Estados e1 = new Estados();
                 if(noState.getNodeType() == Node.ELEMENT_NODE){
                     Element elemetoState = (Element) noState;
                     e1.setId(elemetoState.getAttribute("id"));
                     e1.setName(elemetoState.getAttribute("name"));
                     
                     NodeList listaFilhoState = elemetoState.getChildNodes();
                     List<String> list = Arrays.asList("x", "y", "initial", "final");
                     for(int j=0; j<listaFilhoState.getLength(); j++){
                         Node noFilhoState = listaFilhoState.item(j);
                         if(noFilhoState.getNodeType() == Node.ELEMENT_NODE){
                             Element elementoFilhoState = (Element) noFilhoState;
                             
                             
                             switch (list.indexOf(elementoFilhoState.getTagName())) {
                                 case 0 -> e1.setX(elementoFilhoState.getTextContent());
                                 case 1 -> e1.setY(elementoFilhoState.getTextContent());
                                 case 2 -> this.estadoInicial = e1.getId();
                                 case 3 -> e1.estadoFinal = true;
                             }
                         }
                 }
            }
            Estados v1 = new Estados(e1.getId(), e1.getName(), e1.getX(), e1.getY());
            v1.estadoFinal = e1.estadoFinal;
            state.add(v1);
         }
         } catch (ParserConfigurationException ex) {
             Logger.getLogger(Estados.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SAXException ex) {
             Logger.getLogger(Estados.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(Estados.class.getName()).log(Level.SEVERE, null, ex);
         }
    } // logica principal de leitura ;
    private void pegarTransicoes(File localAquivo){
        Transicao t1 = new Transicao();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(localAquivo);
            NodeList lisTransicao = doc.getElementsByTagName("transition");
            for(int i=0; i<lisTransicao.getLength();i++){
              Node  noTransition = lisTransicao.item(i);
              if(noTransition.getNodeType() == Node.ELEMENT_NODE){
                  
                  Element elementoTrasition = (Element) noTransition;
                  NodeList listFilhoTransition = elementoTrasition.getChildNodes();
                  
                  for(int j=0; j<listFilhoTransition.getLength(); j++){
                      Node noFIlhoTransition = listFilhoTransition.item(j);
                      if(noFIlhoTransition.getNodeType() == Node.ELEMENT_NODE){
                          Element elementoFilho = (Element) noFIlhoTransition;
                          switch (elementoFilho.getTagName()) {
                              case "from":
                                  t1.setFrom(elementoFilho.getTextContent());
                                  break;
                              case "to":
                                  t1.setTo(elementoFilho.getTextContent());
                                  break;
                              case "read":
                                  t1.setRead(elementoFilho.getTextContent());
                                  break;
                           }
                        }
                  }
              }
            if(t1.getRead().equalsIgnoreCase("")){
                Transicao t2 = new Transicao(t1.getFrom(), t1.getTo(), "");
                this.transition.add(t2);
            }else{
                Transicao t2 = new Transicao(t1.getFrom(), t1.getTo(), t1.getRead());
            this.transition.add(t2);
            }
            
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Transicao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Transicao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Transicao.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
}// logica principal de leitura;
    
    
    private void imprimirEstados(){
        System.out.println("\nINICIO_IMPRESSAO_ESTADOS\n");
        
        for(int i=0; i<this.state.size(); i++){
            System.out.println("ID: "+this.state.get(i).getId());
            System.out.println("FINAL: " + state.get(i).estadoFinal);
            System.out.println("Name: "+this.state.get(i).getName());
            System.out.println("Localizacao X:"+this.state.get(i).getX()+" Y: "+this.state.get(i).getY());
            System.out.println("----------------------------------------------------------------------");
            
            if(i == this.state.size()-1){
                System.out.println("ESTADO_INICIAL: "+this.estadoInicial);
               // System.out.println("ESTADO_FINAL: "+this.estadoFinal);
            }
  
        }
        System.out.println("\nFIM_LISTA_ESTADOS \n"+this.state.size());
        System.out.println("\n___________________________________________________________________________\n");
    } // apenas para fins de teste em relação a leitura do automato;
    private void imprimirTransicao(){
    System.out.println("\nINICIO_LISTA_TRANSICAO\n");
    for(int i=0; i<this.transition.size(); i++){ 
        System.out.println("From: "+transition.get(i).getFrom());
        System.out.println("To: "+transition.get(i).getTo());
        System.out.println("Read: "+transition.get(i).getRead());
        System.out.println("-----------------------------------");
    }
    System.out.println("\nFIM_LISTA_TRANSICAO \n"+transition.size());
    System.out.println("\n___________________________________________________________________________\n");
} // apenas para fins de teste em relação a leitura do automato;
    
    public void imprimirAuto(){
        imprimirEstados();
        imprimirTransicao();
    } // apenas para fins de teste em relação a leitura do automato;
}
