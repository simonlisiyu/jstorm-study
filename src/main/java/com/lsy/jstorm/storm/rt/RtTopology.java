package com.lsy.jstorm.storm.rt;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * Created by lisiyu on 2016/11/9.
 * Jstorm的Topology类
 */
public class RtTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {

        /**
        * Kafka Spout配置
        * */
        //配置zookeeper 主机:端口号
        BrokerHosts brokerHosts =new ZkHosts("172.27.36.85:2181,172.27.36.86:2181,172.27.36.87:2181,172.27.36.98:2181,172.27.36.99:2181"
                ,"/rt/kafka/brokers");
        //接收消息队列的主题
        String topic="com.lsy.rt";
        //zookeeper汇报offset信息的root路径，如果该项为空则等待最新
        String offsetZkRoot="/rt";
        //存储该spout id的消费offset信息,譬如以topoName来命名，记录offset需要与上面的offsetZkRoot一致
        String offsetZkId="rt";
        SpoutConfig spoutConfig=new SpoutConfig(brokerHosts, topic, offsetZkRoot, offsetZkId);

        //设置Scheme如何处理kafka message消息队列输入流
        spoutConfig.scheme=new SchemeAsMultiScheme(new RtMessageScheme());
        //where to start reading data, 2: kafka.api.OffsetRequest.EarliestTime() or LatestTime() , from beginning or new messages
        spoutConfig.startOffsetTime = kafka.api.OffsetRequest.LatestTime();

        Config conf=new Config();
        //不输出调试信息
        conf.setDebug(false);
        //设置一个spout task中处于pending状态的最大的tuples数量
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        /**
        * Topology Build and Submit
        * */
        TopologyBuilder builder =new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig),1);   //最后一个参数决定storm启动的worker数量
//        builder.setBolt("bolt1", new SplitProcessIdBolt(),1).shuffleGrouping("spout");
//        builder.setBolt("bolt2", new PreDecodingBolt(),1).fieldsGrouping("bolt1", new Fields("processId", "log"));
//        builder.setBolt("bolt2", new PreDecodingBolt(),1).fieldsGrouping("bolt1", new Fields("processId"));
//        builder.setBolt("bolt3", new DecodingBolt(),1).shuffleGrouping("bolt2", "preDecode");



        if(args.length==0){
            LocalCluster cluster = new LocalCluster();
            //提交本地集群
            cluster.submitTopology("IDSTopoly", conf, builder.createTopology());
//            //等待6s之后关闭集群
//            Thread.sleep(6000);
//            //关闭集群
//            cluster.shutdown();
        }
        StormSubmitter.submitTopology("IDSTopoly", conf, builder.createTopology());
    }
}
