package com.blockchain.scanning.monitor;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.enums.TokenType;
import com.blockchain.scanning.monitor.filter.EthMonitorFilter;

/**
 * Ethereum listening events
 */
public interface EthMonitorEvent {

    /**
     * ethMonitorFilter
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
