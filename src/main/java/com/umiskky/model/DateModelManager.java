package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.pcap.NetworkCardSelector;
import com.umiskky.model.tools.NetworkCardConverter;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.ArrayList;

/**
 * @author UmiSkky
 */
public class DateModelManager implements DateModel{

    /**
     *
     * @return arrayList of all network cards
     */
    @Override
    public  ArrayList<NetworkCardDto> getAllNetworkCards() throws Exception{
        ArrayList<NetworkCardDto> allDevs = new ArrayList<>();
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
                allDevs.add(NetworkCardConverter.networkCardConverter(pif));
            }
        }
        return allDevs;
    }
}
