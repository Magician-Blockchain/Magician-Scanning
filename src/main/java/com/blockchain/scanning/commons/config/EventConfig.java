package com.blockchain.scanning.commons.config;

import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.TronMonitorEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Listen event set
 */
public class EventConfig {

    /**
     * ETH monitoring event
     */
    private List<EthMonitorEvent> ethMonitorEventList = new ArrayList<>();

    /**
     * Tron monitoring event
     */
    private List<TronMonitorEvent> tronMonitorEvents = new ArrayList<>();

    public List<EthMonitorEvent> getEthMonitorEvent() {
        return ethMonitorEventList;
    }

    public void addEthMonitorEvent(EthMonitorEvent ethMonitorEvent) {
        ethMonitorEventList.add(ethMonitorEvent);
    }

    public List<TronMonitorEvent> getTronMonitorEvents() {
        return tronMonitorEvents;
    }

    public void addTronMonitorEvents(TronMonitorEvent tronMonitorEvent) {
        this.tronMonitorEvents.add(tronMonitorEvent);
    }
}
