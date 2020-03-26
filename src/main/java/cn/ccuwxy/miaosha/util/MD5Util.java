package cn.ccuwxy.miaosha.util;

import cn.ccuwxy.miaosha.exception.GlobleException;
import cn.ccuwxy.miaosha.result.CodeMsg;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "s5rmscoa1";

    public static String inputPassToFormPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(5)+inputPass+salt.charAt(2)+salt.charAt(6);
        return md5(str);
    }

    public static String tokenToRedis(String imput){
        String str = ""+salt.charAt(1)+salt.charAt(6)+imput+salt.charAt(2)+salt.charAt(3);
        return md5(str);
    }

    public static String formPassToDbPass(String inputPass,String salt){
        if(salt.length()<6)
            throw new GlobleException(CodeMsg.DB_SALT_ERROR);
        String str = ""+salt.charAt(0)+salt.charAt(4)+inputPass+salt.charAt(5)+salt.charAt(6);
        return md5(str);
    }

    public static String inputPassToDbPass(String input,String saltDB){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDbPass(formPass, saltDB);
        return dbPass;
    }
    public static void main(String[] args) {
        System.out.println(inputPassToDbPass("12345678","qweasdzxc"));
        System.out.println(inputPassToFormPass("12345678"));
    }
}
