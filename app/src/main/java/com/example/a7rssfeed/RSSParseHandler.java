package com.example.a7rssfeed;

import android.annotation.SuppressLint;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RSSParseHandler extends DefaultHandler {

    Boolean inItem = false;
    Boolean inTitle = false;
    Boolean inDate = false;
    Boolean inAuthor = false;
    Boolean inDescription = false;

    StringBuilder sb;

    public List<Article> articles;
    Article article;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.d("JK","Start of the Document");

        articles = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d("JK","End of the Document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.d("JK","Start Element - " + qName);

        if(qName.equals("item")){
            article = new Article();
            inItem = true;
        }else if (inItem && qName.equals("title")){
            inTitle = true;
            sb = new StringBuilder();
        }else if (inItem && qName.equals("pubDate")){
            inDate = true;
            sb = new StringBuilder();
        }
        else if (inItem && qName.equals("author")){
            inAuthor = true;
            sb = new StringBuilder();
        }
        else if (inItem && qName.equals("description")){
            inDescription = true;
            sb = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.d("JK","End Element - " + qName);
        if(qName.equals("item")){
            articles.add(article);
            inItem = false;
        }
        else if (inItem && qName.equals("title")){
            inTitle = false;
            article.setTitle(sb.toString());
        }else if (inItem && qName.equals("pubDate")){
            inDate = false;
            String date = sb.toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter=new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
            Date new_date= null;
            try {
                new_date = formatter.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat("MMMM dd, yyyy");
            assert new_date != null;
            date = formatter2.format(new_date);
            article.setDate(date);
        }
        else if (inItem && qName.equals("author")){
            inAuthor = false;
            article.setAuthor(sb.toString());
        }
        else if (inItem && qName.equals("description")){
            inDescription = false;
            String full = sb.toString();
            int start_of_p = full.indexOf("<p>");
            int end_of_p = full.indexOf("</p>");
            String para = full.substring((start_of_p+3),(end_of_p));
            article.setDescription(para);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        //String characterString = new String(ch,start,length);
        //sb.append(ch, start, length);
        if((inItem && inTitle) || (inItem && inDate) || (inItem && inAuthor) || (inItem && inDescription)){
            sb.append(ch, start, length);
            //Log.d("JK","Content - " + characterString);
        }

    }
}
