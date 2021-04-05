package com.umiskky.model.pcap.nifbuilder;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

public class CaptureNif implements Nif{
    private PcapHandle.Builder builder;

    public CaptureNif(String nifName) {
        this.builder = new PcapHandle.Builder(nifName);
    }

    @Override
    public PcapHandle buildNif() throws PcapNativeException {
        PcapHandle handle = null;
        try {
            handle = this.builder.build();
        } catch (PcapNativeException e){
            e.printStackTrace();
        }
        return handle;
    }
}
