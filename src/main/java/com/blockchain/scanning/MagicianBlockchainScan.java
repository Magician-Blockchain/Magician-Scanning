package com.blockchain.scanning;

import com.blockchain.scanning.biz.scan.ScanService;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.commons.enums.ChainType;
import com.blockchain.scanning.config.BlockChainConfig;
import com.blockchain.scanning.config.EventConfig;
import com.blockchain.scanning.monitor.EthMonitorEvent;

import java.math.BigInteger;

/**
 * Main class, used to create a block sweep task
 */
public class MagicianBlockchainScan {

    /**
     * Business class, Used to perform scan block logic
     */
    private ScanService scanService = new ScanService();

    /**
     * Configure the parameters required for this block scanning task
     */
    private BlockChainConfig blockChainConfig = new BlockChainConfig();

    public static MagicianBlockchainScan create(){
        return new MagicianBlockchainScan();
    }

    /**
     * set node url
     * @param rpcUrl
     * @return
     */
    public MagicianBlockchainScan setRpcUrl(String rpcUrl) {
        blockChainConfig.setRpcUrl(rpcUrl);
        return this;
    }

    /**
     * Set the blockchain type (ETH, SOL, TRON, etc.)
     * @param chainType
     * @return
     */
    public MagicianBlockchainScan setChainType(ChainType chainType) {
        blockChainConfig.setChainType(chainType);
        return this;
    }

    /**
     * Set the scan polling interval, milliseconds
     * @param scanPeriod
     * @return
     */
    public MagicianBlockchainScan setScanPeriod(long scanPeriod) {
        blockChainConfig.setScanPeriod(scanPeriod);
        return this;
    }

    /**
     * Set the starting block height of the scan
     * @param beginBlockNumber
     * @return
     */
    public MagicianBlockchainScan setBeginBlockNumber(BigInteger beginBlockNumber) {
        blockChainConfig.setBeginBlockNumber(beginBlockNumber);
        return this;
    }

    /**
     * Set the scan size (a few blocks per scan)
     * @param scanSize
     * @return
     */
    public MagicianBlockchainScan setScanSize(int scanSize) {
        blockChainConfig.setScanSize(scanSize);
        return this;
    }

    /**
     * Add ETH monitoring event
     * @param ethMonitorEvent
     * @return
     */
    public MagicianBlockchainScan addEthMonitorEvent(EthMonitorEvent ethMonitorEvent) {
        EventConfig.addEthMonitorEvent(ethMonitorEvent);
        return this;
    }

    /**
     * Add ETH monitoring event (any transaction on the chain will be triggered, no conditional filtering)
     * @param monitorAllTransactionEvent
     * @return
     */
    public MagicianBlockchainScan setEthMonitorAllTransactionEvent(EthMonitorEvent monitorAllTransactionEvent) {
        EventConfig.setEthMonitorAllTransactionEvent(monitorAllTransactionEvent);
        return this;
    }

    /**
     * start a task
     * @throws Exception
     */
    public void end() throws Exception {
        end(true);
    }

    /**
     * start a task, Can be set to execute automatically
     * @param autoScan true automatic execution, false Manual execution, you need to call the scan methods in the ScanService class
     * @throws Exception
     */
    public void end(boolean autoScan) throws Exception {
        if(blockChainConfig.getRpcUrl() == null || blockChainConfig.getRpcUrl().equals("")){
            throw new Exception("");
        }

        if(blockChainConfig.getChainType() == null){
            throw new Exception("");
        }

        if(blockChainConfig.getScanPeriod() < 3000){
            throw new Exception("");
        }

        if(blockChainConfig.getScanSize() < 0){
            throw new Exception("");
        }

        if(EventConfig.getEthMonitorEvent() == null || EventConfig.getEthMonitorEvent().size() < 1){
            throw new Exception("");
        }

        /**
         * initialization scanService
         */
        scanService.init(blockChainConfig);

        /**
         * If automatic execution is set, execute the scan
         */
        if(autoScan){
            scanService.start();
        }
    }
}