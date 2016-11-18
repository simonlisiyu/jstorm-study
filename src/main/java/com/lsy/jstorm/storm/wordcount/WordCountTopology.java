package com.lsy.jstorm.storm.wordcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class WordCountTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new RandomSentenceSpout(), 5);
        builder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
        builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));
//        builder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", "word", new Fields("count"));

        Config conf = new Config();
        conf.setDebug(true);

        if(args !=null && args.length > 0){
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology("WordCountTopology", conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("WordCountTopology", conf, builder.createTopology());
            Thread.sleep(10000);
            cluster.shutdown();
        }

    }
}
