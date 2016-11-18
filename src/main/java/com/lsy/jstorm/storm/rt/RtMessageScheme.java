package com.lsy.jstorm.storm.rt;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by lisiyu on 2016/11/7.
 * Spout for Kafka传入的message处理
 */
public class RtMessageScheme implements Scheme{
    private static final Logger LOGGER = LoggerFactory.getLogger(RtMessageScheme.class);
    public List<Object> deserialize(byte[] ser) {
        try {
            //从kafka中读取的值直接序列化为UTF-8的str
            String mString=new String(ser, "UTF-8");
            System.out.println("KafkaMessageScheme:"+mString);
            return new Values(mString);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            LOGGER.error("Cannot parse the provided message");
        }
        return null;
    }

    public Fields getOutputFields() {
        // TODO Auto-generated method stub
        return new Fields("msg");
    }
}
