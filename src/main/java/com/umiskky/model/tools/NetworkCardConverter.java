package com.umiskky.model.tools;

import com.umiskky.model.dto.NetworkCardDto;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.PcapIpV4Address;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.util.LinkLayerAddress;
import org.pcap4j.util.MacAddress;

import java.util.ArrayList;
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
        PcapIpV4Address addresses = (PcapIpV4Address) pnif.getAddresses().get(0);
        String address = String.valueOf(addresses.getAddress());
        String netmask = String.valueOf(addresses.getNetmask());
        String broadcastAddr = String.valueOf(addresses.getBroadcastAddress());
        String linkLayerAddr = String.valueOf(pnif.getLinkLayerAddresses().get(0)).toUpperCase();
        return new NetworkCardDto(name, description, address, netmask, broadcastAddr, linkLayerAddr);
    }
}
