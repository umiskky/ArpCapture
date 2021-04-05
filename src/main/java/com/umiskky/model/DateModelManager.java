package com.umiskky.model;

import com.umiskky.model.dto.NetworkCardDto;
import com.umiskky.model.pcap.ArpSender;
import com.umiskky.model.pcap.NetworkCardSelector;
import com.umiskky.model.tools.NetworkCardConverter;
import org.pcap4j.core.PcapNetworkInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class DateModelManager implements DateModel{

    /**
     * @author UmiSkky
     * @return arrayList of all network cards
     */
    @Override
    public  HashMap<String, NetworkCardDto> getAllNetworkCards() throws Exception{
        HashMap<String, NetworkCardDto> allDevs = new HashMap<>();
        ArrayList<PcapNetworkInterface> tmp = new ArrayList<>();
        try {
            tmp = NetworkCardSelector.getAllNetworkCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(PcapNetworkInterface pif : tmp){
            NetworkCardDto networkCardDto = NetworkCardConverter.networkCardConverter(pif);
            //排除没有名字的网卡
            if (networkCardDto.getName() != null) {
                allDevs.put(networkCardDto.getName(), networkCardDto);
            }
        }
        return allDevs;
    }

    /**
     * @author UmiSkky
     * @apiNote send an arp request
     */
    @Override
    public void sendArpRequest(NetworkCardDto networkCard, String strDstIpAddress) {
        final int CORE_POOL_SIZE = 5;
        final int MAX_POOL_SIZE = 10;
        final int QUEUE_CAPACITY = 20;
        final Long KEEP_ALIVE_TIME = 1L;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

        Callable<String> arpSenderThread = new ArpSender(networkCard, strDstIpAddress);
        Future<String> future = executor.submit(arpSenderThread);

        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();

    }
}
