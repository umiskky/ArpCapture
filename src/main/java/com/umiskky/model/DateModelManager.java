package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.pcap.NetworkCardSelector;
import com.umiskky.model.tools.NetworkCardConverter;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class DateModelManager implements DateModel{

    /**
     * @author UmiSkky
     * @return arrayList of all network cards
     */
    @Override
    public  HashMap<String, NetworkCardDto> getAllNetworkCards() throws Exception{
        HashMap<String, NetworkCardDto> allDevs = new HashMap<>();
        ArrayList<PcapNetworkInterface> tmp = new ArrayList<>();
        try {
            tmp = NetworkCardSelector.getAllNetworkCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(PcapNetworkInterface pif : tmp){
            NetworkCardDto networkCardDto = NetworkCardConverter.networkCardConverter(pif);
            //排除没有名字的网卡
            if (networkCardDto.getName() != null) {
                allDevs.put(networkCardDto.getName(), NetworkCardConverter.networkCardConverter(pif));
            }
        }
        return allDevs;
    }
}
