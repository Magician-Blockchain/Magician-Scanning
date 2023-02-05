package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronBlockHeaderModel {

    @JsonProperty("raw_data")
    private TronBlockRawDataModel tronBlockRawDataModel;

    @JsonProperty("witness_signature")
    private String witnessSignature;

    public TronBlockRawDataModel getTronRawDataModel() {
        if(tronBlockRawDataModel == null){
            return new TronBlockRawDataModel();
        }
        return tronBlockRawDataModel;
    }

    public void setTronRawDataModel(TronBlockRawDataModel tronBlockRawDataModel) {
        this.tronBlockRawDataModel = tronBlockRawDataModel;
    }

    public String getWitnessSignature() {
        return witnessSignature;
    }

    public void setWitnessSignature(String witnessSignature) {
        this.witnessSignature = witnessSignature;
    }
}
