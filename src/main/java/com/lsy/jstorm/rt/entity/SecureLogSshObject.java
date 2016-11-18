package com.lsy.jstorm.rt.entity;

import com.lsy.jstorm.rt.topology.SecureDecodingBolt;
import com.lsy.jstorm.utils.StringUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lisiyu on 2016/11/9.
 * 定义/var/log/secure日志SSH类型的Alarm对象
 */
public class SecureLogSshObject extends SecureLogObject {
    private final Logger LOG = LoggerFactory.getLogger(SecureLogSshObject.class);
    String srcIp;
    String username;
    String attackPurpose;
    String attackType;


    @Override
    public JSONObject decodeLog(SecureLogObject secureLogObject){
        LOG.info("SecureLogSshObject decodeLog method, secureLogObject:"+secureLogObject.toString());
        SecureLogSshObject secureLogSshObject = new SecureLogSshObject(secureLogObject);
        if(secureLogSshObject.getLog().contains("Failed password")){
            secureLogSshObject.setAttackPurpose("password crack");
            secureLogSshObject.setAttackType("login");
            secureLogSshObject.setIsAlarm("true");
            secureLogSshObject.setIsAlarmAction("true");
        }
        return JSONObject.fromObject(secureLogSshObject);
    }

    public SecureLogSshObject() {
    }

    public SecureLogSshObject(SecureLogObject secureLogObject) {
        super();
        // ParentLogObject
        this.setEnv(secureLogObject.getEnv());
        this.setIdc(secureLogObject.getIdc());
        this.setApp(secureLogObject.getApp());
        this.setService(secureLogObject.getService());
        this.setHost(secureLogObject.getHost());
        this.setFile(secureLogObject.getFile());
//        this.setBody(secureLogObject.getBody());

        // SecureLogObject
        this.setDatetime(secureLogObject.getDatetime());
        this.setProcessName(secureLogObject.getProcessName());
        this.setLog(secureLogObject.getLog()+"||");

        // SSH log get srcId and username
//        Pattern patternIp = Pattern.compile("(\\d+)[.](\\d+)[.](\\d+)[.](\\d+)");
        Pattern patternIp = Pattern.compile(
                "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
        Pattern patternUser = Pattern.compile("for (\\w+) from");
        Matcher matcher = patternIp.matcher(log);
        while(matcher.find()) {
            this.setSrcIp(matcher.group());
        }
        matcher = patternUser.matcher(log);
        while(matcher.find()) {
            this.setUsername(matcher.group().split(" ")[matcher.group().split(" ").length-2]);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getAttackPurpose() {
        return attackPurpose;
    }

    public void setAttackPurpose(String attackPurpose) {
        this.attackPurpose = attackPurpose;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }


}
