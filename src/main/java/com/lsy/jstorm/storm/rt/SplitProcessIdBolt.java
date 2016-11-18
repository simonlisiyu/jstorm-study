package com.lsy.jstorm.storm.rt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lisiyu on 2016/11/11.
 * Bolt for ProcessId提取
 */
public class SplitProcessIdBolt extends BaseRichBolt {
    private OutputCollector collector;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void cleanup() {
    }

    public void execute(Tuple tuple) {
        // 拿出kafka consumer拿到的消息
        String sentence = tuple.getString(0);

        // 转成json对象，拿出body，获取processId作为key给到下一个bolt
        String processId = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JSONObject jsonObj = objectMapper.readValue(sentence, JSONObject.class);
            processId = jsonObj.getString("body").split(": ")[0];
        } catch (IOException e) {
            processId = sentence.split(": ")[0];
//            e.printStackTrace();
        }

//        Pattern patternProcessId = Pattern.compile("\\[(\\d+)\\]");
//        Matcher matcher = patternProcessId.matcher(sentence);
//        while(matcher.find()) {
//            processId = matcher.group();
//        }

//        collector.emit(new Values(processId, sentence));
        System.out.println("SplitProcessIdBolt,processId:"+processId);
        System.out.println("SplitProcessIdBolt,sentence:"+sentence);
        collector.emit(new Values(processId, sentence));
        collector.ack(tuple);
    }

    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("processId", "log"));
//    }
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("processId", "log"));
    }
}
