package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronBlockModel {

    @JsonProperty("blockID")
    private String blockID;

    @JsonProperty("block_header")
    private TronBlockHeaderModel tronBlockHeaderModel;

    @JsonProperty("transactions")
    private List<TronTransactionModel> transactions;

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public TronBlockHeaderModel getTronBlockHeaderModel() {
        if(tronBlockHeaderModel == null){
            return new TronBlockHeaderModel();
        }
        return tronBlockHeaderModel;
    }

    public void setTronBlockHeaderModel(TronBlockHeaderModel tronBlockHeaderModel) {
        this.tronBlockHeaderModel = tronBlockHeaderModel;
    }

    public List<TronTransactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TronTransactionModel> transactions) {
        this.transactions = transactions;
    }
}
