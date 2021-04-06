package com.umiskky.model.pcap;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.pcap.nifbuilder.CaptureNif;
import lombok.Getter;
import lombok.Setter;
import org.pcap4j.core.*;
import org.pcap4j.packet.ArpPacket;
import org.pcap4j.packet.namednumber.ArpOperation;
import org.pcap4j.util.MacAddress;

import java.io.EOFException;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class ArpCapture implements Callable<PcapPacket>, Supplier<PcapPacket> {

    @Setter
    @Getter
    private volatile boolean exit = false;

    private HashMap<String, Object> builderParams;
    private CaptureNif captureNif;
    private PcapHandle captureHandle;

    /**
     * @author UmiSkky
     * @param networkCard
     * @param strDstIpAddress
     */
    public ArpCapture(NetworkCardDto networkCard, String strDstIpAddress) {
        this.builderParams = new HashMap<>();
        this.builderParams.put("SNAP_LEN", 65536);
        this.builderParams.put("READ_TIMEOUT", 10);
        this.builderParams.put("BUFFER_SIZE", 10 * 1024 * 1024);
        this.builderParams.put("TIMESTAMP_PRECISION_NANO", false);

        String nifName = networkCard.getName();
        this.captureNif = new CaptureNif(nifName, this.builderParams);
        try {
            String filter = "arp and src host "
                    + strDstIpAddress
                    + " and dst host "
                    + networkCard.getAddress().replace("/", "")
                    + " and ether dst "
                    + Pcaps.toBpfString(MacAddress.getByName(networkCard.getLinkLayerAddr()));
            this.captureNif.setFilter(filter);
            this.captureHandle = this.captureNif.buildNif();
        } catch (PcapNativeException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author UmiSkky
     * @return
     * @throws Exception
     */
    @Override
    public PcapPacket call() throws Exception {
        PcapPacket packet = null;
        boolean isCaptured = false;
        while (!exit) {
            try {
                packet = this.captureHandle.getNextPacketEx();
                if (packet.contains(ArpPacket.class)) {
                    ArpPacket arp = packet.get(ArpPacket.class);
                    if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                        this.exit = true;
                        isCaptured = true;
//                    return packet;
                    }
                }
            } catch (TimeoutException ignored) {
            }
        }
        if (this.captureHandle != null && this.captureHandle.isOpen()) {
            this.captureHandle.close();
        }
        if(isCaptured){
            return packet;
        }else {
            return null;
        }

    }

    /**
     * @author UmiSkky
     * @return
     */
    @Override
    public PcapPacket get() {
        PcapPacket packet = null;
        boolean isCaptured = false;
        while (!exit) {
            try {
                try {
                    packet = this.captureHandle.getNextPacketEx();
                } catch (PcapNativeException | EOFException | NotOpenException e) {
                    e.printStackTrace();
                }
                assert packet != null;
                if (packet.contains(ArpPacket.class)) {
                    ArpPacket arp = packet.get(ArpPacket.class);
                    if (arp.getHeader().getOperation().equals(ArpOperation.REPLY)) {
                        this.exit = true;
                        isCaptured = true;
//                    return packet;
                    }
                }
            } catch (TimeoutException ignored) {
            }
        }
        if (this.captureHandle != null && this.captureHandle.isOpen()) {
            this.captureHandle.close();
        }
        if(isCaptured){
            return packet;
        }else {
            return null;
        }
    }
}
