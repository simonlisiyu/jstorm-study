package com.lsy.jstorm.rt.topology;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsy.jstorm.rt.constant.LogRules;
import com.lsy.jstorm.rt.entity.ParentLogObject;
import com.lsy.jstorm.utils.StringUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lisiyu on 2016/11/11.
 * Bolt for 对象转换并fieldId提取
 */
public class JSONParserBolt extends BaseBasicBolt {
    private final Logger LOG = LoggerFactory.getLogger(JSONParserBolt.class);
//    private OutputCollector collector;

//    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
//        this.collector = collector;
//    }
//
//    public void cleanup() {
//    }

    public void execute(Tuple tuple, BasicOutputCollector collector) {
        // 拿出kafka consumer拿到的消息
        String sentence = tuple.getString(0);

        // 转成json对象，拿出body，获取log的file名称作为group key给到下一个bolt
        ParentLogObject logObject;
        ObjectMapper objectMapper = new ObjectMapper();
        // ignore Unrecognized field
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            if (!StringUtils.isNotEmpty(sentence)) {
                LOG.warn("json parser bolt: kafka input sentence is null");
            } else {
                LOG.info("sentence:" + sentence);
                logObject = objectMapper.readValue(sentence, ParentLogObject.class);
                String file = logObject.getFile();
                String fieldId = "null";

                if (file.equals(LogRules.SECURE_LOG_FLIE_NAME)) {
                    fieldId = logObject.getBody().split(": ")[0];
                    LOG.info("file:"+file+", fieldId:"+fieldId+", logObject:"+logObject.toString());
                    collector.emit("secure", new Values(fieldId, logObject));
                } else if(file.equals(LogRules.AUDIT_LOG_FILE_NAME)){
                    // TODO /var/log/audit/audit.log
//                    fieldId = logObject.getBody();
//                    LOG.info("file:"+file+", fieldId:"+fieldId+", logObject:"+logObject.toString());
//                    collector.emit("audit", new Values(fieldId, logObject));
                }
            }

        } catch (IOException e) {
            LOG.error("JSONParserBolt Exception:" + e.getMessage());
        }
//        collector.ack(tuple);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("secure", new Fields("fieldId", "logObject"));
//        declarer.declareStream("audit", new Fields("fieldId", "logObject"));
    }
}
