package com.trabalhodetc.lucas_marley_walter;


import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFileChooser;


import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import io.reactivex.Flowable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class ChatGpt {

    @FXML
    Button sendButton;

    @FXML
    TextArea askText;



    private static final String apiKey = "";

    CompletableFuture<String> futureResult = new CompletableFuture<>();

    private String path;

    String response =  "";

    StringBuilder buffer = new StringBuilder();

    public void setPath(String path){
        this.path = path;
    }

    public void request(String request) {

        OpenAiService service = new OpenAiService(apiKey);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(
                ChatMessageRole.SYSTEM.value(), "o usuario vai descrever um automato e voce vai cria-lo e escrever um arquivo jff deve ser criado com jflap 6.4, não dê mais nenhuma informação adicional apenas o arquivo, sem ``` no comeco nem no final");
        final ChatMessage secondMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),"exemplo: crie uma afn com alfabeto 0 e 1 que sempre termina em 1. a resposta deve ser:<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"q0\">&#13;<x>92.0</x>&#13;<y>200.0</y>&#13;<initial/>&#13;</state>&#13;<state id=\"1\" name=\"q1\">&#13;<x>234.0</x>&#13;<y>194.0</y>&#13;<final/>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>0</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>1</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>1</read>&#13;</transition>&#13;</automaton>&#13;</structure> sem os tres tracinhos, e sem mensagens extras");
        final ChatMessage userMessage = new ChatMessage("user", request);

        messages.add(systemMessage);
        messages.add(secondMessage);
        messages.add(userMessage);
        
        

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().model("gpt-3.5-turbo")
            .messages(messages)
            .n(1)
            .maxTokens(1000)
            .logitBias(new HashMap<>())
            .build();

        Flowable<ChatCompletionChunk> flowableResult = service.streamChatCompletion(chatCompletionRequest)
            .doOnError(Throwable::printStackTrace);

        flowableResult.subscribe(chunk -> {
            chunk.getChoices().forEach(choice -> {
                String result = choice.getMessage().getContent();
                if (result != null) {   
                    buffer.append(result);
                    System.out.print(result);                    
                }
            });
        }, Throwable::printStackTrace, () -> {
            String finalResult = buffer.toString();
            futureResult.complete(finalResult);
        });


        

        service.shutdownExecutor();
            
            
        
    }

    public void sendMessage(){
        String request = askText.getText();
        request(request);

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Salvar em:");
        chooser.showSaveDialog(chooser);
        path = chooser.getSelectedFile().getAbsolutePath();
        saveFile();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFlap volume 2");
        alert.setContentText("Your jff file was saved.");
        alert.setHeaderText("Your automaton is ready, try open it!");
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
        alert.showAndWait();
        
        
    }


    public void saveFile(){

            try ( BufferedWriter w = new BufferedWriter(new FileWriter(path + ".jff"))) {
                w.write(futureResult.get());
            } catch (Exception e) {
                System.out.println("massa");
            }
           

    }

}
