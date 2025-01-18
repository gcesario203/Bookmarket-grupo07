package dominio;

import java.io.IOException;
import java.io.Serializable;

/*
 * Classe que possui a avaliação de um item de pedido de algum livro adquirido por um cliente
 */
public class Evaluate implements Serializable {
	private static final long serialVersionUID = -4063511252485472431L;
	
	private final Order order;
	
	private final OrderLine orderLine;
	
	private final int value;
	
	public Evaluate(Order order, OrderLine orderLine, int value) throws IOException
	{
		this.order = order;
		
		this.orderLine = orderLine;
		
		this.value = value;
		
		this.ValidateEvaluateValue();
	}
	
	public int getValue() {
		return this.value;
	}
	
	public Order getOrder() {
		return this.order;
	}
	
	public OrderLine getOrderLine() {
		return this.orderLine;
	}
	
	
	private void ValidateEvaluateValue() throws IOException {
		if(this.value < 0 || this.value > 5)
			throw new IOException("Avaliação com valor inválido");
	}
}
