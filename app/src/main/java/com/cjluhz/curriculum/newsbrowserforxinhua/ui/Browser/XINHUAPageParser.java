package com.cjluhz.curriculum.newsbrowserforxinhua.ui.Browser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XINHUAPageParser {
    private final String url;
    private String title;
    private String keywords;
    private List<PageContent> contents;

    public XINHUAPageParser(String url){
        this.url = url;
        title = "";
        keywords = "";
        contents = new ArrayList<>();
    }

    public void access(){
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
            title = document.getElementsByAttributeValue("class", "h-title").text();
            keywords = document.getElementsByAttributeValue("name", "keywords").text();
            Element et = null;
            Elements ets = null;

            String source = "";
            if ((et = document.getElementById("source")) != null){
                source = et.text();
            } else {
                ets = document.getElementsByAttributeValue("class", "source");
                if (ets.first() != null)
                    source = ets.first().text();
            }

            contents.add(new PageContent(PageContent.dateandsource, source));

            Elements es = document.getElementsByTag("p");
            for (Element e: es) {
                if ( e.getElementsByTag("strong").first() != null ) {
                    if ( e.getElementsByAttributeValue("color", "navy").first() != null) {
                        contents.add(new PageContent(PageContent.navyfont, e.text()));
                    } else {
                        contents.add(new PageContent(PageContent.strong, e.text()));
                    }
                    continue;
                }

                Element child = null;
                if ( (child = e.getElementsByTag("img").first()) != null ) {
                    String src = child.attr("src");
                    String regex = "(.*/)";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(url);
                    if (m.find()){
                        src = m.group(0) + src;
                    } else {
                        src = "http://iph.href.lu/500x500";
                    }
                    contents.add(new PageContent(PageContent.img, src));
                    continue;
                }

                contents.add(new PageContent(PageContent.p, e.text()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PageContent> getContents() {
        return contents;
    }
}
