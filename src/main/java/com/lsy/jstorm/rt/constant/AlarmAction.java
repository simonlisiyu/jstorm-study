package com.lsy.jstorm.rt.constant;

import com.lsy.jstorm.rt.entity.SecureLogObject;
import com.lsy.jstorm.utils.HttpUtils;
import net.sf.json.JSONObject;

/**
 * Created by lisiyu on 2016/11/9.
 * 日志log分析alarm的方法类
 */
public class AlarmAction<T extends SecureLogObject> {

    // 发送警报
    public static void sendAlarm(String msg){
        System.out.println("====================alarm====================alarm====================");
        System.out.println(msg);
        System.out.println("====================alarm====================alarm====================");
        System.out.println(msg);
        System.out.println("====================alarm====================alarm====================");
        System.out.println(msg);
        System.out.println("====================alarm====================alarm====================");
        System.out.println(msg);
        System.out.println("====================alarm====================alarm====================");

        String returnCode = HttpUtils.doPost("http://mail.lsy.com",
                "tolist=lisiyu@163.com&cclist=&subject=告警邮件&content="+msg);
    }

    // 第一次提取方法，提取message公共字段
    public static SecureLogObject preDecoding(String sentence){
        SecureLogObject secureLogObject = new SecureLogObject(sentence);
        return secureLogObject;
    }

    // 第二次提取方法, 根据公共字段，提取不同类型的message字段的方法
    public static JSONObject decoding(SecureLogObject preSecureLogObject){
        JSONObject jsonObject;

        // matching alarm map
        // TODO host，datetime，processName
        if(LogRules.PROCESS_ALARM_MAP.get(preSecureLogObject.getProcessName()) != null){
            // decode log
            jsonObject = LogRules.PROCESS_ALARM_MAP.get(preSecureLogObject.getProcessName()).decodeLog(preSecureLogObject);
            // analysis by rule
//            jsonObject = LogRules.PROCESS_ALARM_MAP.get(preSecureLogObject.getProcessName()).analysis(jsonObject);
        } else{
            // Default decode log
            jsonObject = preSecureLogObject.decodeLog(preSecureLogObject);
            // Default no analysis by rule
        }
        return jsonObject;
    }



}
