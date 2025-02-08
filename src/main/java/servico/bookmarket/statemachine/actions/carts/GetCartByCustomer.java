package servico.bookmarket.statemachine.actions.carts;

import java.util.stream.Stream;

import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;
import servico.bookstore.Bookstore;

public class GetCartByCustomer extends ExpecificBookstoreAction {
	int customerId;
	
	public GetCartByCustomer(int storeId, int customerId) {
		super(storeId);
		
		this.customerId = customerId;
	}

	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).getCart(customerId);
	}
}
