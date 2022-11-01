package com.blockchain.scanning.monitor;

import com.blockchain.scanning.chain.model.TransactionModel;

/**
 * Tron listening events
 *
 * TODO In development.......
 */
public interface TronMonitorEvent {

    void call(TransactionModel transactionModel);
}
