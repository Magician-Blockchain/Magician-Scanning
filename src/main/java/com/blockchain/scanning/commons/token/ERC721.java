package com.blockchain.scanning.commons.token;

/**
 * All functions of the ERC721 standard and the code corresponding to the function
 */
public enum ERC721 {
    OWNER_OF("ownerOf", ""),
    BALANCE_OF("balanceOf", ""),
    SAFE_TRANSFER_FROM("safeTransferFrom", ""),
    SAFE_TRANSFER_FROM_TWO("safeTransferFrom", ""),
    TRANSFER_FROM("transferFrom", ""),
    APPROVE("approve", ""),
    SET_APPROVAL_FOR_ALL("setApprovalForAll", ""),
    GET_APPROVED("getApproved", ""),
    IS_APPROVED_FOR_ALL("isApprovedForAll", ""),;

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
