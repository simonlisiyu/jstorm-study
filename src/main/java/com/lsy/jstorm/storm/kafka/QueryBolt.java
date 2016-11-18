package com.lsy.jstorm.storm.kafka;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.lsy.jstorm.hbase.HBaseClient;

/**
 * Created by lisiyu on 2016/11/7.
 */
public class QueryBolt implements IRichBolt {

    List<String> list;
    OutputCollector collector;
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {

        list=new ArrayList<String>();
        this.collector=collector;

    }

    public void execute(Tuple input) {
        // TODO Auto-generated method stub
        String str=(String) input.getValue(0);
        //将str加入到list
        list.add(str);
        //发送ack
        collector.ack(input);

        String timeStamp = System.currentTimeMillis()+"";
        String rowId = "row-"+timeStamp;
        try {
            HBaseClient.put("word", rowId, "storm", "data", str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //发送该str
        collector.emit(new Values(str));
    }

    public void cleanup() {//topology被killed时调用
        //将list的值写入到文件
        try {
            FileOutputStream outputStream=new FileOutputStream("/export/servsers/"+this+".txt");
            PrintStream p=new PrintStream(outputStream);
            p.println("begin!");
            p.println(list.size());
            for(String tmp:list){
                p.println(tmp);
            }
            p.println("end!");
            try {
                p.close();
                outputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("message"));

    }

    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

}
