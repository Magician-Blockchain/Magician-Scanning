package com.blockchain.scanning.commons.constant;

import java.math.BigInteger;

/**
 * Some values are not convenient to use enumerations, so they are unified in constants
 */
public class Web3jConstant {

    /**
     * When starting a scan job, start scanning from the latest block
     */
    public static final BigInteger LAST_BLOCK_NUMBER = new BigInteger("-1");
}
