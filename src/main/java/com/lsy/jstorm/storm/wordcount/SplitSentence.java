package com.lsy.jstorm.storm.wordcount;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class SplitSentence extends BaseBasicBolt {
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String sentence = tuple.getString(0);
        for(String word : sentence.split(" ")){
            collector.emit(new Values(word));
        }
    }
//    public void execute(Tuple tuple, BasicOutputCollector collector) {
//        String sentence = tuple.getString(0);
//        for(String word : sentence.split(" ")){
//            collector.emit(word, new Values(1));
//        }
//    }

    @Override
//    public void declareOutputFields(OutputFieldsDeclarer declarer) {
//        declarer.declare(new Fields("word"));
//    }
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("word", new Fields("count"));
    }
}
