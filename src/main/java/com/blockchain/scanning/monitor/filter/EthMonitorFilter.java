package com.blockchain.scanning.monitor.filter;

import java.math.BigInteger;

/**
 * Monitor filter
 */
public class EthMonitorFilter {

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
     * Amount of coins sent
     *
     * If set, filter out >= its
     */
    private BigInteger minValue;

    /**
     * Amount of coins sent
     *
     * If set, filter out <= its
     */
    private BigInteger maxValue;

    public static EthMonitorFilter builder(){
        return new EthMonitorFilter();
    }

    public String getFunctionCode() {
        if(functionCode != null){
            return functionCode.toLowerCase();
        }
        return null;
    }

    public EthMonitorFilter setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
        return this;
    }

    public String getFromAddress() {
        if(fromAddress != null){
            return fromAddress.toLowerCase();
        }
        return null;
    }

    public EthMonitorFilter setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
        return this;
    }

    public String getToAddress() {
        if(toAddress != null){
            return toAddress.toLowerCase();
        }
        return null;
    }

    public EthMonitorFilter setToAddress(String toAddress) {
        this.toAddress = toAddress;
        return this;
    }

    public BigInteger getMinValue() {
        return minValue;
    }

    public EthMonitorFilter setMinValue(BigInteger minValue) {
        this.minValue = minValue;
        return this;
    }

    public BigInteger getMaxValue() {
        return maxValue;
    }

    public EthMonitorFilter setMaxValue(BigInteger maxValue) {
        this.maxValue = maxValue;
        return this;
    }
}
