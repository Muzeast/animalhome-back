package com.songmin.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Utils {
    //自定义盐值
    private static final String salt = "1s2o3n4g";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPass2FormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPass2DBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPass2DBPass(String inputPass, String dbSalt) {
        String formPass = inputPass2FormPass(inputPass);
        String dbPass = formPass2DBPass(formPass, dbSalt);
        return dbPass;
    }

    //Test
    /*public static void main(String args[]) {
        String s = "SongMin";
        System.out.println("初始值: " + s);
        System.out.println("加密值: " + Md5Utils.inputPass2FormPass(s));
    }*/
}
