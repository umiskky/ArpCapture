package com.umiskky.viewmodel.main;

import com.umiskky.model.DateModel;
import com.umiskky.model.dto.NetworkCardDto;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MainViewModel {
    private  DateModel dateModel;

    private ArrayList<NetworkCardDto> networkCardDtoArrayList;

    private ArrayList<String> networkCardName;
    private StringProperty networkCardSelected;

    public MainViewModel(DateModel dateModel) {
        this.dateModel = dateModel;
//        networkCardSelected.set("");
        networkCardsSelectorInit();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to get NetworkCards Info from data model
     */
    public void networkCardsSelectorInit(){
        networkCardName = new ArrayList<>();
        try {
            networkCardDtoArrayList = dateModel.getAllNetworkCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (NetworkCardDto networkCardDto : networkCardDtoArrayList){
            networkCardName.add(networkCardDto.getName());
        }
    }
}
