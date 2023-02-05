package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * TransactionInfo on the Tron chain
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronTransactionModel {

    @JsonProperty("txID")
    private String txID;

    @JsonProperty("ret")
    private List<TronRetModel> ret;

    @JsonProperty("signature")
    private List<String> signature;

    @JsonProperty("raw_data_hex")
    private String rawDataHex;

    @JsonProperty("raw_data")
    private TronTransactionRawDataModel rawData;

    // ------------ Block information, used for developers to obtain in the listener -------------

    @JsonIgnore
    private String blockID;

    @JsonIgnore
    private TronBlockHeaderModel tronBlockHeaderModel;

    public String getTxID() {
        return txID;
    }

    public void setTxID(String txID) {
        this.txID = txID;
    }

    public List<TronRetModel> getRet() {
        return ret;
    }

    public void setRet(List<TronRetModel> ret) {
        this.ret = ret;
    }

    public List<String> getSignature() {
        return signature;
    }

    public void setSignature(List<String> signature) {
        this.signature = signature;
    }

    public String getRawDataHex() {
        return rawDataHex;
    }

    public void setRawDataHex(String rawDataHex) {
        this.rawDataHex = rawDataHex;
    }

    public TronTransactionRawDataModel getRawData() {
        return rawData;
    }

    public void setRawData(TronTransactionRawDataModel rawData) {
        this.rawData = rawData;
    }

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public TronBlockHeaderModel getTronBlockHeaderModel() {
        return tronBlockHeaderModel;
    }

    public void setTronBlockHeaderModel(TronBlockHeaderModel tronBlockHeaderModel) {
        this.tronBlockHeaderModel = tronBlockHeaderModel;
    }
}
