package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by lisiyu on 2015/5/10.
 */

public class healthCheckClient {

//    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	private static String uri = "http://dddd.com";
    private static String param = " {\"tolist\":\"ddd@163.com\", \"cclist\" :\"\",\"subject\":\"在测试一下\",\"content\":\"<html><p>11111111111</p><p>22222222</p></html>\"}";
    public static void main(String[] args) throws Exception {
        test();
	}

    public static void mail() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri).path("/");
        Form form = new Form();
        form.param("tolist","ddd@163.com");
        form.param("cclist","");
        form.param("subject","在测试一下");
        form.param("content","2323");
        target.request(MediaType.APPLICATION_JSON_TYPE)
                        .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE));
//        target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
    }

    public static void test() throws Exception{
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri).path("/");
        Form form = new Form();
        form.param("strJson","ddd@163.com");
        target.request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE));
//        target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
    }

//    public static String offlineAdd() throws Exception{
//
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(serverUri).path("user/manageOfflinedate");
//        System.out.println("target.path = " + target.getUri().getPath());
//        Form form = new Form();
//        form.param("CriJsonDevice",hcReportParam);
//
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//        String requestInfo = response.readEntity(String.class);
//
//        return requestInfo;
//    }
//
//    public static String deviceLogin() throws Exception{
//
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(serverUri).path("user/rlogin/device/login");
//        System.out.println("target.path = " + target.getUri().getPath());
//        Form form = new Form();
//        form.param("CriJsonLogin",deviceLoginParam);
//
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//        String requestInfo = response.readEntity(String.class);
//
//        return requestInfo;
//    }
//
//	public static String updateUser() throws Exception{
//
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(serverUri).path("user/updateUserInfo");
//        System.out.println("target.path = " + target.getUri().getPath());
//        Form form = new Form();
//        form.param("userInfo","");
//
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//        String requestInfo = response.readEntity(String.class);
//
//        return requestInfo;
//	}
//
//	public static String requestRegistClick(String mobile) throws Exception{
//
//	        Client client = ClientBuilder.newClient();
//	        WebTarget target = client.target(serverUri).path("user/requestRegister");
//	        System.out.println("target.path = " + target.getUri().getPath());
//	        Form form = new Form();
//
//	        form.param("registPhone",mobile);
//
//
//	        Response response = target.request(MediaType.APPLICATION_JSON)
//	                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//	        String requestInfo = response.readEntity(String.class);
//
//	        return requestInfo;
//
//	}
//
//
//	public static String registerCodeByPhone() throws Exception{
//
//		Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(serverUri).path("user/register");
//        System.out.println("target.path = " + target.getUri().getPath());
//        Form form = new Form();
//        form.param("jsonstr",regiesterParam);
//
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//        String requestInfo = response.readEntity(String.class);
//
//        return requestInfo;
//	}
//
//
//	public static String addAndroidPostHealthCheck() throws Exception{
//
//
//        String result = "";
//        HttpPost httpRequst = new HttpPost("/check/addHealthCheck");//创建HttpPost对象
//        System.out.println("serverUri = " + serverUri);
//
//        List <NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("hcRecord", hcReportParam));
//
//        try {
//            httpRequst.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
//            if(httpResponse.getStatusLine().getStatusCode() == 200)
//            {
//                HttpEntity httpEntity = httpResponse.getEntity();
//                result = EntityUtils.toString(httpEntity);//取出应答字符串
//            }
//        } catch (UnsupportedEncodingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            result = e.getMessage().toString();
//        }
//        catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            result = e.getMessage().toString();
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            result = e.getMessage().toString();
//        }
//
//        return result;
//	}
//
//	public static String addPostHealthCheck() throws Exception{
////        CriUser user = new CriUser(1, "nameA", "descA");
////        Client client = ClientBuilder.newClient();
////        WebTarget target = client.target(serverUri).path("createPostUser");
////        Response response = target.request().buildPost(Entity.entity(user, MediaType.APPLICATION_JSON)).invoke();
////        response.close();
//
//        //HCHealthSuggestion hcHealthSuggestion = new HCHealthSuggestion();
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(serverUri).path("check/addHealthCheck");
//        System.out.println("target.path = " + target.getUri().getPath());
//        Form form = new Form();
//        form.param("hcRecord",hcReportParam);
//
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//        String suggestion = response.readEntity(String.class);
//
//
//        //解析出来看数据
////        ObjectMapper mapper = new ObjectMapper();
////        JSONObject result = mapper.readValue(suggestion, JSONObject.class);
////        int status = Integer.parseInt(result.get("status").toString());
////        String message = result.get("message").toString();
////        System.out.println("status = "+status + ",message = "+message);
////        if(status == 1){
////            String data = result.get("data").toString();
////            System.out.println("data = "+data);
////            hcHealthSuggestion=mapper.readValue(data, HCHealthSuggestion.class);
////        }
////
////        System.out.println("hc.id = " + hcHealthSuggestion.getHc_health_id() + ",hc.suggestion = " + hcHealthSuggestion.getHealth_suggestion());
//
//        return suggestion;
//    }
//
//    public static String test() throws Exception{
////        CriUser user = new CriUser(1, "nameA", "descA");
////        Client client = ClientBuilder.newClient();
////        WebTarget target = client.target(serverUri).path("createPostUser");
////        Response response = target.request().buildPost(Entity.entity(user, MediaType.APPLICATION_JSON)).invoke();
////        response.close();
//
//        //HCHealthSuggestion hcHealthSuggestion = new HCHealthSuggestion();
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(serverUri).path("check/test");
//        System.out.println("target.path = " + target.getUri().getPath());
//        Form form = new Form();
//        form.param("hcRecord","{\"id_card_no\":\"110108197\",\"check_time\":\"2015-04-04T12:00:05.000+0800\",\"send_time\":\"2015-04-04T12:30:05.000+0800\",\"hc_device_mac\":\"345633345534\",\"systolic_pressure\":\"3\",\"diastolic_pressure\":\"2\",\"pulse\":\"3\",\"blood_oxygen\":\"4\",\"weight\":\"180\",\"brainwave_attention\":\"100\",\"brainwave_meditation\":\"3\",\"moisture_rate\":\"2\",\"visceral_fat\":\"1\",\"muscle_rate\":\"2\",\"bodyfat_rate\":\"3\",\"bone_rate\":\"2\",\"body_temperature\":\"3\",\"energy\":\"2\",\"blood_pressure_heart_rate\":\"1\",\"ecg_heart_rate\":\"4\",\"heart_age\":\"5\",\"respiration_rate\":\"2\",\"relaxation_level\":\"2\",\"five_min_heart_age\":\"2\",\"ecg_rr_interval\":\"2\",\"blink\":\"2\",\"ecg_history\":\"rrtrtrtr2\",\"systolic_pressure_status\":\"2\",\"diastolic_pressure_status\":\"2\",\"pulse_status\":\"2\",\"blood_oxygen_status\":\"2\",\"weight_status\":\"2\",\"brainwave_attention_status\":\"2\",\"brainwave_meditation_status\":\"2\",\"moisture_rate_status\":\"2\",\"visceral_fat_status\":\"2\",\"muscle_rate_status\":\"2\",\"bodyfat_rate_status\":\"2\",\"bone_rate_status\":\"2\",\"body_temperature_status\":\"2\",\"energy_status\":\"2\",\"blood_pressure_heart_rate_status\":\"2\",\"ecg_heart_rate_status\":\"2\",\"heart_age_status\":\"2\",\"respiration_rate_status\":\"2\",\"relaxation_level_status\":\"2\",\"five_min_heart_age_status\":\"2\",\"ecg_rr_interval_status\":\"2\",\"blink_status\":\"2\"}");
//
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED));
//        String suggestion = response.readEntity(String.class);
//
//
//        return suggestion;
//    }
	
}
