package com.lsy.jstorm.rt.topology;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.lsy.jstorm.rt.constant.AlarmAction;
import com.lsy.jstorm.rt.dao.HBaseLogUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lisiyu on 2016/11/9.
 * Bolt for 日志预处理分析类，tick合并相同fieldId，并分析secure的前缀
 */
public class StorageBolt extends BaseBasicBolt {
    private final Logger LOG = LoggerFactory.getLogger(StorageBolt.class);

//    private OutputCollector collector;
//
//    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
//        this.collector = collector;
//    }
//
//    public void cleanup() {
//    }

    public void execute(Tuple tuple, BasicOutputCollector collector) {
        JSONObject jsonObject = (JSONObject) tuple.getValue(0);

        // 分析结果存HBase
        if(HBaseLogUtils.saveLogObjectToHBase(jsonObject)){
            LOG.info("success for put to hbase, jsonObject:"+jsonObject.toString());
        } else {
            LOG.error("fail for put to hbase, jsonObject:"+jsonObject.toString());
        }

        // 如果是alarm，触发动作
        if(jsonObject.getString("isAlarmAction").equals("true")){
            LOG.info("alarmAction!! Will send email!!!");
            AlarmAction.sendAlarm(jsonObject.toString());
        }

//        LOG.info("ack to Acktor.");
//        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

}
