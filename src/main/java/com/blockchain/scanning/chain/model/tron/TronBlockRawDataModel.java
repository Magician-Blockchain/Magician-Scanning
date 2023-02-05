package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronBlockRawDataModel {

    @JsonProperty("number")
    private BigInteger number;

    @JsonProperty("txTrieRoot")
    private String txTrieRoot;

    @JsonProperty("witness_address")
    private String witnessAddress;

    @JsonProperty("parentHash")
    private String parentHash;

    @JsonProperty("version")
    private String version;

    @JsonProperty("timestamp")
    private Long timestamp;

    public BigInteger getNumber() {
        if(number == null){
            return BigInteger.ZERO;
        }
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public String getTxTrieRoot() {
        return txTrieRoot;
    }

    public void setTxTrieRoot(String txTrieRoot) {
        this.txTrieRoot = txTrieRoot;
    }

    public String getWitnessAddress() {
        return witnessAddress;
    }

    public void setWitnessAddress(String witnessAddress) {
        this.witnessAddress = witnessAddress;
    }

    public String getParentHash() {
        return parentHash;
    }

    public void setParentHash(String parentHash) {
        this.parentHash = parentHash;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
