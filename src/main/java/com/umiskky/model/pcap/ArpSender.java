package com.umiskky.model.pcap;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.pcap.nifbuilder.CaptureNif;
import com.umiskky.model.pcap.nifbuilder.SendNif;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.namednumber.ArpHardwareType;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.MacAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

public class ArpSender implements Callable<String> {
    private InetAddress strSrcIpAddress;
    private InetAddress strDstIpAddress;
    private MacAddress srcMacAddress;
    private MacAddress dstMacAddress;
    private MacAddress resolvedAddr;
    private ArpHardwareType arpHardwareType;

    private SendNif sendNifBuilder;
    private CaptureNif captureNifBuilder;
    private PcapHandle sendHandle;


    public ArpSender(NetworkCardDto networkCard, String strDstIpAddress) {

        try {
            this.strDstIpAddress = InetAddress.getByName(strDstIpAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            this.strSrcIpAddress = InetAddress.getByName(networkCard.getAddress().replace("/",""));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.srcMacAddress = MacAddress.getByName(networkCard.getLinkLayerAddr());
        this.dstMacAddress = MacAddress.ETHER_BROADCAST_ADDRESS;

        String nifName = networkCard.getName();
        this.sendNifBuilder = new SendNif(nifName);
//        this.captureNifBuilder = new CaptureNif(nifName);
        this.sendHandle = sendNifBuilder.buildNif();
        this.arpHardwareType = ArpHardwareType.getInstance(sendHandle.getDlt().value().shortValue());
    }

    /**
     * @author UmiSkky
     * @apiNote this method is used to construct an arp packet
     * @return Arp Package
     */
    public Packet packetBuilder(){

        ArpPacket.Builder arpBuilder = new ArpPacket.Builder();
        arpBuilder
                .hardwareType(arpHardwareType)
                .protocolType(EtherType.IPV4)
                .hardwareAddrLength((byte) MacAddress.SIZE_IN_BYTES)
                .protocolAddrLength((byte) ByteArrays.INET4_ADDRESS_SIZE_IN_BYTES)
                .operation(ArpOperation.REQUEST)
                .srcHardwareAddr(srcMacAddress)
                .srcProtocolAddr(strSrcIpAddress)
                .dstHardwareAddr(dstMacAddress)
                .dstProtocolAddr(strDstIpAddress);

        EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
        etherBuilder
                .dstAddr(dstMacAddress)
                .srcAddr(srcMacAddress)
                .type(EtherType.ARP)
                .payloadBuilder(arpBuilder)
                .paddingAtBuild(true);

        return etherBuilder.build();
    }

    @Override
    public String call() throws Exception {
        Packet packet = packetBuilder();
        sendHandle.sendPacket(packet);
        if (sendHandle != null && sendHandle.isOpen()) {
            sendHandle.close();
        }
        return "Send Successfully!";
    }
}
