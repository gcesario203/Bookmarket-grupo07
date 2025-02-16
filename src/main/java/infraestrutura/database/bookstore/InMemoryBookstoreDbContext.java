package infraestrutura.database.bookstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dominio.Book;
import dominio.Cart;
import dominio.Order;
import dominio.Review;
import dominio.Stock;
import infraestrutura.database.bookstore.interfaces.IBookstoreDbContext;

public class InMemoryBookstoreDbContext implements IBookstoreDbContext {
    private Map<Book, Stock> stockByBook = new HashMap<>();
    private List<Cart> cartsById = new ArrayList<>();
    private List<Review> reviewsByIds = new ArrayList<>();
    private List<Order> ordersById = new ArrayList<>();
    private LinkedList<Order> ordersByCreation = new LinkedList<>();
    
	@Override
	public Map<Book, Stock> getStockByBook() {
		return stockByBook;
	}
	@Override
	public List<Cart> getCartsById() {
		return cartsById;
	}
	@Override
	public List<Review> getReviewsByIds() {
		return reviewsByIds;
	}
	@Override
	public List<Order> getOrdersById() {
		return ordersById;
	}
	@Override
	public LinkedList<Order> getOrdersByCreation() {
		return ordersByCreation;
	}
	@Override
	public void flushDatabase() {
        cartsById = new ArrayList<>();
        ordersById = new ArrayList<>();
        reviewsByIds = new ArrayList<>();
        ordersByCreation = new LinkedList<>();
        stockByBook = new HashMap<>();
	}
}
