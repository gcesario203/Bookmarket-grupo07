package servico;

import dominio.Address;
import dominio.Author;
import dominio.Book;
import dominio.Cart;
import dominio.Customer;
import dominio.Order;
import dominio.Review;
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
     * Test of isPopulated method, of class Bookstore.
     */
    @Test
    public void testIsPopulated() {
        System.out.println("isPopulated");
        boolean expResult = true;
        boolean result = instance.isPopulated();
        assertEquals(expResult, result);
    }

    /**
     * Test of alwaysGetAddress method, of class Bookstore.
     */
    @Test
    public void testAlwaysGetAddress() {
        System.out.println("alwaysGetAddress");
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
        System.out.println("getCustomer");
        int cId = 0;
        Customer result = instance.getCustomer(cId);
        assertEquals(cId, result.getId());
    }

    /**
     * Test of getCustomer method, of class Bookstore.
     */
    @Test
    public void testGetCustomer_String() {
        System.out.println("getCustomer");
        String username = instance.getCustomer(10).getUname();
        Customer result = instance.getCustomer(username).get();
        assertEquals(username, result.getUname());

    }

    /**
     * Test of createCustomer method, of class Bookstore.
     */
    @Test
    public void testCreateCustomer() {
        System.out.println("createCustomer");
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

        Customer result = instance.createCustomer(fname, lname, street1, street2, city, state, zip, countryName, phone, email, discount, birthdate, data, now);
        int id = result.getId();
        String uname = result.getUname();
        Date since = result.getSince();
        Date lastVisit = result.getLastVisit();
        Date login = result.getLogin();
        Date expiration = result.getExpiration();
        Address address = result.getAddress();
        Customer expResult = new Customer(id, uname, uname.toLowerCase(), fname,
                lname, phone, email, since, lastVisit, login, expiration,
                discount, 0, 0, birthdate, data, address);
        assertEquals(expResult, result);

    }

    /**
     * Test of refreshCustomerSession method, of class Bookstore.
     */
    @Test
    public void testRefreshCustomerSession() {
        System.out.println("refreshCustomerSession");
        int cId = 0;
        long now = 0L;
        instance.refreshCustomerSession(cId, now);
    }

    /**
     * Test of getBook method, of class Bookstore.
     */
    @Test
    public void testGetBook() {
        System.out.println("getBook");
        int bId = 0;
        Book result = instance.getBook(bId).get();
        assertEquals(bId, result.getId());

    }

    /**
     * Test of getBooksBySubject method, of class Bookstore.
     */
    @Test
    public void testGetBooksBySubject() {
        System.out.println("getBooksBySubject");
        String subject = "ARTS";
        List<Book> result = instance.getBooksBySubject(subject);
        assertEquals(result.size(), result.stream().filter(book -> book.getSubject().equals(subject)).count());

    }

    /**
     * Test of getBooksByTitle method, of class Bookstore.
     */
    @Test
    public void testGetBooksByTitle() {
        System.out.println("getBooksByTitle");
        String title = instance.getBook(0).get().getTitle().substring(0, 4);
        List<Book> result = instance.getBooksByTitle(title);
        assertEquals(result.size(), result.stream().filter(book -> book.getTitle().startsWith(title)).count());
    }

    /**
     * Test of getBooksByAuthor method, of class Bookstore.
     */
    @Test
    public void testGetBooksByAuthor() {
        System.out.println("getBooksByAuthor");
        Author author = instance.getBook(0).get().getAuthor();
        List<Book> result = instance.getBooksByAuthor(author.getLname());
        assertEquals(result.size(), result.stream().filter(book -> book.getAuthor().getLname().equals(author.getLname())).count());

    }

    /**
     * Test of getNewBooks method, of class Bookstore.
     */
    @Test
    public void testGetNewBooks() {
        System.out.println("getNewBooks");
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
        System.out.println("updateBook");
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
    // @Test
    public void testGetCart() {
        System.out.println("getCart");
        int id = 0;

        Cart expResult = null;
        Cart result = instance.getCart(id);
        assertEquals(expResult, result);

    }

    /**
     * Test of createCart method, of class Bookstore.
     */
    private void testCreateCart() {
        System.out.println("createCart");
        long now = 0L;

        Cart expResult = null;
        Cart result = instance.createCart(now);
        assertEquals(expResult, result);

    }

    /**
     * Test of cartUpdate method, of class Bookstore.
     */
    // @Test
    public void testCartUpdate() {
        System.out.println("cartUpdate");
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
    // @Test
    public void testConfirmBuy() {
        System.out.println("confirmBuy");
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
    	
    	double lastReview = review.getValue();
    	
    	if(randomValue == lastReview)
    		randomValue = Math.random() * 6;
    	
    	instance.changeReviewValue(review.getId(), randomValue);
    	
    	Review changedReview = instance.getReviewById(review.getId()).get();
    	
    	assertTrue(randomValue == changedReview.getValue());
    	
    	assertFalse(lastReview == changedReview.getValue());
    	
    	assertTrue(changedReview.getId() == review.getId());
    }
    
    @Test
    public void shouldRemoveAReview() throws IOException {
    	Review review = instance.getReviews().get(0);
    	
    	instance.removeReviewById(review.getId());
    	
    	Optional<Review> removedReview = instance.getReviewById(review.getId());
    	
    	assertFalse(removedReview.isPresent());
    	
    	assertEquals(instance.getReviews().size(), 9999);
    	
    	instance.createReview(review.getCustomer(), review.getBook(), review.getValue());
    }
    
    @Test(expected = IOException.class)
    public void cannotCreateAReviewWithoutAExistingCustomer() throws IOException {
    	Book book = instance.getBook(1).get();
    	
    	instance.createReview(new Customer(-2, null, null, null, null, null, null, null, null, null, null, 0, 0, 0, null, null, null), book, 2);
    }
    
    @Test(expected = IOException.class)
    public void cannotCreateAReviewWithoutAExistingBook() throws IOException {
    	Customer customer = instance.getCustomer(1);
    	
    	instance.createReview(customer, new Book(-2, null, null, null, null, null, null, null, 0, null, null, 0, null, null, null), 0);
    }

    







    /**
     * Test of getId method, of class Bookstore.
     */
    //@Test
    public void testGetId() {
        System.out.println("getId");
        Bookstore instance = null;
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecommendationByItens method, of class Bookstore.
     */
    //@Test
    public void testGetRecommendationByItens() {
        System.out.println("getRecommendationByItens");
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
        System.out.println("getRecommendationByUsers");
        int c_id = 0;
        List<Book> expResult = null;
        List<Book> result = Bookstore.getRecommendationByUsers(c_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getABookAnyBook method, of class Bookstore.
     */
    //@Test
    public void testGetABookAnyBook() {
        System.out.println("getABookAnyBook");
        Random random = null;
        Book expResult = null;
        Book result = Bookstore.getABookAnyBook(random);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrdersById method, of class Bookstore.
     */
    //@Test
    public void testGetOrdersById() {
        System.out.println("getOrdersById");
        Bookstore instance = null;
        List<Order> expResult = null;
        List<Order> result = instance.getOrdersById();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    

    /**
     * Test of updateStock method, of class Bookstore.
     */
    //@Test
    public void testUpdateStock() {
        System.out.println("updateStock");
        int bId = 0;
        double cost = 0.0;
        Bookstore instance = null;
        instance.updateStock(bId, cost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    

    /**
     * Test of populateInstanceBookstore method, of class Bookstore.
     */
    //@Test
    public void testPopulateInstanceBookstore() {
        System.out.println("populateInstanceBookstore");
        int number = 0;
        Random rand = null;
        long now = 0L;
        Bookstore instance = null;
        instance.populateInstanceBookstore(number, rand, now);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
