package test;

import com.lsy.jstorm.utils.HttpUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by lisiyu on 2016/11/11.
 */
public class RestClientTest {
    public static void main(String[] args) {
        testPost();
    }

    static void testGet(){

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("tolist", "ddd@163.com");
        map.add("cclist", "");
        map.add("subject", "在测试一下");
        map.add("content", "2323");
        String result = restTemplate.postForObject("http://ddd.com", map, String.class);
        System.out.println(result);


//        Map<String, String> params = new HashMap<String, String>();
//        params.put("tolist","ddd@163.com");
//        params.put("cclist","");
//        params.put("subject","在测试一下");
//        params.put("content","2323");
//
//        String xml = HttpXmlClient.post("http://ddd.com", params);


//        String data = HttpUtils.doGet("http://localhost:8001/", "");
//        System.out.println(data);
    }

    static void testPost(){
//        String data = HttpUtils.doPost("http://localhost:8001/post", "strJson={\"aa\":\"dfdf\"}");
        String msg = "{\"tolist\":\"ddd@163.com\",\"cclist\":\"\",\"subject\":\"测试一下\",\"content\":\"<html><p>11111111111</p><p>22222222</p></html>\"}";

        MailObject mailObject = new MailObject();
        mailObject.tolist = "ddd@163.com";
        mailObject.cclist = "";
        mailObject.subject = "测试一下";
        mailObject.content = "123";


        String data = HttpUtils.doPost("http://ddd.com", "tolist=ddd@163.com&cclist=&subject=测试一下&content=122");
        System.out.println(data);
    }

    static class MailObject{
        String tolist;
        String cclist;
        String subject;
        String content;

    }
}
