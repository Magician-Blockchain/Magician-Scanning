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
    private List<EthMonitorEvent> ethMonitorEventList = new ArrayList<>();

    public List<EthMonitorEvent> getEthMonitorEvent() {
        return ethMonitorEventList;
    }

    public void addEthMonitorEvent(EthMonitorEvent ethMonitorEvent) {
        ethMonitorEventList.add(ethMonitorEvent);
    }

}
