package com.lsy.jstorm.storm.rt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.lsy.jstorm.rt.constant.AlarmAction;
import com.lsy.jstorm.rt.entity.SecureLogObject;

import java.util.List;
import java.util.Map;

/**
 * Created by lisiyu on 2016/11/9.
 * Bolt for 日志预处理分析类，第一步合并相同processId，并前缀分析
 */
public class PreDecodingBolt extends BaseRichBolt {

    private OutputCollector collector;
    private SecureLogObject preSecureLogObject = new SecureLogObject();

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    public void cleanup() {
    }

    public void execute(Tuple tuple) {
        List<Object> sentenceList = tuple.getValues();

        // 遍历相同processId的log，进行预处理，分析头信息
        for (Object sentence : sentenceList) {
            System.out.println("====================[====================[====================");
            System.out.println("====================sentence.contain====================" +
                    sentence.toString().contains(": ") +
                    "[====================");
            System.out.println(sentence);
            if (sentence.toString().contains(": ")) {
                SecureLogObject newSecureLogObject = AlarmAction.preDecoding(sentence.toString());
                preSecureLogObject.setDatetime(newSecureLogObject.getDatetime());
//                preSecureLogObject.setHostname(newSecureLogObject.getHostname());
                preSecureLogObject.setProcessName(newSecureLogObject.getProcessName());
                preSecureLogObject.setLog(preSecureLogObject.getLog().concat(" " + newSecureLogObject.getLog()));
            }
        }
        System.out.println("PreDecodingBolt,preDecode:"+preSecureLogObject.toString());
        collector.emit("preDecode", new Values(preSecureLogObject));

    }

//    public void execute(Tuple tuple) {
//        String sentence = tuple.getString(0);
//        System.out.println("PreDecodingBolt,sentence:"+sentence);
//
//        // 遍历相同processId的log，进行预处理，分析头信息
//        SecureLogObject newSecureLogObject = AlarmAction.preDecoding(sentence);
//        preSecureLogObject.setDatetime(newSecureLogObject.getDatetime());
//        preSecureLogObject.setHostname(newSecureLogObject.getHostname());
//        preSecureLogObject.setProcessName(newSecureLogObject.getProcessName());
//        preSecureLogObject.setLog(preSecureLogObject.getLog().concat(" "+ newSecureLogObject.getLog()));
//
//        System.out.println("PreDecodingBolt,preDecode:"+preSecureLogObject.toString());
//        collector.emit("preDecode", new Values(preSecureLogObject));
//    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("preDecode", new Fields("preDecode"));
    }

//    Map<String,SecureLogObject> preAlarmObjectMap = new HashedMap();

//    @Override
//    public void execute(Tuple tuple, BasicOutputCollector collector) {
//        List<Object> sentenceList = tuple.getValues();
//
//        SecureLogObject preSecureLogObject = new SecureLogObject();
//
//        // 遍历相同processId的log，进行预处理，分析头信息
//        for(Object sentence : sentenceList){
//            System.out.println("====================[====================[====================");
//            System.out.println("====================sentence.contain====================" +
//                    sentence.toString().contains(": ")+
//                    "[====================");
//            System.out.println(sentence);
//            if(!sentence.toString().contains(": ")){
//                if (preAlarmObjectMap.get(sentence) != null){
//                    preSecureLogObject = preAlarmObjectMap.get(sentence);
//                }
//            } else {
//                SecureLogObject newSecureLogObject = AlarmAction.preDecoding(sentence.toString());
//                preSecureLogObject.setDatetime(newSecureLogObject.getDatetime());
//                preSecureLogObject.setHostname(newSecureLogObject.getHostname());
//                preSecureLogObject.setProcessName(newSecureLogObject.getProcessName());
//                preSecureLogObject.setLog(preSecureLogObject.getLog().concat(" "+ newSecureLogObject.getLog()));
//
////                Pattern patternProcessId = Pattern.compile("\\[(\\d+)\\]");
////                Matcher matcher = patternProcessId.matcher(sentence.toString());
////                while(matcher.find()) {
////                    String processId = matcher.group();
////                    preAlarmObjectMap.put(processId, preSecureLogObject);
////                }
//
//                String processId = sentence.toString().split(":")[0];
//                preAlarmObjectMap.put(processId, preSecureLogObject);
//
//            }
//        }
//
//        // 再处理，分析细节
//        JSONObject jsonObject = AlarmAction.decoding(preSecureLogObject);
//        System.out.println("====================[====================[====================");
//        System.out.println("====================jsonObject====================" +
//                jsonObject.toString()+"[====================");
//        System.out.println("====================[====================[====================");
//
//        // 分析结果存HBase
//        System.out.println(HBaseLogUtils.saveAlarmObjectToHBase(jsonObject));
//
//        // 如果是alarm，触发动作
//        if(jsonObject.get("alarmAction").equals("true")){
//            AlarmAction.sendAlarm(jsonObject.toString());
//        }
//
//        collector.emit(new Values(jsonObject));
//    }




}
