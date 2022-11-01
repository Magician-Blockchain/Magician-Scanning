package com.blockchain.scanning.biz.parsing;

import com.blockchain.scanning.commons.enums.TokenType;

import java.util.Map;

public class EthParsingInputData {

    public static Map<String, Object> parsing(String inputData, TokenType tokenType, String functionName){
        switch (tokenType){
            case ERC20:
                return erc20(inputData, functionName);
            case ERC1155:
                return erc1155(inputData, functionName);
            case ERC721:
                return erc721(inputData, functionName);
        }
        return null;
    }

    private static Map<String, Object> erc20(String inputData, String functionName){
        return null;
    }

    private static Map<String, Object> erc1155(String inputData, String functionName){
        return null;
    }

    private static Map<String, Object> erc721(String inputData, String functionName){
        return null;
    }
}
