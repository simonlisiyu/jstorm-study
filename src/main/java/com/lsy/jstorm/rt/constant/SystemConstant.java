package com.lsy.jstorm.rt.constant;

/**
 * Created by lisiyu on 2016/11/17.
 */
public class SystemConstant {

    /*
    * 常量
    * */
    // HBASE常量
    public static final String RT_HBASE_TABLE = "rt_alarm";
    public static final String RT_HBASE_CF = "info";
    public static final int RT_HBASE_REGIONSERVERS_COUNT = 9;  // hbase集群regionserver数量的基数

    // JSTORM程序常量
    public static final int StatisticsTopologyTickTupleFreqSecs = 10;    //tuple tick 单位时间，单位秒

    // JSTORM集群环境常量
    public static final int STORM_NUM_ACKERS = 1;    // acker的进程数量
    public static final int STORM_NUM_WORKERS = 1;    // 每台supervisor上worker的进程数量
    public static final int TOPOLOGY_MAX_SPOUT_PENDING = 1;    // 设置一个spout task中处于pending状态的最大的tuples数量
    public static final boolean STORM_DEBUG_LEVEL = false;    // 是否输出debug调试信息
}
