package reviewScraper.java;

import java.net.*;
import java.util.Enumeration;
import java.io.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;



public class IOSReview {
	
	Node node;
	NodeList children;
	
	public IOSReview(Node node) {
		this.node = node;
		children = node.getChildNodes();
	}
	
	public String getTitle() {
		//title is item(5)
		return children.item(5).getTextContent();
	}
	
	public String getRating() {
		//rating is item(15)
		return children.item(15).getTextContent();
	}
	
	public String getReview() {
		//review is item(7)
		String review = children.item(7).getTextContent();
		//review.replaceAll("\n", " ");
		//review.replaceAll("\t", " ");
		return review;
	}
	
	public String getID() {
		//id is item(3)
		return children.item(3).getTextContent();
	}
	
	public String getDate() {
		String toReturn = "";
		//date is item(1)
		//parse for actual date
		
		String raw = children.item(1).getTextContent();
		String year = raw.substring(0, 4);
		String month = raw.substring(5, 7);
		String date = raw.substring(8, 10);
		toReturn = month + "/" + date + "/" + year;
		
		
		return toReturn;
	}
	
	public String getVersion() {
		//version is item(17)
		return children.item(17).getTextContent();
	}
	
	

}
