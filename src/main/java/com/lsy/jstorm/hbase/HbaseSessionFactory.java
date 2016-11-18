package com.lsy.jstorm.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * Created by lisiyu on 2016/11/5.
 */
public class HbaseSessionFactory {
    public static Configuration getHbaseConf() {
//        ServerConstants serverConstants = new ServerConstants();
//        serverConstants.getProperties();
        String zk1 = "localhost:2181";
//        String zk2 = serverConstants.getHbaseSlave2Ip();
//        String zk3 = serverConstants.getHbaseSlave3Ip();

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zk1);

        return conf;

    }
}
