package com.lsy.jstorm.storm.wordcount;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class RandomSentenceSpout extends BaseRichSpout {
    SpoutOutputCollector _collector;
    Random _rand;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        _collector = collector;
        _rand = new Random();

    }

    @Override
    public void nextTuple() {
        Utils.sleep(100);
        String[] sentences = new String[]{
                "the cow jumped over the moon",
                "this is a test line",
                "something is not right",
                "hadoop with hbase will be good",
                "storm and spark streaming is real time compute tools"
        };
        String sentence = sentences[_rand.nextInt(sentences.length)];
        _collector.emit(new Values(sentence));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declare) {
        declare.declare(new Fields("word"));
    }

    @Override
    public void ack(Object id){

    }

    @Override
    public void fail(Object id){

    }
}
