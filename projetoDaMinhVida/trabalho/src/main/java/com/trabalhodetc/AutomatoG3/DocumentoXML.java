/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.trabalhodetc.AutomatoG3;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

 public class DocumentoXML {
    int contStateauto1 = 0;   

    public DocumentoXML() {
    }
    public void concatenacao(Automato auto1,Automato auto2, String patch){
        try {
            
            DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element structure = doc.createElement("structure");                        // cria a tag do cabeçalho do arquivo xml;
            doc.appendChild(structure);

            Element tipo = doc.createElement("type");                                  // cria a tag type;
            tipo.appendChild(doc.createTextNode("fa"));                                   // preenche a tag type com "fa" (finite automaton);
            structure.appendChild(tipo);

            Element automaton = doc.createElement("automaton");                        // cria o nó principal com as especificações do autômato;
            structure.appendChild(automaton);
      
            
            for(int i=0; i<auto1.getState().size(); i++){                                      // for para percorrer o ArrayList armazenado na classe autômato que foi lido do diretório;
                Element state = doc.createElement("state");                            // cria o nó filho state;
                Attr id = doc.createAttribute("id");                                      // cria o atributo do no filho state que enumera o estado;
                Attr name = doc.createAttribute("name");                                  // cria o atributo do no filho state que nomeia o estado;
                id.setValue(auto1.getState().get(i).getId());                                  // define valor para o atributo id;
                name.setValue(auto1.getState().get(i).getName());                              // define valor para o atributo name;
                state.setAttributeNode(id);                                                    // permite setar o valor do atributo id;
                state.setAttributeNode(name);                                                  // permite setar o valor do atributo name;
                
                Element x = doc.createElement("x");                                    // cria o elemento x (elementos responsáveis pela coordenada do autômato em um plano);
                Element y = doc.createElement("y");                                    // cria o elemento y;
                
                x.appendChild(doc.createTextNode(auto1.getState().get(i).getX()));             // atribui valor ao elemento x através de valores lidos de autômatos anteriores;
                y.appendChild(doc.createTextNode(auto1.getState().get(i).getY()));             // atribui valor ao elemento y através de valores lidos de autômatos anteriores;
                
                if(auto1.getState().get(i).getId().equalsIgnoreCase(auto1.getEstadoInicial())){   // enquanto o for é executado verifica se o id é igual ao do Estado inicial guardado na classe Autômato;
                  Element inicial = doc.createElement("initial");                         // se for encontrado cria o elemento final no nó estado;
                  state.appendChild(inicial);
                }
                automaton.appendChild(state);                                                     // aqui marca como fim do nó state;
                contStateauto1 = i;
            }

            for(int k=0; k<auto2.getState().size(); k++){
                Element state = doc.createElement("state");
                Attr id = doc.createAttribute("id");
                Attr name = doc.createAttribute("name");
                id.setValue("1"+auto2.getState().get(k).getId());
                name.setValue("q"+auto2.getState().get(k).getName());
                state.setAttributeNode(id);
                state.setAttributeNode(name);
                
                Element x = doc.createElement("x");
                Element y = doc.createElement("y");
                
                x.appendChild(doc.createTextNode(auto2.getState().get(k).getX()));
                y.appendChild(doc.createTextNode(auto2.getState().get(k).getY()));


                if(auto2.getState().get(k).estadoFinal){       // como esse é o autômato 2 que ficará no final ele pesquisa o id do estado final do automato2 para definir como final da concatenação;                    
                    Element ffinal = doc.createElement("final");
                    state.appendChild(ffinal);
                }
                automaton.appendChild(state);
            }
            for(Estados e : auto1.getState()){
                Element transition = doc.createElement("transition");                                      // cria o Nó filho transição logo depois de juntar os estados dos dois autômatos;
                Element from = doc.createElement("from");                                                  // cria o elemento From para a transição 
                Element to = doc.createElement("to");                                                      // cria o elemento To para a transição
                Element read = doc.createElement("read");           
                
                if(e.estadoFinal){
                    from.appendChild(doc.createTextNode(e.getId()));            // se não simplesmente monta o estado de acordo com os dados do arrayList;
                    transition.appendChild(from);
                    
                    to.appendChild(doc.createTextNode("1"+auto2.getEstadoInicial()));
                    transition.appendChild(to);

                    read.appendChild(doc.createTextNode(""));
                    transition.appendChild(read);
                    automaton.appendChild(transition);
                }
            }
            for(int j = 0; j < auto1.getTransition().size(); j++) {
                Element transition = doc.createElement("transition");                                      // cria o Nó filho transição logo depois de juntar os estados dos dois autômatos;
                Element from = doc.createElement("from");                                                  // cria o elemento From para a transição 
                Element to = doc.createElement("to");                                                      // cria o elemento To para a transição
                Element read = doc.createElement("read");           
                
                // cria o elemento Read para a transicao
                
                
                    from.appendChild(doc.createTextNode(auto1.getTransition().get(j).getFrom()));
                    transition.appendChild(from);
                    to.appendChild(doc.createTextNode(auto1.getTransition().get(j).getTo()));
                    transition.appendChild(to);

                   
                    read.appendChild(doc.createTextNode(auto1.getTransition().get(j).getRead()));
                    transition.appendChild(read);
                    automaton.appendChild(transition);                   
            }

            for(int h=0; h<auto2.getTransition().size(); h++){
                Element transition = doc.createElement("transition");
                Element from = doc.createElement("from");
                from.appendChild(doc.createTextNode("1"+auto2.getTransition().get(h).getFrom()));
                transition.appendChild(from);
                Element to = doc.createElement("to");
                to.appendChild(doc.createTextNode("1"+auto2.getTransition().get(h).getTo()));
                transition.appendChild(to);
                Element read = doc.createElement("read");
                read.appendChild(doc.createTextNode(auto2.getTransition().get(h).getRead()));
                transition.appendChild(read);
                automaton.appendChild(transition);
            }
            TransformerFactory tf = TransformerFactory.newInstance();                           // a partir daqui é instanciado as classes que usaremos para criar o arquivo.jff
            Transformer t = tf.newTransformer();
            DOMSource docFonte = new DOMSource(doc);
            StreamResult docFinal = new StreamResult(new File(patch));
            t.transform(docFonte, docFinal);
            System.out.println("Documento criado com sucesso!");
            System.out.println("Diretorio autômato 1: "+auto1.getLocalArquivo());
            System.out.println("Diretorio do autômato 2: "+auto2.getLocalArquivo());
            System.out.println("Diretório do documento gerado: " + patch);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}