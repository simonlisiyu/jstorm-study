package com.lsy.jstorm.rt.constant;

import com.lsy.jstorm.rt.entity.SecureLogObject;
import com.lsy.jstorm.rt.entity.SecureLogSshObject;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by lisiyu on 2016/11/9.
 * Alarm规则rule类，和RT常量类
 */
public class LogRules {
    /*
    * Alarm规则
    * */
    public static Map<String, SecureLogObject> HOST_ALARM_MAP = new HashedMap();
    public static String CLOCK_ALARM_START = "18:00";
    public static String CLOCK_ALARM_END = "9:00";
    public static Map<String, SecureLogObject> PROCESS_ALARM_MAP = new HashedMap();

    static {
        HOST_ALARM_MAP.put("127.0.0.1", new SecureLogObject());
        PROCESS_ALARM_MAP.put("sshd", new SecureLogSshObject());
    }

    /*
    * RT常量
    * */
//    // HBASE常量
//    public static String RT_HBASE_TABLE = "rt_alarm";
//    public static String RT_HBASE_CF = "info";
//    public static int RT_HBASE_REGIONSERVERS_COUNT = 9;  // hbase集群regionserver数量的基数

    // Secure Log常量
    public static String SECURE_LOG_FLIE_NAME = "/var/log/secure";
    public static String SECURE_LOG_DATE_FORMATE = "MMM dd hh:mm:ss yyyy";

    // Audit Log常量
    public static String AUDIT_LOG_FILE_NAME = "/var/log/audit/audit.log";
}
