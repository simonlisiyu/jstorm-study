package com.lsy.jstorm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class TestTopology implements ILogTopology {
//    @Override
    public void start(Properties properties) throws AlreadyAliveException, InvalidTopologyException, InterruptedException, IOException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("testspout", new TestSpout(), 1);
        builder.setBolt("testbolt", new TestBolt(), 2).shuffleGrouping("testspout");
        Config conf = ConfigUtils.getStormConfig(properties);
        conf.setNumAckers(1);
        StormSubmitter.submitTopology("testtopology", conf, builder.createTopology());
        System.out.println("storm cluster will start");
    }

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("testspout", new TestSpout(), 1);
        builder.setBolt("testbolt", new TestBolt(), 2).shuffleGrouping("testspout");
        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumAckers(1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());
        System.out.println("storm cluster will start");

        Utils.sleep(10000);
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
