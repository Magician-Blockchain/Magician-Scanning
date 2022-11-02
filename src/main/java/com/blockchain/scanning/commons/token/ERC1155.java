package com.blockchain.scanning.commons.token;

/**
 * All functions of the ERC1155 standard and the code corresponding to the function
 */
public enum ERC1155 {

    BALANCE_OF("balanceOf", null),
    BALANCE_OF_BATCH("balanceOfBatch", null),
    IS_APPROVAL_FOR_ALL("isApprovedForAll", null),
    SET_APPROVAL_FOR_ALL("setApprovalForAll", "0xa22cb465"),
    SAFE_TRANSFER_FROM("safeTransferFrom", "0xf242432a"),
    SAFE_BATCH_TRANSFER_FROM("safeBatchTransferFrom", "0x2eb2c2d6");

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
