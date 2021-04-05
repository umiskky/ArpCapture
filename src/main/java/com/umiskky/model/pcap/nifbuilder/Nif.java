package com.umiskky.model.pcap.nifbuilder;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;

public interface Nif {
    /**
     * @author Umiskky
     * @apiNote this interface is used to generate pcapHandle builder to build a nif
     * @throws PcapNativeException
     */
    PcapHandle buildNif() throws PcapNativeException;
}
