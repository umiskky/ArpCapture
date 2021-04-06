package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.pcap.ArpCapture;
import com.umiskky.model.pcap.ArpSender;
import com.umiskky.model.pcap.NetworkCardSelector;
import com.umiskky.model.tools.NetworkCardConverter;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class DateModelManager implements DateModel{

    /**
     * @author UmiSkky
     * @return arrayList of all network cards
     */
    @Override
    public  HashMap<String, NetworkCardDto> getAllNetworkCards() {
        HashMap<String, NetworkCardDto> allDevs = new HashMap<>();
        ArrayList<PcapNetworkInterface> tmp = new ArrayList<>();
        try {
            tmp = NetworkCardSelector.getAllNetworkCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(PcapNetworkInterface pif : tmp){
            NetworkCardDto networkCardDto = NetworkCardConverter.networkCardConverter(pif);

            if (networkCardDto.getName() != null) {
                allDevs.put(networkCardDto.getName(), networkCardDto);
            }
        }
        return allDevs;
    }

    /**
     * @author UmiSkky
     * @apiNote send an arp request
     */
    @Override
    public void sendArpRequest(NetworkCardDto networkCard, String strDstIpAddress, ThreadPoolExecutor executor) {
        Callable<String> arpSenderThread = new ArpSender(networkCard, strDstIpAddress);
        Future<String> future = executor.submit(arpSenderThread);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author UmiSkky
     * @param networkCard
     * @param strDstIpAddress
     * @param executor
     * @return
     */
    @Override
    public PcapPacket catchArpReply(NetworkCardDto networkCard, String strDstIpAddress, ThreadPoolExecutor executor) {
        PcapPacket packet = null;
        Callable<PcapPacket> arpCaptureThread = new ArpCapture(networkCard, strDstIpAddress);
        Future<PcapPacket> future = executor.submit(arpCaptureThread);
        try {
            packet = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("Capture Successfully!");
        System.out.println(packet);
        return packet;
    }

}
