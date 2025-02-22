package dominio;

import java.io.IOException;
import java.io.Serializable;

import servico.shared.IdGenerator;

/**
 * This class has a book of an order item rating bought by a customer.
 */
public class Review implements Serializable {
	private static final long serialVersionUID = -4063511252485472431L;
	
	private final int id;
	private final Customer customer;
	private final Book book;
	private final int bookstoreId;
	private double rating;

	/**
	 * @param customer Review's customer.
	 * @param book Review's book.
	 * @param bookstoreId Review's book identifier.
	 * @param rating Review's rating.
	 */
	
	public Review(Customer customer, Book book, double rating, int bookstoreId) throws IOException
	{
		this.id = IdGenerator.getInstance().getNextReviewId();;
		this.customer = customer;
		this.book = book;
		this.bookstoreId = bookstoreId;
		this.setRating(rating);
	}

	/**
	 * Review's id getter.
	 *
	 * @return Retrieves Review's id.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Review's rating getter.
	 *
	 * @return Retrieves Review's rating.
	 */
	public double getRating() {
		return this.rating;
	}

	/**
	 * Review's rating setter.
	 *
	 * @return Sets a Review's rating.
	 */
	public void setRating(double value) throws IOException {
		this.validateRatingValue(value);

		this.rating = value;
	}

	/**
	 * Review's customer getter.
	 *
	 * @return Retrieves Review's customer.
	 */
	public Customer getCustomer() {
		return this.customer;
	}

	/**
	 * Review's book getter.
	 *
	 * @return Retrieves Review's book.
	 */
	public Book getBook() {
		return this.book;
	}

	/**
	 * Review's bookstoreId getter.
	 *
	 * @return Retrieves Review's bookstoreId.
	 */
	public int getBookstoreId() {
		return this.bookstoreId;
	}

	/**
	 * Validate rating value method.
	 */
	private void validateRatingValue(double value) throws IOException {
		if(value < 0 || value > 5)
			throw new IOException("Rating with invalid value");
	}
	
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Review review = (Review) obj;
        return this.customer.equals(review.getCustomer()) &&
        		this.book.equals(review.getBook()) &&
        		this.id == review.id &&
        		this.rating == review.rating;
    }
}
