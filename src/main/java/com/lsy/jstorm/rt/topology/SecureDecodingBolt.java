package com.lsy.jstorm.rt.topology;

import backtype.storm.Config;
import backtype.storm.Constants;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.lsy.jstorm.rt.constant.AlarmAction;
import com.lsy.jstorm.rt.constant.LogRules;
import com.lsy.jstorm.rt.entity.ParentLogObject;
import com.lsy.jstorm.rt.entity.SecureLogObject;
import com.lsy.jstorm.rt.constant.SystemConstant;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lisiyu on 2016/11/9.
 * Bolt for 日志预处理分析类，tick合并相同fieldId，并分析secure的前缀
 */
public class SecureDecodingBolt extends BaseBasicBolt {
    private final Logger LOG = LoggerFactory.getLogger(SecureDecodingBolt.class);

//    private OutputCollector collector;
//
//    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
//        this.collector = collector;
//    }
//
//    public void cleanup() {
//    }

    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, SystemConstant.StatisticsTopologyTickTupleFreqSecs);

        return conf;
    }

    public void execute(Tuple tuple, BasicOutputCollector collector) {
        if (isTickTuple(tuple)){
            LOG.info("tuple is a tick tuple.");
            List<Object> sentenceList = tuple.getValues();
            // 遍历相同processId的log，进行预处理，分析头信息
            for(Object setence : sentenceList){
                LOG.info("tick tuple, setence:"+setence.toString());
            }
//            collector.ack(tuple);
        }else{
            LOG.info("tuple is not a tick tuple.");
//            String fieldId = tuple.getString(0);
            ParentLogObject logObject = (ParentLogObject) tuple.getValue(1);

            // JSONObject null
            // cc return
            if (logObject == null) {
                LOG.warn("logObject is null! ack and return this tuple.");
//                collector.ack(tuple);
                return;
            }

            // secure log进行预处理，分析头信息
            SecureLogObject secureLogObject = new SecureLogObject(logObject);

            // 再提取方法, 根据公共字段，提取不同类型的message字段的方法
            JSONObject jsonObject;
            // matching alarm map
            // TODO host，datetime，processName
            if(LogRules.PROCESS_ALARM_MAP.get(secureLogObject.getProcessName()) != null){
                LOG.info("SecureDecodingBolt, processName():"+secureLogObject.getProcessName());
                // decode log
                jsonObject = LogRules.PROCESS_ALARM_MAP.get(secureLogObject.getProcessName()).decodeLog(secureLogObject);
            } else{
                // Default decode log
                jsonObject = secureLogObject.decodeLog(secureLogObject);
            }

            LOG.info("SecureDecodingBolt, jsonObject:"+jsonObject.toString());
            collector.emit("storage", new Values(jsonObject));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("storage", new Fields("jsonObject"));
    }

    private boolean isTickTuple(Tuple tuple) {
        String sourceComponent = tuple.getSourceComponent();
        String sourceStreamId = tuple.getSourceStreamId();

        return sourceComponent.equals(Constants.SYSTEM_COMPONENT_ID)
                && sourceStreamId.equals(Constants.SYSTEM_TICK_STREAM_ID);
    }
}
