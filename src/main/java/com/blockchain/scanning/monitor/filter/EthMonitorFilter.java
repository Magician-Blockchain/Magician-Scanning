package com.blockchain.scanning.monitor.filter;

import com.blockchain.scanning.commons.enums.TokenType;

/**
 * Monitor filter
 */
public class EthMonitorFilter {

    /**
     * Token Type
     */
    private TokenType tokenType;

    /**
     * abi signature of the function to filter (top ten of inputData)
     */
    private String functionCode;

    /**
     * The from address in the transaction
     */
    private String fromAddress;

    /**
     * The address to receive the transaction
     */
    private String toAddress;

    /**
     * The contract address, if it is a transaction calling the contract, then it is the same as toAddress
     */
    private String contractAddress;

    public static EthMonitorFilter builder(){
        return new EthMonitorFilter();
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public EthMonitorFilter setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public EthMonitorFilter setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
        return this;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public EthMonitorFilter setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    public String getToAddress() {
        return toAddress;
    }

    public EthMonitorFilter setToAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public EthMonitorFilter setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
        return this;
    }
}
