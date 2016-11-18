package com.lsy.jstorm.utils;

import com.lsy.jstorm.rt.constant.SystemConstant;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.UUID;
/**
 * Created by lisiyu on 2016/11/10.
 */
public abstract class IdGen {

    @SuppressWarnings("unused")
    private static SecureRandom random = new SecureRandom();

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Byte prefix(Timestamp ts) {
        int i = Long.valueOf(ts.getTime()).hashCode();
        byte prefix = (byte)(i % SystemConstant.RT_HBASE_REGIONSERVERS_COUNT);
        return prefix;
    }
}
