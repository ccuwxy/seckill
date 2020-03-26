package cn.ccuwxy.miaosha.util;

import com.mysql.jdbc.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");
    public static boolean isMobile(String src){
        if(StringUtils.isNullOrEmpty(src))
            return false;
        Matcher m = MOBILE_PATTERN.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("1511114444"));
    }
}
