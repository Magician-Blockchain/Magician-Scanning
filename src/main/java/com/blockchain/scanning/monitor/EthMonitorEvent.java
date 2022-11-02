package com.blockchain.scanning.monitor;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;

/**
 * Ethereum listening events
 */
public interface EthMonitorEvent {

    /**
     * Monitor filter
     *
     * When a qualified transaction is scanned, the call method will be triggered
     * @return
     */
    default EthMonitorFilter ethMonitorFilter(){
        return null;
    }

    /**
     * Filter the transaction data according to the above conditions, and execute the monitoring event
     * @param transactionModel
     */
    void call(TransactionModel transactionModel);
}
