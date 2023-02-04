package com.blockchain.scanning.chain.model;

import org.tron.trident.proto.Chain;

/**
 * TransactionInfo on the Tron chain
 */
public class TronTransactionModel {

    /**
     * block objects on the Tron chain
     */
    private Chain.Block block;

    /**
     * Transaction objects on the Tron chain
     */
    private Chain.Transaction transaction;

    public static TronTransactionModel builder(){
        return new TronTransactionModel();
    }

    public Chain.Block getBlock() {
        return block;
    }

    public TronTransactionModel setBlock(Chain.Block block) {
        this.block = block;
        return this;
    }

    public Chain.Transaction getTransaction() {
        return transaction;
    }

    public TronTransactionModel setTransaction(Chain.Transaction transaction) {
        this.transaction = transaction;
        return this;
    }
}
