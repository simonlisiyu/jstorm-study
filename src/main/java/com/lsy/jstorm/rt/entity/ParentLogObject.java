package com.lsy.jstorm.rt.entity;

import java.io.Serializable;

/**
 * Created by lisiyu on 2016/11/17.
 * 日志收集进行Json转换的类，与flume通过kafka传输过来的json string格式匹配
 */
public class ParentLogObject implements Serializable {
    String env;
    String idc;
    String app;
    String service;
    String host;
    String file;
    String body;
    String processName;
    String datetime;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "ParentLogObject{" +
                "env='" + env + '\'' +
                ", idc='" + idc + '\'' +
                ", app='" + app + '\'' +
                ", service='" + service + '\'' +
                ", host='" + host + '\'' +
                ", file='" + file + '\'' +
                ", body='" + body + '\'' +
                ", processName='" + processName + '\'' +
                '}';
    }
}
