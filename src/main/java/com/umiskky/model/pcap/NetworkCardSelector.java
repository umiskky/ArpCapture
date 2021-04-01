package com.umiskky.model.pcap;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author UmiSkky
 */
public class NetworkCardSelector {

    /**
     * ?
     * @return All NetworkCards as ArrayList<PcapNetworkInterface>
     * @throws Exception ?
     */
    public static ArrayList<PcapNetworkInterface> getAllNetworkCards() throws Exception{

        List allDevs;
        try {
            allDevs = Pcaps.findAllDevs();
        } catch (PcapNativeException var) {
            throw new IOException(var.getMessage());
        }

        ArrayList<PcapNetworkInterface> allNetworkCards = new ArrayList<>();
        if (allDevs != null && !allDevs.isEmpty()) {
            for(Object object : allDevs){
                allNetworkCards.add((PcapNetworkInterface) object);
            }
            return allNetworkCards;
        } else {
            throw new IOException("No NIF to capture.");
        }
    }

}
