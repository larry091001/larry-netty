package com.larry.boot.utils.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.larry.boot.utils.regexp.RegexpUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 匹配规则
 * @author zhangying[zhang_ying@suixingpay.com]
 * @date 2019年8月16日 下午2:53:45
 * @version v1.0 
 * 	类说明
 */
public class RegexpTest {
	/**
	 * '.':匹配任何单字符，例如：表达式b.g能匹配big，bug，bg，但是匹配不了buug
	 * '*':匹配0个至多个在它之前的字符。例如zo*能匹配z以及zoo
	 * '?':匹配前面的子表达式零次或一次。例如do(es)可以匹配do或does中的do
	 * '()':将()之间括起来的表达式定义为“组”(group)，并且将匹配表达式的字符保存到一个临时区域，元字符在字符串提取的时候非常有用
	 * \t：制表符，等同于/u0009
	 * \n：换行符，等同于/u000A
	 * \d：代表一个数字，等同于[0-9]
	 * \D：代表非数字，等同于[^0-9]
	 * \s：代表换行符、Tab制表符等空白字符
	 * \S：代表非空白字符
	 * \w：字母字符，等同于[a-zA-Z_0-9]
	 * \W：非字母字符，等同于[^/w]
	 */
	private final static String IP_REG = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
	private final static String PARAM_REG = "([0-9]{4,}/\\w*)|([0-9]{4,}:\\w*)|([0-9]{4,}/\\w*)|(SERVICE_NAME\\s*=\\s*\\w*)";
	private final static String SPLIT_FLAG = "=|/|:|,|;";
	private final static String START_SPLIT = "map|target";
	private final static String END_SPLIT = "=|/|:|,|;";

	private final static String TABLE_BEGIN_REG = "(map\\s*\\S*,)";
	private final static String TABLE_END_REG = "(target\\s*\\S*;)";

	private final static String result_json = "{\"alarmPositionCount\":300000,\"consumer\":{\"consumerName\":\"KafkaFetch\",\"converter\":\"oggJson\",\"emptyFetchNoticeSpan\":3600,\"emptyFetchThreshold\":3600,\"includes\":\"PTS.T_PTS_WLWX_QR_TRANDATA\",\"offset\":\"3994674\",\"source\":{\"servers\":\"10.3.101.1:9092,10.3.101.2:9092,10.3.101.3:9092\",\"sourceType\":\"KAFKA\",\"topics\":\"t9_out3\",\"oncePollSize\":\"300\",\"pollTimeOut\":\"200\",\"autoCommit\":\"false\",\"group\":\"10209\"}},\"loader\":{\"insertOnUpdateError\":false,\"loaderName\":\"JdbcBatch\",\"source\":{\"connectionErrorRetryAttempts\":\"3\",\"password\":\"zkw\",\"minPoolSize\":\"10\",\"sourceType\":\"JDBC\",\"maxWait\":\"10000\",\"dbType\":\"ORACLE\",\"userName\":\"zkw\",\"initialPoolSize\":\"10\",\"maxPoolSize\":\"20\",\"url\":\"jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = no)(FAILOVER = yes)(ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.11.75)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = dcdb))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.11.76)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = dcdb)))\"}},\"localTask\":false,\"mapper\":[{\"forceMatched\":true,\"ignoreTargetCase\":true,\"schema\":[\"PTS\",\"BD\"],\"table\":[\"T_PTS_WLWX_QR_TRANDATA\",\"T_PTS_WLWX_QR_TRANDATA_gss\"]}],\"nodeId\":\"176\",\"positionCheckInterval\":1800,\"receiver\":[],\"restartIncreaseBySecond\":300,\"restartRetries\":20,\"status\":\"WORKING\",\"taskId\":\"10209\"}";
	private final static String table_json = "map rcse.t_rcse_rule_hit, target rcse.t_rcse_rule_hit;";
	private final static String table_json3 = "MAP rcse.t_rcse_rule_hit, target rcse.t_rcse_rule_hit;";
	private final static String table_json2 = "map pts.pts_inmno_amt,target dcm_owner.pts_inmno_amt,filter (@getenv ('transaction', 'csn') > 24331772)";

	private final static String SOURCE_REG = "(map(.*?)\\,)|(map(.*?)\\;)";
	private final static String TARGET_REG = "(target(.*?)\\;)|(target(.*?)\\,)";
	private final static String TABLE_BEGIN_REG3 = "target(.*?)\\,";

	public static void main(String[] args) {
//		queryIp();
//		queryLoardName();
//		queryTable();
		tableName();
	}

	public static void queryIp() {
		String ip = RegexpUtils.findByRegexp(result_json, IP_REG);
		System.out.println("ip=====" + ip);
	}

	public static void queryLoardName() {
		String loaderDbName = RegexpUtils.extractFindByRegexp(result_json, PARAM_REG, SPLIT_FLAG);
		System.out.println("loaderDbName=====" + loaderDbName);
	}

	public static void tableName1() {
		String tableName = RegexpUtils.extractFindByRegexp(table_json, SOURCE_REG, SPLIT_FLAG);
		System.out.println("tableName=====" + tableName);
		String tableName2 = RegexpUtils.findByRegexp(table_json2, SOURCE_REG);
		System.out.println("tableName2=====" + tableName2);
		String tableName3 = RegexpUtils.findByRegexp(table_json3, SOURCE_REG);
		System.out.println("tableName3=====" + tableName3);
	}

	public static void tableName() {
		String sourceTable = RegexpUtils.regexpSubstring(table_json2, SOURCE_REG, START_SPLIT, END_SPLIT);
		String targetTable = RegexpUtils.regexpSubstring(table_json2, TARGET_REG, START_SPLIT, END_SPLIT);;
		System.out.println("sourceTable=====" + sourceTable);
		System.out.println("targetTable=====" + targetTable);
	}

	public static void queryTable() {
//		String tableName = RegexpUtils.extractFindByRegexp(table_json, TABLE_BEGIN_REG, SPLIT_FLAG);
//		System.out.println("table=====" + tableName);
//		String tableName = RegexpUtils.beginExtractRegexp(table_json, TABLE_BEGIN_REG, SPLIT_FLAG);
//		System.out.println("table=====" + tableName);


		//Pattern.CASE_INSENSITIVE 不区分大小写
		Pattern pattern = Pattern.compile(SOURCE_REG,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(table_json2);
		//System.out.println("value=====" + matcher.find());
		if(matcher.find()){
			if(StringUtils.isNotBlank(matcher.group())) {
				// 包含前后的两个字符
				System.out.println(matcher.group());
			}
			if(StringUtils.isNotBlank(matcher.group(1))) {
				// 不包含前后的两个字符
				System.out.println(matcher.group(1).trim());
			}
		}
//        String PARAM_REG1 = "(SERVICE_NAME\\s*=\\s*\\w*)";
//        String result_json = "SERVICE_NAME=dcdb";
//        Pattern pattern = Pattern.compile(PARAM_REG1);
//        Matcher matcher = pattern.matcher(result_json);
//        System.out.println("value=====" + matcher.find());

		// 内容
		String value = "fileNameCode-->_AD2467524284sd234.json";

		// 匹配规则
		String reg = "_(.*?)\\.";
		Pattern pattern1 = Pattern.compile(reg);

		// 内容 与 匹配规则 的测试
		Matcher matcher1 = pattern1.matcher(value);

		if(matcher1.find()){
			// 包含前后的两个字符
			System.out.println(matcher1.group());
			// 不包含前后的两个字符
			System.out.println( matcher1.group(1));
		}else{
			System.out.println(" 没有匹配到内容....");
		}


		Pattern pattern2 = Pattern.compile("_\\{(.*?)\\}_");
		Matcher matcher2 = pattern2.matcher("asdf_{asd}_fas_{dfa}_sdfasdf_{dfa}_");
		while(matcher2.find()){
			System.out.println(matcher2.group(1));
		}
	}

}
