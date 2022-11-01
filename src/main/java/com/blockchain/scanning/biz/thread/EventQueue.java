package com.blockchain.scanning.biz.thread;

import com.blockchain.scanning.chain.model.TransactionModel;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Event queue for processing scanned transaction data
 */
public class EventQueue {

    /**
     * Event queue
     */
    private LinkedBlockingQueue<List<TransactionModel>> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<List<TransactionModel>> getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }

    public void add(List<TransactionModel> transactionModelList){
        linkedBlockingQueue.add(transactionModelList);
    }
}
