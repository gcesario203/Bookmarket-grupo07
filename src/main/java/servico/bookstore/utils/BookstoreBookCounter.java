package servico.bookstore.utils;

import dominio.Book;

public class BookstoreBookCounter {
    Book book;
    int count;

    public BookstoreBookCounter(Book book) {
        this.book = book;
        this.count = 0;
    }

    public void addQuantity(int qty) {
        this.count += qty;
    }

    public int getCount() {
        return this.count;
    }
    
    public Book getBook() {
    	return this.book;
    }
}
