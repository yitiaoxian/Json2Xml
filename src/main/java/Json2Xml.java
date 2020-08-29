import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Json2Xml {

    /**
     *
     * @param json
     * @return string
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

    /**
     *
     * @param jsonObject
     * @param stringBuffer
     * @return
     */

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
    public static String readFile (String sourceFile) throws IOException {

        FileInputStream fis=new FileInputStream(sourceFile);
        InputStreamReader fr=new InputStreamReader(fis,"utf-8");//重点是这个，我也不知道为什么用utf-8不行
        BufferedReader br=new BufferedReader(fr);

        StringBuffer s=new StringBuffer();
        String tmp = null;
        int i=0;
        while ((tmp=br.readLine())!=null){
            s.append(tmp);
            tmp = null;
           // System.out.println(s);
        }
        br.close();
        return s.toString();
    }
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    public static String readFileByChars(String fileName) {
        File file = new File(fileName);
        StringBuffer str = new StringBuffer();
        Reader reader = null;
        try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        String xmlstr = null;
        try {
            xmlstr = readFile("E:\\Json2Xml\\annotations.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("读取完毕");
        //String xmlstr = jsonToXml("{\"class\": {\"student\": [{\"age\": \"18\",\"gender\": \"男\",\"name\": \"张三\",\"qk\": [{\"q1\": \"001\",\"q2\": \"002\",\"q3\": \"003\"},{\"q1\": \"001\",\"q2\": \"002\",\"q3\": \"003\"}]},{\"age\": \"17\",\"gender\": \"男\",\"name\": \"李四\",\"qk\": {\"q1\": \"005\",\"q2\": \"006\",\"q3\": \"007\"}},{\"age\": \"19\",\"gender\": \"女\",\"name\": \"王五\",\"qk\": {\"q1\": \"008\",\"q2\": \"009\",\"q3\": \"010\"}}]}}");
        System.out.println(xmlstr);
    }

}
