package com.blockchain.scanning.commons.constant;

import java.util.HashMap;
import java.util.Map;

public class TronConstants {

    public static final String GET_NOW_BLOCK  = "/getblock";

    public static final Map<String, Object> GET_NOW_BLOCK_PARAMETER = new HashMap<String, Object>(){
        {
            put("detail", false);
        }
    };

    public static final String GET_BLOCK_BY_NUM = "/getblockbynum";
}
