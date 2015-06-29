/**
 * Review class compiles and organizes all of the information within the 
 * given node representing a single review in the form of an Element object.
 * 
 * @author Maddie Gordon
 * @version 6/18/15
 */
package reviewScraper.java;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.*;
import javax.swing.text.Element;

public class Review {

	// instance variables
	private HTMLDocument html;
	private Element reviewNode;

	// need these? or can just make methods public & call them? does it
	// matter?(i.e. less secure w/public inst var's)
	String title;
	String review;
	String date;
	int rating;

	/**
	 * constructor assigns parameters to instance variables so that they are
	 * accessible by the entire class
	 * 
	 * @param html
	 *            -represents entire html in the form of an HTMLDocument object
	 * @param reviewNode
	 *            -entire node of a single review in Element form
	 */
	public Review(HTMLDocument html, Element reviewNode) {
		this.html = html;
		this.reviewNode = reviewNode;

		// ***may not need lines below
		title = getTitle();
		review = getReview();
		date = getDate();
		rating = getRating();
	}

	/**
	 * method to retrieve review title
	 * 
	 * @return String representation of title of review
	 */
	private String getTitle() {
		Element titleLeaf = reviewNode.getElement(0).getElement(2);
		return makeString(titleLeaf);
	}

	/**
	 * method to retrieve review itself
	 * 
	 * @return String representation of actual review
	 */
	private String getReview() {
		Element reviewLeaf = reviewNode.getElement(1);
		return makeString(reviewLeaf);
	}

	/**
	 * method to retrieve date review was posted--for purpose of determining
	 * whether review was posted today only options are "a day ago" or
	 * "<x> hours ago", so if not "a day ago" (string.contains("hours ago")) then review was posted today
	 * 
	 * @return String representing how long ago review was posted
	 */
	private String getDate() {
		Element dateLeaf = reviewNode.getElement(2).getElement(1);
		return makeString(dateLeaf);
	}

	/**
	 * helper method to convert Element object representing a leaf node into the
	 * string that it represents
	 * 
	 * @param leafNode
	 *            -Element representing node containing desired text
	 * @return String representation of desired text within HTMLDocument
	 */
	private String makeString(Element leafNode) {
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

	/**
	 * method to retrieve review's star rating
	 * 
	 * @return int representation of review's rating
	 */
	private int getRating() {
		String rating = reviewNode.getClass().getName();
		return stars(rating);
	}

	/**
	 * auxiliary function to convert rating from string to int
	 * 
	 * @param rating
	 *            -string representation
	 * @return int representation of rating
	 */
	private int stars(String rating) {
		int stars = -1;
		if (rating.equals("one")) {
			stars = 1;
		} else if (rating.equals("two")) {
			stars = 2;
		} else if (rating.equals("three")) {
			stars = 3;
		} else if (rating.equals("four")) {
			stars = 4;
		} else if (rating.equals("five")) {
			stars = 5;
		}
		return stars;
	}
	

}
