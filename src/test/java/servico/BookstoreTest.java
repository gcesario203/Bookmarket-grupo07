package servico;

import dominio.*;
import dominio.customer.enums.Type;
import util.TPCW_Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.After;
import servico.bookstore.Bookstore;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author INF329
 */
public class BookstoreTest {

    public BookstoreTest() {
    }

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

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of populateInstanceBookstore method, of class Bookstore.
     */
    @Test
    public void shouldPopulateInstanceBookstore() {
        long seed = 0;
        long now = System.currentTimeMillis();
        int items = 83;
        int customers = 4;
        int addresses = 13;
        int authors = 1;
        int orders = 30;
        Random rand = new Random();
        Bookstore.populate(seed, now, items, customers, addresses, authors);
        Bookstore bookstore = new Bookstore(21);
        bookstore.populateInstanceBookstore(orders, rand, now);

        List<Order> instanced_orders = bookstore.getOrdersById();
        List<Review> reviews = bookstore.getReviews();
        Author author = bookstore.getBook(0).get().getAuthor();
        List<Book> result = bookstore.getBooksByAuthor(author.getLname());
        List<Stock> stock = bookstore.getStocks();

        assertEquals(30, instanced_orders.size());
        assertEquals(30, reviews.size());
        assertTrue(stock.size() > 30);
        assertTrue(stock.get(0).getQty() > 0);
        assertTrue(stock.get(0).getCost() > 0);
        assertEquals(stock.get(0).getIdBookstore(),bookstore.getId());
    }

    /**
     * Test of isPopulated method, of class Bookstore.
     */
    @Test
    public void testIsPopulated() {
        boolean expResult = true;
        boolean result = instance.isPopulated();
        assertEquals(expResult, result);
    }

    /**
     * Test of alwaysGetAddress method, of class Bookstore.
     */
    @Test
    public void testAlwaysGetAddress() {
        String street1 = "";
        String street2 = "";
        String city = "";
        String state = "";
        String zip = "";
        String countryName = "";
        Address result = instance.alwaysGetAddress(street1, street2, city, state, zip, countryName);
        Address expResult = new Address(0, street1, street2, city, state, zip, result.getCountry());
        assertEquals(expResult, result);

    }

    /**
     * Test of getCustomer method, of class Bookstore.
     */
    @Test
    public void testGetCustomer_int() {
        int cId = 0;
        Customer result = instance.getCustomer(cId);
        assertEquals(cId, result.getId());
    }

    /**
     * Test of getCustomer method, of class Bookstore.
     */
    @Test
    public void testGetCustomer_String() {
        String username = instance.getCustomer(10).getUname();
        Customer result = instance.getCustomer(username).get();
        assertEquals(username, result.getUname());

    }

    /**
     * Test of createCustomer method, of class Bookstore.
     */
    @Test
    public void testCreateCustomer() {
        String fname = "";
        String lname = "";
        String street1 = "";
        String street2 = "";
        String city = "";
        String state = "";
        String zip = "";
        String countryName = "";
        String phone = "";
        String email = "";
        double discount = 0.0;
        Date birthdate = null;
        String data = "";
        long now = 0L;

        Customer result = instance.createCustomer(fname, lname, street1, street2, city, state, zip, countryName, phone, email, discount, birthdate, data, now, Type.DEFAULT);
        int id = result.getId();
        String uname = result.getUname();
        Date since = result.getSince();
        Date lastVisit = result.getLastVisit();
        Date login = result.getLogin();
        Date expiration = result.getExpiration();
        Address address = result.getAddress();
        Customer expResult = new Customer(id, uname, uname.toLowerCase(), fname,
                lname, phone, email, since, lastVisit, login, expiration,
                discount, 0, 0, birthdate, data, address, null);
        assertTrue(expResult.equals(result));

    }

    /**
     * Test of refreshCustomerSession method, of class Bookstore.
     */
    @Test
    public void testRefreshCustomerSession() {
        int cId = 0;
        long now = 0L;
        instance.refreshCustomerSession(cId, now);
    }

    /**
     * Test of getBook method, of class Bookstore.
     */
    @Test
    public void testGetBook() {
        int bId = 0;
        Book result = instance.getBook(bId).get();
        assertEquals(bId, result.getId());

    }

    /**
     * Test of getBooksBySubject method, of class Bookstore.
     */
    @Test
    public void testGetBooksBySubject() {
        String subject = "ARTS";
        List<Book> result = instance.getBooksBySubject(subject);
        assertEquals(result.size(), result.stream().filter(book -> book.getSubject().equals(subject)).count());

    }

    /**
     * Test of getBooksByTitle method, of class Bookstore.
     */
    @Test
    public void testGetBooksByTitle() {
        String title = instance.getBook(0).get().getTitle().substring(0, 4);
        List<Book> result = instance.getBooksByTitle(title);
        assertEquals(result.size(), result.stream().filter(book -> book.getTitle().startsWith(title)).count());
    }

    /**
     * Test of getBooksByAuthor method, of class Bookstore.
     */
    @Test
    public void testGetBooksByAuthor() {
        Author author = instance.getBook(0).get().getAuthor();
        List<Book> result = instance.getBooksByAuthor(author.getLname());
        assertEquals(result.size(), result.stream().filter(book -> book.getAuthor().getLname().equals(author.getLname())).count());

    }

    /**
     * Test of getNewBooks method, of class Bookstore.
     */
    @Test
    public void testGetNewBooks() {
        String subject = instance.getBook(0).get().getSubject();
        List<Book> result = instance.getNewBooks(subject);
        assertEquals(result.size(),
                result.stream().filter(book -> book.getSubject().equals(subject)).count());

    }

    /**
     * Test of updateBook method, of class Bookstore.
     */
    @Test
    public void testUpdateBook() {
        int bId = 0;
        double cost = 0.0;
        String image = "";
        String thumbnail = "";
        long now = 0L;
        Book book = instance.getBook(bId).get();
        instance.updateBook(bId, image, thumbnail, now);
        assertEquals(bId, book.getId());
        //assertEquals(cost, book.getCost(), 0.0);
        assertEquals(image, book.getImage());
        assertEquals(thumbnail, book.getThumbnail());
    }

    /**
     * Test of getCart method, of class Bookstore.
     */
//    @Test
    public void testGetCart() {
        int id = 0;

        Cart expResult = null;
        Cart result = instance.getCart(id);
        assertEquals(expResult, result);

    }

    /**
     * Test of createCart method, of class Bookstore.
     */
    private void testCreateCart() {
        long now = 0L;

        Cart expResult = null;
        Cart result = instance.createCart(now);
        assertEquals(expResult, result);

    }

    /**
     * Test of cartUpdate method, of class Bookstore.
     */
//    @Test
    public void testCartUpdate() {
        testCreateCart();
        int cId = 0;
        Integer bId = 1;
        List<Integer> bIds = Arrays.asList(10, 20);
        List<Integer> quantities = Arrays.asList(10, 20);
        long now = 0L;

        Cart expResult = null;
        Cart result = instance.cartUpdate(cId, bId, bIds, quantities, now);
        assertEquals(expResult, result);

    }

    /**
     * Test of confirmBuy method, of class Bookstore.
     */
//    @Test
    public void testConfirmBuy() {
        int customerId = 0;
        int cartId = 0;
        String comment = "";
        String ccType = "";
        long ccNumber = 0L;
        String ccName = "";
        Date ccExpiry = null;
        String shipping = "";
        Date shippingDate = null;
        int addressId = 0;
        long now = 0L;
        Order expResult = null;
        Order result = instance.confirmBuy(customerId, cartId, comment, ccType, ccNumber, ccName, ccExpiry, shipping, shippingDate, addressId, now);
        assertEquals(expResult, result);
    }
    
    @Test
    public void shouldHasPopulatedReviews() {
    	assertEquals(instance.getReviews().size(), 10000);
    }
    
    @Test(expected = IOException.class)
    public void cannotCreateReviewWithInvalidValue() throws IOException {
    	Customer customer = instance.getCustomer(1);
    	
    	Optional<Book> book = instance.getBook(1);
    	
    	instance.createReview(customer, book.get(), -1);
    }
    
    @Test
    public void shouldGetTheCorrectReviewById() {
    	Review review = instance.getReviews().get(0);
    	
    	assertEquals(review.getId(), instance.getReviewById(review.getId()).get().getId());
    }
    
    @Test
    public void shouldGetTheCorrectReviewByCustomer() {
    	Review review = instance.getReviews().get(0);
    	
    	boolean condition = instance.getReviewsByCustomer(review.getCustomer())
    								.stream()
    								.allMatch(r -> r.getCustomer().getId() == review.getCustomer().getId());
    	assertTrue(condition);
    }
    
    @Test
    public void shouldGetTheCorrectReviewByBook() {
    	Review review = instance.getReviews().get(0);
    	
    	boolean condition = instance.getReviewsByBook(review.getBook())
    								.stream()
    								.allMatch(r -> r.getBook().getId() == review.getBook().getId());
    	assertTrue(condition);
    }
    
    @Test
    public void shouldChangeAReview() throws IOException {
    	Review review = instance.getReviews().get(0);
    	
    	double randomValue = Math.random() * 6;
    	
    	double lastReview = review.getRating();
    	
    	while(randomValue == lastReview || randomValue > 5)
    		randomValue = Math.random() * 6;
    	
    	instance.changeReviewValue(review.getId(), randomValue);
    	
    	Review changedReview = instance.getReviewById(review.getId()).get();
    	
    	assertTrue(randomValue == changedReview.getRating());
    	
    	assertFalse(lastReview == changedReview.getRating());
    	
    	assertTrue(changedReview.getId() == review.getId());
    }
    
    @Test
    public void shouldRemoveAReview() throws IOException {
    	Review review = instance.getReviews().get(0);
    	
    	instance.removeReviewById(review.getId());
    	
    	Optional<Review> removedReview = instance.getReviewById(review.getId());
    	
    	assertFalse(removedReview.isPresent());
    	
    	assertEquals(instance.getReviews().size(), 9999);
    	
    	instance.createReview(review.getCustomer(), review.getBook(), 2);
    }
    
    @Test(expected = IOException.class)
    public void cannotCreateAReviewWithoutAExistingCustomer() throws IOException {
    	Book book = instance.getBook(1).get();
    	
    	instance.createReview(new Customer(-2, null, null, null, null, null, null, null, null, null, null, 0, 0, 0, null, null, null, null), book, 2);
    }
    
    @Test(expected = IOException.class)
    public void cannotCreateAReviewWithoutAExistingBook() throws IOException {
    	Customer customer = instance.getCustomer(1);
    	
    	instance.createReview(customer, new Book(-2, null, null, null, null, null, null, null, 0, null, null, 0, null, null, null), 0);
    }

    /**
     * Test of getId method, of class Bookstore.
     */
    @Test
    public void shouldReturnCreatedInstanceId() {
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void shouldReturnNewBookstoreId() {
        int expResult = 24;
        Bookstore testBookstore = new Bookstore(expResult);
        int result = testBookstore.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getABookAnyBook method, of class Bookstore.
     */
    @Test
    public void shouldReturnAnyBook() {
        Random random = new Random();
        Book result = Bookstore.getABookAnyBook(random);
        assertNotNull(result);
    }

    /**
     * Test of getOrdersById method, of class Bookstore.
     */
    @Test
    public void ShouldReturnCreatedInstanceOrdersId() {
        List<Order> result = instance.getOrdersById();
        for(int i = 0; i < 10000; i++) {
            assertEquals(i, result.get(i).getId());
        }
    }

    @Test
    public void ShouldReturnNewBookstoreOrdersId() {
        long seed = 1;
        long now = System.currentTimeMillis();
        int items = 2;
        int customers = 2;
        int addresses = 2;
        int authors = 1;
        int orders = 10;
        Random rand = new Random();
        Bookstore.populate(seed, now, items, customers, addresses, authors);
        Bookstore bookStore1 = new Bookstore(13);
        Bookstore bookStore2 = new Bookstore(14);
        bookStore1.populateInstanceBookstore(orders, rand, now);
        bookStore2.populateInstanceBookstore(orders, rand, now);

        List<Order> result1 = bookStore1.getOrdersById();
        for(int i = 0; i < 10; i++) {
            assertEquals(i, result1.get(i).getId());
        }

        List<Order> result2 = bookStore2.getOrdersById();
        for(int i = 0; i < 10; i++) {
            assertEquals(i, result2.get(i).getId());
        }

        assertNotSame(result2,result1);
    }
    

    /**
     * Test of updateStock method, of class Bookstore.
     */
    @Test
    public void ShouldupdateStockBook() {
        Book book = instance.getABookAnyBook(new Random());
        Stock stockBook = instance.getStock(book.getId());
        double previosCost = stockBook.getCost();
        double newCost = 10.0;
        if (newCost == previosCost) {
            newCost = 20.0;
        }
        instance.updateStock(book.getId(), newCost);
        assertEquals(newCost, instance.getStock(book.getId()).getCost(), 0.0);
    }


    /**
     * Test of getRecommendationByItens method, of class Bookstore.
     */
    //@Test
    public void testGetRecommendationByItens() {
        int c_id = 0;
        List<Book> expResult = null;
        List<Book> result = Bookstore.getRecommendationByItens(c_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecommendationByUsers method, of class Bookstore.
     */
    //@Test
    public void testGetRecommendationByUsers() {
        int c_id = 0;
        List<Book> expResult = null;
        List<Book> result = Bookstore.getRecommendationByUsers(c_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
