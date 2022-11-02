package com.blockchain.web3;

import com.blockchain.web3.eth.EthBuilder;

/**
 * Unified entry for all tool classes in this module
 */
public class MagicianWeb3j {

    /**
     * Unified entry for all tools in the ETH module
     */
    private static EthBuilder ethBuilder = new EthBuilder();

    public static EthBuilder getEthBuilder() {
        return ethBuilder;
    }

}
