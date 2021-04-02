package com.umiskky.viewmodel.main;

import com.umiskky.model.DateModel;
import com.umiskky.model.dto.NetworkCardDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
public class MainViewModel {

    private  DateModel dateModel;

    private StringProperty macAddress;
    private StringProperty ipAddress;
    private StringProperty netmask;

    private HashMap<String, NetworkCardDto> networkCardDtoHashMap;
    private ArrayList<String> networkCardName;

    @Setter
    private String networkCardSelected;

    public MainViewModel(DateModel dateModel) {
        this.dateModel = dateModel;
        propertiesInit();
        networkCardsSelectorInit();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to init the binding-properties
     */
    public void propertiesInit(){
        macAddress = new SimpleStringProperty();
        ipAddress = new SimpleStringProperty();
        netmask = new SimpleStringProperty();
    }
    /**
     * @author Umiskky
     * @apiNote this method is used to get NetworkCards Info from data model
     */
    public void networkCardsSelectorInit(){
        networkCardName = new ArrayList<>();
        networkCardDtoHashMap = new HashMap<>();
        try {
            networkCardDtoHashMap = dateModel.getAllNetworkCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (NetworkCardDto networkCardDto : networkCardDtoHashMap.values()){
            networkCardName.add(networkCardDto.getName());
        }
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to update view of NetworkCards info
     */
    public void updateNetworkCardsInfo(){
        if(networkCardName.contains(networkCardSelected)){
            NetworkCardDto networkCardDto = networkCardDtoHashMap.get(networkCardSelected);
            macAddress.setValue(networkCardDto.getLinkLayerAddr());
            ipAddress.setValue(networkCardDto.getAddress());
            netmask.setValue(networkCardDto.getNetmask());
        }else{
            macAddress.setValue("");
            ipAddress.setValue("");
            netmask.setValue("");
        }
    }
}
