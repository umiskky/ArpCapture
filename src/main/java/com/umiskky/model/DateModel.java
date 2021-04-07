package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;
import org.pcap4j.core.PcapPacket;

import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author UmiSkky
 */
public interface DateModel {

    HashMap<String, NetworkCardDto> getAllNetworkCards() throws Exception;

    void sendArpRequest(NetworkCardDto networkCard, String strDstIpAddress, ThreadPoolExecutor executor);

    PcapPacket catchArpReply(NetworkCardDto networkCard, String strDstIpAddress, ThreadPoolExecutor executor);
}
