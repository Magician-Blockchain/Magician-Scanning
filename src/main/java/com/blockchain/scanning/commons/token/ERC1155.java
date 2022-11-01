package com.blockchain.scanning.commons.token;

/**
 * All functions of the ERC1155 standard and the code corresponding to the function
 */
public enum ERC1155 {

    BALANCE_OF("balanceOf", ""),
    BALANCE_OF_BATCH("balanceOfBatch", ""),
    SET_APPROVAL_FOR_ALL("setApprovalForAll", ""),
    IS_APPROVAL_FOR_ALL("isApprovedForAll", ""),
    SAFE_TRANSFER_FROM("safeTransferFrom", ""),
    SAFE_BATCH_TRANSFER_FROM("safeBatchTransferFrom", "");

    private String functionName;

    private String functionCode;

    ERC1155(String functionName, String functionCode){
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
