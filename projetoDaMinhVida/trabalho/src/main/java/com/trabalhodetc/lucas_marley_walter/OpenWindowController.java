package com.trabalhodetc.lucas_marley_walter;


import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

public class OpenWindowController {
    

    @FXML
    private Button buttonOpenJflap;

    @FXML
    private Label labelOpen;


    private Vector<Circle> circulos = new Vector<Circle>();

    private Vector<Line> setas = new Vector<Line>();
    private Vector<Polygon> arrows = new Vector<Polygon>();
    private Vector<Label> ids = new Vector<Label>();
    private Vector<Label> praOndeVai = new Vector<Label>();
    private Vector<CubicCurve> praEleMesmo = new Vector<CubicCurve>();
    private Polygon inicial = new Polygon(0.0, 0.0, 30.0, -30.0, 30.0, 30.0);
    private Vector<Circle> finais = new Vector<Circle>();

    private com.trabalhodetc.lucas_marley_walter.Automato automato;

    private static String path;

    public void zoom(ZoomEvent event){
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOpenInterface.fxml"));
            double a = event.getZoomFactor();
            workArea.setScaleX(workArea.getScaleX() * a);
            workArea.setScaleY(workArea.getScaleY() * a);
        } catch (Exception e) {
            System.out.println("não é assim");
        }
    }
    @FXML
    public void OpenJflap() {
        
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "JFLAP.jar", path);
            Process process = pb.start();
            process.supportsNormalTermination();
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void abrir(BorderPane borderPane){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(fileChooser);
        path = fileChooser.getSelectedFile().getAbsolutePath();
        System.out.println(path);

        

        automato = Automato.loadFromJff(path);
        
        WAController.pathOpen = path;

        AnchorPane workArea;

        
        int radius = 30;
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.BLACK);
       

        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOpenInterface.fxml"));
            borderPane.setCenter(workArea);

            for (int i = 0; i < automato.getNumeroDeEstados(); i++) {

                double x = automato.getEstado(i).getPosition().x + 300;
                double y = automato.getEstado(i).getPosition().y + 200;
                Circle c = new Circle(x, y, radius);
                c.setId(automato.getEstado(i).getNome());  
                c.setStroke(Color.BLACK);
                c.setStrokeWidth(3);
                c.setFill(Color.GOLDENROD);

                circulos.add(c);

                if(automato.getEstado(i).IsFinal()){
                    Circle cFinal = new Circle(x,y,radius - radius/5);
                    cFinal.setFill(Color.TRANSPARENT);
                    cFinal.setStroke(Color.BLACK);
                    cFinal.setStrokeWidth(3);
                    finais.add(cFinal);
                }
                Label id = new Label(automato.getEstado(i).getNome());
                id.setFont(new Font(radius/1.5));

                id.setTranslateX(x - radius/2);
                id.setTranslateY(y - radius/2);
                
                id.setTextFill(Color.BLACK);
            
                ids.add(id);

            }
            int inicio = automato.getInicial();
            inicial.setFill(Color.BLACK);
            inicial.setStroke(Color.BLACK);
            inicial.setTranslateX(circulos.get(inicio).getCenterX() - radius - 30);
            inicial.setTranslateY(circulos.get(inicio).getCenterY());
            inicial.setRotate(180);

            for (int i = 0; i < automato.getNumeroDeEstados(); i++) {
                for (String a : automato.getAlfabetoAsArray()) {
                    List<Integer> transas = automato.getEstado(i).getListaDeTransicao(a);
                    if(transas == null){
                        continue;
                    }
                    for (Integer j : transas) {
                        if(j == null){
                            continue;
                        }else if(j == i){
                            double sx = circulos.get(i).getCenterX();
                            double sy = circulos.get(i).getCenterY();

                            CubicCurve c = new CubicCurve(sx, sy - radius, sx - radius * 4,sy - radius * 4,sx + radius * 4,sy-radius * 4,sx,sy-radius);
                            c.setStrokeWidth(2);
                            c.setStroke(javafx.scene.paint.Color.BLACK);
                            c.setFill(Color.TRANSPARENT);
                            c.setEffect(dropShadow);
                            

                            Polygon arrow = new Polygon();
                            arrow.getPoints().addAll(0.0, 0.0, 20.0, 10.0, 0.0, 20.0);
                            arrow.setFill(Color.BLACK);
                            arrow.setStroke(Color.BLACK);
                            arrow.setEffect(dropShadow);
                            
                            // Position the arrow at the end of the line
                            
                            arrow.setTranslateX(sx - 10);
                            arrow.setTranslateY(sy - radius - 10);
    
                            arrow.setRotate(90);
                            
    
                            Label simbol = new Label(a);
                            if(a.equals("")){
                                simbol.setText("λ");
                            }
                            simbol.setTranslateX(sx);
                            simbol.setTranslateY(sy - radius * 4.5);
                            simbol.setFont(new Font(radius));
                            simbol.setTextFill(Color.BLACK);

                            praOndeVai.add(simbol);
                            praEleMesmo.add(c);
                            arrows.add(arrow);
                            
                            continue;
                        }
                        double sX = circulos.get(i).getCenterX();
                        double sY = circulos.get(i).getCenterY();
                        double eX = circulos.get(j).getCenterX();
                        double eY = circulos.get(j).getCenterY();

                        Line seta = new Line(sX,sY,eX,eY);
                        
                        seta.setFill(Color.BLACK);
                        seta.setStroke(Color.BLACK);
                        seta.setStrokeWidth(radius/7);


                        Polygon arrow = new Polygon();
                        arrow.getPoints().addAll(0.0, 0.0, 20.0, 10.0, 0.0, 20.0);
                        arrow.setFill(Color.BLACK);
                        arrow.setStroke(Color.BLACK);
                        arrow.setEffect(dropShadow);
                        
                        // Position the arrow at the end of the line
                        double degrees = Math.toDegrees(Math.atan2(eY - sY, eX - sX));
                        
                        arrow.setTranslateX(eX - 12.5 - Math.cos(Math.toRadians(degrees))* radius);
                        arrow.setTranslateY(eY - 12.5 - Math.sin(Math.toRadians(degrees))* radius);

                        arrow.setRotate(degrees);
                        

                        Label simbol = new Label(a);
                        if(a.equals("")){
                            simbol.setText("λ");
                        }
                        simbol.setTranslateX((sX - eX)/2 + eX);
                        simbol.setTranslateY((sY - eY)/2 + eY);
                        simbol.setFont(new Font(radius));
                        simbol.setTextFill(Color.BLACK);

                        praOndeVai.add(simbol);
                        arrows.add(arrow);
                        setas.add(seta);

                        
                    }

                }

            }
            
            workArea.getChildren().addAll(setas);
            workArea.getChildren().addAll(circulos);
            workArea.getChildren().addAll(arrows); 
            workArea.getChildren().addAll(praEleMesmo);
            workArea.getChildren().addAll(ids);
            workArea.getChildren().addAll(praOndeVai);  
            workArea.getChildren().add(inicial);
            workArea.getChildren().addAll(finais);

        } catch (IOException e) {
            System.out.println("aff que vida dificil");
        }
    }
}
