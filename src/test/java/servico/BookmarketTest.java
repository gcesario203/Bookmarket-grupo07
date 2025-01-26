package servico;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import dominio.Book;
import dominio.Cart;
import dominio.Customer;
import dominio.Order;
import dominio.Review;
import dominio.Stock;
import servico.bookmarket.Bookmarket;
import servico.bookmarket.exceptions.UmbrellaException;
import util.TPCW_Util;

public class BookmarketTest {

	static Bookstore amazon;
	static Bookstore saraiva;
	
	static Bookmarket bookmarket;
	public BookmarketTest() {
		
	}
    @BeforeClass
    public static void setUpClass() {
    	startUpTestObjects();
    }
    
    private static void startUpTestObjects() {
        long seed = 178;
        long now = System.currentTimeMillis();
        Random rand = new Random(seed);
        Bookstore.populate(seed, now, 10000, 1000, 100, 1000);
        
        amazon = new Bookstore(0);
        saraiva = new Bookstore(1);
        
        amazon.populateInstanceBookstore(10000, rand, now);
        
        saraiva.populateInstanceBookstore(1000, rand, now);
        
        bookmarket = new Bookmarket();
        
        bookmarket.init(amazon, saraiva);
    }
    
    private static void cleanTestObjects() {
    	amazon = null;
    	
    	saraiva = null;
    	
    	bookmarket = null;
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldThrowUmbrellaExceptionWhenInitWithNullValue() {
        Bookmarket errorBookMarket = new Bookmarket();
        
        errorBookMarket.init(null);
    }
    
    @Test
    public void shouldCreateABookmarket() {
        Bookmarket successBookmarket = new Bookmarket();
        
        successBookmarket.init(amazon, saraiva);
        
        assertTrue(successBookmarket != null);
        assertTrue(successBookmarket.getABookAnyBook() != null);
    }
    
    @Test
    public void shouldGetACustomerByName() {
    	Customer saraivaCustomer = saraiva.getCustomer(1);
    	
    	Customer amazonCustomer = amazon.getCustomer(30);
    	
    	Customer bookmarketCustomer1 = bookmarket.getCustomer(saraivaCustomer.getUname());
    	
    	Customer bookmarketCustomer2 = bookmarket.getCustomer(amazonCustomer.getUname());
    	
    	assertTrue(saraivaCustomer.getId() == bookmarketCustomer1.getId());
    	
    	assertTrue(amazonCustomer.getId() == bookmarketCustomer2.getId());
    }
    
    @Test
    public void shouldGetCustomerFullName() {
    	Customer amazonCustomer = amazon.getCustomer(51);
    	
        String name[] = new String[3];
        name[0] = amazonCustomer.getFname();
        name[1] = amazonCustomer.getLname();
        name[2] = amazonCustomer.getUname();
        
        assertEquals(name, bookmarket.getName(amazonCustomer.getId()));
    }
    
    @Test
    public void shouldGetCustomerUserName() {
    	Customer amazonCustomer = amazon.getCustomer(72);
    	
    	assertEquals(amazonCustomer.getUname(), bookmarket.getUserName(amazonCustomer.getId()));
    }
    
    @Test
    public void shouldGetCustomerPassword() {
    	Customer amazonCustomer = amazon.getCustomer(72);
    	
    	assertEquals(amazonCustomer.getPasswd(), bookmarket.getPassword(amazonCustomer.getUname()));
    }
    
    @Test
    public void shouldGetMostRecentOrder() {
    	Customer amazonCustomer = amazon.getCustomer(80);
    	Order amazonCustomerMostRecentOrder = amazonCustomer.getMostRecentOrder();
    	
    	assertEquals(amazonCustomerMostRecentOrder.getId(), bookmarket.getMostRecentOrder(amazonCustomer.getUname()).getId());
    }
    
    @Test
    public void shouldCreateANewCustomerToAllBookstore() {
        String fname = "John";
        String lname = "Doe";
        String street1 = "123 Main St";
        String street2 = "Apt 4B";
        String city = "Springfield";
        String state = "IL";
        String zip = "62704";
        String countryName = "USA";
        String phone = "555-1234";
        String email = "john.doe@example.com";
        double discount = 10.5;
        String birthdate = "1990-01-01";
        Date data = new Date(); // Instância atual de Date
        long now = System.currentTimeMillis(); // Timestamp atual
        
        Customer createdCustomer = bookmarket.createNewCustomer(fname, lname, street1, street2, city, state, zip, countryName, phone, email, data, birthdate);
        
        Customer amazonCustomer = amazon.getCustomer(createdCustomer.getUname()).get();
        Customer saraivaCustomer = saraiva.getCustomer(createdCustomer.getUname()).get();

        // Verifica IDs
        assertEquals(createdCustomer.getId(), amazonCustomer.getId());
        assertEquals(createdCustomer.getId(), saraivaCustomer.getId());
        
        // Verifica os campos
        assertEquals(createdCustomer.getFname(), amazonCustomer.getFname());
        assertEquals(createdCustomer.getFname(), saraivaCustomer.getFname());
        
        assertEquals(createdCustomer.getLname(), amazonCustomer.getLname());
        assertEquals(createdCustomer.getLname(), saraivaCustomer.getLname());
        
        assertEquals(createdCustomer.getPhone(), amazonCustomer.getPhone());
        assertEquals(createdCustomer.getPhone(), saraivaCustomer.getPhone());
        
        assertEquals(createdCustomer.getEmail(), amazonCustomer.getEmail());
        assertEquals(createdCustomer.getEmail(), saraivaCustomer.getEmail());
        
        assertEquals(createdCustomer.getDiscount(), amazonCustomer.getDiscount(), 0.001);
        assertEquals(createdCustomer.getDiscount(), saraivaCustomer.getDiscount(), 0.001);
        
        assertEquals(createdCustomer.getBirthdate(), amazonCustomer.getBirthdate());
        assertEquals(createdCustomer.getBirthdate(), saraivaCustomer.getBirthdate());
        
        assertEquals(createdCustomer.getData(), amazonCustomer.getData());
        assertEquals(createdCustomer.getData(), saraivaCustomer.getData());
    }

    
    @Test
    public void bookstoresShouldHaveDifferentListOfReviews() {
    	List<Review> reviewsFromAmazon = amazon.getReviews();
    	
    	List<Review> reviewsFromSaraiva = saraiva.getReviews();
    	
    	List<Review> allReviews = bookmarket.getReviews();
    	
    	assertEquals(reviewsFromAmazon.size() + reviewsFromSaraiva.size(), allReviews.size());
    	
    	assertFalse(reviewsFromAmazon.equals(reviewsFromSaraiva));
    }
    
    @Test
    public void shouldGetAReviewThatExistsInABookstore() {
    	List<Review> amazonReviews = bookmarket.getReviewsByBookstore(amazon.getId());
    	
    	List<Review> saraivaReviews = bookmarket.getReviewsByBookstore(saraiva.getId());
    	
    	assertTrue(TPCW_Util.areReviewFromAUniqueBookstore(saraivaReviews, saraiva.getId()));
    	
    	assertTrue(TPCW_Util.areReviewFromAUniqueBookstore(amazonReviews, amazon.getId()));
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAReviewWithInvalidValues() {
    	bookmarket.createReview(-1, null, null, -5);
    }
    
    @Test
    public void shouldCreateAReviewForABookstore() {
    	Customer amazonCustomer = amazon.getCustomer(1);
    	
    	Book amazonBook = amazon.getABookAnyBook(new Random(0));
    	
    	Review amazonReview = bookmarket.createReview(amazon.getId(), amazonCustomer, amazonBook, Math.random() * 5);
    	
    	assertTrue(bookmarket.getReviewById(amazon.getId(), amazonReview.getId()).get().getId() == amazonReview.getId());
    	
    	assertFalse(bookmarket.getReviewById(saraiva.getId(), amazonReview.getId()).isPresent());
    }
    
    @Test
    public void shouldChangeAReviewFromABookstore() {
    	Review saraivaReview = saraiva.getReviews().get(0);
    	
    	double reviewLastValue = saraivaReview.getRating();
    	
    	double newValue = Math.random() * 6;
    	
    	while(reviewLastValue == newValue || newValue > 5)
    		newValue = Math.random() * 6;
    	
    	boolean result = bookmarket.changeReviewValue(saraiva.getId(), saraivaReview.getId(), newValue);
    	
    	Review changedSaraivaReview = bookmarket.getReviewById(saraiva.getId(), saraivaReview.getId()).get();
    	
    	assertTrue(result);
    	
    	assertEquals(changedSaraivaReview.getId(), saraivaReview.getId());
    	
    	assertTrue(changedSaraivaReview.getRating() == newValue);
    	
    	assertFalse(changedSaraivaReview.getRating() == reviewLastValue);
    }
    
    @Test
    public void shouldRemoveAReviewFromABookstore() {
    	List<Review> saraivaReviews = bookmarket.getReviewsByBookstore(saraiva.getId());
    	
    	Review saraivaReview = saraivaReviews.get(0);
    	
    	int saraivaLastSize = saraivaReviews.size();
    	
    	boolean result = bookmarket.removeReviewById(saraiva.getId(), saraivaReview.getId());
    	
    	assertTrue(result);
    	
    	assertTrue(saraivaLastSize == (bookmarket.getReviewsByBookstore(saraiva.getId()).size() + 1));
    	
    	assertFalse(bookmarket.removeReviewById(saraiva.getId(), saraivaReview.getId()));
    	
    	assertFalse(bookmarket.getReviewById(saraiva.getId(), saraivaReview.getId()).isPresent());
    }
    
    @Test
    public void shouldAllReviewTobeFromTheSameBook() {
    	Book amazonBook = amazon.getABookAnyBook(new Random(0));
    	
    	Book saraivaBook = saraiva.getABookAnyBook(new Random(2));
    	 
    	List<Review> amazonReviews = bookmarket.getReviewsByBook(amazon.getId(), amazonBook);
    	
    	List<Review> saraivaReviews = bookmarket.getReviewsByBook(saraiva.getId(), saraivaBook);
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(saraivaReviews, saraivaBook));
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(amazonReviews, amazonBook));
    }
    
    @Test
    public void shouldAllReviewTobeFromTheSameCustomer() {
    	Customer amazonCustomer = amazon.getCustomer(1);
    	
    	Customer saraivaCustomer = saraiva.getCustomer(2);
    	 
    	List<Review> amazonReviews = bookmarket.getReviewsByCustomer(amazon.getId(), amazonCustomer);
    	
    	List<Review> saraivaReviews = bookmarket.getReviewsByCustomer(saraiva.getId(), saraivaCustomer);
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(saraivaReviews, saraivaCustomer));
    	
    	assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(amazonReviews, amazonCustomer));
    }
    
    @Test
    public void shouldRefreshUserSessionByTwoHours() {
    	long now = System.currentTimeMillis();
    	
    	Date dateNow = new Date(now);
    	
    	Customer amazonCustomer = amazon.getCustomer(1);
    	
    	bookmarket.refreshSession(amazonCustomer.getId());
    	
    	assertTrue(amazonCustomer.getExpiration().getHours() == dateNow.getHours() + 2);
    	
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldThrownAExceptionWhenRefreshingSessionOfAInvalidUser() {
    	bookmarket.refreshSession(-1);
    }
    
    @Test
    public void shouldGetABook() {
    	Book saraivaBook = saraiva.getABookAnyBook(new Random(0));
    	
    	assertEquals(saraivaBook.getId(), bookmarket.getBook(saraivaBook.getId()).getId());
    }
    
    @Test
    public void shouldGetBookBySubject() {
    	String subject = "HISTORY";
    	
    	List<Book> saraivaBooks = saraiva.getBooksBySubject(subject);
    	
    	List<Book> amazonBooks = amazon.getBooksBySubject(subject);
    	
    	List<Book> bookmarketBooks = bookmarket.doSubjectSearch(subject);
    	
    	assertTrue(bookmarketBooks.containsAll(saraivaBooks));
    	
    	assertTrue(bookmarketBooks.containsAll(amazonBooks));
    }
    
    @Test
    public void shouldGetBookByAuthor() {
    	String author = saraiva.getABookAnyBook(new Random(1)).getAuthor().getLname();
    	
    	List<Book> saraivaBooks = saraiva.getBooksByAuthor(author);
    	
    	List<Book> amazonBooks = amazon.getBooksByAuthor(author);
    	
    	List<Book> bookmarketBooks = bookmarket.doAuthorSearch(author);
    	
    	assertTrue(bookmarketBooks.containsAll(saraivaBooks));
    	
    	assertTrue(bookmarketBooks.containsAll(amazonBooks));
    }
    
    @Test
    public void shouldGetBookByTitle() {
    	String title = saraiva.getABookAnyBook(new Random()).getTitle();
    	
    	List<Book> saraivaBooks = saraiva.getBooksByTitle(title);
    	
    	List<Book> amazonBooks = amazon.getBooksByTitle(title);
    	
    	List<Book> bookmarketBooks = bookmarket.doTitleSearch(title);
    	
    	assertTrue(bookmarketBooks.containsAll(saraivaBooks));
    	
    	assertTrue(bookmarketBooks.containsAll(amazonBooks));
    }
    
    @Test
    public void shouldGetNewestPublishedBooks() {
    	String subject = "HISTORY";
    	
    	List<Book> newestAmazonBooks = amazon.getNewBooks(subject);
    	
    	List<Book> newestSaraivaBooks = amazon.getNewBooks(subject);
    	
    	List<Book> bookmarketBooks = bookmarket.getNewProducts(subject);
    	
    	assertTrue(bookmarketBooks.containsAll(newestSaraivaBooks));
    	
    	assertTrue(bookmarketBooks.containsAll(newestAmazonBooks));
    }
    
    @Test
    public void shouldGetAllBookCost() {
    	Book saraivaBook = saraiva.getABookAnyBook(new Random(3));
    	
    	Book amazonBook = amazon.getABookAnyBook(new Random(1));
    	
    	double saraivaBookCost = saraiva.getStock(saraivaBook.getId())
    										  .getCost();
    	
    	double amazonBookCost = amazon.getStock(amazonBook.getId())
    								  .getCost();
    	
    	List<Double> amazonBookCosts = bookmarket.getCosts(amazonBook);
    	
    	List<Double> saraivaBookCosts = bookmarket.getCosts(saraivaBook);
    	
    	assertTrue(amazonBookCosts.contains(amazonBookCost));
    	
    	assertTrue(saraivaBookCosts.contains(saraivaBookCost));
    }
    
    @Test
    public void shouldGetABookStock() {
    	Book amazonBook = amazon.getABookAnyBook(new Random(1));
    	
    	Stock amazonBookStock = amazon.getStock(amazonBook.getId());
    	
    	assertTrue(amazonBookStock == bookmarket.getStock(amazon.getId(), amazonBook.getId()));
    }
    
    @Test
    public void shouldGetABookStocks() {
    	Book amazonBook = amazon.getABookAnyBook(new Random(1));
    	
    	Stock amazonBookStock = amazon.getStock(amazonBook.getId());
    	
    	assertTrue(bookmarket.getStocks(amazonBook.getId()).contains(amazonBookStock));
    }
    
    @Test
    public void shouldGetRelatedBooks() {
    	Book amazonBook = amazon.getABookAnyBook(new Random(1));
    	
    	List<Book> relatedBooksByBookmarket = bookmarket.getRelated(amazonBook.getId());
    	
    	assertTrue(relatedBooksByBookmarket.containsAll(amazonBook.getAllRelated()));
    }
    
    @Test
    public void shouldUpdateABook() {
    	Book amazonBook = amazon.getABookAnyBook(new Random(1));
    	
    	double oldCost = amazon.getStock(amazonBook.getId()).getCost();
    	
    	bookmarket.adminUpdate(amazonBook.getId(), 23.50, null, null);
    	
    	Book updatedBook = bookmarket.getBook(amazonBook.getId());
    	
    	double newCost = amazon.getStock(updatedBook.getId()).getCost();
    	
    	assertEquals(amazonBook.getId(), updatedBook.getId());
    	
    	assertTrue(newCost == 23.50);
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionWhenUpdateAInvalidBook() {
    	bookmarket.adminUpdate(-1, 23.50, null, null);
    }
    
    @Test
    public void shouldCreateAEmptyCart() {
    	int newCartId = bookmarket.createEmptyCart(amazon.getId());
    	
    	assertTrue(newCartId ==  amazon.getCart(newCartId).getId());
    	
    	assertTrue(newCartId == bookmarket.getCart(amazon.getId(), newCartId).getId());
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAEmptyCartWithAInvalidStoreId() {
    	bookmarket.createEmptyCart(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldGetACartFromABookstore() {
    	int newCartId = bookmarket.createEmptyCart(amazon.getId());
    	
    	Cart amazonCart = bookmarket.getCart(amazon.getId(), newCartId);
    	
    	assertEquals(amazonCart,amazon.getCart(newCartId));
    	
    	Cart saraivaCart = bookmarket.getCart(saraiva.getId(), newCartId);
    }
    
    @Test
    public void shouldCreateACartWithBooks() {
    	int newCartId = bookmarket.createEmptyCart(amazon.getId());
    	
    	Book book = bookmarket.getABookAnyBook();
    	
    	List<Integer> bookIds = new ArrayList<Integer>(book.getId());
    	
    	List<Integer> bookQtdy = new ArrayList<Integer>(2);
    	
        Cart cart = bookmarket.doCart(amazon.getId(), newCartId, book.getId(), bookIds, bookQtdy);
        
        assertEquals(newCartId, cart.getId());
        
        assertTrue(cart.getLines().stream().anyMatch(line -> line.getBook().getId() == book.getId()));
        
        int newEmptyCart = bookmarket.createEmptyCart(saraiva.getId());
        
        Cart cartWithRandomBook = bookmarket.doCart(saraiva.getId(), newEmptyCart, null, null, null);
        
        assertEquals(newEmptyCart, cartWithRandomBook.getId());
        
        assertTrue(cartWithRandomBook.getLines().size() > 0);
        
        assertTrue(cartWithRandomBook.getLines().size() == 1);
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotCreateACartWithInvalidCartId() {
    	bookmarket.doCart(-1, 0, null, null, null);
    }
    
    @Test
    public void shouldCreateAOrder() {
    	int newCartId = bookmarket.createEmptyCart(amazon.getId());
    	
    	Book book = bookmarket.getABookAnyBook();
    	
    	Customer customer = amazon.getCustomer(5);
    	
        Cart cart = bookmarket.doCart(amazon.getId(), newCartId, book.getId(), null, null);
        
        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = customer.getFname() + customer.getLname();
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";
        
        Order order = bookmarket.doBuyConfirm(amazon.getId(),newCartId,customer.getId(), ccType, ccNumber, ccName, ccExpiry, shipping);
        
        Order ordemFromAmazon = amazon.getOrdersById().stream().filter(o -> o.getId() == order.getId()).findFirst().get();
        
        assertEquals(order.getId(), ordemFromAmazon.getId());
        assertEquals(order.getCustomer(), ordemFromAmazon.getCustomer());
        assertEquals(order.getCC(), ordemFromAmazon.getCC());
        assertEquals(order.getBillingAddress(), ordemFromAmazon.getBillingAddress());
    }
    
    @Test
    public void shouldCreateAOrderSecondMethod() {
    	int newCartId = bookmarket.createEmptyCart(amazon.getId());
    	
    	Book book = bookmarket.getABookAnyBook();
    	
    	Customer customer = amazon.getCustomer(5);
    	
        Cart cart = bookmarket.doCart(amazon.getId(), newCartId, book.getId(), null, null);
        
        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = customer.getFname() + customer.getLname();
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";
        
        // Endereço adicional
        String street1 = "123 Main St";
        String street2 = "Apt 4B";
        String city = "Springfield";
        String state = "IL";
        String zip = "62704";
        String country = "USA";
        
        Order order = bookmarket.doBuyConfirm(amazon.getId(), newCartId, customer.getId(), ccType, ccNumber, ccName, ccExpiry, shipping, street1, street2, city, state, zip, country);
        
        Order ordemFromAmazon = amazon.getOrdersById().stream().filter(o -> o.getId() == order.getId()).findFirst().get();
        
        assertEquals(order.getId(), ordemFromAmazon.getId());
        assertEquals(order.getCustomer(), ordemFromAmazon.getCustomer());
        assertEquals(order.getCC(), ordemFromAmazon.getCC());
        assertEquals(order.getBillingAddress(), ordemFromAmazon.getBillingAddress());
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAOrderWithInvalidParams()
    {
        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = "Ola mundo";
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";
        
        // Endereço adicional
        String street1 = "123 Main St";
        String street2 = "Apt 4B";
        String city = "Springfield";
        String state = "IL";
        String zip = "62704";
        String country = "USA";
        
        Order order = bookmarket.doBuyConfirm(amazon.getId(), -1, -32, ccType, ccNumber, ccName, ccExpiry, shipping, street1, street2, city, state, zip, country);
    }
    
    @Test(expected = RuntimeException.class)
    public void shouldNotCreatAOrderWithInvalidParamsSecondMethod() {
        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = "Teste";
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";
        
        Order order = bookmarket.doBuyConfirm(amazon.getId(),-4,-2, ccType, ccNumber, ccName, ccExpiry, shipping);
       
    }
    
    @Test
    public void shouldPopulateByBookmarket() {
    	cleanTestObjects();
    	
    	Bookstore livraria = new Bookstore(3);
    	
    	Bookstore sebo = new Bookstore(4);
    	
    	Bookmarket bookmarketTest = new Bookmarket();
    	
    	bookmarketTest.init(livraria, sebo);
    	
    	bookmarketTest.populate(1000, 500, 100, 1000, 200);
    	
    	assertEquals(livraria.isPopulated(), true);
    	
    	assertEquals(sebo.isPopulated(), true);
    	
    	startUpTestObjects();
    }
    
    @Test
    public void shouldNotPopulateWhenInvalidParams(){
    	try 
    	{
        	cleanTestObjects();
        	
        	Bookstore livraria = new Bookstore(3);
        	
        	Bookstore sebo = new Bookstore(4);
        	
        	Bookmarket bookmarketTest = new Bookmarket();
        	
        	bookmarketTest.init(livraria, sebo);
        	
        	bookmarketTest.populate(-1,-2,-3,-4,-5);

        	
    	}catch(Exception e) {
    		assertTrue(e.getClass() == RuntimeException.class);
    		startUpTestObjects();
    	}

    }
    
}