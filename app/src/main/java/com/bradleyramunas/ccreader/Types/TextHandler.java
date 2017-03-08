package com.bradleyramunas.ccreader.Types;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Bradley on 2/20/2017.
 */

public class TextHandler {

    public static String convertMessageToText(String message){
        message = message.replaceAll("<br>", "\n");
        message = message.replaceAll("&amp;", "&");
        while(message.indexOf("<a") != -1){
            int indxS = message.indexOf("<a");
            int indxE = message.indexOf("</a>");
            String toParse = message.substring(indxS, indxE);
            Document doc = Jsoup.parse(toParse);
            String result = doc.select("a").text();
            message = message.substring(0, indxS) + result + message.substring(indxE+4);
        }
        while(message.indexOf("<blockquote") != -1){
            int indxS = message.indexOf("<blockquote");
            int indxE = message.indexOf("</blockquote>");
            String toParse = message.substring(indxS, indxE);
            Document doc = Jsoup.parse(toParse);
            String result = doc.select(".QuoteText").text();
            message = message.substring(0, indxS) + "\n" + "\"" + result + "\"" + "\n" + message.substring(indxE + 13);
        }
        while(message.indexOf("<span class=") != -1){
            int indxS = message.indexOf("<span class=");
            int indxE = message.indexOf("</span></span>");
            String toParse = message.substring(indxS, indxE);
            Document doc = Jsoup.parse(toParse);
            String result = doc.select("span.Emoticon").get(0).text();
            message = message.substring(0, indxS) + result + message.substring(indxE+14);
        }
        return message;
    }
}
