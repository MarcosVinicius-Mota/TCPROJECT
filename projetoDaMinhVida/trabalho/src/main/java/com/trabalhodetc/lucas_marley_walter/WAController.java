package com.trabalhodetc.lucas_marley_walter;

import javax.swing.JFileChooser;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class WAController {

    Automato automato;
    Automato automato2;
    private static JFileChooser fileChooser = new JFileChooser();
    static public String pathOpen;

    String path;

    @FXML
    private Label label01Unity;

    @FXML
    private Label label02Unity;

    @FXML
    private Button saveUnity;

    @FXML
    private Button select01Unity;

    @FXML
    private Button select02Unity;

    @FXML
    private Button afnButton;

    @FXML
    private Button afdButton;

    public int optionUnitySelected = 1; // inicializa afn pre selecionado

    @FXML
    public void afnSelectButton() {
        optionUnitySelected = 1;
        changeColors();
    }

    @FXML
    public void afdSelectButton() {
        optionUnitySelected = 2;
        changeColors();
    };

    private void changeColors() {
        if (optionUnitySelected == 1) {
            afdButton.setStyle("-fx-background-color: #272727;");
            afnButton.setStyle("-fx-background-color: #FFC700;");
        } else if (optionUnitySelected == 2) {
            afnButton.setStyle("-fx-background-color: #272727;");
            afdButton.setStyle("-fx-background-color: #FFC700;");
        }
    }

    com.trabalhodetc.uniao_afds_afns.Automato at1;
    com.trabalhodetc.uniao_afds_afns.Automato at2;

    @FXML
    public void select01Unity() {
        JFileChooser fileChooser = new JFileChooser();
        path = getPath(fileChooser);
        at1 = new com.trabalhodetc.uniao_afds_afns.Automato();
        at1.carregaDados(path);
        label01Unity.setText(path);

    }

    @FXML
    public void select02Unity() {
        JFileChooser fileChooser = new JFileChooser();
        path = getPath(fileChooser);
        at2 = new com.trabalhodetc.uniao_afds_afns.Automato();
        at2.carregaDados(path);

        label02Unity.setText(path);
    
    }

    @FXML
    public void saveUnity() {
        com.trabalhodetc.uniao_afds_afns.Automato result = new com.trabalhodetc.uniao_afds_afns.Automato();

        boolean operacaoRealizada = false;

        Alert alertError = new Alert(Alert.AlertType.INFORMATION);
        alertError.setTitle("JFlap volume 2");
        alertError.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));

        if (optionUnitySelected == 2) {
            if (!at1.verificaSeAutomatoDeterministico(at1) && !at2.verificaSeAutomatoDeterministico(at2)) {
                alertError.setContentText("operation cannot be realized the automatons need to be deterministics.");
                alertError.setHeaderText("try selecting the automatons again.");
                alertError.showAndWait();
                at1 = null;
                at2 = null;
                return;
            } else {

                result = result.uniaoAFD(at1, at2);
                operacaoRealizada = true;
            }
        } else {
            result = result.uniaoAFN(at1, at2);
            operacaoRealizada = true;
        }

        fileChooser.showSaveDialog(fileChooser);

        String path = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            com.trabalhodetc.uniao_afds_afns.AutomatoWriter.escreveAutomato(result, path);
        } catch (Exception e) {
            System.out.println("falha em salvar arquivo");
        }

        if (operacaoRealizada) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("JFlap volume 2");
            alert.setContentText("Your automaton has already been saved.");
            alert.setHeaderText("Completed union!");
            alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
            alert.showAndWait();
        }
    }

    @FXML
    private Label label01Intersection;

    @FXML
    private Label label02Intersection;

    @FXML
    private Button saveIntersection;

    @FXML
    private Button select01Intersection;

    @FXML
    private Button select02Intersection;

    @FXML
    void saveIntersection() {

        Automato.saveInJff(getSavePath(fileChooser), Intersecionador.Intersecionar(automato, automato2));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFlap volume 2");
        alert.setContentText("Your automaton has already been saved.");
        alert.setHeaderText("Completed intersection!");
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
        alert.showAndWait();
    }

    String path01;

    @FXML
    public void select01Intersection() {
        path = getPath(fileChooser);
        automato = Automato.loadFromJff(path);
        label01Intersection.setText(path);
    }

    @FXML
    public void select02Intersection() {
        path = getPath(fileChooser);
        automato2 = Automato.loadFromJff(path);
        label02Intersection.setText(path);
    }

    @FXML
    private Label label01Concatenation;

    @FXML
    private Label label02Concatenation;

    @FXML
    private Button saveConcatenation;

    @FXML
    private Button select01Concatenation;

    @FXML
    private Button select02Concatenation;

    com.trabalhodetc.AutomatoG3.Automato auto1;
    com.trabalhodetc.AutomatoG3.Automato auto2;

    @FXML
    public void saveConcatenation() {

        com.trabalhodetc.AutomatoG3.DocumentoXML doc = new com.trabalhodetc.AutomatoG3.DocumentoXML(); // objeto doc
                                                                                                       // criado da
                                                                                                       // classe
                                                                                                       // "DocumentoXML"

        doc.concatenacao(auto1, auto2, getSavePath(fileChooser));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFlap volume 2");
        alert.setContentText("Your automaton has already been saved.");
        alert.setHeaderText("Completed concatenation!");
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
        alert.showAndWait();
    }

    @FXML
    public void select01Concatenation() {
        path = getPath(fileChooser);
        auto1 = new com.trabalhodetc.AutomatoG3.Automato();
        auto1.setLocalArquivo(path);
        label01Concatenation.setText(path);
    }

    @FXML
    public void select02Concatenation() {

        path = getPath(fileChooser);
        auto2 = new com.trabalhodetc.AutomatoG3.Automato();
        auto2.setLocalArquivo(path);
        label02Concatenation.setText(path);
    }

    @FXML
    private Label labelComplement;

    @FXML
    private Button saveComplement;

    @FXML
    private Button selectComplement;

    @FXML
    void saveComplement() {

        try {

            Complemento.complemento(automato);
            AutomatoWriter.saveInJff(getSavePath(fileChooser), automato);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("JFlap volume 2");
            alert.setContentText("Your automaton has already been saved.");
            alert.setHeaderText("Completed complement!");
            alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
            alert.showAndWait();

        } catch (NullPointerException asd) {
        }
    }

    @FXML
    void selectionComplement() {

        path = getPath(fileChooser);
        automato = Automato.loadFromJff(path);
        labelComplement.setText(path);
    }

    @FXML
    private Label labelStar;

    @FXML
    private Button saveStar;

    @FXML
    private Button selectStar;

    @FXML
    void saveStar() {

        try {

            Estrela.estrela(automato);
            AutomatoWriter.saveInJff(getSavePath(fileChooser), automato);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("JFlap volume 2");
            alert.setContentText("Your automaton has already been saved.");
            alert.setHeaderText("Completed star!");
            alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
            alert.showAndWait();

        } catch (NullPointerException asd) {
        }
    }

    @FXML
    void selectionStar() {
        path = getPath(fileChooser);
        automato = Automato.loadFromJff(path);
        labelStar.setText(path);
    }

    @FXML
    private Label labelConvert;

    @FXML
    private Button saveConvert;

    @FXML
    private Button selectConvert;

    @FXML
    public void saveConvert() {

        System.out.println("AAA");
        Automato afd = Conversor.converter(automato);
        System.out.println("AAA");
        Automato.saveInJff(getSavePath(fileChooser), afd);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFlap volume 2");
        alert.setContentText("Your automaton has already been saved.");
        alert.setHeaderText("Completed conversion!");
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
        alert.showAndWait();

    }

    @FXML
    public void selectConvert() {
        path = getPath(fileChooser);
        automato = Automato.loadFromJff(path);
        labelConvert.setText(path);
    }

    @FXML
    private Label labelMinimization;

    @FXML
    private Button saveMinimization;

    @FXML
    private Button selectMinimization;

    @FXML
    public void saveMinimization() {
        Minimizador.minimizar(automato);
        Automato.saveInJff(getSavePath(fileChooser), automato);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("JFlap volume 2");
        alert.setContentText("Your automaton has already been saved.");
        alert.setHeaderText("Completed minimization!");
        alert.setGraphic(new ImageView(this.getClass().getResource("images/logoIcon.png").toString()));
        alert.showAndWait();
    }

    @FXML
    public void selectMinimization() {
        path = getPath(fileChooser);
        automato = Automato.loadFromJff(path);
        labelMinimization.setText(path);
    }

    static String getPath(JFileChooser fileChooser) {

        fileChooser.showOpenDialog(fileChooser);

        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    static String getSavePath(JFileChooser fileChooser) {
        if (fileChooser.showSaveDialog(fileChooser) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return fileChooser.getSelectedFile().getAbsolutePath();
    }
}