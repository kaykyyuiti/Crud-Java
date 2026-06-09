package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        Main.class.getResource("cadastroLutador.fxml")
                );

        Scene scene = new Scene(loader.load(), 850, 650);

        stage.setTitle("Cadastro de Campeões Brasileiros");
        stage.setScene(scene);
        stage.setResizable(false); // impede redimensionamento da janela
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}