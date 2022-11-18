package com.blockchain.scanning;

import com.blockchain.scanning.biz.scan.ScanService;
import com.blockchain.scanning.commons.enums.ChainType;
import com.blockchain.scanning.config.BlockChainConfig;
import com.blockchain.scanning.config.EventConfig;
import com.blockchain.scanning.monitor.EthMonitorEvent;
import okhttp3.*;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.net.Proxy;

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
    public MagicianBlockchainScan setRpcUrl(String rpcUrl) throws Exception {
        if(blockChainConfig.getHttpService() != null){
            throw new Exception("You have set the rpcUrl");
        }

        blockChainConfig.setHttpService(new HttpService(rpcUrl));
        return this;
    }

    /**
     * set node url
     * @param rpcUrl
     * @param proxy
     * @param authenticator
     * @return
     */
    public MagicianBlockchainScan setRpcUrl(String rpcUrl, Proxy proxy, Authenticator authenticator) throws Exception {
        if (blockChainConfig.getHttpService() != null) {
            throw new Exception("You have set the rpcUrl");
        }

        OkHttpClient.Builder okHttpClientBuilder = HttpService.getOkHttpClientBuilder();
        okHttpClientBuilder.proxy(proxy);
        if (authenticator != null) {
            okHttpClientBuilder.proxyAuthenticator(authenticator);
        }

        blockChainConfig.setHttpService(new HttpService(rpcUrl, okHttpClientBuilder.build()));
        return this;
    }

    /**
     * set node url
     * @param rpcUrl
     * @param proxy
     * @return
     */
    public MagicianBlockchainScan setRpcUrl(String rpcUrl, Proxy proxy) throws Exception {
        return setRpcUrl(rpcUrl, proxy, null);
    }

    /**
     * set node url
     * @param okHttpClient
     * @return
     */
    public MagicianBlockchainScan setRpcUrl(OkHttpClient okHttpClient) throws Exception {
        if(blockChainConfig.getHttpService() != null){
            throw new Exception("You have set the rpcUrl");
        }

        blockChainConfig.setHttpService(new HttpService(okHttpClient));
        return this;
    }

    /**
     * set node url
     * @param httpService
     * @return
     */
    public MagicianBlockchainScan setRpcUrl(HttpService httpService) throws Exception {
        if(blockChainConfig.getHttpService() != null){
            throw new Exception("You have set the rpcUrl");
        }
        blockChainConfig.setHttpService(httpService);
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
     * start a task
     * @throws Exception
     */
    public void start() throws Exception {
        if(blockChainConfig.getHttpService() == null){
            throw new Exception("rpcUrl cannot be empty");
        }

        if(blockChainConfig.getChainType() == null){
            throw new Exception("ChainType cannot be empty");
        }

        if(blockChainConfig.getScanPeriod() < 3000){
            throw new Exception("scanPeriod must be greater than 3000");
        }

        if(blockChainConfig.getScanSize() < 0){
            throw new Exception("scanSize must be greater than 0");
        }

        if(blockChainConfig.getChainType().equals(ChainType.ETH) && EventConfig.getEthMonitorEvent() == null || EventConfig.getEthMonitorEvent().size() < 1){
            throw new Exception("You need to set up at least one monitor event");
        }

        // initialization scanService
        scanService.init(blockChainConfig);

        // execute the scan
        scanService.start();
    }
}
