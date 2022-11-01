package com.blockchain.scanning.chain.factory;

import com.blockchain.scanning.chain.ChainScanner;
import com.blockchain.scanning.chain.impl.ETHChainScanner;
import com.blockchain.scanning.chain.impl.SolChainScanner;
import com.blockchain.scanning.chain.impl.TronChainScanner;
import com.blockchain.scanning.commons.enums.ChainType;

/**
 * Factory class, get scanner
 */
public class ChainScannerFactory {

    /**
     * get scanner
     * @param chainType
     * @return
     */
    public static ChainScanner getChainScanner(ChainType chainType) {
        switch (chainType) {
            case ETH:
                return new ETHChainScanner();
            case SOL:
                return new SolChainScanner();
            case TRON:
                return new TronChainScanner();
        }

        return null;
    }
}
