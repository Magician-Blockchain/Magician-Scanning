package com.blockchain.web3.eth.helper;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * ETH coin balance inquiry and other management methods
 */
public class EthHelper {

    /**
     * native web3j
     */
    private Web3j web3j;

    private EthHelper(Web3j web3j){
        this.web3j = web3j;
    }

    public static EthHelper builder(Web3j web3j){
        return new EthHelper(web3j);
    }

    /**
     * Transfer ETH coins to the specified address
     * @param toAddress
     * @param privateKey
     * @param value
     * @param unit
     * @return
     * @throws Exception
     */
    public TransactionReceipt transfer(String toAddress, String privateKey, BigDecimal value, Convert.Unit unit) throws Exception {
        return Transfer.sendFunds(
                web3j,
                Credentials.create(privateKey),
                toAddress,
                value,
                unit
        ).send();
    }

    /**
     * Query the ETH coin balance under the specified address
     * @param fromAddress
     * @return
     * @throws IOException
     */
    public BigInteger balanceOf(String fromAddress) throws IOException {
        EthGetBalance ethGetBalance =  web3j.ethGetBalance(fromAddress, DefaultBlockParameterName.LATEST).send();
        return ethGetBalance.getBalance();
    }
}
