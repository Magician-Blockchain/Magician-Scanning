package com.blockchain.scanning.monitor;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.commons.enums.TokenType;

/**
 * Ethereum listening events
 */
public interface EthMonitorEvent {

    /**
     * Token Type
     * @return
     */
    default TokenType tokenType(){
        return null;
    }

    /**
     * The first ten digits of inputData, including 0x
     * Only valid when tokenType is not equal to main chain currency
     * @return
     */
    default String functionCode(){
        return null;
    }

    /**
     * The from address in the transaction
     * @return
     */
    default String fromAddress(){
        return null;
    }

    /**
     * The to address in the transaction
     * @return
     */
    default String toAddress(){
        return null;
    }

    /**
     * The contract address, if it is a transaction calling the contract, then it is the same as toAddress
     * @return
     */
    default String contractAddress(){
        return null;
    }

    /**
     * Filter the transaction data according to the above conditions, and execute the monitoring event
     * @param transactionModel
     */
    void call(TransactionModel transactionModel);
}
