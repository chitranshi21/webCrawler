package com.webcrawler.test;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void notmain(String args[])throws Exception{
		
		try {
			Document doc = Jsoup.connect("https://bing.com").get();
			Elements links = doc.select("a");
			
			for(Element link: links){
				System.out.println(link.attr("abs:href"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(JsoupTest.class.getName()).log(Level.SEVERE,null,e);
		}
		
		
		
		String url = null,output_format = null;
		int no_of_crawls = 0;
		
		switch(args.length){
		case 0:
			url = "https://www.google.com";
			no_of_crawls = 100;
			output_format = "json";
			break;
		case 1:
			url = args[0];
			no_of_crawls = 100;
			output_format = "json";
			break;
		case 2:
			url = args[0];
			if(Integer.parseInt(args[1]) > 500)
				no_of_crawls = 500;
			else
				no_of_crawls = Integer.parseInt(args[1]);
			output_format = "json";
			break;
		case 3:
			url = args[0];
			if(Integer.parseInt(args[1]) > 500)
				no_of_crawls = 500;
			else
				no_of_crawls = Integer.parseInt(args[1]);
			output_format = args[2];
			break;
		default:
			url = "https://www.google.com";
			no_of_crawls = 100;
			output_format = "json";
			break;
		
		}
		
		HashMap<String, Integer> test = new HashMap();
		test.put("one", 1);
		System.out.println(test.containsKey("one"));
		
		
		
		
		System.out.println(url+"\t"+no_of_crawls);
		//System.out.println(new UrlValidator().isValid("http://google. erer.com"));
		
	}
}
