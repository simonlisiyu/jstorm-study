package com.lsy.jstorm.rt.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.lsy.jstorm.rt.constant.SystemConstant;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * Created by lisiyu on 2016/11/9.
 * Jstorm的Topology类
 */
public class LogTopology {
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
        spoutConfig.scheme=new SchemeAsMultiScheme(new KafkaMessageScheme());
        //where to start reading data, 2: kafka.api.OffsetRequest.EarliestTime() or LatestTime() , from beginning or new messages
        spoutConfig.startOffsetTime = kafka.api.OffsetRequest.LatestTime();

        Config conf=new Config();
        conf.setNumAckers(SystemConstant.STORM_NUM_ACKERS);
        conf.setNumWorkers(SystemConstant.STORM_NUM_WORKERS);
        conf.setDebug(SystemConstant.STORM_DEBUG_LEVEL);

        //设置一个spout task中处于pending状态的最大的tuples数量
        conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, SystemConstant.TOPOLOGY_MAX_SPOUT_PENDING);

        /**
        * Topology Build and Submit
        * */
        TopologyBuilder builder =new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig),1);   //最后一个参数决定storm启动的工作线程数量
        builder.setBolt("json_bolt", new JSONParserBolt(),1).shuffleGrouping("spout");
        builder.setBolt("secure_bolt", new SecureDecodingBolt(),1).fieldsGrouping("json_bolt", "secure", new Fields("fieldId"));
        builder.setBolt("storage_bolt", new StorageBolt(),1).shuffleGrouping("secure_bolt", "storage");

        if(args.length==0){
            LocalCluster cluster = new LocalCluster();
            //提交本地集群
            cluster.submitTopology("LogTopoly", conf, builder.createTopology());
//            //等待6s之后关闭集群
//            Thread.sleep(6000);
//            //关闭集群
//            cluster.shutdown();
        }
        StormSubmitter.submitTopology("LogTopoly", conf, builder.createTopology());
    }
}
