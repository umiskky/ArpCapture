package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;
import org.pcap4j.core.PcapPacket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * @author UmiSkky
 */
public interface DateModel {

    HashMap<String, NetworkCardDto> getAllNetworkCards() throws Exception;

    void sendArpRequest(NetworkCardDto networkCard, String strDstIpAddress);

    void catchArpReply(NetworkCardDto networkCard, String strDstIpAddress);
}
