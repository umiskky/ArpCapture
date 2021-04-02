package com.umiskky.view.main;

import com.umiskky.viewmodel.main.MainViewModel;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.ArrayList;


public class MainViewController {

    @FXML
    private ChoiceBox<String> networkCardSelector;

    @FXML
    private Label macAddr;

    @FXML
    private Label ipAddr;

    @FXML
    private Label netmask;

    private final static String EMPTYINFO = "选择工作网卡";
    private ArrayList<String> networkCards;

    private MainViewModel mainViewModel;

    public void init(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        bindInit();
        networkCardSelectorInit();
        addListener();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to init property bindings
     */
    public void bindInit(){
        macAddr.textProperty().bind(mainViewModel.getMacAddress());
        ipAddr.textProperty().bind(mainViewModel.getIpAddress());
        netmask.textProperty().bind(mainViewModel.getNetmask());
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

    /**
     * @author UmiSkky
     * @apiNote add listener for choice box
     */
    public void addListener(){
        networkCardSelector.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String oldVal, String newVal) -> {
                mainViewModel.setNetworkCardSelected(newVal);
                mainViewModel.updateNetworkCardsInfo();
            }
        );
    }
}
