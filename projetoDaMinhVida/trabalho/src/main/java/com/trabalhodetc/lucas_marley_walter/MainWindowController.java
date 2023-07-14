package com.trabalhodetc.lucas_marley_walter;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML
    private Pane generalArea;
    @FXML
    private ImageView closeButton, miniButton, maxButton;
    @FXML
    private Button open;
    @FXML
    private Button gptButton;
    @FXML
    private Button unity;
    @FXML
    private Button concatenation;
    @FXML
    private Button complement;
    @FXML
    private Button star;
    @FXML
    private Button intersect;
    @FXML
    private Button convertAfnToAfd;
    @FXML
    private Button minimization;
    @FXML
    private BorderPane borderPane;

    boolean[] bools;

    double x, y;

    public void init(Stage stage) {

        bools = new boolean[9];
        for (int i = 0; i < bools.length; i++) {
            bools[i] = true;
        }

        generalArea.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        
        generalArea.setOnMouseDragged(mouseEvent -> {
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
        });
        

        closeButton.setOnMouseClicked(mouseEvent -> stage.close());
        miniButton.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

        maxButton.setOnMouseClicked(mouseEvent -> {
            if (stage.isMaximized()) {
                stage.setMaximized(false);
            } else {
                stage.setMaximized(true);
            }
        });

        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAHomeInterface.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hoveropen() {
        open.setStyle("-fx-background-color: #D9A900;");// i dont know just trying
    }

    public void deshoveropen() {
        if (bools[0])
            open.setStyle("-fx-background-color: #272727;");
    }

    public void hoverUnity() {
        unity.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverUnity() {
        if (bools[1])
            unity.setStyle("-fx-background-color: #272727;");
    }

    public void hoverIntersect() {
        intersect.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverIntersect() {
        if (bools[2])
            intersect.setStyle("-fx-background-color: #272727;");
    }

    public void hoverConcatenation() {
        concatenation.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverConcatenation() {
        if (bools[3])
            concatenation.setStyle("-fx-background-color: #272727;");
    }

    public void hoverComplement() {
        complement.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverComplement() {
        if (bools[4])
            complement.setStyle("-fx-background-color: #272727;");
    }

    public void hoverAfnToAfd() {
        convertAfnToAfd.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverAfnToAfd() {
        if (bools[5])
            convertAfnToAfd.setStyle("-fx-background-color: #272727;");
    }

    public void hoverMinimization() {
        minimization.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverMinimization() {
        if (bools[6])
            minimization.setStyle("-fx-background-color: #272727;");
    }

    public void hoverGpt() {
        gptButton.setStyle("-fx-background-color: #D9A900;");// i dont know just trying
    }

    public void deshoverGpt() {
        if (bools[7])
            gptButton.setStyle("-fx-background-color: #272727;");
    }

    public void hoverStar() {
        star.setStyle("-fx-background-color: #717171;");// i dont know just trying
    }

    public void deshoverStar() {
        if (bools[8])
        star.setStyle("-fx-background-color: #272727;");
    }

    public void menuBar() {
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAHomeInterface.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desHoverGeral(int j) {
        for (int i = 0; i < bools.length; i++) {
            if (i != j) {
                bools[i] = true;
            } else {
                bools[i] = false;
            }
            if (j != 0) {
                open.setStyle("-fx-background-color: #272727;");
            }
            if (j != 1)
                unity.setStyle("-fx-background-color: #272727;");
            if (j != 2)
                intersect.setStyle("-fx-background-color: #272727;");
            if (j != 3)
                concatenation.setStyle("-fx-background-color: #272727;");
            if (j != 4)
                complement.setStyle("-fx-background-color: #272727;");
            if (j != 5)
                convertAfnToAfd.setStyle("-fx-background-color: #272727;");
            if (j != 6)
                minimization.setStyle("-fx-background-color: #272727;");
            if (j != 7)
                gptButton.setStyle("-fx-background-color: #272727;");
            if (j != 8)
                star.setStyle("-fx-background-color: #272727;");

        }
    }

    public void abrir() {
        desHoverGeral(0);
        OpenWindowController vista = new OpenWindowController();
        vista.abrir(borderPane);
    }

    @FXML
    public void criarGpt() {
        desHoverGeral(7);
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAChatGpt.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unir() {

        desHoverGeral(1);

        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationUnity.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void intersectar() {

        desHoverGeral(2);

        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationIntersection.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void concatenar() {
        desHoverGeral(3);
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationConcatenation.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void complementar() {
        desHoverGeral(4);
        complement.setStyle("-fx-background-color: #717171;");
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationComplement.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void estrela() {
        desHoverGeral(8);
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationStar.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void converter() {
        desHoverGeral(5);

        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationConvert.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void minimizar() {
        desHoverGeral(6);
        minimization.setStyle("-fx-background-color: #717171;");
        AnchorPane workArea;
        try {
            workArea = FXMLLoader.load(getClass().getResource("WAOperationMinimization.fxml"));
            borderPane.setCenter(workArea);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sair() {
        System.out.println("SAINDO");
    }

}
