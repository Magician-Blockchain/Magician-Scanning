package com.blockchain.scanning.commons.config.rpcinit.impl;

import com.blockchain.scanning.commons.config.rpcinit.RpcInit;

/**
 * Set the rpc address of tron
 */
public class TronRpcInit extends RpcInit {

    public static TronRpcInit create() {
        return new TronRpcInit();
    }

    /**
     * set node url
     *
     * @param rpcUrl
     * @return
     */
    public TronRpcInit addRpcUrl(String rpcUrl) throws Exception {
        blockChainConfig.addTronRpcUrls(rpcUrl);
        return this;
    }
}
