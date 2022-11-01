package com.blockchain.scanning.biz.thread;

import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.model.TransactionModel;

import java.util.List;

/**
 * event consumer
 */
public class EventConsumer implements Runnable {

    private ChainScanner chainScanner;
    private EventQueue eventQueue;

    public EventConsumer(ChainScanner chainScanner, EventQueue eventQueue){
        this.chainScanner = chainScanner;
        this.eventQueue = eventQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<TransactionModel> transactionResultList = eventQueue.getLinkedBlockingQueue().take();

                for (TransactionModel transactionModel : transactionResultList) {
                    chainScanner.call(transactionModel);
                }
            } catch (Exception e){

            }
        }
    }
}
