package com.umiskky.model.tools;

import com.umiskky.model.dto.NetworkCardDto;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapIpV4Address;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.List;

/**
 * @author UmiSkky
 */
public class NetworkCardConverter {
    /**
     *  Convert network card properties to string
     * @param pnif
     * @return
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
        //排除没有IPV4地址的网卡
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
