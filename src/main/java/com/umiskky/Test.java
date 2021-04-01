package com.umiskky;

import com.umiskky.model.pcap.NetworkCardSelector;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList <PcapNetworkInterface> test = new ArrayList<>();
        try {
            test = NetworkCardSelector.getAllNetworkCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(test);
    }
}
