/*
 * required packages 
 * Jsoup - 1.8.2
 * apache commons-validator-1.4.1
 * 
 * three methods ->
 * 1) isCorrectUrl --> takes url as input and return true if url is correctly formated,
 * using apache commons-validator to validate, according to the docs http, https, ftp are supported.
 * docs -->https://commons.apache.org/proper/commons-validator/javadocs/api-1.4.1/
 * 
 * 2) isDuplicate --> takes string(key to the hashmap) as input and return true if it exist in hashmap
 * 
 * 3) getAllUrlFromPage --> takes page as an input and return list of all valid(Correct) and Unique(not in hashmap) pages with url in it
 * 
 * these method are kept static because they dont modify any object, lets code now..!!
 * 
 * 
 */


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.webcrawler.datastructure.WebPage;



public class StaticChecks {
	
	/*
	 * the hashmap is used to keep track of already visited urls, key - URL, Value - WebPage Object
	 * the hashmap is kept a static reference, so that we can a single object, even if multiple URL fetcher are running.
	 */
	
	private static UrlValidator urlValidate = new UrlValidator();
	private static HashMap<String, WebPage> indexMap = new HashMap<String, WebPage>();
	private static Date date = new Date();

	
	public static void addToHashMap(WebPage page){
		
		/*
		 * this method adds a entry to the hashmap
		 */
		
		indexMap.put(page.getUrl(), page);
	}


	public static boolean isCorrectUrl(String url){
		/*
		 * Apache common url validator is used to validate the urls. return boolean for check.
		 */
		
		if(urlValidate.isValid(url)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	public static boolean isDuplicate(String url){
		/*
		 * simply checks for duplicate in hashmap and returns boolean 
		 * used to verify if the url is already visited .
		 */
		
		return indexMap.containsKey(url);
	}
	
	
	
	public static ArrayList<WebPage> getAllUrlFromPage(WebPage page){
		
		/*
		 * return an arraylist of webpages for a given url. this method will be called recursively
		 * by the main method to get the new list of urls.
		 * uses Jsoup api to retrieve all the hrefs from a webpage
		 * for each link it checks if its valid and is not already present.
		 * then it adds it to the hashmap and final list.
		 * Finally returns the list.
		 */
		
		ArrayList<WebPage> list = new ArrayList<WebPage>();
		try {
			Document doc = Jsoup.connect(page.getUrl()).get();
			Elements links = doc.select("a");
			
			for(Element link: links){
				//System.out.println(link.attr("abs:href"));
				if(isCorrectUrl(link.attr("abs:href")) && !isDuplicate(link.attr("abs:href"))){
					WebPage link_page = new WebPage(link.attr("abs:href"), new Timestamp(date.getTime()));
					indexMap.put(link.attr("abs:href"), link_page);
					list.add(link_page);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		    // Normally error occurs when the url return 404,500,403.. anything apart from 200
			e.printStackTrace();
			Logger.getLogger(StaticChecks.class.getName()).log(Level.WARNING,null,e);
		}
		
		return list;
	}
}
