package course.java.sdm.gui;

import course.java.sdm.gui.MainMenu.*;
import course.java.sdm.gui.common.ConsoleGUIConstants;
import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.awt.*;
import java.net.URL;
import java.util.Scanner;

public class RunGUi extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        //System.out.println(MainMenuController.class);
        //System.out.println(getClass());

        URL mainFXML = MainMenuController.class.getResource("MainMenu.fxml");
        loader.setLocation(mainFXML);
        Parent root = loader.load();

        // wire up controller
        MainMenuController mainMenuController = loader.getController();
        //BusinessLogic businessLogic = new BusinessLogic(histogramController);
        mainMenuController.setPrimaryStage(primaryStage);
        //histogramController.setBusinessLogic(businessLogic);

        // set stage
        primaryStage.setTitle("Super Duper Market by Alon Ben Harosh");
        Scene scene = new Scene(root, 1200, 730);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
