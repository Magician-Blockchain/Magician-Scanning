package com.blockchain.scanning.biz.thread;

import java.math.BigInteger;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Queue, When a block height is skipped for some reason and the user has set a retry policy, the skipped block height will be placed in this queue and wait for a retry.
 */
public class RetryStrategyQueue {
    private LinkedBlockingQueue<BigInteger> linkedBlockingQueue = new LinkedBlockingQueue<>();

    public void add(BigInteger blockNumber){
        linkedBlockingQueue.add(blockNumber);
    }

    public LinkedBlockingQueue<BigInteger> getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }
}
