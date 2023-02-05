package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronRetModel {

    @JsonProperty("contractRet")
    private String contractRet;

    public String getContractRet() {
        return contractRet;
    }

    public void setContractRet(String contractRet) {
        this.contractRet = contractRet;
    }
}
