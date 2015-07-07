import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webcrawler.datastructure.WebPage;

/**
 * this is the main class with the main method.
 * this web crawler is single threaded. and basic in functionality,
 * its takes 3 values as argument -->
 * 1) url - The starting url :: Default - http://www.google.com/
 * 2) number of crawls - The number of crawled webpages required : Default - 100,
 * upper limit - 500, in case if the input is greater than 500 it is set to 500 anyways..
 * 3) output_format - the list of crawled webpages format, Default - json,
 * can take either json or CSV, for now I will implement json.
 * 
 * Choose to go with BFS method of crawling, thus using Queue.
 * 
 * the external api used are - 
 * 1) Jsoup(1.8.2) - for URL handeling
 * 2) Gson(2.2.4) - To convert to json format.
 * 3) apache common validator(1.4.1) - to validate the url  
 * 
 * 
 * 
 * @author shubham
 *
 */



public class WebCrawler {
	
	
	/*
	 * Main method - takes 3 arguments as input. 
	 * 1) the starting url 
	 * 2) no of hops 
	 * 3) output format (CSV,JSON)
	 * 
	 */
	
	public static void main(String args[])throws Exception{
		//initialize the variables,
		// index_list is the final list of urls that we want.
		
		String url = null, output_format = null;
		int no_of_crawls = 0;
		ArrayList<WebPage> index_list = new ArrayList<WebPage>();
		
		//Switch case to check if the argumnets are given, incase they are not given
		// they are set to default.
		
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
		
		// ok so now we start crawling and get the links ..
		
		index_list = startCrawling(url, no_of_crawls);
		System.out.println(convertToFormat(index_list, output_format));
		
	}
	
	public static ArrayList<WebPage> startCrawling(String url, int no_of_crawls){
		/*
		 * this method takes the initial url and number of crawls as input
		 * and it uses the BFS to crawl for each link. 
		 * the while loops stops when either the no of crawls are reached or queue is empty.
		 * internally it calls the static functions in StaticChecks class to get valid and non-duplicate urls
		 * once the urls are added to the list, it is returned.
		 */
		
		Date date = new Date();
		ConcurrentLinkedQueue<WebPage> queue = new ConcurrentLinkedQueue<WebPage>();
		ArrayList<WebPage> indexed_list = new ArrayList<WebPage>();
		ArrayList<WebPage> current_page_urls;
		
		WebPage page = new WebPage(url,  new Timestamp(date.getTime()));
		queue.add(page);
		StaticChecks.addToHashMap(page);
		
		
		
		while(no_of_crawls > 0 && !queue.isEmpty()){
			WebPage currentPage = queue.remove();
			current_page_urls = StaticChecks.getAllUrlFromPage(currentPage);
			if(!current_page_urls.isEmpty()){
				for(WebPage p : current_page_urls){
					queue.add(p);
				}
			}
			indexed_list.add(currentPage);
			no_of_crawls--;
		}
		return indexed_list;
	}
	
	public static String convertToFormat(ArrayList<WebPage> list, String format_type){
		/*
		 * this method converts the list of Webpages object to desired format.
		 * the valid format are - 
		 * 1) json
		 * 2) csv
		 * in case non of the format matches, it return null.
		 * 
		 * for Json handling Google Gson is used
		 * https://code.google.com/p/google-gson/
		 * 
		 * for csv, I have written a custom converter, that uses String Builder to add commas.
		 */
		
		
		if(format_type.equalsIgnoreCase("json")){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(list);
			return json;
		}
		else if(format_type.equalsIgnoreCase("csv")){
			StringBuilder csv = new StringBuilder();
			for(WebPage p : list){
				csv.append(p.getUrl());
				csv.append(",");
				csv.append(p.getTimestamp());
				csv.append(",");
				csv.append(p.getInfo());
				csv.append(",");
			}
			csv.deleteCharAt(csv.length()-1);
			return csv.toString();
		}
		
		return null;
	}
	
	
	
	
	
	
	

}
