package com.blockchain.scanning.commons.token;

/**
 * All functions of the ERC20 standard and the code corresponding to the function
 */
public enum ERC20 {
    TOTAL_SUPPLY("totalSupply", ""),
    BALANCE_OF("balanceOf", ""),
    TRANSFER("transfer", ""),
    ALLOWANCE("allowance", ""),
    APPROVE("approve", ""),
    TRANSFER_FROM("transferFrom", ""),;

    private String functionName;

    private String functionCode;

    ERC20(String functionName, String functionCode){
        this.functionName = functionName;
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getFunctionCode() {
        return functionCode;
    }
}
