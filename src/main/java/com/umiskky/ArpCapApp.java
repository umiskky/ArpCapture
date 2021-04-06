package com.umiskky;

import com.umiskky.model.ModelFactory;
import com.umiskky.view.ViewHandler;
import com.umiskky.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 * @author UmiSkky
 */
public class ArpCapApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.start();
    }

}