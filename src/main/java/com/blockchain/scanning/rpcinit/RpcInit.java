package com.blockchain.scanning.rpcinit;

import com.blockchain.scanning.config.BlockChainConfig;

/**
 * Set rpc address
 */
public class RpcInit {

    /**
     * Configure the parameters required for this block scanning task
     */
    protected BlockChainConfig blockChainConfig = new BlockChainConfig();

    public BlockChainConfig getBlockChainConfig(){
        return blockChainConfig;
    }
}
