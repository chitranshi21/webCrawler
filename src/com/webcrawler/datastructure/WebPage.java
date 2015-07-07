package com.webcrawler.datastructure;

/*
 * this is the webpage class, it provides the structure for the 
 * object to be saved as .....well webpage.
 * currently it has url, timestamp and info.
 * for now info is kept empty
 * 
 * two constructor taking url and timestamp/ url, timestamp and info
 */



import java.sql.Timestamp;

public class WebPage {

	private String url;
	private Timestamp timestamp;
	private String info;
	
	public WebPage(String url, Timestamp timestamp){
		this.url = url;
		this.timestamp = timestamp;
		this.info = "Default";
	}
	public WebPage(String url, Timestamp timestamp, String info){
		this.url = url;
		this.timestamp = timestamp;
		this.info = info;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
}
