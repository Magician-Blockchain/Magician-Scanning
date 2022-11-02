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
     * abi signature of the function to filter (top ten of inputData)
     *
     * If you only want to monitor the transaction records of a certain function, you can set it in this method.
     * If you do not want to monitor the specified function, you can not implement this method or return null
     * @return
     */
    default String functionCode(){
        return null;
    }

    /**
     * The from address in the transaction
     *
     * If you want to monitor the transaction records sent by a certain address,
     * you can implement this method, otherwise you can not implement it or return null
     * @return
     */
    default String fromAddress(){
        return null;
    }

    /**
     * The address to receive the transaction
     *
     * If you want to monitor transactions received by an address,
     * This method can be implemented, otherwise it cannot be implemented or returns null
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
