package com.blockchain.scanning.commons.token;

/**
 * All functions of the ERC721 standard and the code corresponding to the function
 */
public enum ERC721 {
    OWNER_OF("ownerOf", null),
    BALANCE_OF("balanceOf", null),
    GET_APPROVED("getApproved", null),
    IS_APPROVED_FOR_ALL("isApprovedForAll", null),
    SAFE_TRANSFER_FROM("safeTransferFrom", "0xb88d4fde"),
    SAFE_TRANSFER_FROM_TWO("safeTransferFrom", "0x42842e0e"),
    TRANSFER_FROM("transferFrom", "0x23b872dd"),
    APPROVE("approve", "0x095ea7b3"),
    SET_APPROVAL_FOR_ALL("setApprovalForAll", "0xa22cb465");

    private String functionName;

    private String functionCode;

    ERC721(String functionName, String functionCode){
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
