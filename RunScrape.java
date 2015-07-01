package reviewScraper.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class RunScrape {

	public static void main(String[] args) {
		ReviewScraper rs = new ReviewScraper();
		ArrayList<IOSReview> returned = new ArrayList<IOSReview>();

		String xml = "https://itunes.apple.com/rss/customerreviews/id=635150066/xml";
		
		Scanner dateReader = new Scanner(System.in);
		System.out.println("Enter today's date: ");
		String date = dateReader.nextLine();
		
		
		try {
			returned = rs.scrapeios(xml);
		} catch (IOException a) {
			a.printStackTrace();
		} catch (ParserConfigurationException b) {
			b.printStackTrace();
		} catch (SAXException c) {
			c.printStackTrace();
		}

		rs.createiOStxt(returned, date);

	}

}
