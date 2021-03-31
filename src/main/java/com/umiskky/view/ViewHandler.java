package com.umiskky.view;

import com.umiskky.view.main.MainViewController;
import com.umiskky.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class ViewHandler {

    private Stage stage;
    private ViewModelFactory viewModelFactory;

    public ViewHandler(Stage stage, ViewModelFactory viewModelFactory){
        this.stage = stage;
        this.viewModelFactory = viewModelFactory;
    }

    public void start() throws Exception{
        openView("Main");
    }

    public void openView(String viewToOpen) throws IOException{
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        loader.setLocation(getClass().getResource(viewToOpen.toLowerCase(Locale.ROOT) + "/" + viewToOpen + "View.fxml"));
        root = loader.load();
        switch (viewToOpen){
            case "MainView":
                MainViewController mainView = loader.getController();
                mainView.init(viewModelFactory.getMainViewModel());
                stage.setTitle("ArpCapture");
                break;
            case "Primary":
                break;
            default:
                break;
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
