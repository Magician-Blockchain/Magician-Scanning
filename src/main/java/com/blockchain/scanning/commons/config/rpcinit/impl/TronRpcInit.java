package com.blockchain.scanning.commons.config.rpcinit.impl;

import com.blockchain.scanning.commons.config.rpcinit.RpcInit;
import org.tron.trident.core.ApiWrapper;

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
     * @param apiWrapper
     * @return
     */
    public TronRpcInit addRpcUrl(ApiWrapper apiWrapper) throws Exception {
        blockChainConfig.addApiWrappers(apiWrapper);
        return this;
    }
}
