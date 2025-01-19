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
	
	private double value;
	
	public Review(String id, Customer customer, Book book, double value) throws IOException
	{
		this.id = id;
		
		this.customer = customer;
		
		this.book = book;
		
		this.setValue(value);
	}
	
	public Review(Customer customer, Book book, double value) throws IOException
	{
		this.id = UUID.randomUUID().toString();
		
		this.customer = customer;
		
		this.book = book;
		
		this.value = value;
		
		this.validateEvaluateValue();
	}
	
	public String getId() {
		return this.id;
	}
	
	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value) throws IOException {
		this.validateEvaluateValue();
		
		this.value = value;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public Book getBook() {
		return this.book;
	}
	
	
	private void validateEvaluateValue() throws IOException {
		if(this.value < 0 || this.value > 5)
			throw new IOException("Avaliação com valor inválido");
	}
}
