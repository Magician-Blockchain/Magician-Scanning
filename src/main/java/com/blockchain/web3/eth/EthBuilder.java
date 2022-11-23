package com.blockchain.web3.eth;

import com.blockchain.web3.eth.codec.EthAbiCodec;
import com.blockchain.web3.eth.contract.EthContract;
import com.blockchain.web3.eth.helper.EthHelper;
import org.web3j.protocol.Web3j;

/**
 * Unified entry for all tools in the ETH module, developers can easily get the tool class objects they need from here
 */
public class EthBuilder {

    /**
     * ABI codec
     */
    private static EthAbiCodec ethAbiCodec;

    /**
     * Query contract data and write data to the contract
     */
    private static EthContract ethContract;

    /**
     * ETH coin balance inquiry and other management methods
     */
    private static EthHelper ethHelper;

    public EthAbiCodec getEthAbiCodec(){
        if(ethAbiCodec == null){
            ethAbiCodec = new EthAbiCodec();
        }
        return ethAbiCodec;
    }

    public EthContract getEthContract(Web3j web3j){
        if(ethContract == null){
            ethContract = EthContract.builder(web3j);
        }
        return ethContract;
    }

    public EthHelper getEth(Web3j web3j){
        if(ethHelper == null){
            ethHelper = EthHelper.builder(web3j);
        }
        return ethHelper;
    }
}
