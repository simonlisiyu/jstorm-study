package com.lsy.jstorm.utils;

import java.util.Map;


/**
 * 常量类
 * @author Qizj
 * 2016-05-06
 */
public class Const {
	
	// CMS项目server地址
//	public static final String CMS_SERVER_URL = "http://192.168.1.215:8080/jeecms";
//	public static final String CMS_SERVER_URL = "http://192.168.16.228:8080/jeecms";
//	public static final String CMS_SERVER_URL = "http://10.44.63.202:1688/jeecms";
	
	// 字典表Map，存储格式为 (dic_type_id-code -> name) , (51-0 -> 一口价)
	public static Map<String, String> dicMap = null;
	
	// 平台资金账户对应的会员ID 
//	public static final String MEMBER_ID = "1";
	
	// 服务域区分
//	public static final String EXCHANGE_DOMAIN_ID = "domain1";
		
	//分页默认条数
	public static final int DEFAULT_PAGEABLE_SIZE = 10;
	
	//资金账户支持的银行
	public static final String BANK = "中国银行";
	
	//允许上传文件类型
	public static final String[] UPLOAD_SUFFIXES = { "jpg", "jpeg", "gif","bmp","png","tiff"};
	public static final String UPLOAD_TYPE = "jpg,jpeg,gif,bmp,png,tiff";
	
	//上传文件存储目录
	/*public static final String UPLOAD_ROOT="/opt/images/upload";*/
//	public static final String UPLOAD_ROOT="/usr/local/tomcat7/webapps/img";
//	public static final String UPLOAD_ROOT="D://apache-tomcat-7.0.70/webapps/img";
	
	//图片显示根目录  需要软连接到 文件存储目录    Linux建立软链: ln -s upload 存储目录F:/temp
//	public static final String UPLOAD_SERVER_ROOT="";
	
	
	/**
	 * 图片服务器访问地址
	 */
//	public static final String IMGAE_SERVICE_HOST= "http://192.168.1.215";//http://192.168.1.197(215)
//	public static final String IMGAE_SERVICE_HOST= "http://192.168.1.215:8080/img";//http://192.168.1.197(215)
//	public static final String IMGAE_SERVICE_HOST= "http://192.168.16.228:8080/img";//http://192.168.1.197(215)
//	public static final String IMGAE_SERVICE_HOST= "http://123.57.207.212:1688/img";
		
//	public static final String IMAGE_TMP_DIR= "/home/chin/company/backup/workspace/exchange-PLT/src/main/webapp/upload";//====
							//   /usr/local/tomcat8/webapps/ex/upload
	
	/**
	 * 挂牌类型sale_type
	 */
	public static final String SALE_TYPE_0 = "一口价";
	public static final String SALE_TYPE_1 = "可洽谈";
	
	// 流水号的前缀
	public static final String TM_TAGOUT="tg";
	public static final String TM_DISCUSS="ds";
	public static final String TM_MEMBER_ORDER="or";
	public static final String TM_CONTRACT="co";
	public static final String TM_PURCHASE="pu";
	public static final String TM_DELIVERY="de";
	public static final String TM_DISSENTION="di";
	public static final String TM_MESSAGE="ms";
	public static final String TL_MEMBER_FUND_LOG="fl";
	public static final String TM_RELIEVE="re";
	public static final String TM_FUND_FREEZE="fr";
	public static final String TM_FUND_FREEZE_HISTORY="fh";
	
	/**
	 * 操作结果 提示信息
	 */
	public static final String OPER_SUCCESS_MSG = "操作成功！";
	public static final String OPER_FAIL_MSG = "操作失败！";
	public static final String OPER_ERROR_MSG = "发生错误，操作失败！";
	public static final String JSON_PARSE_ERROR_MSG = "输入的json格式错误！";
	public static final String CASH_NOT_ENOUGH_ERROR_MSG = "资金不足！";
	public static final String PAYPASSWORD_ERROR_MSG = "支付密码不正确！";
	public static final String RESULT_EMPTY_ERROR_MSG = "结果为空！";
	public static final String FUND_NOT_EXIST_MSG = "资金账户不存在！";
	public static final String MEMBER_NOT_EXIST_MSG = "会员账户不存在！";
	
	public static final int USER_SALE = 0;
	public static final int USER_PURCHASE = 1;

	// 标记删除
	public static final Byte MARK_FOR_DELETE_YES = 0; //删除
	public static final Byte MARK_FOR_DELETE_NO = 1; //非删除
	
	// 字典类型
	public static final String DIC_TYPE_MARK_FOR_DELETE = "2";
	public static final String DIC_TYPE_TAGOUT_TYPE = "3";
	public static final String DIC_TYPE_ORDER_STATUS = "31";
	public static final String DIC_TYPE_ORDER_SOURCE = "32";
	public static final String DIC_TYPE_COMP_TYPE = "33";
	public static final String DIC_TYPE_COMP_MODE = "34";
	public static final String DIC_TYPE_COMP_SCALE = "37";
	public static final String DIC_TYPE_PAPER_TYPE = "38";
	public static final String DIC_TYPE_CERT_STATUS = "39";
	public static final String DIC_TYPE_CERT_TYPE = "40";
	public static final String DIC_TYPE_IS_PENALTY = "41";
	public static final String DIC_TYPE_WHO_BREAK = "42";
	public static final String DIC_TYPE_PRODUCT_UNIT = "43";
	public static final String DIC_TYPE_PRODUCT_CLASS = "44";
	public static final String DIC_TYPE_PRODUCT_SPECIFICATIONS = "45";
	public static final String DIC_TYPE_WEIGHT_TYPE = "46";
	public static final String DIC_TYPE_DISCUSS_STATUS = "47";
	public static final String DIC_TYPE_DISCUSS_TYPE = "48";
	public static final String DIC_TYPE_ASSESS_LEVEL = "49";
	public static final String DIC_TYPE_TRADE_TYPE = "50";
	public static final String DIC_TYPE_SALE_TYPE = "51";
	public static final String DIC_TYPE_SALE_PERIOD = "52";
	public static final String DIC_TYPE_DELIVER_TYPE = "53";
	public static final String DIC_TYPE_RECEIVE_TYPE = "54";
	public static final String DIC_TYPE_BALANCE_TYPE = "55";
	public static final String DIC_TYPE_GUARANTEE_TYPE = "56";
	public static final String DIC_TYPE_GUARANTEE_FEE_PAID = "57";
	public static final String DIC_TYPE_IDENTIFY_STATUS = "59";
	public static final String DIC_TYPE_GUARANTEE_STATUS = "60";
	public static final String DIC_TYPE_APPENDIX_TYPE = "61";
	public static final String DIC_TYPE_APPENDIX_USE_TYPE = "62";
	public static final String DIC_TYPE_APPLICATE_RELIEVE = "63";
	public static final String DIC_TYPE_RELIEVE_STATUS = "64";
	public static final String DIC_TYPE_CONTRACT_STATUS = "65";
	public static final String DIC_TYPE_SMS_STATUS = "66";
	public static final String DIC_TYPE_INTEGRAL_SOURCE_STATUS = "67";
	public static final String DIC_TYPE_PACKAGE_TYPE = "68";
	public static final String DIC_TYPE_ASSESS_STATUS = "69";
	public static final String DIC_TYPE_DEAL_TYPE = "70";
	public static final String DIC_TYPE_COMMODITY_LEVEL = "71";
	public static final String DIC_TYPE_FEE_TYPE = "72";
	public static final String DIC_TYPE_PROPOSE_BY = "73";
	public static final String DIC_TYPE_FUND_TYPE = "74";
	public static final String DIC_TYPE_PURCHASE_STATUS = "75";
	public static final String DIC_TYPE_PRODUCT_SPECIFICATIONS_2 = "76";
	public static final String DIC_TYPE_MESSAGE_TYPE = "77";
	public static final String DIC_TYPE_STATUS = "78";
	public static final String DIC_TYPE_FREEZE_STATUS = "79";
	public static final String DIC_TYPE_TRADE_DAY = "80";
	public static final String DIC_TYPE_DISSENT_STATUS = "81";
	public static final String DIC_TYPE_ACCOUNT_TYPE = "82";
	public static final String DIC_TYPE_BIND_STATUS = "83";
	public static final String DIC_TYPE_CERTIFICATE_TYPE = "85";
	
}
