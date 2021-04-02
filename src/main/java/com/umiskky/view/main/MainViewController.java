package com.umiskky.view.main;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.viewmodel.main.MainViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;


public class MainViewController {

    @FXML
    private ChoiceBox<String> networkCardSelector;

    private final static String EMPTYINFO = "选择工作网卡";

    private StringProperty networkCardSelected;
    private ArrayList<String> networkCards;

    private MainViewModel mainViewModel;

    public void init(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        bindInit();
        networkCardSelectorInit();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to init property bindings
     */
    public void bindInit(){
//        networkCardSelected.bindBidirectional(mainViewModel.getNetworkCardSelected());
    }

    /**
     * @author UmiSkky
     * @apiNote this method is used to init the choice box
     */
    public void networkCardSelectorInit(){
        networkCards = mainViewModel.getNetworkCardName();
        networkCardSelector.getItems().add(EMPTYINFO);
        networkCardSelector.getItems().addAll(networkCards);
        networkCardSelector.setValue(EMPTYINFO);
    }

}
