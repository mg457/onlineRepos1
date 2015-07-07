package reviewScraper.java;

import java.io.*;
import java.net.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ReviewScraper {

	public ReviewScraper() { // constructor for junit testing
	}

	public static ArrayList<IOSReview> scrapeios(String url)
			throws IOException, ParserConfigurationException, SAXException {
		ArrayList<IOSReview> toReturn = new ArrayList<IOSReview>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// added 6/30
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setExpandEntityReferences(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(url);

		// System.out.println(doc.getDocumentURI());
		NodeList n = doc.getElementsByTagName("entry");

		if (n.getLength() > 0) {
			System.out.println("connected");
		} else {
			System.out.println("connection didn't work");
		}

		// System.out.println("15 "
		// +n.item(1).getChildNodes().item(15).getTextContent());

		// IOSReview one = new IOSReview(n.item(1));
		// System.out.println(one.getDate());
		// System.out.println(one.getTitle());
		// System.out.println(one.getReview());
		// System.out.println(one.getRating());

		// for loop & add all to iosreview list starting at index 1 (1st "entry"
		// node is random web stuff
		for (int i = 1; i < n.getLength(); i++) {
			toReturn.add(new IOSReview(n.item(i)));
		}

		// System.out.println(toReturn.size());
		return toReturn;
	}

	public static ArrayList<AppFiguresReview> appFiguresReviews(String docPath)
			throws SAXException, IOException, ParserConfigurationException {
		ArrayList<AppFiguresReview> toReturn = new ArrayList<AppFiguresReview>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setExpandEntityReferences(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		//InputSource is = new InputSource(docPath);
		File file = new File(docPath);
		FileReader fr = new FileReader(file);
		JSONParser parser = new JSONParser();
		
		
		try {
			Object obj = parser.parse(fr);
			//JSONArray array = (JSONArray) obj;
			JSONObject jsobj = (JSONObject) obj;
		//	System.out.println(jsobj.toJSONString(jsobj));
			JSONArray reviewList = (JSONArray) jsobj.get("reviews");
//			JSONObject first = (JSONObject) reviewList.get(0);
//			AppFiguresReview rev = new AppFiguresReview(first);
			//System.out.println(rev.getDate());
			
			for (int i = 0; i < reviewList.size(); i++) {
				toReturn.add(new AppFiguresReview((JSONObject)reviewList.get(i)));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Document doc = builder.parse(file);
//		NodeList n = doc.getElementsByTagName("li");
//		if (n.getLength() > 0) {
//			System.out.println("connected. length: " + n.getLength());
//		} else {
//			System.out.println("connection didn't work");
//		}

		

		return toReturn;
	}
	
	public static void createAppFigurestxt(ArrayList<AppFiguresReview> list,
			String date) {
		AppFiguresReview current;
		String curLine;
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(
							//"C:\\Users\\madeline2\\Documents\\scrape\\reviews\\new_appfigures_reviews.txt"));
							"P:\\DPI_Mobile Product\\new_android_reviews.txt"));
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);
				curLine = current.getDate() + "," + current.getTitle() + ",\""
						+ current.getReview() + "\"," + current.getVersion()
						+ "," + current.getDevice() + "," + current.getRating();
				
				if (current.getDate().equals(date) /*&& current.getType().equals(type)*/) {
					out.write(curLine);
					out.newLine();
					//System.out.println(curLine);
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void countReviews(ArrayList<AppFiguresReview> list,
			int date) throws SAXException, IOException, ParserConfigurationException {
		AppFiguresReview current;
		String curLine;
		int pos = 0;
		int neg = 0;
		int neutral = 0;
		int total = 0;
		//ArrayList<AppFiguresReview> list;
		
		
			
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);
//				curLine = current.getDate() + "," + current.getTitle() + ",\""
//						+ current.getReview() + "\"," + current.getVersion()
//						+ "," + current.getDevice() + "," + current.getRating();
//				
				if (current.getDate().charAt(1)==date) /*&& current.getType().equals(type)*/ {
					if(current.getRating().equals("5.00") || current.getRating().equals("4.00")) {
						pos++;
						System.out.println(pos);
					}
					else if(current.getRating().equals("3.00") ) 
						neutral++;
					else if(current.getRating().equals("2.00") || current.getRating().equals("1.00")) {
						neg++;
					}
					total++;
				}
			}
			
			
			
		
		System.out.println("Positive: " + pos + "\nNegative: " + neg + "\nNeutral: " + neutral + "\nTotal: " + total);

	}

	public static void createiOStxt(ArrayList<IOSReview> list, String date) {
		IOSReview current;
		String curLine;
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter(
					 "P:\\DPI_Mobile Product\\new_iOSreviews.txt"));
						//	"C:\\Users\\madeline2\\Documents\\scrape\\reviews\\new_iOSreviews.txt"));
			// out.write("Date\tTitle\tReview\tVersion\tStars\tCategory\tStatus\n");
			for (int i = 0; i < list.size(); i++) {
				current = list.get(i);
				// curLine = current.getDate() + "\t" + current.getTitle() +
				// "\t"
				// + current.getReview() + "\t" + current.getVersion()
				// + "\t" + current.getRating();
				curLine = current.getDate() + "," + current.getTitle() + ",\""
						+ current.getReview() + "\"," + current.getVersion()
						+ "," + current.getRating();
				if (current.getDate().equals(date)) {

					/*
					 * curLine = current.getDate() + ";;" + current.getTitle() +
					 * ";;" + current.getReview() + ";;" + current.getRating();
					 */
					out.write(curLine);
					out.newLine();
					// System.out.println(curLine);
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
