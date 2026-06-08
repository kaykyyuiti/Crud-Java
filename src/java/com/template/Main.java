package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        // Força a leitura do arquivo relativo à classe Main na mesma pasta
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("cadastroLutador.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        stage.setTitle("Cadastro de Lutadores");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}