package com.trabalhodetc.lucas_marley_walter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainWindow extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindowControllerInterface.fxml"));
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(true);
        stage.setTitle("JFlap volume 2");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/logoIcon.png")));
        ((MainWindowController)loader.getController()).init(stage);
        stage.show();
    }

    public void run(){
        launch();
    }
    
}
