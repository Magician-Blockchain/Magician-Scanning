package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronTransactionRawDataModel {

    @JsonProperty("ref_block_bytes")
    private String refBlockBytes;

    @JsonProperty("ref_block_hash")
    private String refBlockHash;

    @JsonProperty("expiration")
    private Long expiration;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("contract")
    private List<TronContractModel> contract;

    public String getRefBlockBytes() {
        return refBlockBytes;
    }

    public void setRefBlockBytes(String refBlockBytes) {
        this.refBlockBytes = refBlockBytes;
    }

    public String getRefBlockHash() {
        return refBlockHash;
    }

    public void setRefBlockHash(String refBlockHash) {
        this.refBlockHash = refBlockHash;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<TronContractModel> getContract() {
        return contract;
    }

    public void setContract(List<TronContractModel> contract) {
        this.contract = contract;
    }
}
