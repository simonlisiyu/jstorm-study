package com.lsy.jstorm.rt.entity;

import com.lsy.jstorm.rt.constant.LogRules;
import com.lsy.jstorm.rt.topology.SecureDecodingBolt;
import com.lsy.jstorm.utils.DateUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lisiyu on 2016/11/9.
 * 定义/var/log/secure日志通用Alarm对象
 */
public class SecureLogObject extends ParentLogObject implements Serializable {
    private final Logger LOG = LoggerFactory.getLogger(SecureLogObject.class);

    String isAlarm;
    String isAlarmAction;
//    String hostname;
    String log;

    public JSONObject decodeLog(SecureLogObject secureLogObject){
        return JSONObject.fromObject(secureLogObject);
    }

    public SecureLogObject() {
    }

    public SecureLogObject(ParentLogObject parentLogObject) {
        try{
            this.setEnv(parentLogObject.getEnv());
            this.setIdc(parentLogObject.getIdc());
            this.setApp(parentLogObject.getApp());
            this.setService(parentLogObject.getService());
            this.setHost(parentLogObject.getHost());
            this.setFile(parentLogObject.getFile());
            this.setBody(parentLogObject.getBody());

            String[] word = parentLogObject.getBody().split(": ");
            String log = parentLogObject.getBody().substring(16);
            this.setLog(log);
            String dateStr = formatDateStr(word[0].substring(0,15));
            this.setDatetime(dateStr);
            String otherStr = word[0].substring(16);
            String processName = otherStr.split(" ")[1].split(":")[0].split("\\[")[0];
            this.setProcessName(processName);
        }catch (Exception ex){
            LOG.error("fail try for parse to SecureLogObject, parentLogObject="+parentLogObject.toString());
        }
    }

    // old method for RTTopology
    public SecureLogObject(String sentence) {
        try{
            String[] word = sentence.split(": ");
            String log = sentence.substring(16);
//            System.out.println("====================[====================[====================");
//            System.out.println("====================word[0].substring(0,15)====================" +
//                    word[0].substring(0,15)+"[====================");
//            System.out.println("====================[====================[====================");
            String dateStr = formatDateStr(word[0].substring(0,15));
//            System.out.println("====================[====================[====================");
//            System.out.println("====================dateStr====================" +
//                    dateStr+"[====================");
//            System.out.println("====================[====================[====================");
            String otherStr = word[0].substring(16);
            String hostname = otherStr.split(" ")[0];
            String processName = otherStr.split(" ")[1].split(":")[0].split("\\[")[0];
//            System.out.println("====================[====================[====================");
//            System.out.println("====================processName====================" +
//                    processName+"[====================");
//            System.out.println("====================[====================[====================");

            this.setDatetime(dateStr);
//            this.setHostname(hostname);
            this.setProcessName(processName);
            this.setLog(log);
        }catch (Exception ex){
            this.setDatetime("empty");
//            this.setHostname("empty");
            this.setProcessName("empty");
            this.setLog(sentence);
        }
    }

//    public static void main(String[] args) throws ParseException {
//        String dateStr = "Nov 16 12:59:09";
//        System.out.println(formatDateStr(dateStr));
//    }

    public static String formatDateStr(String dateStr) throws ParseException {
        Date date = DateUtils.strToDate(dateStr+" "+ Calendar.getInstance().get(Calendar.YEAR), LogRules.SECURE_LOG_DATE_FORMATE);
        return DateUtils.sdf.format(date);
    }

    public String getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(String isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getIsAlarmAction() {
        return isAlarmAction;
    }

    public void setIsAlarmAction(String isAlarmAction) {
        this.isAlarmAction = isAlarmAction;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "SecureLogObject{" +
                "isAlarm=" + isAlarm +
                ", isAlarmAction=" + isAlarmAction +
                ", log='" + log + '\'' +
                '}';
    }
}
