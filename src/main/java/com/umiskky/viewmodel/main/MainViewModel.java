package com.umiskky.viewmodel.main;

import com.umiskky.model.DateModel;
import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.tools.AddressUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.ArpPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

@Getter
public class MainViewModel {

    private  DateModel dateModel;

    private StringProperty macAddress;
    private StringProperty ipAddress;
    private StringProperty netmask;

    private StringProperty ipInput;
    private StringProperty resolvedAddr;

    private HashMap<String, NetworkCardDto> networkCardDtoHashMap;
    private ArrayList<String> networkCardName;

    @Setter
    private String networkCardSelected;

    public MainViewModel(DateModel dateModel) {
        this.dateModel = dateModel;
        vmPropertiesInit();
        vmNetworkCardsSelectorInit();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to init the binding-properties
     */
    public void vmPropertiesInit(){
        macAddress = new SimpleStringProperty();
        ipAddress = new SimpleStringProperty();
        netmask = new SimpleStringProperty();
        ipInput = new SimpleStringProperty();
        resolvedAddr = new SimpleStringProperty();
    }
    /**
     * @author Umiskky
     * @apiNote this method is used to get NetworkCards Info from data model
     */
    public void vmNetworkCardsSelectorInit(){
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
    public void vmUpdateNetworkCardsInfo(){
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

    /**
     * @author Umiskky
     * @apiNote this method is used to send an arp request
     */
    public void vmSendArpRequest(){
        if(networkCardName.contains(networkCardSelected)){
            String ipAddress = ipInput.getValue();
            if(AddressUtils.isValidIPAddress(ipAddress)) {
                dateModel.sendArpRequest(networkCardDtoHashMap.get(networkCardSelected), ipAddress);
            }else {
                System.out.println("InValid IP Address!!!");
            }
        }else{
            System.out.println("Please choice a valid network card!!!");
        }

    }

    /**
     * @author UmiSkky
     * @apiNote this method is used to capture an arp reply package
     */
    public void vmCaptureArpReply(){
        if(networkCardName.contains(networkCardSelected)){
            String ipAddress = ipInput.getValue();
            if(AddressUtils.isValidIPAddress(ipAddress)) {
                dateModel.catchArpReply(networkCardDtoHashMap.get(networkCardSelected), ipAddress);
            }else {
                System.out.println("InValid IP Address!!!");
            }
        }else{
            System.out.println("Please choice a valid network card!!!");
        }
    }
}
