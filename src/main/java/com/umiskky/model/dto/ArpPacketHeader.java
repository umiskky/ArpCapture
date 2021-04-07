package com.umiskky.model.dto;

import lombok.Data;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.Packet;

@Data
public class ArpPacketHeader {

    private int hashcode;
    private String timestamp;
    private String hardwareType;
    private String protocolType;
    private int hardwareAddrLength;
    private int protocolAddrLength;
    private String operation;
    private String srcHardwareAddr;
    private String srcProtocolAddr;
    private String dstHardwareAddr;
    private String dstProtocolAddr;

    /**
     * @author UmiSkky
     * @param packet ?
     */
    public ArpPacketHeader(Packet packet) {
        this.hashcode = packet.hashCode();
        this.timestamp = packet.get(PcapPacket.class).getTimestamp().toString();
        ArpPacket.ArpHeader arpHeader = packet.get(ArpPacket.class).getHeader();
        this.hardwareType = arpHeader.getHardwareType().toString();
        this.protocolType = arpHeader.getProtocolType().toString();
        this.hardwareAddrLength = arpHeader.getHardwareAddrLengthAsInt();
        this.protocolAddrLength = arpHeader.getProtocolAddrLengthAsInt();
        this.operation = arpHeader.getOperation().toString();
        this.srcHardwareAddr = arpHeader.getSrcHardwareAddr().toString();
        this.srcProtocolAddr = arpHeader.getSrcProtocolAddr().toString();
        this.dstHardwareAddr = arpHeader.getDstHardwareAddr().toString();
        this.dstProtocolAddr = arpHeader.getDstProtocolAddr().toString();
    }
}
