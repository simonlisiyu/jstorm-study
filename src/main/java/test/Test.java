package test;

import com.lsy.jstorm.rt.entity.SecureLogObject;
import com.lsy.jstorm.rt.entity.SecureLogSshObject;
import com.lsy.jstorm.utils.DateUtils;
import com.lsy.jstorm.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by lisiyu on 2016/11/9.
 */
public class Test {
    public static void main(String[] args) {
//        testSplit();
//        testMap();
//        Timestamp ts = DateUtils.dateToTimestamp(new Date());
////        Long l = 234234234L;
////        int i = Long.valueOf(l).hashCode();
//
//        System.out.println(ts.toString());
//        System.out.println(ts.getTime());
//        int i = Long.valueOf(ts.getTime()).hashCode();
//        byte prefix = (byte)(i%9);
//        System.out.println(prefix);

        SecureLogObject secureLogObject = new SecureLogObject();
        JSONObject jsonObject = JSONObject.fromObject(secureLogObject);
        if(StringUtils.isNotEmpty(jsonObject.getString("isAlarmAction"))){
            System.out.println("dfs");
        }
        System.out.println(jsonObject.get("isAlarmAction"));
        System.out.println(jsonObject.get("body"));
        System.out.println(jsonObject.toString());
        System.out.println(secureLogObject.getIsAlarmAction());

    }



    static void testObjectConstructor(){
        String sentence = "Mar 29 09:56:37 firewall sshd[1423]: " +
                "Failed password for illegal user jordan from 61.244.242.25 port 48631 ssh2";
//        JSONObject jsonObject = JSONObject.fromObject(new SecureLogObject(sentence));
        new SecureLogSshObject(new SecureLogObject(sentence));
//        System.out.println(jsonObject);
    }



    static void testMap(){
        Map<Integer, String> map = new HashedMap();
        map.put(1,"1");
        System.out.println(map.get(0) == null);


    }


}
