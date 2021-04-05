package com.umiskky.model.pcap.nifbuilder;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

public class SendNif implements Nif{

    private PcapHandle.Builder builder;

    public SendNif(String nifName) {
        this.builder = new PcapHandle.Builder(nifName);
    }

    @Override
    public PcapHandle buildNif() {
        PcapHandle handle = null;
        try {
            handle = this.builder.build();
        } catch (PcapNativeException e){
            e.printStackTrace();
        }
        return handle;
    }
}
