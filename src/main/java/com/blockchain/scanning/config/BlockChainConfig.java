package com.blockchain.scanning.config;

import com.blockchain.scanning.commons.enums.ChainType;

import java.math.BigInteger;

/**
 * Configure the parameters required for this block scanning task
 */
public class BlockChainConfig {

    /**
     * Node url
     */
    private String rpcUrl;

    /**
     * Blockchain type (ETH, SOL, TRON, etc.)
     */
    private ChainType chainType;

    /**
     * Scan polling interval, milliseconds
     */
    private long scanPeriod = 5000;

    /**
     * Starting block height of the scan
     */
    private BigInteger beginBlockNumber = BigInteger.ONE;

    /**
     * Scan size (a few blocks per scan)
     */
    private int scanSize = 1000;

    public String getRpcUrl() {
        return rpcUrl;
    }

    public void setRpcUrl(String rpcUrl) {
        this.rpcUrl = rpcUrl;
    }

    public ChainType getChainType() {
        return chainType;
    }

    public void setChainType(ChainType chainType) {
        this.chainType = chainType;
    }

    public long getScanPeriod() {
        return scanPeriod;
    }

    public void setScanPeriod(long scanPeriod) {
        this.scanPeriod = scanPeriod;
    }

    public BigInteger getBeginBlockNumber() {
        return beginBlockNumber;
    }

    public void setBeginBlockNumber(BigInteger beginBlockNumber) {
        this.beginBlockNumber = beginBlockNumber;
    }

    public int getScanSize() {
        return scanSize;
    }

    public void setScanSize(int scanSize) {
        this.scanSize = scanSize;
    }
}
