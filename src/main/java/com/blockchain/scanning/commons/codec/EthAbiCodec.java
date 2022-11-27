package com.blockchain.scanning.commons.codec;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.Utils;
import org.web3j.abi.datatypes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ABI codec
 */
public class EthAbiCodec {

    /**
     * Encode the function as inputData
     * @param functionName
     * @param inputTypes
     * @return
     */
    public static String getInputData(String functionName, Type... inputTypes){
        List<Type> inputTypeList = new ArrayList<>();
        for(Type type : inputTypes){
            inputTypeList.add(type);
        }

        Function function = new Function(functionName, inputTypeList, new ArrayList<>());
        return FunctionEncoder.encode(function);
    }

    /**
     * Get the function's signature
     * @param functionName
     * @param inputTypes
     * @return
     */
    public static String getFunAbiCode(String functionName, Type... inputTypes) {
        String inputData = getInputData(functionName, inputTypes);
        if(inputData == null || inputData.equals("")){
            return null;
        }
        return inputData.substring(0, 10);
    }

    /**
     * Decode inputData into raw data
     * @param inputData
     * @param inputTypes
     * @return
     */
    public static List<Type> decoderInputData(String inputData, TypeReference... inputTypes){
        List<TypeReference<?>> inputTypeList = new ArrayList<>();
        for(TypeReference typeReference : inputTypes){
            inputTypeList.add(typeReference);
        }
        return FunctionReturnDecoder.decode(inputData, Utils.convert(inputTypeList));
    }
}
