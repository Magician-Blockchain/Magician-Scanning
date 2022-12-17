package com.blockchain.scanning.biz.thread;

import com.blockchain.scanning.biz.thread.model.EventModel;
import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.model.TransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * event consumer
 */
public class EventConsumer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    /**
     * The main class used for scanning, there are currently 3 implementation classes, which can be extended in the future
     */
    private ChainScanner chainScanner;

    /**
     * Queue, every time a block is scanned, a task will be placed in it, the scanned data will be processed asynchronously, and MonitorEvent will be called as needed
     */
    private EventQueue eventQueue;

    /**
     * Whether to stop
     */
    private boolean shutdown;

    public EventConsumer(ChainScanner chainScanner, EventQueue eventQueue){
        this.chainScanner = chainScanner;
        this.eventQueue = eventQueue;
        this.shutdown = false;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    @Override
    public void run() {
        while (true) {
            EventModel eventModel = null;

            try {
                eventModel = eventQueue.getLinkedBlockingQueue().poll(2000, TimeUnit.MILLISECONDS);

                if(eventModel == null){
                    if(shutdown){
                        return;
                    }
                    continue;
                }

                logger.info("Transaction records with block height [{}] are being processed", eventModel.getCurrentBlockHeight());

                List<TransactionModel> transactionResultList = eventModel.getTransactionModels();

                for (TransactionModel transactionModel : transactionResultList) {
                    chainScanner.call(transactionModel);
                }
            } catch (Exception e){
                if(eventModel == null){
                    logger.error("Exception in processing transaction record", e);
                } else {
                    logger.error("Exception in processing transaction record, block height: [{}]", eventModel.getCurrentBlockHeight(), e);
                }
            }
        }
    }
}
