package com.blockchain.scanning.rpcinit;

import com.blockchain.scanning.config.BlockChainConfig;

/**
 * Set rpc address
 */
public class RpcInit {

    /**
     * Configuration object that is used to pass the configuration of the RPC URL to the main configuration object
     */
    protected BlockChainConfig blockChainConfig = new BlockChainConfig();

    public BlockChainConfig getBlockChainConfig(){
        return blockChainConfig;
    }
}
