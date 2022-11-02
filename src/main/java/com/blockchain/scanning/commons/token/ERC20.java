package com.blockchain.scanning.commons.token;

/**
 * All functions of the ERC20 standard and the code corresponding to the function
 */
public enum ERC20 {
    TOTAL_SUPPLY("totalSupply", null),
    BALANCE_OF("balanceOf", null),
    ALLOWANCE("allowance", null),
    TRANSFER("transfer", "0xa9059cbb"),
    APPROVE("approve", "0x095ea7b3"),
    TRANSFER_FROM("transferFrom", "0x23b872dd");

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
