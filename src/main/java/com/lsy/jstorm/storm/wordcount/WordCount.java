package com.lsy.jstorm.storm.wordcount;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.lsy.jstorm.hbase.HBaseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class WordCount extends BaseBasicBolt {
    private static final Logger logger = LoggerFactory.getLogger(WordCount.class);
    Map<String, Integer> counts = new HashMap<>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String word = tuple.getString(0);
        Integer count = counts.get(word);
        if(count == null) count = 0;
        count++;
        counts.put(word, count);
        collector.emit(new Values(word, count));
    }
//    public void execute(Tuple tuple, BasicOutputCollector collector) {
//        List<Object> intList = tuple.getValues();
//        String word = tuple.getMessageId().toString();
//        int count = 0;
//        for(Object i : intList){
//            count++;
//        }
//        counts.put(word, count);
//        collector.emit(new Values(word, count));
//    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }

    @Override
    public void cleanup(){
        String table = "word";
        String cf = "s";
        try {
            if (HBaseClient.exists(table)) {
            } else {
                HBaseClient.create(table, cf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int rowid = 0;

        for(Map.Entry<String, Integer> entry : counts.entrySet()){
            try {
                HBaseClient.put("word", rowid+"", cf, entry.getKey(), entry.getValue()+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("===================");
            logger.info("===================");
            logger.info("===================");
            logger.info("===================");
            logger.info("===================");
            logger.info(entry.getKey() + ":" + entry.getValue());
            logger.info("===================");
            logger.info("===================");
            logger.info("===================");
            logger.info("===================");
            logger.info("===================");
            rowid++;
        }
    }
}
