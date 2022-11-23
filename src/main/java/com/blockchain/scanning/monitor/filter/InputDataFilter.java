package com.blockchain.scanning.monitor.filter;

import org.web3j.abi.TypeReference;

/**
 * InputData Filter
 *
 * Set the conditions for filtering by inputData
 */
public class InputDataFilter {

    /**
     * abi signature of the function to filter (top ten of inputData)
     */
    private String functionCode;

    /**
     * List of parameters for the function (type only)
     */
    private TypeReference[] typeReferences;

    /**
     * Filter conditions
     */
    private String[] value;

    public static InputDataFilter create(){
        return new InputDataFilter();
    }

    public String getFunctionCode() {
        if(this.functionCode != null){
            return this.functionCode.toLowerCase();
        }
        return null;
    }

    public InputDataFilter setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
        return this;
    }

    public TypeReference[] getTypeReferences() {
        return typeReferences;
    }

    public InputDataFilter setTypeReferences(TypeReference... typeReferences) {
        this.typeReferences = typeReferences;
        return this;
    }

    public String[] getValue() {
        return value;
    }

    public InputDataFilter setValue(Object... value) {
        if (value == null || value.length == 0) {
            return this;
        }

        this.value = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            if(value[i] == null){
                this.value[i] = null;
                continue;
            }

            this.value[i] = value[i].toString().toLowerCase();
        }

        return this;
    }
}
