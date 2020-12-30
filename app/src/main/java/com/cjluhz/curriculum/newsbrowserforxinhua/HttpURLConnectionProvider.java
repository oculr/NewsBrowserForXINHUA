package com.cjluhz.curriculum.newsbrowserforxinhua;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * HttpClient发送GET、POST请求
 * @Author ShanY
 * @CreateDate 2020.12
 */
public class HttpURLConnectionProvider{
    /**
     * 发送GET请求
     * @param surl   请求url
     * @return JSON或者字符串
     * @throws Exception
     */
    public static Object sendGet(String surl) throws Exception {
//        StringBuilder urlJoint = new StringBuilder(surl);
//
//        if (nameValue != null){
//            urlJoint.append("?");
//            for (Map.Entry<String, String> entry : nameValue.entrySet()) {
//                urlJoint.append(entry.getKey());
//                urlJoint.append("=");
//                urlJoint.append(entry.getValue());
//                urlJoint.append("&");
//            }
//            urlJoint.deleteCharAt(urlJoint.length() - 1);
//
//        }

        URL url = new URL(surl);
        HttpURLConnection urlConnection = null;
        try {
            /**
             * 创建HttpURLConnection对象
             */
            urlConnection = (HttpURLConnection) url.openConnection();
            /**
             * 设置GET参数
             */
            urlConnection.setRequestMethod("GET");
            /**
             * 使用标准编码格式编码参数的键值
             */
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            /**
             * 获取响应吗
             */
            int statusCode = urlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == statusCode) {
                /**
                 * 获取响应流
                 */
                InputStream inputStream = urlConnection.getInputStream();
                /**
                 * 获取返回内容
                 */
                return dealResponseResult(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * 发送POST请求
//     * @param surl
//     * @param nameValue
//     * @return JSON或者字符串
//     * @throws Exception
//     */
//    public static Object sendPost(String surl, Map<String, String> nameValue) throws Exception{
//        HttpURLConnection urlConnection = null;
//        URL url = new URL(surl);
//        try{
//            /**
//             *  创建一个httpclient对象
//             */
//            urlConnection = (HttpURLConnection) url.openConnection();
//            /**
//             * 设置连接时间
//             */
//            urlConnection.setConnectTimeout(3000);
//            /**
//             * 打开输入流，以便从服务器获取数据
//             * 打开输出流，以便向服务器提交数据
//             */
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            /**
//             * 设置以POST方式提交数据
//             */
//            urlConnection.setRequestMethod("POST");
//            /**
//             * 关闭缓存
//             */
//            urlConnection.setUseCaches(false);
//            /**
//             * 请求的类型是文本类型
//             */
//            urlConnection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//            /**
//             * 设置请求的内容
//             */
//            post.setEntity(entity);
//            /**
//             * 设置请求的报文头部的编码
//             */
//            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
//            /**
//             * 设置请求的报文头部的编码
//             */
//            post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
//            /**
//             * 执行post请求
//             */
//            response = client.execute(post);
//            /**
//             * 获取响应码
//             */
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (SUCCESS_CODE == statusCode){
//                /**
//                 * 通过EntityUitls获取返回内容
//                 */
//                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
//                /**
//                 * 转换成json,根据合法性返回json或者字符串
//                 */
//                try{
//                    jsonObject = JSONObject.parseObject(result);
//                    return jsonObject;
//                }catch (Exception e){
//                    return result;
//                }
//            }else{
//                LOGGER.config("HttpClientService-line: 146, errorMsg：POST请求失败！");
//            }
//        }catch (Exception e){
//            LOGGER.config("HttpClientService-line: 149, Exception：other");
//        }finally {
//            response.close();
//            client.close();
//        }
//        return null;
//    }
//
//    /**
//     * 组织请求参数{参数名和参数值下标保持一致}
//     * @param params    参数名数组
//     * @param values    参数值数组
//     * @return 参数对象
//     */
//    public static List<NameValuePair> getParams(Object[] params, Object[] values){
//        /**
//         * 校验参数合法性
//         */
//        boolean flag = params.length>0 && values.length>0 &&  params.length == values.length;
//        if (flag){
//            List<NameValuePair> nameValuePairList = new ArrayList<>();
//            for(int i =0; i<params.length; i++){
//                nameValuePairList.add(new BasicNameValuePair(params[i].toString(),values[i].toString()));
//            }
//            return nameValuePairList;
//        }else{
//            LOGGER.config("HttpClientService-line: 197, errorMsg：请求参数为空且参数长度不一致");
//        }
//        return null;
//    }

    /*
     * Function : 处理服务器的响应结果（将输入流转化成字符串）
     */
    private static String dealResponseResult(InputStream inputStream) {
        String resultData = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

}