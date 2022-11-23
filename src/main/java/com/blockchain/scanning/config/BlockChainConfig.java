package com.blockchain.scanning.config;

import com.blockchain.scanning.commons.enums.ChainType;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

/**
 * Configure the parameters required for this block scanning task
 */
public class BlockChainConfig {

    /**
     * Node url
     */
    private HttpService httpService;

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

    public HttpService getHttpService() {
        return httpService;
    }

    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
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
}
