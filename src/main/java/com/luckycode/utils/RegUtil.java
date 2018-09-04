package com.luckycode.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sean on 2018/08/28.
 * 正则表达式工具类
 */
public class RegUtil {

    private static Pattern mailPattern=Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    public static boolean vaildEmail(String email){
        Matcher matcher=mailPattern.matcher(email);
        if (matcher.find()){
            return true;
        }else {
            return false;
        }
    }

}
