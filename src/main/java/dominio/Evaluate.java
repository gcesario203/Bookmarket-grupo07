package dominio;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/*
 * Classe que possui a avaliação de um item de pedido de algum livro adquirido por um cliente
 */
public class Evaluate implements Serializable {
	private static final long serialVersionUID = -4063511252485472431L;
	
	private final UUID id;
	
	private final Customer customer;
	
	private final Book book;
	
	private final int value;
	
	public Evaluate(UUID id, Customer customer, Book book, int value) throws IOException
	{
		this.id = id;
		
		this.customer = customer;
		
		this.book = book;
		
		this.value = value;
		
		this.validateEvaluateValue();
	}
	
	public Evaluate(Customer customer, Book book, int value) throws IOException
	{
		this.id = UUID.randomUUID();
		
		this.customer = customer;
		
		this.book = book;
		
		this.value = value;
		
		this.validateEvaluateValue();
	}
	
	public string getId() {
		return this.id;
	}
	
	public int getValue() {
		return this.value;
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
