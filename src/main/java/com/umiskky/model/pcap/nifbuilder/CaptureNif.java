package com.umiskky.model.pcap.nifbuilder;

import lombok.Setter;
import org.pcap4j.core.*;

import java.util.HashMap;

public class CaptureNif implements Nif{
    private final PcapHandle.Builder builder;
    @Setter
    private String filter;

    /**
     * @author UmiSkky
     * @param nifName
     * @param builderParams
     */
    public CaptureNif(String nifName, HashMap<String, Object> builderParams) {
        this.builder = new PcapHandle.Builder(nifName)
                .snaplen((Integer)builderParams.get("SNAP_LEN"))
                .promiscuousMode(PcapNetworkInterface.PromiscuousMode.PROMISCUOUS)
                .timeoutMillis((Integer)builderParams.get("READ_TIMEOUT"))
                .bufferSize((Integer)builderParams.get("BUFFER_SIZE"));
        if((Boolean) builderParams.get("TIMESTAMP_PRECISION_NANO")){
            this.builder.timestampPrecision(PcapHandle.TimestampPrecision.NANO);
        }
        this.filter="";
    }

    /**
     * @author UmiSkky
     * @return
     * @throws PcapNativeException
     */
    @Override
    public PcapHandle buildNif() throws PcapNativeException {
        PcapHandle handle = null;
        try {
            handle = this.builder.build();
            if(!"".equals(this.filter)){
                try {
                    handle.setFilter(this.filter, BpfProgram.BpfCompileMode.OPTIMIZE);
                } catch (NotOpenException e) {
                    e.printStackTrace();
                }
            }
        } catch (PcapNativeException e){
            e.printStackTrace();
        }
        return handle;
    }
}
