package com.blockchain.scanning;

import com.blockchain.scanning.biz.thread.EventConsumer;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.biz.thread.RetryStrategyConsumer;
import com.blockchain.scanning.chain.RetryStrategy;
import com.blockchain.scanning.biz.scan.ScanService;
import com.blockchain.scanning.commons.enums.ChainType;
import com.blockchain.scanning.commons.config.BlockChainConfig;
import com.blockchain.scanning.commons.config.rpcinit.RpcInit;
import com.blockchain.scanning.commons.config.rpcinit.impl.EthRpcInit;
import com.blockchain.scanning.commons.config.rpcinit.impl.SolRpcInit;
import com.blockchain.scanning.commons.config.rpcinit.impl.TronRpcInit;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import com.blockchain.scanning.monitor.TronMonitorEvent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Main class, used to create a block sweep task
 */
public class MagicianBlockchainScan {

    /**
     * Business class, Used to perform scan block logic
     */
    private ScanService scanService;

    /**
     * Configure the parameters required for this block scanning task
     */
    private BlockChainConfig blockChainConfig;

    /**
     * Does the rpc address exist
     */
    private boolean rpcUrlExist = false;

    /**
     * all scan job
     */
    private static List<MagicianBlockchainScan> magicianBlockchainScans;

    private MagicianBlockchainScan(){
        scanService = new ScanService();
        blockChainConfig = new BlockChainConfig();
        magicianBlockchainScans = new ArrayList<>();
    }

    public static MagicianBlockchainScan create(){
        return new MagicianBlockchainScan();
    }

    /**
     * Set the RPC URL for the blockchain
     * @param rpcInit
     * @return
     */
    public MagicianBlockchainScan setRpcUrl(RpcInit rpcInit){
        if(rpcInit instanceof EthRpcInit){
            blockChainConfig.setChainType(ChainType.ETH);
            blockChainConfig.setHttpService(rpcInit.getBlockChainConfig().getHttpService());
        } else if(rpcInit instanceof SolRpcInit){
            blockChainConfig.setChainType(ChainType.SOL);
            // TODO In development.......
        } else if(rpcInit instanceof TronRpcInit){
            blockChainConfig.setChainType(ChainType.TRON);
            blockChainConfig.setTronRpcUrls(rpcInit.getBlockChainConfig().getTronRpcUrls());
        }
        rpcUrlExist = true;
        return this;
    }

    /**
     * Setting the retry strategy
     * @param retryStrategy
     * @return
     */
    public MagicianBlockchainScan setRetryStrategy(RetryStrategy retryStrategy){
        blockChainConfig.setRetryStrategy(retryStrategy);
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
     * Add ETH monitoring event
     * @param ethMonitorEvent
     * @return
     */
    public MagicianBlockchainScan addEthMonitorEvent(EthMonitorEvent ethMonitorEvent) {
        blockChainConfig.getEventConfig().addEthMonitorEvent(ethMonitorEvent);
        return this;
    }

    /**
     * Add TRON monitoring event
     * @param tronMonitorEvent
     * @return
     */
    public MagicianBlockchainScan addTronMonitorEvent(TronMonitorEvent tronMonitorEvent) {
        blockChainConfig.getEventConfig().addTronMonitorEvents(tronMonitorEvent);
        return this;
    }

    /**
     * start a task
     * @throws Exception
     */
    public void start() throws Exception {
        if (rpcUrlExist == false) {
            throw new Exception("rpcUrl cannot be empty");
        }

        if (blockChainConfig.getChainType() == null) {
            throw new Exception("ChainType cannot be empty");
        }

        if (blockChainConfig.getScanPeriod() < 1) {
            throw new Exception("scanPeriod must be greater than 1");
        }

        if (blockChainConfig.getChainType().equals(ChainType.ETH)
                && (blockChainConfig.getEventConfig() == null
                || blockChainConfig.getEventConfig().getEthMonitorEvent() == null
                || blockChainConfig.getEventConfig().getEthMonitorEvent().size() < 1)
        ) {
            throw new Exception("You need to set up at least one monitor event");
        }

        if (blockChainConfig.getChainType().equals(ChainType.TRON)
                && (blockChainConfig.getEventConfig() == null
                || blockChainConfig.getEventConfig().getTronMonitorEvents() == null
                || blockChainConfig.getEventConfig().getTronMonitorEvents().size() < 1)
        ) {
            throw new Exception("You need to set up at least one monitor event");
        }

        // initialization scanService
        scanService.init(blockChainConfig);

        // execute the scan
        scanService.start();

        magicianBlockchainScans.add(this);
    }

    /**
     * Stop the current scan job
     */
    public void shutdown() {
        Timer timer = scanService.getTimer();
        if (timer != null) {
            timer.cancel();
        }

        EventConsumer eventConsumer = scanService.getEventConsumer();
        if (eventConsumer != null) {
            eventConsumer.setShutdown(true);
        }

        RetryStrategyConsumer retryStrategyConsumer = scanService.getRetryStrategyConsumer();
        if (retryStrategyConsumer != null) {
            retryStrategyConsumer.setShutdown(true);
        }
    }

    /**
     * Stop all scan job
     */
    public static void shutdownAll(){
        for(MagicianBlockchainScan magicianBlockchainScan : magicianBlockchainScans){
            magicianBlockchainScan.shutdown();
        }
        EventThreadPool.shutdown();
    }
}
