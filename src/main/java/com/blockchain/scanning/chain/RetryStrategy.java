package com.blockchain.scanning.chain;

import java.math.BigInteger;

/**
 * Retry strategy
 */
public interface RetryStrategy {

   /**
    * Rescan the specified block height
    * @param blockNumber
    */
   void retry(BigInteger blockNumber);
}
