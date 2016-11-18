package com.lsy.jstorm.storm.kafka;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
//import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import storm.kafka.bolt.KafkaBolt;

/**
 * Created by lisiyu on 2016/11/7.
 */
public class KafkaTopoly {
    public static void main(String [] args) throws Exception{
        //配置zookeeper 主机:端口号
        BrokerHosts brokerHosts =new ZkHosts("172.27.36.85:2181,172.27.36.86:2181,172.27.36.87:2181,172.27.36.98:2181,172.27.36.99:2181"
                ,"/rt/kafka/brokers");
        //接收消息队列的主题
        String topic="test";
        //zookeeper设置文件中的配置，如果zookeeper配置文件中设置为主机名：端口号 ，该项为空
        String zkRoot="";
        //任意
        String spoutId="rt";
        SpoutConfig spoutConfig=new SpoutConfig(brokerHosts, topic, zkRoot, spoutId);
        //设置如何处理kafka消息队列输入流
        spoutConfig.scheme=new SchemeAsMultiScheme(new MessageScheme());
        Config conf=new Config();
        //不输出调试信息
        conf.setDebug(false);
        //设置一个spout task中处于pending状态的最大的tuples数量
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        Map<String, String> map=new HashMap<String,String>();
        // 配置Kafka broker地址
        map.put("metadata.broker.list", "172.27.36.57:9092,172.27.36.59:9092,172.27.36.60:9092");
        // serializer.class为消息的序列化类
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        // 配置KafkaBolt生成的topic
        conf.put("topic", "receiver");


        TopologyBuilder builder =new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig),1);
        builder.setBolt("bolt1", new QueryBolt(),1).setNumTasks(1).shuffleGrouping("spout");
        builder.setBolt("bolt2", new KafkaBolt<String, String>(),1).setNumTasks(1).shuffleGrouping("bolt1");
        if(args.length==0){
            LocalCluster cluster = new LocalCluster();
            //提交本地集群
            cluster.submitTopology("KafkaTopoly", conf, builder.createTopology());
//            //等待6s之后关闭集群
//            Thread.sleep(6000);
//            //关闭集群
//            cluster.shutdown();
        }
        StormSubmitter.submitTopology("KafkaTopoly", conf, builder.createTopology());
    }
}
