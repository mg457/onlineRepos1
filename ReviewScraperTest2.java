package reviewScraper.java;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.*;
import org.xml.sax.SAXException;

import java.util.*;

public class ReviewScraperTest2 {

	ReviewScraper rs;
	private HTMLDocument html;
	private HTMLEditorKit kit;

	@Before
	public void setup() {
		rs = new ReviewScraper();
		kit = new HTMLEditorKit();
		
	}

	@Test
	public void testScraping() {
		ArrayList<IOSReview> returned = new ArrayList<IOSReview>();
		
		String url = "https://play.google.com/store/apps/details?id=com.hilton.android.hhonors&hl=en";
		String xml = "https://itunes.apple.com/rss/customerreviews/id=635150066/xml";
		String urltest = "https://www.google.com";
		try {
			//rs.scrapeGooglePlay2(urltest);
			returned = rs.scrapeios(xml);
		} catch (IOException a) {
			fail("exception");
			System.out.println(a.toString());
		} catch (ParserConfigurationException b) {
			System.out.println(b.toString());
			fail("exception");
		} catch (SAXException c) {
			System.out.println(c.toString());
			fail("exception");
		}
		
		assert(returned.size() == 51);

	}

	/*@Test
	public void testGetUrl() {
		// fail("Not yet implemented");
		html = rs.getUrl("https://play.google.com/store/apps/details?id=com.hilton.android.hhonors&hl=en",
				kit);
		
	}*/

}
