package servico.bookmarket.statemachine.actions.carts;

import java.util.stream.Stream;

import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;
import servico.bookstore.Bookstore;

public class GetCartById extends ExpecificBookstoreAction {
	int cartId;
	
	public GetCartById(int storeId, int cartId) {
		super(storeId);
		this.cartId = cartId;
	}
	
	@Override
	public Object executeOnBookstore(Stream<Bookstore> bookstore) {
		return getBookstoreById(bookstore).getCart(cartId);
	}

}
