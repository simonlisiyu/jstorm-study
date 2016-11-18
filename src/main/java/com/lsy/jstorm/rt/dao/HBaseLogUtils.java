package com.lsy.jstorm.rt.dao;

import com.lsy.jstorm.hbase.HBaseClient;
import com.lsy.jstorm.rt.constant.SystemConstant;
import com.lsy.jstorm.utils.DateUtils;
import com.lsy.jstorm.utils.IdGen;
import com.lsy.jstorm.utils.StringUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by lisiyu on 2016/11/9.
 * HBase工具类 for 处理后的日志
 */
public class HBaseLogUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HBaseLogUtils.class);

    public static boolean saveLogObjectToHBase(JSONObject jsonObject) {
        try{
            if (HBaseClient.exists(SystemConstant.RT_HBASE_TABLE)) {
                putToHBase(SystemConstant.RT_HBASE_TABLE, SystemConstant.RT_HBASE_CF, jsonObject);
            } else {
                HBaseClient.create(SystemConstant.RT_HBASE_TABLE, SystemConstant.RT_HBASE_CF);
                putToHBase(SystemConstant.RT_HBASE_TABLE, SystemConstant.RT_HBASE_CF, jsonObject);
            }
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private static void putToHBase(String table, String cf, JSONObject jsonObject) throws ParseException {
        Date date = DateUtils.strToDate(jsonObject.getString("datetime"));
        LOG.info("HBaseLogUtils date = "+date.toString());
        Timestamp ts = DateUtils.dateToTimestamp(date);
        LOG.info("HBaseLogUtils ts = "+ts.toString());
        String rowId = IdGen.prefix(ts)+
                "|"+jsonObject.getString("env")+
                "|"+jsonObject.getString("idc")+
                "|"+jsonObject.getString("app")+
                "|"+jsonObject.getString("service")+
                "|"+jsonObject.getString("host")+
                "|"+jsonObject.getString("processName")+
                "|"+ts.getTime();
        LOG.info("HBaseLogUtils rowId = "+rowId);
        for(Object key : jsonObject.keySet()){
            try {
                if (StringUtils.isNotEmpty(jsonObject.getString(key.toString()))) {
                    HBaseClient.append(table, rowId, cf, key.toString(), jsonObject.getString(key.toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
