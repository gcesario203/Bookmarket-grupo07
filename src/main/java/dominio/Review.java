package dominio;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/*
 * Classe que possui a avaliação de um item de pedido de algum livro adquirido por um cliente
 */
public class Review implements Serializable {
	private static final long serialVersionUID = -4063511252485472431L;
	
	private final String id;
	
	private final Customer customer;
	
	private final Book book;
	
	private final int bookstoreId;
	
	private double rating;
	
	public Review(String id, Customer customer, Book book, double rating, int bookstoreId) throws IOException
	{
		this.id = id;
		
		this.customer = customer;
		
		this.book = book;
		
		this.bookstoreId = bookstoreId;
		
		this.setRating(rating);
	}
	
	public Review(Customer customer, Book book, double value, int bookstoreId) throws IOException
	{
		this.id = UUID.randomUUID().toString();
		
		this.customer = customer;
		
		this.book = book;
		
		this.rating = value;
		
		this.bookstoreId = bookstoreId;
		
		this.validateRatingValue();
	}
	
	public String getId() {
		return this.id;
	}
	
	public double getRating() {
		return this.rating;
	}
	
	public void setRating(double value) throws IOException {
		this.validateRatingValue();
		
		this.rating = value;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public Book getBook() {
		return this.book;
	}
	
	public int getBookstoreId() {
		return this.bookstoreId;
	}
	
	
	private void validateRatingValue() throws IOException {
		if(this.rating < 0 || this.rating > 5)
			throw new IOException("Avaliação com valor inválido");
	}
}
