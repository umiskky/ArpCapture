package com.umiskky.viewmodel.main;

import com.umiskky.model.DateModel;
import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.tools.AddressUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.ArpPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public class MainViewModel {

    private DateModel dateModel;

    private StringProperty macAddress;
    private StringProperty ipAddress;
    private StringProperty netmask;

    private StringProperty ipInput;
    private StringProperty resolvedAddr;

    private HashMap<String, NetworkCardDto> networkCardDtoHashMap;
    private ArrayList<String> networkCardName;
    private PcapPacket resolvedArpPacket;

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
    public void vmSendArpRequest(ThreadPoolExecutor executor){
        if(networkCardName.contains(networkCardSelected)){
            String ipAddress = ipInput.getValue();
            if(AddressUtils.isValidIPAddress(ipAddress)) {
                dateModel.sendArpRequest(networkCardDtoHashMap.get(networkCardSelected), ipAddress, executor);
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
    public PcapPacket vmCaptureArpReply(ThreadPoolExecutor executor){
        PcapPacket packet = null;
        if(networkCardName.contains(networkCardSelected)){
            String ipAddress = ipInput.getValue();
            if(AddressUtils.isValidIPAddress(ipAddress)) {
                packet = dateModel.catchArpReply(networkCardDtoHashMap.get(networkCardSelected), ipAddress, executor);
            }else {
                System.out.println("InValid IP Address!!!");
            }
        }else{
            System.out.println("Please choice a valid network card!!!");
        }
        return packet;
    }

    /**
     * @author UmiSkky
     */
    public void arpService(){
        final int CORE_POOL_SIZE = 4;
        final int MAX_POOL_SIZE = 12;
        final int QUEUE_CAPACITY = 4;
        final long KEEP_ALIVE_TIME = 1L;

        ThreadPoolExecutor mainExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        CompletableFuture<PcapPacket> cfCaptureArp = CompletableFuture.supplyAsync(()-> this.vmCaptureArpReply(executor), mainExecutor);
        CompletableFuture.supplyAsync(()->{
            this.vmSendArpRequest(executor);
            return null;
        }, mainExecutor);
        cfCaptureArp.thenAcceptAsync((packet)-> {
            this.resolvedArpPacket = packet;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    resolvedAddr.setValue(packet.get(ArpPacket.class).getHeader().getSrcHardwareAddr().toString());
                }
            }
            );
        });
        mainExecutor.shutdown();
    }
}
