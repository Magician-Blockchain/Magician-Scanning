package com.blockchain.scanning.config;

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

    /**
     * ETH monitoring event (any transaction on the chain will be triggered, no conditional filtering)
     */
    private static EthMonitorEvent ethMonitorAllTransactionEvent;

    public static List<EthMonitorEvent> getEthMonitorEvent() {
        return ethMonitorEvent;
    }

    public static void addEthMonitorEvent(EthMonitorEvent ethMonitorEvent) {
        EventConfig.ethMonitorEvent.add(ethMonitorEvent);
    }

    public static EthMonitorEvent getEthMonitorAllTransactionEvent() {
        return ethMonitorAllTransactionEvent;
    }

    public static void setEthMonitorAllTransactionEvent(EthMonitorEvent ethMonitorAllTransactionEvent) {
        EventConfig.ethMonitorAllTransactionEvent = ethMonitorAllTransactionEvent;
    }
}
