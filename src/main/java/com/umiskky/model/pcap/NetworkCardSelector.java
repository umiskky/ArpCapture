package com.umiskky.model.pcap;

import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                PcapNetworkInterface pnif = ((PcapNetworkInterface) object);
                // 排除没有地址的网卡
                if(pnif.getAddresses().size() != 0) {
                    allNetworkCards.add(pnif);
                }
            }
            return allNetworkCards;
        } else {
            throw new IOException("No NIF to capture.");
        }
    }

}
