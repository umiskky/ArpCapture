package com.umiskky.model.dto;

import lombok.Data;
import org.pcap4j.packet.Packet;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class ArpPacketString {

    private String packetStr;

    public ArpPacketString(Packet packet) {
        this.packetStr = packet.toString();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to parse Arp Packet
     * @return arpPacketMap
     */
    public HashMap<String, ArrayList<String>> getArpPacketData(){
        HashMap<String, ArrayList<String>> arpPacketMap = new HashMap<>();

        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> length = new ArrayList<>();
        ArrayList<String> ethernetHeader = new ArrayList<>();
        ArrayList<String> arpHeader = new ArrayList<>();
//        ArrayList<String> ethernetPad = new ArrayList<>();

        String[] stringArray = packetStr.split("\r\n");
        if(stringArray.length == 16){
            time.add(stringArray[0]);
            length.add(stringArray[1]);
            ethernetHeader.add(stringArray[2]);
            ethernetHeader.add(stringArray[3].substring(2));
            ethernetHeader.add(stringArray[4].substring(2));
            ethernetHeader.add(stringArray[5].substring(2));
            arpHeader.add(stringArray[6]);
            arpHeader.add(stringArray[7].substring(2));
            arpHeader.add(stringArray[8].substring(2));
            arpHeader.add(stringArray[9].substring(2));
            arpHeader.add(stringArray[10].substring(2));
            arpHeader.add(stringArray[11].substring(2));
            arpHeader.add(stringArray[12].substring(2));
            arpHeader.add(stringArray[13].substring(2));
            arpHeader.add(stringArray[14].substring(2));
            arpHeader.add(stringArray[15].substring(2));
//            ethernetPad.add(stringArray[16]);
//            ethernetPad.add(stringArray[17].substring(2));
        }

        arpPacketMap.put("time", time);
        arpPacketMap.put("length", length);
        arpPacketMap.put("ethernetHeader", ethernetHeader);
        arpPacketMap.put("arpHeader", arpHeader);
//        arpPacketMap.put("ethernetPad", ethernetPad);
        return arpPacketMap;
    }
}
