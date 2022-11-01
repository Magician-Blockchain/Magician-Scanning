package com.blockchain.scanning.monitor;

import com.blockchain.scanning.chain.model.TransactionModel;

/**
 * SOL listening events
 *
 * TODO In development.......
 */
public interface SolMonitorEvent {

    void call(TransactionModel transactionModel);
}
