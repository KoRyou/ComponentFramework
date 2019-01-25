package zgl.com.cn.model_flight.json2bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

/**
 *  描述：根据json生成实体bean文件
 *  来源：https://blog.csdn.net/anyview93/article/details/80644228
 * @author : update  by  jsj_android
 * @date : 2019/1/25
 */

public class Json2BeanHelper {
    /**
     * 读取本地json文件，生成对应的JavaBean
     * @param sourceFilePath	json文件路径地址  	eg："jsonFile/json.text"
     * @param targetPath		生成JavaBean路径 	eg："jsonFile/"
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void json2Bean(String sourceFilePath, String targetPath) throws IOException {

        File source = new File(sourceFilePath);
        if (!source.exists()) {
            System.out.println("【路径检测】： json串文件不存在！！！请添加");
            return;
        }
        File target = new File(targetPath);
        if (!target.exists()) {
            target.mkdir();
            System.out.println("【路径检测】： 存放路径不存在，自动创建完毕");
        }


        JSONObject jsonObject = JSON.parseObject(getJsonStr(sourceFilePath));
        System.out.println("--自动转换中 ......");
        boolean b = template(jsonObject, targetPath);
        if(true == b) {
            System.out.println("【生成完毕】：json生成JavaBean成功！");
        }else{
            System.out.println("【抱歉失败】：请查找原因！");
        }
    }

    /**
     * 	IO读取json源文件
     * @param path	json源文件地址
     * @return
     * @throws IOException
     */
    private static String getJsonStr(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();
        return builder.toString();
    }

    /**
     * 	IO写入JavaBean文件
     * @param bean		JavaBean字符串
     * @param path		JavaBean文件路径
     * @return
     * @throws IOException
     */
    private static boolean writeJavaBean(String bean, String path) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
        bw.write(bean);
        bw.close();
        return true;
    }

    /**
     * 	生成JavaBean
     * @param jsonObject	源json对象
     * @param targetPath	Java文件路径
     * @return
     * @throws IOException
     */
    private static boolean template(JSONObject jsonObject, String targetPath) throws IOException {
        boolean b = template(jsonObject, targetPath, null);
        return b;
    }

    /**
     * 	首字母大写
     * @param string
     * @return
     */
    private static String firstUpcase(String string) {
        if(null == string) {
            return "";
        }
        String str1 = string.substring(0, 1);
        String str2 = string.substring(1);
        return str1.toUpperCase() + str2;
    }

    /**
     * 	生成所有字段
     * @param jsonObject	源json对象
     * @param targetPath	JavaBean文件路径
     * @return
     * @throws IOException
     */
    private static String generateFields(JSONObject jsonObject, String targetPath) throws IOException {
        if(null == jsonObject || jsonObject.size() == 0) {
            return "";
        }
        Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
        StringBuilder builder = new StringBuilder();
        for (Entry<String, Object> entry : entrySet) {
            String fieldType = "String";
            //如果字段类型是List或者Map，需要递归生成子依赖类
            Object value = entry.getValue();
            if(value instanceof JSONArray) {
                JSONArray jsonArr = (JSONArray) value;
                Object object = "";
                if(null != jsonArr && jsonArr.size() > 0) {
                    object = jsonArr.get(0);
                }
                String beanName = formatBeanName(entry.getKey());
                //TODO 这里需要对特殊情况处理  【字段】
                JSONObject jb = null;
                if (object instanceof JSONArray) {
                    jb = (JSONObject)object;
                    template( jb, targetPath, beanName);
                    fieldType = "List<" + beanName + ">";
                    builder.append(generateField(entry.getKey(), fieldType));
                }else if(object instanceof JSONObject){
                    jb = JSONObject.parseObject(object.toString());
                    template( jb, targetPath, beanName);
                    fieldType = "List<" + beanName + ">";
                    builder.append(generateField(entry.getKey(), fieldType));
                }else{
                    fieldType = "List<" + formatStringStr(object.toString()) + ">";
                    builder.append(generateField(entry.getKey(), fieldType));
                }
                /*template( jb, targetPath, beanName);
                fieldType = "List<" + beanName + ">";
                builder.append(generateField(entry.getKey(), fieldType));*/
            }else if(value instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) value;
                String beanName = formatBeanName(entry.getKey());
                template(jsonObj, targetPath, beanName);
                fieldType = beanName;
                builder.append(generateField(entry.getKey(), fieldType));
            }else {
                builder.append(generateField(entry.getKey(), fieldType));
            }

        }
        return builder.toString();
    }

    /**
     * 生成所有get/set方法
     * @param jsonObject	源json对象
     * @return
     * @throws IOException
     */
    private static String generateMethods(JSONObject jsonObject) throws IOException {
        if(null == jsonObject || jsonObject.size() == 0) {
            return "";
        }
        Set<Entry<String, Object>> entrySet = jsonObject.entrySet();
        StringBuilder builder = new StringBuilder();
        for (Entry<String, Object> entry : entrySet) {
            String fieldType = "String";
            //如果字段类型是List或者Map，需要递归生成子依赖类
            Object value = entry.getValue();
            if(value instanceof JSONArray) {

                //TODO 这里需要做特殊处理【get  +  set】
                JSONArray jsonArr = (JSONArray) value;
                Object object = "";
                if(null != jsonArr && jsonArr.size() > 0) {
                    object = jsonArr.get(0);
                }
                if (object instanceof JSONArray) {
                    String beanName = formatBeanName(entry.getKey());
                    fieldType = "List<" + beanName + ">";
                    builder.append(generateMethod(entry.getKey(), fieldType));
                }else if(object instanceof JSONObject){
                    String beanName = formatBeanName(entry.getKey());
                    fieldType = "List<" + beanName + ">";
                    builder.append(generateMethod(entry.getKey(), fieldType));
                }else{
                    fieldType = "List<" + object.toString() + ">";
                    builder.append(generateMethod(entry.getKey(), fieldType));
                }


            }else if(value instanceof JSONObject) {
                String beanName = formatBeanName(entry.getKey());
                fieldType = beanName;
                builder.append(generateMethod(entry.getKey(), fieldType));
            }else {
                builder.append(generateMethod(entry.getKey(), fieldType));
            }

        }
        return builder.toString();
    }

    /**
     *  生成单个字段
     * @param field			字段名
     * @param fieldType		字段类型
     * @return
     */
    private static String generateField(String field, String fieldType) {
        if(null == field || "" == field || null == fieldType || "" == fieldType) {
            return "";
        }

        fieldType = formatStringStr(fieldType);

        StringBuilder builder = new StringBuilder();
        builder.append("\t")
                .append("private ")
                .append(fieldType).append(" ")
                .append(field)
                .append(";")
                .append("\r\n");
        return builder.toString();
    }

    /**
     * 生成单个get/set方法
     * @param field			字段名
     * @param fieldType		字段类型
     * @return
     */
    private static String generateMethod(String field, String fieldType) {
        if(null == field || "" == field || null == fieldType || "" == fieldType) {
            return "";
        }

        fieldType = formatStringStr(fieldType);

        StringBuilder builder = new StringBuilder();
        builder.append("\t").append("public ").append(fieldType).append(" get").append(firstUpcase(field)).append("() {")
                .append("\r\n")
                .append("\t").append("\t").append("return ").append(field).append(";")
                .append("\r\n")
                .append("\t").append("}");

        builder.append("\r\n");

        builder.append("\t").append("public void set").append(firstUpcase(field)).append("(").append(fieldType).append(" ").append(field).append(") {")
                .append("\r\n")
                .append("\t").append("\t").append("this. ").append(field).append(" = ").append(field).append(";")
                .append("\r\n")
                .append("\t").append("}");

        builder.append("\r\n");

        return builder.toString();
    }

    /**
     * 	生成JavaBean
     * @param jsonObject	源json对象
     * @param targetPath	JavaBean生成路径
     * @param beanName		JavaBean名
     * @return
     * @throws IOException
     */
    private static boolean template(JSONObject jsonObject, String targetPath, String beanName) throws IOException {
        if(null == beanName || "" == beanName) {
            beanName = "JsonBean";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("public class ").append(beanName).append(" {");
        builder.append("\r\n");

        if(null != jsonObject && jsonObject.size() > 0) {
            String fields = generateFields(jsonObject, targetPath);
            String methods = generateMethods(jsonObject);
            builder.append(fields).append(methods);
        }

        builder.append("}");
        String path = targetPath + beanName + ".java";
        boolean b = writeJavaBean(builder.toString(),path);
        return b;
    }

    /**
     * 	字段格式化，去除_并大写首字母
     * @param beanName	JavaBean名
     * @return
     */
    private static String formatBeanName(String beanName) {
        if(null == beanName || "".equals(beanName)) {
            return "";
        }
        String[] split = beanName.split("_");
        StringBuilder builder = new StringBuilder();
        if(null != split && split.length > 0) {
            for (String str : split) {
                builder.append(firstUpcase(str));
            }
        }
        return builder.toString();
    }


    private static String formatStringStr(String beanName){
        if(null == beanName || "".equals(beanName)) {
            return "";
        }

        if (beanName.startsWith("List<")) {
            String str = beanName.substring(5,11);
            if (str.equals("string")) {
                str = firstUpcase(str);
            }
            beanName = "List<"+str+">";
        }else if (beanName.equals("string")) {
            beanName = firstUpcase(beanName);
        }

        return beanName;
    }

}
