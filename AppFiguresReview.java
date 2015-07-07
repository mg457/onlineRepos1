package reviewScraper.java;

import java.net.*;
import java.util.Enumeration;
import java.io.*;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.w3c.dom.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AppFiguresReview {

	// JSONArray attributes;
	JSONObject attributes;

	public AppFiguresReview(JSONObject attributes) {
		this.attributes = attributes;
	}

	public String getTitle() {
		String title = (String) attributes.get("title");
		return title;
	}

	public String getDate() {
		String toReturn = "";
		String raw = (String) attributes.get("date");

		String year = raw.substring(0, 4);
		String month = raw.substring(5, 7);
		String date = raw.substring(8, 10);
		toReturn = month + "/" + date + "/" + year;

		return toReturn;
	}

	public String getVersion() {
		return (String) attributes.get("version");
	}

	public String getRating() {
		String stars = (String) attributes.get("stars");
		// int num = Integer.parseInt(stars);
		//
		// if (Integer.valueOf(stars).doubleValue() != num) {
		//
		// }
		// String rating = stars;
		// if (stars.length() > 1) {
		// for (int i = 1; i < stars.length(); i ++) {
		// if(stars.charAt(1)=='.' && stars.charAt(i)=='0') {
		//
		// }
		// }
		// }
		return stars;
	}

	public String getReview() {
		return (String) attributes.get("review");
	}

	public String getDevice() { // ..?
		return "";
	}

	public String getType() { // change once iOS version updates
		String type = "";
		if (!(getVersion() == null)) {
			if (getVersion().equals("2.2.2")) {
				type = "iOS";
			}
		} else {
			type = "Android";
		}

		return type;
	}

}
