package com.blockchain.scanning.chain.model.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TronParameterModel {

    @JsonProperty("type_url")
    private String typeUrl;

    @JsonProperty("value")
    private TronValueModel value;

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    public TronValueModel getValue() {
        return value;
    }

    public void setValue(TronValueModel value) {
        this.value = value;
    }
}
