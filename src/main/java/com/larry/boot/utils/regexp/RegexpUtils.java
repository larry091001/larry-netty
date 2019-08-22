package com.larry.boot.utils.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 匹配正则
 * @author zhangying[zhang_ying@suixingpay.com]
 * @date 2019年8月16日 下午2:52:20
 * @version v1.0 
 * 	类说明
 */
public class RegexpUtils {
    /**
     * 匹配截取
     * @param paramStr 处理参数
     * @param regexp 匹配规则
     * @return
     */
    public static String findByRegexp(String paramStr, String regexp) {
        Pattern pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(paramStr);
        while (matcher.find()) {
            if(StringUtils.isNotBlank(matcher.group())) {
//            	System.out.println(matcher.group());
                return matcher.group();
            }
        }
        return paramStr;
    }

    /**
     * 匹配规则截取
     * @param paramStr 处理参数
     * @param regexp 匹配规则
     * @param split 截取
     * @return
     */
    public static String extractFindByRegexp(String paramStr, String regexp, String split) {
        String splitStr = findByRegexp(paramStr, regexp);
        Pattern pattern = Pattern.compile(split, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(splitStr);
        if (matcher.find() && splitStr.indexOf(matcher.group()) > -1) {
            return splitStr.substring(splitStr.indexOf(matcher.group()) + 1);
        } else {
            return splitStr;
        }
    }

    /**
     * 匹配规则截取
     * @param paramStr 处理参数
     * @param regexp 匹配规则
     * @param split 截取
     * @return
     */
    public static String regexpSubstring(String paramStr, String regexp, String startSplit, String endSplit) {
        String splitStr = findByRegexp(paramStr, regexp);
        Pattern startPattern = Pattern.compile(startSplit, Pattern.CASE_INSENSITIVE);
        Matcher startMatcher = startPattern.matcher(splitStr);
        //System.out.println("startMatcher:" + startMatcher.find() + "=" + startMatcher.group());

        Pattern endPattern = Pattern.compile(endSplit);
        Matcher endMatcher = endPattern.matcher(splitStr);
        //System.out.println("endMatcher:" + endMatcher.find() + "=" + endMatcher.group());

        if(startMatcher.find() && endMatcher.find()
                && splitStr.indexOf(startMatcher.group()) > -1
                && splitStr.indexOf(endMatcher.group()) > -1) {

            return splitStr.substring(startMatcher.group().length(), splitStr.indexOf(endMatcher.group())).trim();
        }else {
            return splitStr;
        }
    }


    public static void main(String[] args) {
        String table_json = "map rcse.t_rcse_rule_hit, target rcse.t_rcse_rule_hit;";
        String table_json3 = "MAP rcse.t_rcse_rule_hit, target rcse.t_rcse_rule_hit;";
        String table_json2 = "map pts.pts_inmno_amt,target dcm_owner.pts_inmno_amt,filter (@getenv ('transaction', 'csn') > 24331772)";

        String str="access_token=7BBFBDAE62CA6B9EC0F4B4E810F1C38C&expires_in=7776000&refresh_token=579B4051EF86407B82CC5E2AF9434F8B";
        System.out.println(str.substring(str.indexOf("=")+1, str.indexOf("&")));
        String str1="target rcse.t_rcse_rule_hit;";

        String START_SPLIT = "map|target";
        String END_SPLIT = "=|/|:|,|;";

        Pattern startPattern = Pattern.compile(START_SPLIT, Pattern.CASE_INSENSITIVE);
        Matcher startMatcher = startPattern.matcher(table_json3);
        System.out.println(startMatcher.find() + "\n" + startMatcher.group());

        Pattern endPattern = Pattern.compile(END_SPLIT);
        Matcher endMatcher = endPattern.matcher(table_json3);
        System.out.println(endMatcher.find() + "\n" + endMatcher.group());
    }

}
