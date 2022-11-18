package com.blockchain.scanning.commons.enums;

import java.math.BigInteger;

/**
 * Block-related enumerations
 */
public enum BlockEnums {

    // When starting a scan job, start scanning from the latest block
    LAST_BLOCK_NUMBER("LAST_BLOCK_NUMBER", new BigInteger("-1"));

    private String text;

    private BigInteger value;

    BlockEnums(String text, BigInteger value){
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public BigInteger getValue() {
        return value;
    }
}
