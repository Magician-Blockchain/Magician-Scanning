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

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
