package com.blockchain.scanning.commons.config;

import com.blockchain.scanning.monitor.EthMonitorEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listen event set
 */
public class EventConfig {

    /**
     * ETH monitoring event
     */
    private static List<EthMonitorEvent> ethMonitorEvent = new ArrayList<>();

    public static List<EthMonitorEvent> getEthMonitorEvent() {
        return ethMonitorEvent;
    }

    public static void addEthMonitorEvent(EthMonitorEvent ethMonitorEvent) {
        EventConfig.ethMonitorEvent.add(ethMonitorEvent);
    }

}
