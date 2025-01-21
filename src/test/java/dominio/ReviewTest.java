package dominio;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import org.junit.BeforeClass;

import servico.Bookmarket;
import servico.Bookstore;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
*
* @author INF329
*/
public class ReviewTest {
    static Bookstore instance;

    @BeforeClass
    public static void setUpClass() {
        
        long seed = 0;
        long now = System.currentTimeMillis();
        int items = 10000;
        int customers = 1000;
        int addresses = 1000;
        int authors = 100;
        int orders = 10000;
        Random rand = new Random(seed);
        Bookstore.populate(seed, now, items, customers, addresses, authors);
        instance = new Bookstore(0);
        instance.populateInstanceBookstore(orders, rand, now);
    }
    
    @Test(expected = IOException.class)
    public void ShouldThrowExceptionWhenCreateWithInvalidValueBiggerThanFive() throws IOException {
    	Customer customer = instance.getCustomer(1);
    	
    	Optional<Book> book = instance.getBook(1);
    	
    	new Review(customer, book.get(), 6, instance.getId());

    }

    @Test(expected = IOException.class)
    public void ShouldThrowExceptionWhenCreateWithInvalidValueSmallerThanZero() throws IOException {
    	Customer customer = instance.getCustomer(1);

    	Optional<Book> book = instance.getBook(1);

    	new Review(customer, book.get(), -1, instance.getId());

    }

    @Test
    public void ShouldCreateReviewWithValidValue() throws IOException {
    	Customer customer = instance.getCustomer(1);

    	Optional<Book> book = instance.getBook(1);

    	Review review = new Review(customer, book.get(), 3, instance.getId());

    	assertEquals(3, review.getRating(), 0);

    }
}
