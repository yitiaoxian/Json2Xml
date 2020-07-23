import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Json2Xml {

    /*
        传入json字符串
        返回xml 字符串
     */
    public static String jsonToXml(String json){
        try{
            StringBuffer buffer = new StringBuffer();
            buffer.append("<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>");
            JSONObject object = JSON.parseObject(json);
            jsonToXmlStr(object,buffer);
            return buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String jsonToXmlStr(JSONObject jsonObject,StringBuffer stringBuffer){
        Set<Map.Entry<String,Object>> se = jsonObject.entrySet();
        for(Iterator<Map.Entry<String,Object>> it = se.iterator();it.hasNext();){
            Map.Entry<String,Object> en = it.next();
            if(en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
                stringBuffer.append("<"+en.getKey()+">");
                JSONObject jo = jsonObject.getJSONObject(en.getKey());
                jsonToXmlStr(jo,stringBuffer);
                stringBuffer.append("</"+en.getKey()+">");
            }else if(en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONArray")){
                JSONArray jarray = jsonObject.getJSONArray(en.getKey());
                for (int i = 0; i < jarray.size(); i++) {
                    stringBuffer.append("<"+en.getKey()+">");
                    JSONObject jsonobject =  jarray.getJSONObject(i);
                    jsonToXmlStr(jsonobject,stringBuffer);
                    stringBuffer.append("</"+en.getKey()+">");
                }
            }else if(en.getValue().getClass().getName().equals("java.lang.String")){
                stringBuffer.append("<"+en.getKey()+">"+en.getValue());
                stringBuffer.append("</"+en.getKey()+">");
            }
        }
        return stringBuffer.toString();
    }
    public static void main(String[] args) {
        String xmlstr = jsonToXml("{\"class\": {\"student\": [{\"age\": \"18\",\"gender\": \"男\",\"name\": \"张三\",\"qk\": [{\"q1\": \"001\",\"q2\": \"002\",\"q3\": \"003\"},{\"q1\": \"001\",\"q2\": \"002\",\"q3\": \"003\"}]},{\"age\": \"17\",\"gender\": \"男\",\"name\": \"李四\",\"qk\": {\"q1\": \"005\",\"q2\": \"006\",\"q3\": \"007\"}},{\"age\": \"19\",\"gender\": \"女\",\"name\": \"王五\",\"qk\": {\"q1\": \"008\",\"q2\": \"009\",\"q3\": \"010\"}}]}}");
        System.out.println(xmlstr);
    }

}
