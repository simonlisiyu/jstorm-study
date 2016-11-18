package test;

import com.lsy.jstorm.rt.entity.SecureLogObject;
import net.sf.json.JSONObject;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lisiyu on 2016/11/11.
 */
public class RegexTest {
    public static void main(String[] args) {
        testIpAdd();
    }

    static void testIpAdd() {
        String sentence = "Nov 16 12:59:09 A06-R12-302F0413-I37-42 sshd[140697]:  Failed password for root from 127.0.0.1 port 53204 ssh2";
        Pattern patternIp = Pattern.compile("(\\d+)[.](\\d+)[.](\\d+)[.](\\d+)");
        Pattern patternIp1 = Pattern.compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");

        Matcher matcher = patternIp.matcher(sentence);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    static void testProcessId() {
        String sentence = "Mar 29 09:56:37 firewall sshd[1423]: " +
                "Failed password for illegal user jordan from 61.244.242.25 port 48631 ssh2";
        String processId = "";

        Pattern patternProcessId = Pattern.compile("\\[(\\d+)\\]");
        Matcher matcher = patternProcessId.matcher(sentence);
        while(matcher.find()) {
            processId = matcher.group();
        }
        System.out.println(processId);
    }

    static void testSplit(){
        String a = "Mar 29 09:56:37 firewall sshd[1423]: Failed password for illegal user jordan from 61.244.242.25 port 48631 ssh2";
        String[] word = a.split("]:");
        String log = word[1];
        String datetime = word[0].substring(0,14);
        String otherStr = word[0].substring(16);
        String hostname = otherStr.split(" ")[0];
        String processName = otherStr.split(" ")[1].split("\\[")[0];
        SecureLogObject secureLogObject = new SecureLogObject();
        secureLogObject.setDatetime(datetime);
//        secureLogObject.setHostname(hostname);
        secureLogObject.setProcessName(processName);
        secureLogObject.setLog(log);

        JSONObject jsonObject = JSONObject.fromObject(secureLogObject);

        System.out.println("datetime="+datetime);
        System.out.println("hostname="+hostname);
        System.out.println("processname="+processName);
        System.out.println("json="+jsonObject.toString());
    }


    static void testRegex(){
        //        System.out.println(Pattern.matches("(\\d+).(\\d+).(\\d+).(\\d+)", "61.244.242.25"));

        String sentence = "Failed password for root from dd=61.244.242.25 port 48631 ssh2";
//        Pattern p = Pattern.compile("(\\d+).(\\d+).(\\d+).(\\d+)");
        Pattern p = Pattern.compile("for (\\w+) from");
        Matcher m = p.matcher(sentence);
        while(m.find()) {
            System.out.println(m.group().split(" ")[m.group().split(" ").length-2]);
        }
//        String[] splits = sentence.split(" ");
//        for(String str : splits){
//            if(Pattern.matches("(\\d+).(\\d+).(\\d+).(\\d+)", str)){
//                System.out.println(str);
//            }
//        }

    }
}
