package com.lsy.jstorm.storm.rt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.lsy.jstorm.rt.constant.AlarmAction;
import com.lsy.jstorm.rt.dao.HBaseLogUtils;
import com.lsy.jstorm.rt.entity.SecureLogObject;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * Created by lisiyu on 2016/11/9.
 * Bolt for 日志处理分析类，第二步详细分析，并存储、报警
 */
public class DecodingBolt extends BaseRichBolt {
    private OutputCollector collector;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void cleanup() {
    }

    public void execute(Tuple tuple) {
        SecureLogObject preSecureLogObject = (SecureLogObject)tuple.getValue(0);
        System.out.println("DecodingBolt,preSecureLogObject:"+preSecureLogObject.toString());

        // 再处理，分析细节
        JSONObject jsonObject = AlarmAction.decoding(preSecureLogObject);
        System.out.println("====================[====================[====================");
        System.out.println("====================jsonObject====================" +
                jsonObject.toString()+"[====================");
        System.out.println("====================[====================[====================");

        // 分析结果存HBase
        System.out.println(HBaseLogUtils.saveLogObjectToHBase(jsonObject));

        // 如果是alarm，触发动作
        if(jsonObject.get("alarmAction").equals("true")){
            AlarmAction.sendAlarm(jsonObject.toString());
        }

        collector.emit(new Values(jsonObject));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("decode"));
    }
}
