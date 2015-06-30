package reviewScraper.java;

import java.net.*;
import java.util.Enumeration;
import java.io.*;

import javax.swing.text.AttributeSet;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.*;
//import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.event.DocumentEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

/*
 *sample code from site using Jsoup library:
 *	https://www.packtpub.com/books/content/
 * 	creating-sample-web-scraper
 *"scrapes" first paragraph of web page
 *originally intended for wikipedia page--
 *	may need extra extraction code to reach
 *	individual reviews & titles
 */
public class ReviewScraper {

	private static HTMLDocument html;
	private static HTMLEditorKit kit;

	public ReviewScraper() {
	} // constructor for junit testing

	public static void main(String[] args) {
		// scrapeHiltonGarden();
		// scrapeiTunes();
		scrapeGooglePlay();

	}

	/**
	 * scrapes the Hilton Garden Inn Dallas's website for random text within
	 * bullet points
	 */
	public static void scrapeHiltonGarden() {
		kit = new HTMLEditorKit();
		html = getUrl("http://hiltongardeninn3.hilton.com/en/hotels/texas/"
				+ "hilton-garden-inn-dallas-market-center-DALMAGI/index.html",
				kit);
		// System.out.println(html.replaceAll("\\s", ""));
		// System.out.println(kit.getContentType());

		// leaf(single bullet in desired list with text)
		Element leaf;

		// parent of leaf; contains entire bulleted list
		/*
		 * Element leafParent = html.getElement("ls-gen11-ls-area-body")
		 * .getElement(0).getElement(1).getElement(1).getElement(0)
		 * .getElement(0);
		 */// not used

		// parent node who holds the bullets
		Element grandNode = html.getElement("ls-gen11-ls-area-body")
				.getElement(0).getElement(1).getElement(1);

		int children = grandNode.getElementCount();
		System.out.println("children: " + children); // number of bullet points
		// loop through all bullet points(leaf elements) contained in grandNode,
		// make them into ParagraphView objects, find their starting and ending
		// positions in the main HTMLDocument, and isolate those sections into
		// Strings & print them
		for (int i = 0; i < children; i++) {
			leaf = html.getElement("ls-gen11-ls-area-body").getElement(0)
					.getElement(1).getElement(1).getElement(i).getElement(0)
					.getElement(0);
			ParagraphView paraView = new ParagraphView(leaf);

			// to get the element's position within the document
			int start = paraView.getStartOffset();
			int end = paraView.getEndOffset();
			int length = end - start;

			try {
				String target = html.getText(start, length);
				System.out.println(target);
			} catch (BadLocationException e) {
				System.out.println("bad location exception thrown");
			}
		}

		// Document doc = Jsoup.parse(html); // PATT
		// String contentText =
		// doc.select("#mw-content-text > p").first().text(); // PATT
		// #content-body(id) ; .ng-binding > p (class)
		// System.out.println(contentText); //PATT
	}

	/**
	 * scrapes the app reviews that are featured on the PUBLIC iTunes page for
	 * the HHonors app
	 */
	public static void scrapeiTunes() {
		kit = new HTMLEditorKit();
		html = getUrl(
				"https://itunes.apple.com/us/app/hilton-hhonors/id635150066?mt=8",
				kit); // works for https url...

		// leaf(single bullet in desired list with text)
		Element leaf;

		// Element review =
		// html.getElement("content").getElement(0).getElement(4).getElement(1);
		Element reviewParent = html.getElement("content").getElement(0)
				.getElement(1).getElement(4);

		int children = reviewParent.getElementCount() - 1;
		System.out.println("children: " + children); // number of bullet points
		// loop through all bullet points(leaf elements) contained in grandNode,
		// make them into ParagraphView objects, find their starting and ending
		// positions in the main HTMLDocument, and isolate those sections into
		// Strings & print them
		for (int i = 1; i < children + 1; i++) { // +1 because review nodes have
													// same parent as
													// "Customer Reviews" header
													// node
			leaf = html.getElement("content").getElement(0).getElement(1)
					.getElement(4).getElement(i);

			ParagraphView paraView = new ParagraphView(leaf);

			// to get the element's position within the document
			int start = paraView.getStartOffset();
			int end = paraView.getEndOffset();
			int length = end - start;

			try {
				String target = html.getText(start, length);
				System.out.println(target);
			} catch (BadLocationException e) {
				System.out.println("bad location exception thrown");
			}
		}
	}

	public static void scrapeGooglePlay() {
		kit = new HTMLEditorKit();
		html = getUrl(
				"https://play.google.com/store/apps/details?id=com.hilton.android.hhonors&hl=en",
				kit); // works for https url...

		// Element leaf;
		// Element intermediate = html.getElement("body-content").getElement(0)
		// .getElement(0).getElement(0);
		// Element parent = intermediate.getElement(1).getElement(1)
		// .getElement(0).getElement(0).getElement(1).getElement(0)
		// .getElement(0).getElement(1);
		// Element helpfulness = parent.getElement(2);
		// Element newest = parent.getElement(0);
		// Element display = intermediate.getElement(1).getElement(1)
		// .getElement(0).getElement(0).getElement(1).getElement(0)
		// .getElement(0).getElement(0).getElement(0);
		// Element dropdown = intermediate.getElement(1).getElement(1)
		// .getElement(0).getElement(0).getElement(1).getElement(0);
		// //.getElement(0);
		// // System.out.print(makeString(display));
		// System.out.print("1: " + makeString(display));
		// try {
		// //html.setInnerHTML(dropdown,
		// "<div class=\"dropdown-menu-container review-filter id-review-sort-filter open\">");
		//
		// html.setInnerHTML(newest,
		// "<div class=\"dropdown-child selected\" data-dropdown-value=\"0\">Newest</div>");
		// html.setInnerHTML(helpfulness,
		// "<div class=\"dropdown-child\" data-dropdown-value=\"2\">Helpfulness</div>");
		// html.setInnerHTML(display,
		// "<span class=\"displayed-child\">Newest</span>");
		// } catch (BadLocationException | IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.print("2: " + makeString(display));
		// // System.out.print(makeString(display));

		// Element expandPagesContainer = html.getElement("body-content")
		// .getElement(0).getElement(0).getElement(0).getElement(1)
		// .getElement(1).getElement(0).getElement(1).getElement(0).getElement(0);

		// Element previewReviews =
		// html.getElement("body-content").getElement(0)
		// .getElement(0).getElement(0).getElement(1).getElement(1)
		// .getElement(0).getElement(1).getElement(0).getElement(0);

		// System.out.println(attributes(previewReviews, "1"));

		Element one = html.getElement("body-content");
		// System.out.println(attributes(one, "1"));

		Element two = one.getElement(0); // class="outer-container"
		// System.out.println(attributes(two, "2"));

		Element three = two.getElement(0);// class="inner-container"
		// System.out.println(attributes(three, "3"));

		Element four = three.getElement(0);
		// System.out.println(attributes(four, "4")); //class="main-content"

		Element five = four.getElement(1);
		// System.out.println(attributes(five, "5"));
		// //class="details-wrapper apps"

		Element six = five.getElement(1);
		// System.out.println(attributes(six, "6"));
		// //class="details-section reviews"

		Element seven = six.getElement(0);
		// System.out.println("(details-section contents): \n" +
		// attributes(seven, "7")); //class="details-section contents"

		Element eight = seven.getElement(1);
		// System.out.println(attributes(eight, "8"));
		// //class="details-section-body expandable"

		Element nine = eight.getElement(0); // this seems to be expandable
											// reviews
		// System.out.println(attributes(nine, "9")); //no attributes

		Element ten = nine.getElement(0);
		// System.out.println(attributes(ten, "10"));
		// //class="expand-pages-container"
		// System.out.println(makeString(ten));

		Element eleven;
		for (int i = 0; i < ten.getElementCount(); i++) {
			eleven = ten.getElement(i); // class="expand-pages-container"
			// System.out.println(attributes(eleven, "11"));
			// System.out.println(makeString(eleven));
		}

		Element ten2 = eight.getElement(1);
		System.out.println("children of element:  " + ten2.getElementCount()
				+ "\n");
		Element eleven2;
		for (int i = 0; i < ten2.getElementCount(); i++) {
			eleven2 = ten2.getElement(i); // class="expand-pages-container"
			// if(makeString(eleven2).contains("June 8, 2015")){
			// System.out.println(attributes(eleven, "11"));
			// System.out.println(makeString(eleven2));
			// System.out.println("eleven2: " +
			// eleven2.getElement(0).getElementCount());
			try {
				Element authorDate = eleven2.getElement(0).getElement(1)
						.getElement(0).getElement(0);
				String header = makeString(authorDate);
				// System.out.println(header);

				String date = "June 29, 2015";
				if (header.contains(date)) { // specify date (will need to
												// separate review body node to
												// make
												// sure doesn't contain
												// feedback)
					// System.out.println("review: \n" + makeString(eleven2));
					Element review = eleven2.getElement(0).getElement(2);
					System.out.println(header + "\n" + makeString(review));
					// System.out.println(header); //when "Newest" isn't
					// selected, doesn't find all from most recent date
				}
			} catch (NullPointerException e) {
				// skip to next review
			}
		}
		//
		// try {
		// Element featuredReview1 = previewReviews.getElement(1)
		// .getElement(0);
		// // Element author = featuredReview1.getElement(1).getElement(0)
		// // .getElement(0);
		// // System.out.println("author: " + makeString(author));
		// System.out.println(attributes(featuredReview1, "2"));
		//
		// } catch (NullPointerException e) {
		// e.printStackTrace();
		// // Element singleReviewRating =
		// }

	}

	public static void scrapeGooglePlay2(String url) throws IOException,
			ParserConfigurationException, SAXException {
		// String url =
		// "https://play.google.com/store/apps/details?id=com.hilton.android.hhonors&hl=en";
		// kit = new HTMLEditorKit();
		// html = getUrl(url, kit);
		// http://www.rgagnon.com/javadetails/java-0530.html

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(url);
		DOMImplementation impl = builder.getDOMImplementation();
		System.out.println(doc.getDocumentURI());

	}

	/**
	 * auxiliary function takes string url and returns the HTMLDocument
	 * associated with the web page that the url represents
	 * 
	 * @param url
	 *            -url of web page we want to connect to
	 * @param kit
	 *            -HTMLEditorKit object constructed in methods calling this
	 *            method; purpose for inclusion is createDefaultDocument method
	 *            to obtain HTML in HTMLDocument format
	 * @return HTML of desired web page in HTMLDocument object format
	 */
	public static HTMLDocument getUrl(String url, HTMLEditorKit kit) {
		URL urlObj = null;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("The url was malformed!");
		}
		/*
		 * URLConnection urlCon = null; BufferedReader in = null; String
		 * outputText = ""; try { urlCon = urlObj.openConnection(); in = new
		 * BufferedReader(new InputStreamReader( urlCon.getInputStream()));
		 * String line = ""; while ((line = in.readLine()) != null) { outputText
		 * += line + "\n"; } in.close(); } catch (IOException e) {
		 * System.out.println("There was an error connecting to the URL");
		 * return ""; } return outputText;
		 */

		HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();

		try {
			doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
			Reader HTMLReader = new InputStreamReader(urlObj.openConnection()
					.getInputStream());
			kit.read(HTMLReader, doc, 0);
		} catch (IOException e) {
			System.out.println("There was an error connecting to the URL");
		} catch (BadLocationException e) {
			System.out.println("bad location in document");
		}
		return doc;
	}

	/**
	 * function solely for purpose of testing/finding html element
	 * 
	 * @param e
	 *            -element whose attributes are being printed
	 * @param x
	 *            -identifier to separate calls to this method
	 * @return String containing all attributes of given element
	 */
	private static String attributes(Element e, String x) {
		String toReturn = "";
		AttributeSet as = e.getAttributes();
		for (Enumeration<?> en = as.getAttributeNames(); en.hasMoreElements();) {
			toReturn += (x + ": " + en.nextElement() + "\n");
		}
		toReturn += "element count for " + x + ": " + e.getElementCount()
				+ "\n";
		return toReturn;
	}

	/**
	 * helper method to convert Element object representing a leaf node into the
	 * string that it represents
	 * 
	 * @param leafNode
	 *            -Element representing node containing desired text
	 * @return String representation of desired text within HTMLDocument
	 */
	private static String makeString(Element leafNode) {
		String toReturn = ""; // initialize local variable

		// get the element's position within the document
		ParagraphView paraView = new ParagraphView(leafNode);
		int start = paraView.getStartOffset();
		int end = paraView.getEndOffset();
		int length = end - start;

		// create string
		try {
			toReturn = html.getText(start, length);
		} catch (BadLocationException e) {
			System.out.println("bad location exception thrown");
		}

		return toReturn;
	}

}
