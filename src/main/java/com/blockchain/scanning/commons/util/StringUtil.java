package com.blockchain.scanning.commons.util;

/**
 * String Util Class
 */
public class StringUtil {

    /**
     * Is empty
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj){
        if(obj == null || obj.trim().equals("")){
            return true;
        }
        return false;
    }

    /**
     * Is not empty
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(String obj){
        return isEmpty(obj) == false;
    }
}
