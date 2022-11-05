package com.blockchain.scanning.biz.thread;

import com.blockchain.scanning.biz.thread.model.EventModel;
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
    private LinkedBlockingQueue<EventModel> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public LinkedBlockingQueue<EventModel> getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }

    public void add(EventModel eventModel){
        linkedBlockingQueue.add(eventModel);
    }
}
