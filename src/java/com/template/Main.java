package com.template;

import com.template.controller.MainController;
import com.template.validator.ILutadorValidator;
import com.template.validator.LutadorValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ILutadorValidator lutadorValidator = new LutadorValidator();

        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("cadastroLutador.fxml")
        );

        fxmlLoader.setControllerFactory(controllerClass -> {
            if(controllerClass == MainController.class){
                return new MainController(lutadorValidator);
            }
            try{
                return controllerClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(fxmlLoader.load(), 850, 650);

        stage.setTitle("Cadastro de Campeões Brasileiros");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}