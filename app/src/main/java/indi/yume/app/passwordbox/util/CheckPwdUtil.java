package indi.yume.app.passwordbox.util;

import android.support.annotation.IntRange;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by bush2 on 2016/4/3.
 */
public class CheckPwdUtil {
    private static final char[] aToZ = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'};

    private static final char[] numbers = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'};

    private static final Set<String> dictionary = new HashSet<>();

    private static final Pattern numPattern = Pattern.compile("^[0-9]+$");

    public static boolean containsSmallAToZ(String str) {
        for(char c : str.toCharArray())
            if(c >= 'a' && c <= 'z')
                return true;
        return false;
    }

    public static boolean containsBiggerAToZ(String str) {
        for(char c : str.toCharArray())
            if(c >= 'A' && c <= 'Z')
                return true;
        return false;
    }

    public static boolean containsNumber(String str) {
        for(char c : str.toCharArray())
            if(c >= '0' && c <= '9')
                return true;
        return false;
    }

    public static boolean contains_(String str) {
        for(char c : str.toCharArray())
            for(char special : Constants.SPECIAL_CHARS)
                if(c == special)
                    return true;
        return false;
    }

    public static boolean isNumberSequence(String str) {
        if(str.length() < 2)
            return false;

        if("01234567890".contains(str)
                || "09876543210".contains(str))
            return true;

        return false;
    }

    /*
     *说明：
     *  该方法主要分析密码的内容构成，密码长度等情况，然后评分划分密码强度等级
     *参数：
     *  pwd 密码，字符串类型
     *  obj 密码强度显示的地方，必须是jquery封装对象
     *  minLen 密码最小长度
     *  maxLen 密码最大长度
     */
    public static int checkPwdStrong(String pwd,
                              int minLen,
                              int maxLen) {
        maxLen = Math.max(maxLen, minLen);

        int level = 1;
        String lowerPwd = pwd.toLowerCase();

        /** ****************加分因子***************** */
        if(containsSmallAToZ(lowerPwd))
            level++;
        if(containsBiggerAToZ(pwd))
            level++;
        if(containsNumber(lowerPwd))
            level++;
        if(contains_(lowerPwd))
            level++;

        if((containsSmallAToZ(lowerPwd) && containsNumber(lowerPwd))
                || (containsSmallAToZ(lowerPwd) && contains_(lowerPwd))
                || (containsNumber(lowerPwd) && contains_(lowerPwd)))
            level++;
        if(containsSmallAToZ(lowerPwd) && containsNumber(lowerPwd) && contains_(lowerPwd))
            level++;
        /** ****************减分因子***************** */
//        if(lowerPwd.length() < (minLen + maxLen) / 2 && lowerPwd.length() >= minLen)
        if(lowerPwd.length() < minLen)
            level--;
        // 全部由同一个字符组成的直接判为弱
        if(lowerPwd.length() > 1) {
            boolean allEquals = true;
            char firstChar = pwd.charAt(0);
            for(char c : pwd.toCharArray())
                if(c != firstChar) {
                    allEquals = false;
                    break;
                }

            if(allEquals)
                return 0;
        }

        // 纯数字的密码不能是一个等差数列数列
        if(isNumberSequence(lowerPwd))
            level--;

        // 不能由连续的字母组成，例如：abcdefg
        if("abcdefghijklmnopqrstuvwxyz".contains(lowerPwd))
            level--;

        // 纯字母组成的密码不能是键盘上的相邻键位字母组合，例如：qwertyu
        if ("qwertyuiop".contains(pwd)
                || "asdfghjkl".contains(pwd)
                || "zxcvbnm".contains(pwd))
            level--;

        // 不能是2段短字符的重复，例如：567567
        if (lowerPwd.length() % 2 == 0) {
            String part1 = pwd.substring(0, pwd.length() / 2);
            String part2 = pwd.substring(pwd.length() / 2);
            if (part1.equals(part2))
                level--;
        }

        // 不能是3段短字符的重复，例如：121212
        if (pwd.length() % 3 == 0) {
            String part1 = pwd.substring(0, pwd.length() / 3);
            String part2 = pwd.substring(pwd.length() / 3, pwd.length() / 3 * 2);
            String part3 = pwd.substring(pwd.length() / 3 * 2);
            if (part1.equals(part2) && part2.equals(part3))
                level--;
        }

        // 不能是一个日期，例如：19870723
        if (numPattern.matcher(pwd).matches()) {
            if (pwd.length() == 8) {
                int year = Integer.valueOf(pwd.substring(0, 4));
                int month = Integer.valueOf(pwd.substring(4, 6));
                int day = Integer.valueOf(pwd.substring(5, 7));
                if (year >= 1000 && year < 2100 && month >= 1 && month <= 12
                        && day >= 1 && day <= 31) {
                    level--;
                }
            }
        }

        // 不能位于内置字典内
        if(dictionary.contains(lowerPwd))
            level--;

        level = Math.min(level, 5);
        level = Math.max(level, 0);

        return level;
    }
}
