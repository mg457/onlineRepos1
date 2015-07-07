package reviewScraper.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class RunScrape {

	// ****RSS FEED..? :
	// http://appfigures.com/reports/reviews/rss/147624:UH4aurnu9=PwNvRxaMRPYg/?sort=-date&products=40045482739&lang=en

	public static void main(String[] args) {
		ReviewScraper rs = new ReviewScraper();

		Scanner scanner = new Scanner(System.in);
		System.out
				.println("Enter 1 if you would like to scrape iOS reviews, or 2 if you would like to extract AppFigures reviews");
		int decision = scanner.nextInt();

		if (decision == 1) {
			Scanner scanner3 = new Scanner(System.in);
			ArrayList<IOSReview> returned = new ArrayList<IOSReview>();

			String xml = "https://itunes.apple.com/rss/customerreviews/id=635150066/xml";

			System.out.println("Enter desired date's month: ");
			String month = scanner3.nextLine();
			if (month.length() == 1) {
				String oldMonth = month;
				month = "0" + oldMonth;
			}
			System.out.println("Enter desired day: ");
			String day = scanner3.nextLine();
			if (day.length() == 1) {
				String oldDay = day;
				day = "0" + oldDay;
			}
			String date = month + "/" + day + "/" + "2015";
			System.out.println("retrieving reviews from " + date);

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
			System.out.println("done");

		} else if (decision == 2) {
			ArrayList<AppFiguresReview> returned = new ArrayList<AppFiguresReview>();

			String jsonPath = "C:\\Users\\madeline2\\Downloads\\reviews.json";
			Scanner scanner2 = new Scanner(System.in);
			System.out.println("Enter desired date's month: ");
			String month = scanner2.nextLine();
			if (month.length() == 1) {
				String oldMonth = month;
				month = "0" + oldMonth;
			}
			System.out.println("Enter desired day: ");
			String day = scanner2.nextLine();
			System.out.println("");
			if (day.length() == 1) {
				String oldDay = day;
				day = "0" + oldDay;
			}
			String date = month + "/" + day + "/" + "2015";
			System.out.println("retrieving reviews from " + date);

			try {
				// returned = rs.appFiguresReviews(jsonPath);
				for (int j = 0; j < 8; j++) {
					String add = "";
					if (j == 0)
						add = "";
					else
						add = " (" + j + ")";
					returned = rs
							.appFiguresReviews("C:\\Users\\madeline2\\Downloads\\reviews"
									+ add + ".json");
					rs.countReviews(returned, 7);
					// rs.createAppFigurestxt(returned, date);
				}
			} catch (IOException a) {
				a.printStackTrace();
			} catch (ParserConfigurationException b) {
				b.printStackTrace();
			} catch (SAXException c) {
				c.printStackTrace();
			}

			System.out.println("done");

		} else {
			System.out.println("Please enter a valid number");
		}

	}

}
