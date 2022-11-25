package com.blockchain.scanning.rpcinit.impl;

import com.blockchain.scanning.rpcinit.RpcInit;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import org.web3j.protocol.http.HttpService;

import java.net.Proxy;

/**
 * Set the rpc address of eth, bsc, polygon etc.
 */
public class EthRpcInit extends RpcInit {

    public static EthRpcInit create() {
        return new EthRpcInit();
    }

    /**
     * set node url
     *
     * @param rpcUrl
     * @return
     */
    public EthRpcInit addRpcUrl(String rpcUrl) throws Exception {
        blockChainConfig.addHttpService(new HttpService(rpcUrl));
        return this;
    }

    /**
     * set node url
     *
     * @param rpcUrl
     * @param proxy
     * @param authenticator
     * @return
     */
    public EthRpcInit addRpcUrl(String rpcUrl, Proxy proxy, Authenticator authenticator) throws Exception {
        OkHttpClient.Builder okHttpClientBuilder = HttpService.getOkHttpClientBuilder();
        okHttpClientBuilder.proxy(proxy);
        if (authenticator != null) {
            okHttpClientBuilder.proxyAuthenticator(authenticator);
        }

        blockChainConfig.addHttpService(new HttpService(rpcUrl, okHttpClientBuilder.build()));
        return this;
    }

    /**
     * set node url
     *
     * @param rpcUrl
     * @param proxy
     * @return
     */
    public EthRpcInit addRpcUrl(String rpcUrl, Proxy proxy) throws Exception {
        return addRpcUrl(rpcUrl, proxy, null);
    }

    /**
     * set node url
     *
     * @param okHttpClient
     * @return
     */
    public EthRpcInit addRpcUrl(OkHttpClient okHttpClient) throws Exception {
        blockChainConfig.addHttpService(new HttpService(okHttpClient));
        return this;
    }

    /**
     * set node url
     *
     * @param httpService
     * @return
     */
    public EthRpcInit addRpcUrl(HttpService httpService) throws Exception {
        blockChainConfig.addHttpService(httpService);
        return this;
    }
}
