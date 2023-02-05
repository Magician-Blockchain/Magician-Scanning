package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronContractModel {

    @JsonProperty("type")
    private String type;

    @JsonProperty("parameter")
    private TronParameterModel parameter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TronParameterModel getParameter() {
        return parameter;
    }

    public void setParameter(TronParameterModel parameter) {
        this.parameter = parameter;
    }
}
