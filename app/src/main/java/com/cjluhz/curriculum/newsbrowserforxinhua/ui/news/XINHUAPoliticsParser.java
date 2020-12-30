package com.cjluhz.curriculum.newsbrowserforxinhua.ui.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjluhz.curriculum.newsbrowserforxinhua.HttpURLConnectionProvider;
import com.cjluhz.curriculum.newsbrowserforxinhua.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XINHUAPoliticsParser {
    private List<XINHUAPoliticsItem> news;
    private JSONArray PoliticsJSON;
    private JSONObject newsConfig;

    private String index;
    private Context context;
    private String accessUrl;

    public XINHUAPoliticsParser(String ind, Context context){
        news = new ArrayList<>();
        this.index = ind;
        this.context = context;
        this.accessUrl = "";
    }


    public void access(){

        //获取json网页配置
        StringBuilder json = new StringBuilder();
        try {
            InputStream in = context.getResources().openRawResource(R.raw.xinhuapagelist);
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine() )!= null){
                json.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //解析json，获取符合的配置
        JSONObject pagejson = JSONObject.parseObject(json.toString());
        JSONArray pageArr = pagejson.getJSONArray("Page");
        for (int i = 0 ; i<pageArr.size(); i++){
            if (index.equals(pageArr.getJSONObject(i).get("name"))){
                newsConfig = pageArr.getJSONObject(i);
                break;
            }
        }
        accessUrl = newsConfig.getString("apiurl");


        //获取js返回
        try {
            Object unhandeled = HttpURLConnectionProvider.sendGet(accessUrl);
            if (unhandeled != null){
                StringBuilder sb = new StringBuilder(unhandeled.toString());
                sb.delete(0, newsConfig.getString("jscall").length());

                //已经返回js条目，解析新闻
                JSONObject jsonObject = JSONObject.parseObject(sb.toString());
                PoliticsJSON = jsonObject.getJSONObject("data").getJSONArray("list");

                for (int i = 0; i < PoliticsJSON.size(); i++){
                    JSONObject obj = PoliticsJSON.getJSONObject(i);
                    JSONArray artDetails = obj.getJSONArray("artDetails");
                    JSONObject detail = artDetails.getJSONObject(0);
                    String title = detail.getString("title");
                    String url = detail.getString("url");
                    String pubtime = obj.getString("pubtime");

                    XINHUAPoliticsItem newitem;
                    if (obj.getJSONArray("imgarray") == null || obj.getJSONArray("imgarray").isEmpty()){
                        newitem = new XINHUAPoliticsItem( url,
                                null,
                                title,
                                pubtime,
                                XINHUAPoliticsItem.NOIMG
                        );
                    } else {
                        newitem = new XINHUAPoliticsItem(url,
                                (String) obj.getJSONArray("imgarray").get(0),
                                title,
                                pubtime,
                                XINHUAPoliticsItem.IMG
                        );
                    }
                    Log.i("newsload", newitem.toString());
                    news.add(newitem);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap getImageBitmap(String path) {
        if ( "".equals(path) || path == null)
            return null;
        Bitmap bm = null;
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bm = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public List<XINHUAPoliticsItem> getNews() {
        return news;
    }
}
