package com.umiskky.model.tools;

import com.umiskky.model.dto.NetworkCardDto;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapIpV4Address;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.List;

public class NetworkCardConverter {
    /**
     * @author UmiSkky
     * @apiNote Convert network card properties to string
     * @param pnif PcapNetworkInterface
     * @return NetworkCardDto which is converted from PcapNetworkInterface
     */
    public static NetworkCardDto networkCardConverter(PcapNetworkInterface pnif){

        String name = pnif.getName();
        String description = pnif.getDescription();

        List<PcapAddress> tmp = pnif.getAddresses();
        Object ipv4Addresses = null;
        for(Object ob : tmp){
            if (ob.getClass().equals(PcapIpV4Address.class)){
                ipv4Addresses = ob;
            }
        }

        if (ipv4Addresses != null){
            PcapIpV4Address addresses = (PcapIpV4Address) ipv4Addresses;
            String address = String.valueOf(addresses.getAddress());
            String netmask = String.valueOf(addresses.getNetmask());
            String broadcastAddr = String.valueOf(addresses.getBroadcastAddress());
            String linkLayerAddr = String.valueOf(pnif.getLinkLayerAddresses().get(0)).toUpperCase();
            return new NetworkCardDto(name, description, address, netmask, broadcastAddr, linkLayerAddr);
        }
        return new NetworkCardDto();
    }
}
