package servico.bookmarket.statemachine.actions;

import java.util.stream.Stream;

import servico.bookstore.Bookstore;

public abstract class ExpecificBookstoreAction extends BookstoreAction{
	
	int bookstoreId;
	
	public ExpecificBookstoreAction(int bookstoreId) {
		this.bookstoreId = bookstoreId;
	}
	
	protected Bookstore getBookstoreById(Stream<Bookstore> bookstores) {
		return bookstores.filter(b -> b.getId() == this.bookstoreId).findFirst().get();
	}
	
	@Override
	public abstract Object executeOnBookstore(Stream<Bookstore> bookstore);
}