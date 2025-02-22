package servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import dominio.customer.enums.Type;

import dominio.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import servico.bookstore.Bookstore;
import servico.bookmarket.Bookmarket;
import util.TPCW_Util;

import static org.junit.Assert.*;

public class BookmarketTest {

    static Bookstore amazon;
    static Bookstore saraiva;

    static Bookmarket bookmarket;

    public BookmarketTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    	cleanTestObjects();
        startUpTestObjects();
    }
    
    @AfterClass
    public static void flushGlobalDatabase() {
    	cleanTestObjects();
    }

    private static void startUpTestObjects() {
        long seed = 178;
        long now = System.currentTimeMillis();
        Random rand = new Random(seed);
        Bookstore.populate(seed, now, 100, 1000, 100, 1000);

        amazon = new Bookstore(0);
        saraiva = new Bookstore(1);
        
        /// PERCECORRE A SEED
        amazon.populateInstanceBookstore(10000, rand, now);
        
        /// CONTINUA DA MESMA SEED
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
        Customer saraivaCustomer = Bookstore.getCustomer(1).get();

        Customer amazonCustomer = Bookstore.getCustomer(30).get();

        Customer bookmarketCustomer1 = Bookmarket.getCustomer(saraivaCustomer.getUname());

        Customer bookmarketCustomer2 = Bookmarket.getCustomer(amazonCustomer.getUname());

        assertTrue(saraivaCustomer.getId() == bookmarketCustomer1.getId());

        assertTrue(amazonCustomer.getId() == bookmarketCustomer2.getId());
    }

    @Test
    public void shouldGetCustomerFullName() {
        Customer amazonCustomer = Bookstore.getCustomer(51).get();

        String name[] = new String[3];
        name[0] = amazonCustomer.getFname();
        name[1] = amazonCustomer.getLname();
        name[2] = amazonCustomer.getUname();

        assertEquals(name, Bookmarket.getName(amazonCustomer.getId()));
    }

    @Test
    public void shouldGetCustomerUserName() {
        Customer amazonCustomer = Bookstore.getCustomer(72).get();

        assertEquals(amazonCustomer.getUname(), Bookmarket.getUserName(amazonCustomer.getId()));
    }

    @Test
    public void shouldGetCustomerPassword() {
        Customer amazonCustomer = Bookstore.getCustomer(72).get();

        assertEquals(amazonCustomer.getPasswd(), Bookmarket.getPassword(amazonCustomer.getUname()));
    }

    @Test
    public void shouldGetMostRecentOrder() {
    	cleanTestObjects();
    	startUpTestObjects();
        Customer amazonCustomer = Bookstore.getCustomer(80).get();
        Order amazonCustomerMostRecentOrder = amazonCustomer.getMostRecentOrder();

        assertEquals(amazonCustomerMostRecentOrder.getId(),
                Bookmarket.getMostRecentOrder(amazonCustomer.getUname()).getId());
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
        String birthdate = "1990-01-01";
        Date data = new Date(); // Instância atual de Date

        Customer createdCustomer = bookmarket.createNewCustomer(fname, lname, street1, street2, city, state, zip,
                countryName, phone, email, data, birthdate);

        Customer amazonCustomer = Bookstore.getCustomer(createdCustomer.getUname()).get();
        Customer saraivaCustomer = Bookstore.getCustomer(createdCustomer.getUname()).get();

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
        
        assertEquals(createdCustomer.getType(), saraivaCustomer.getType());
        assertEquals(createdCustomer.getType(), amazonCustomer.getType());
    }
    
    @Test
    public void shouldCreateANewSubscriberToAllBookstore() {
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

        Customer createdCustomer = bookmarket.createNewSubscriber(fname, lname, street1, street2, city, state, zip,
                countryName, phone, email, data, birthdate);

        Customer amazonCustomer = Bookstore.getCustomer(createdCustomer.getUname()).get();
        Customer saraivaCustomer = Bookstore.getCustomer(createdCustomer.getUname()).get();

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
        
        assertEquals(createdCustomer.getType(), saraivaCustomer.getType());
        assertEquals(createdCustomer.getType(), amazonCustomer.getType());
    }

    @Test
    public void bookstoresShouldHaveDifferentListOfReviews() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
        List<Review> reviewsFromAmazon = amazon.getReviews();

        List<Review> reviewsFromSaraiva = saraiva.getReviews();

        List<Review> allReviews = bookmarket.getReviews();

        assertEquals(reviewsFromAmazon.size() + reviewsFromSaraiva.size(), allReviews.size());

        assertFalse(reviewsFromAmazon.equals(reviewsFromSaraiva));
    }

    @Test
    public void shouldGetAReviewThatExistsInABookstore() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
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
    	cleanTestObjects();
    	startUpTestObjects();
        Customer amazonCustomer = Bookstore.getCustomer(1).get();

        Book amazonBook = Bookstore.getABookAnyBook(new Random(0));

        Review amazonReview = bookmarket.createReview(amazon.getId(), amazonCustomer, amazonBook, Math.random() * 5);

        assertTrue(
        		bookmarket.getReviewById(amazon.getId(), amazonReview.getId()).get().getId() == amazonReview.getId());

        assertFalse(bookmarket.getReviewById(saraiva.getId(), amazonReview.getId()).isPresent());
    }

    @Test
    public void shouldChangeAReviewFromABookstore() {
    	cleanTestObjects();
    	startUpTestObjects();
        Review saraivaReview = saraiva.getReviews().get(0);

        double reviewLastValue = saraivaReview.getRating();

        double newValue = Math.random() * 6;

        while (reviewLastValue == newValue || newValue > 5)
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
    	cleanTestObjects();
    	startUpTestObjects();
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
    	cleanTestObjects();
    	startUpTestObjects();
        Book amazonBook = Bookstore.getABookAnyBook(new Random(0));

        Book saraivaBook = Bookstore.getABookAnyBook(new Random(2));

        List<Review> amazonReviews = bookmarket.getReviewsByBook(amazon.getId(), amazonBook);

        List<Review> saraivaReviews = bookmarket.getReviewsByBook(saraiva.getId(), saraivaBook);

        assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(saraivaReviews, saraivaBook));

        assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(amazonReviews, amazonBook));
    }

    @Test
    public void shouldAllReviewTobeFromTheSameCustomer() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
        Customer amazonCustomer = Bookstore.getCustomer(1).get();

        Customer saraivaCustomer = Bookstore.getCustomer(2).get();

        List<Review> amazonReviews = bookmarket.getReviewsByCustomer(amazon.getId(), amazonCustomer);

        List<Review> saraivaReviews = bookmarket.getReviewsByCustomer(saraiva.getId(), saraivaCustomer);

        assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(saraivaReviews, saraivaCustomer));

        assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(amazonReviews, amazonCustomer));
    }

//     @Test
    public void shouldRefreshUserSessionByTwoHours() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
        long now = System.currentTimeMillis();

        Date dateNow = new Date(now);

        Customer amazonCustomer = amazon.getCustomer(1).get();

        bookmarket.refreshSession(amazonCustomer.getId());

        assertTrue(amazonCustomer.getExpiration().getHours() == dateNow.getHours() + 2);

    }

    @Test(expected = RuntimeException.class)
    public void shouldThrownAExceptionWhenRefreshingSessionOfAInvalidUser() {
        bookmarket.refreshSession(-1);
    }

    @Test
    public void shouldGetABook() {
        Book saraivaBook = Bookstore.getABookAnyBook(new Random(0));

        assertEquals(saraivaBook.getId(), Bookmarket.getBook(saraivaBook.getId()).getId());
    }

    @Test
    public void shouldGetBookBySubject() {
        String subject = "HISTORY";

        List<Book> saraivaBooks = saraiva.getBooksBySubject(subject);

        List<Book> amazonBooks = amazon.getBooksBySubject(subject);

        List<Book> bookmarketBooks = Bookmarket.doSubjectSearch(subject);

        assertTrue(bookmarketBooks.containsAll(saraivaBooks));

        assertTrue(bookmarketBooks.containsAll(amazonBooks));
    }

    @Test
    public void shouldGetBookByAuthor() {
        String author = Bookstore.getABookAnyBook(new Random(1)).getAuthor().getLname();

        List<Book> saraivaBooks = saraiva.getBooksByAuthor(author);

        List<Book> amazonBooks = amazon.getBooksByAuthor(author);

        List<Book> bookmarketBooks = Bookmarket.doAuthorSearch(author);

        assertTrue(bookmarketBooks.containsAll(saraivaBooks));

        assertTrue(bookmarketBooks.containsAll(amazonBooks));
    }

    @Test
    public void shouldGetBookByTitle() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
        String title = Bookstore.getABookAnyBook(new Random()).getTitle();

        List<Book> saraivaBooks = saraiva.getBooksByTitle(title);

        List<Book> amazonBooks = amazon.getBooksByTitle(title);

        List<Book> bookmarketBooks = Bookmarket.doTitleSearch(title);

        assertTrue(bookmarketBooks.containsAll(saraivaBooks));

        assertTrue(bookmarketBooks.containsAll(amazonBooks));
    }

    @Test
    public void shouldGetNewestPublishedBooks() {
        String subject = "HISTORY";

        List<Book> newestAmazonBooks = amazon.getNewBooks(subject);

        List<Book> newestSaraivaBooks = amazon.getNewBooks(subject);

        List<Book> bookmarketBooks = Bookmarket.getNewProducts(subject);

        assertTrue(bookmarketBooks.containsAll(newestSaraivaBooks));

        assertTrue(bookmarketBooks.containsAll(newestAmazonBooks));
    }

    @Test
    public void shouldGetAllBookCost() {
    	cleanTestObjects();
    	setUpClass();
        Book saraivaBook = Bookstore.getABookAnyBook(new Random(3));

        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));
        
        Stock saraivaStock = saraiva.getStock(saraivaBook.getId());
        double saraivaBookCost = saraivaStock == null ? 0 :saraivaStock.getCost();
        
        Stock amazonStock = amazon.getStock(amazonBook.getId());
        double amazonBookCost = amazonStock == null ? 0 : amazonStock.getCost();

        List<Double> amazonBookCosts = bookmarket.getCosts(amazonBook);

        List<Double> saraivaBookCosts = bookmarket.getCosts(saraivaBook);

        assertTrue(amazonBookCosts.contains(amazonBookCost));

        assertTrue(saraivaBookCosts.contains(saraivaBookCost));
    }

    @Test
    public void shouldGetABookStock() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        Stock amazonBookStock = amazon.getStock(amazonBook.getId());

        assertTrue(amazonBookStock == bookmarket.getStock(amazon.getId(), amazonBook.getId()));
    }

    @Test
    public void shouldGetABookStocks() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        Stock amazonBookStock = amazon.getStock(amazonBook.getId());

        assertTrue(bookmarket.getStocks(amazonBook.getId()).contains(amazonBookStock));
    }

    @Test
    public void shouldGetRelatedBooks() {
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        List<Book> relatedBooksByBookmarket = bookmarket.getRelated(amazonBook.getId());

        assertTrue(relatedBooksByBookmarket.containsAll(amazonBook.getAllRelated()));
    }

    @Test
    public void shouldUpdateABook() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        double oldCost = amazon.getStock(amazonBook.getId()).getCost();

        double newCost = oldCost + 10;

        bookmarket.adminUpdate(amazonBook.getId(), newCost, null, null);

        Book updatedBook = Bookmarket.getBook(amazonBook.getId());

        assertEquals(amazonBook.getId(), updatedBook.getId());

        assertTrue(newCost == amazon.getStock(updatedBook.getId()).getCost());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionWhenUpdateAInvalidBook() {
        bookmarket.adminUpdate(-1, 23.50, null, null);
    }

    @Test
    public void shouldCreateAEmptyCart() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
    	Customer customer = amazon.getCustomer(2).get();
        int newCartId = bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        assertTrue(newCartId == amazon.getCart(newCartId).get().getId());

        assertTrue(newCartId == bookmarket.getCart(amazon.getId(), newCartId).get().getId());
    }
    
    @Test
    public void shouldGetACartByCustomer() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	Customer customer = amazon.getCustomer(2).get();
    	
    	Optional<Cart> cart = bookmarket.getCartByCustomer(amazon.getId(), customer.getId());
    	
    	assertTrue(cart.isPresent());
    }
    
    @Test
    public void shouldNotGetANonExistingCartByCustomer() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
    	assertTrue(!bookmarket.getCartByCustomer(amazon.getId(), -1).isPresent());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAEmptyCartWithAInvalidStoreId() {
        bookmarket.createEmptyCart(-1, -2);
    }

    @Test
    public void shouldGetACartFromABookstore() {
    	Customer customer = amazon.getCustomer(2).get();
        int newCartId = bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        Cart amazonCart = bookmarket.getCart(amazon.getId(), newCartId).get();

        assertEquals(amazonCart.getId(), amazon.getCart(newCartId).get().getId());
    }

    @Test
    public void shouldCreateACartWithBooks() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
    	Customer customer = amazon.getCustomer(2).get();
        int newCartId = bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        Book book = Bookmarket.getABookAnyBook();

        List<Integer> bookIds = new ArrayList<Integer>(book.getId());

        List<Integer> bookQtdy = new ArrayList<Integer>(2);

        Cart cart = bookmarket.doCart(amazon.getId(), newCartId, book.getId(), bookIds, bookQtdy).get();

        assertEquals(newCartId, cart.getId());

        assertTrue(cart.getLines().stream().anyMatch(line -> line.getBook().getId() == book.getId()));

        int newEmptyCart = bookmarket.createEmptyCart(saraiva.getId(), saraiva.getCustomer(3).get().getId());

        Cart cartWithRandomBook = bookmarket.doCart(saraiva.getId(), newEmptyCart, null, null, null).get();

        assertEquals(newEmptyCart, cartWithRandomBook.getId());

        assertTrue(cart.getLines().size() > 0);

        assertTrue(cart.getLines().stream().anyMatch(x -> x.getBook().getId() == book.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateACartWithInvalidCartId() {
        bookmarket.doCart(-1, 0, null, null, null);
    }

    @Test
    public void shouldCreateAOrder() {
    	cleanTestObjects();
    	
    	startUpTestObjects();
    	
    	Customer amazonCustomer = amazon.getCustomer(5).get();
        int newCartId = bookmarket.createEmptyCart(amazon.getId(), amazonCustomer.getId());

        Book book = Bookmarket.getABookAnyBook();

        Customer customer = Bookstore.getCustomer(5).get();

        Cart cart = bookmarket.doCart(amazon.getId(), newCartId, book.getId(), null, null).get();

        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = customer.getFname() + customer.getLname();
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";

        Order order = bookmarket.doBuyConfirm(amazon.getId(), newCartId, customer.getId(), ccType, ccNumber, ccName,
                ccExpiry, shipping);

        Order ordemFromAmazon = amazon.getOrdersById().stream().filter(o -> o.getId() == order.getId()).findFirst()
                .get();

        assertEquals(order.getId(), ordemFromAmazon.getId());
        assertEquals(order.getCustomer(), ordemFromAmazon.getCustomer());
        assertEquals(order.getCC(), ordemFromAmazon.getCC());
        assertEquals(order.getBillingAddress(), ordemFromAmazon.getBillingAddress());
    }

    @Test
    public void shouldCreateAOrderSecondMethod() {
    	cleanTestObjects();
    	startUpTestObjects();
    	Customer customer = Bookstore.getCustomer(5).get();
        int newCartId = bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        Book book = bookmarket.getABookAnyBook();

        

        Cart cart = bookmarket.doCart(amazon.getId(), newCartId, book.getId(), null, null).get();

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

        Order order = bookmarket.doBuyConfirm(amazon.getId(), newCartId, customer.getId(), ccType, ccNumber, ccName,
                ccExpiry, shipping, street1, street2, city, state, zip, country);

        Order ordemFromAmazon = amazon.getOrdersById().stream().filter(o -> o.getId() == order.getId()).findFirst()
                .get();

        assertEquals(order.getId(), ordemFromAmazon.getId());
        assertEquals(order.getCustomer(), ordemFromAmazon.getCustomer());
        assertEquals(order.getCC(), ordemFromAmazon.getCC());
        assertEquals(order.getBillingAddress(), ordemFromAmazon.getBillingAddress());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAOrderWithInvalidParams() {
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

        Order order = bookmarket.doBuyConfirm(amazon.getId(), -1, -32, ccType, ccNumber, ccName, ccExpiry, shipping,
                street1, street2, city, state, zip, country);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreatAOrderWithInvalidParamsSecondMethod() {
        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = "Teste";
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";

        Order order = bookmarket.doBuyConfirm(amazon.getId(), -4, -2, ccType, ccNumber, ccName, ccExpiry, shipping);

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
    public void shouldNotPopulateWhenInvalidParams() {
    	
        try {
            cleanTestObjects();

            Bookstore livraria = new Bookstore(3);

            Bookstore sebo = new Bookstore(4);

            Bookmarket bookmarketTest = new Bookmarket();

            bookmarketTest.init(livraria, sebo);

            bookmarketTest.populate(-1, -2, -3, -4, -5);

        } catch (Exception e) {
            assertTrue(e.getClass() == RuntimeException.class);
            startUpTestObjects();
        }

    }

    @Test
    public void shouldGetConsolidatedBookSales() {
        Bookstore bookStore = new Bookstore(3);
        bookStore.publicPopulateBooks(10);  // Criando 10 livros
        bookStore.publicpopulateOrders(20); // Criando 20 pedidos

        HashMap<Book, Integer> salesByBook = bookStore.getConsolidatedBookSales(null);

        assertNotNull(salesByBook);
        assertTrue(salesByBook.size() > 0);

        for (Integer quantity : salesByBook.values()) {
            assertTrue(quantity > 0);
        }

        for (Book book : salesByBook.keySet()) {
            assertNotNull( book);
            assertNotNull( book.getTitle());
        }

        int totalQuantity = salesByBook.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        assertTrue(totalQuantity >= 20);

    }

    @Test
    public void shouldGetConsolidatedBookSalesWithNoOrders() {
        Bookstore bookStore = new Bookstore(3);
        bookStore.publicPopulateBooks(10);

        HashMap<Book, Integer> salesByBook = bookStore.getConsolidatedBookSales(null);

        assertTrue(salesByBook.isEmpty());
    }

    @Test
    public void shouldGetRandomBestSellers() {
        Bookstore livraria = new Bookstore(3);

        Bookstore sebo = new Bookstore(4);

        Bookmarket bookmarketTest = new Bookmarket();

        bookmarketTest.init(livraria, sebo);

        bookmarketTest.populate(1000, 500, 100, 1000, 200);

        HashMap<Book, Integer> totalSales = bookmarket.getConsolidatedBookSales(null);
         
        List<Book> tenBestSellers = bookmarketTest.getBestSellers(10, null);
        List<Book> twentyBestSellers = bookmarketTest.getBestSellers(20, null);
        assertTrue(tenBestSellers.size() == 10);
        assertTrue(twentyBestSellers.size() == 20);
        
        /// a lista de 20 bestsellers deve conter os 10 best sellers do bookmarket instanciado
        /// neste teste
        assertTrue(twentyBestSellers.containsAll(tenBestSellers));
    }

    @Test
    public void shouldGetTheBestSeller() {

        Book bestSeller = bookmarket.getBestSellers(1, null).get(0);
        int numSales = bookmarket.getConsolidatedBookSales(null).get(bestSeller);

        Book newBestSeller = selectBookThatHasOrder(bookmarket, saraiva);
        while (newBestSeller.equals(bestSeller)) {
            newBestSeller = selectBookThatHasOrder(bookmarket, saraiva);
        }

        List<Book> bestSellersSizeOne = new ArrayList<>();
        bestSellersSizeOne.add(newBestSeller);
        updateQtyForBooks(bestSellersSizeOne, saraiva, numSales + 1);

        List<Book> bestSellers = bookmarket.getBestSellers(1, null);
        assertEquals(newBestSeller, bestSellers.get(0));
    }

    /**
     * Faz uma busca até encontrar um livro que apareça em algum pedido dessa livraria.
     */
    private Book selectBookThatHasOrder(Bookmarket bookmarket, Bookstore bookstore) {
        Book bookWithOrder = null;
        boolean found = false;
        while (!found) {
            Book candidate = Bookmarket.getABookAnyBook();
            if (bookHasOrder(candidate, bookstore)) {
                bookWithOrder = candidate;
                found = true;
            }
        }
        return bookWithOrder;
    }

    /**
     * Verifica se um determinado livro está em algum pedido de uma livraria.
     */
    private boolean bookHasOrder(Book book, Bookstore bookstore) {
        return bookstore.getOrdersById().stream().anyMatch(order ->
                order.getLines().stream().anyMatch(line -> line.getBook().getId() == book.getId())
        );
    }

    /**
     * Atualiza a quantidade dos livros selecionados para um valor alto (p.ex.: 1000)
     * em todos os pedidos da livraria fornecida.
     */
    private void updateQtyForBooks(List<Book> books, Bookstore bookstore, int quantity) {
        for (Order pedido : bookstore.getOrdersById()) {
            for (OrderLine linha : pedido.getLines()) {
                if (books.contains(linha.getBook())) {
                    linha.updateQty(quantity);
                    return;
                }
            }
        }
    }

    @Test
    public void shouldGetTheFiveBestSellers() {
        List<Book> fiveSelectedBooks = new ArrayList<>();

        Book bestSeller = bookmarket.getBestSellers(1, null).get(0);
        int numSales = bookmarket.getConsolidatedBookSales(null).get(bestSeller);

        int attempts = 0; //avoid infinite loop
        while (fiveSelectedBooks.size() < 5 && attempts < 1000) {
            Book possibleBook = Bookmarket.getABookAnyBook();
            if (possibleBook.equals(bestSeller)) {
                continue;
            }

            if (!fiveSelectedBooks.contains(possibleBook)) {
                if (bookHasOrder(possibleBook, amazon) || bookHasOrder(possibleBook, saraiva)) {
                    fiveSelectedBooks.add(possibleBook);
                }
            }
            attempts++;
        }
        assertEquals("Não foi possível encontrar 5 livros em pedidos após várias tentativas",
                5, fiveSelectedBooks.size());

        
        updateQtyForBooks(fiveSelectedBooks, amazon, numSales + 1);
        updateQtyForBooks(fiveSelectedBooks, saraiva, numSales + 1);

        List<Book> fiveBestsellers = bookmarket.getBestSellers(5, null);

        assertEquals(5, fiveBestsellers.size());
        for (Book selectedBook : fiveSelectedBooks) {
            boolean existsInBestsellers = fiveBestsellers
                    .stream()
                    .anyMatch(bs -> bs.getId() == bs.getId());

            assertTrue("Livro com ID " + selectedBook.getId() + " não foi encontrado nos bestsellers.",
                    existsInBestsellers);
        }
        
        startUpTestObjects();
    }

    @Test
    public void shouldGetTheMinimumBookValueCost() {

        double saraivaCost = 10;
        double amazonCost = 20;
        int randomBookId = Bookmarket.getABookAnyBook().getId();
        saraiva.updateStock(randomBookId, saraivaCost);
        amazon.updateStock(randomBookId , amazonCost);
        amazon.getStock(randomBookId).setQty(10);
        saraiva.getStock(randomBookId).setQty(10);
        
        double bookmarketMinCost = bookmarket.getMinimumBookPrice(randomBookId);

        assertTrue(bookmarketMinCost == saraivaCost);
        assertTrue(bookmarketMinCost < amazonCost);
    }

    @Test
    public void shouldGetTheAverageCostFromABook() {
        cleanTestObjects();
        startUpTestObjects();

        Stock amazonStock = null;
        Stock saraivaStock = null;
        Book randomBook = null;

        while (true) {
            try {
                randomBook = bookmarket.getABookAnyBook();
                amazonStock = amazon.getStock(randomBook.getId());
                saraivaStock = saraiva.getStock(randomBook.getId());
                if (amazonStock != null && saraivaStock != null)
                    break;
            } catch (Exception e) {
                continue;
            }
        }

        double averageValue = bookmarket.getBookPriceAverage(randomBook.getId());
        double averageValueFromStocks = (amazonStock.getCost() + saraivaStock.getCost()) / 2;

        assertTrue(averageValue == averageValueFromStocks);
    }

    @Test
    public void shouldGetUniqueReviews() {
            List<Review> uniqueReviews = bookmarket.getUniqueReviews();

            // Verifica se o resultado não é nulo
            assertNotNull("O resultado não pode ser nulo.", uniqueReviews);
            assertFalse(uniqueReviews.isEmpty());

            // Verifica que não há duplicação: para cada par (cliente, livro) deve existir
            // apenas uma review
            Set<String> uniquePairs = new HashSet<>();
            for (Review review : uniqueReviews) {
                String key = review.getCustomer().getId() + "-" + review.getBook().getId();
                assertFalse("Review duplicada encontrada para o par cliente-livro: " + key,
                        uniquePairs.contains(key));
                uniquePairs.add(key);
            }
        }


        
    @Test
    public void shouldReturnValidRecommendationsFromSyntheticDataset() {
    	
        // Criação dos livros
        Book b1 = Bookmarket.getBook(1); // Duna
        Book b2 = Bookmarket.getBook(2); // Neuromancer
        Book b3 = Bookmarket.getBook(3); // Fundação
        Book b4 = Bookmarket.getBook(4); // O Senhor dos Anéis
        Book b5 = Bookmarket.getBook(5); // O Hobbit
        Book b6 = Bookmarket.getBook(6); // It - A Coisa

        // Criação dos clientes
        Customer c1 = Bookstore.getCustomer(1).orElse(null);
        Customer c2 = Bookstore.getCustomer(2).orElse(null);
        Customer c3 = Bookstore.getCustomer(3).orElse(null);
        Customer c4 = Bookstore.getCustomer(4).orElse(null);
        Customer c5 = Bookstore.getCustomer(5).orElse(null);

        // Criação de reviews com variação nos ratings para evitar valores idênticos
        List<Review> reviews = new ArrayList<>();
        try {
            // Usuário 1: Avalia Duna e Neuromancer
            reviews.add(new Review(c1, b1, 5.0, 0));
            reviews.add(new Review(c1, b2, 4.5, 0));

            // Usuário 2: Avalia Duna, Neuromancer e Fundação (ratings levemente diferentes)
            reviews.add(new Review(c2, b1, 4.8, 0));
            reviews.add(new Review(c2, b2, 4.3, 0));
            reviews.add(new Review(c2, b3, 4.0, 0));

            // Usuário 3: Avalia Duna, Neuromancer, Fundação e O Senhor dos Anéis
            reviews.add(new Review(c3, b1, 4.2, 0));
            reviews.add(new Review(c3, b2, 4.0, 0));
            reviews.add(new Review(c3, b3, 4.6, 0));
            reviews.add(new Review(c3, b4, 5.0, 0));

            // Usuário 4: Avalia Neuromancer, O Senhor dos Anéis e O Hobbit
            reviews.add(new Review(c4, b2, 3.5, 0));
            reviews.add(new Review(c4, b4, 4.5, 0));
            reviews.add(new Review(c4, b5, 4.0, 0));

            // Usuário 5: Avalia Fundação, O Senhor dos Anéis, O Hobbit e It - A Coisa
            reviews.add(new Review(c5, b3, 5.0, 0));
            reviews.add(new Review(c5, b4, 4.2, 0));
            reviews.add(new Review(c5, b5, 3.8, 0));
            reviews.add(new Review(c5, b6, 4.5, 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        Bookstore bookstore = new Bookstore(
                10,
                new HashMap<Book, Stock>(),
                new ArrayList<Cart>(),
                reviews,
                new ArrayList<Order>(),
                new LinkedList<Order>());

        Bookmarket bookmarket = new Bookmarket();
        bookmarket.init(bookstore);
        // O usuário 1 avaliou apenas b1 e b2; esperamos recomendações de outros itens
        List<Book> recommendations = bookmarket.getRecommendationByUsers(c1.getId());
        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());

        // Validação: verificar se o livro "Fundação" (b3) foi recomendado
        boolean containsFundacao = recommendations.stream()
                .anyMatch(rec -> rec.getId() == b3.getId());
        assertTrue("Espera que o livro 'Fundação' seja recomendado.", containsFundacao);

        recommendations.forEach(rec -> System.out
                .println("Recomendação para o usuário " + c1.getId() + ": Livro ID " + rec.getId()));
    }

    @Test
    public void shouldReturnValidItemsRecommendationsFromSyntheticDataset() {
        // Criação dos livros
        Book b1 = Bookmarket.getBook(1); // Duna
        Book b2 = Bookmarket.getBook(2); // Neuromancer
        Book b3 = Bookmarket.getBook(3); // Fundação
        Book b4 = Bookmarket.getBook(4); // O Senhor dos Anéis
        Book b5 = Bookmarket.getBook(5); // O Hobbit
        Book b6 = Bookmarket.getBook(6); // It - A Coisa

        // Criação dos clientes
        Customer c1 = Bookstore.getCustomer(1).orElse(null);
        Customer c2 = Bookstore.getCustomer(2).orElse(null);
        Customer c3 = Bookstore.getCustomer(3).orElse(null);
        Customer c4 = Bookstore.getCustomer(4).orElse(null);
        Customer c5 = Bookstore.getCustomer(5).orElse(null);

        // Criação de reviews com variação nos ratings para evitar valores idênticos
        List<Review> reviews = new ArrayList<>();
        try {
            // Usuário 1: Avalia Duna e Neuromancer
            reviews.add(new Review(c1, b1, 5.0, 0));
            reviews.add(new Review(c1, b2, 4.5, 0));

            // Usuário 2: Avalia Duna, Neuromancer e Fundação (ratings levemente diferentes)
            reviews.add(new Review(c2, b1, 4.8, 0));
            reviews.add(new Review(c2, b2, 4.3, 0));
            reviews.add(new Review(c2, b3, 4.0, 0));

            // Usuário 3: Avalia Duna, Neuromancer, Fundação e O Senhor dos Anéis
            reviews.add(new Review(c3, b1, 4.2, 0));
            reviews.add(new Review(c3, b2, 4.0, 0));
            reviews.add(new Review(c3, b3, 4.6, 0));
            reviews.add(new Review(c3, b4, 5.0, 0));

            // Usuário 4: Avalia Neuromancer, O Senhor dos Anéis e O Hobbit
            reviews.add(new Review(c4, b2, 3.5, 0));
            reviews.add(new Review(c4, b4, 4.5, 0));
            reviews.add(new Review(c4, b5, 4.0, 0));

            // Usuário 5: Avalia Fundação, O Senhor dos Anéis, O Hobbit e It - A Coisa
            reviews.add(new Review(c5, b3, 5.0, 0));
            reviews.add(new Review(c5, b4, 4.2, 0));
            reviews.add(new Review(c5, b5, 3.8, 0));
            reviews.add(new Review(c5, b6, 4.5, 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Bookstore bookstore = new Bookstore(
                10,
                new HashMap<Book, Stock>(),
                new ArrayList<Cart>(),
                reviews,
                new ArrayList<Order>(),
                new LinkedList<Order>());

        // Pegamos um livro como referência para recomendação baseada em itens
        Bookmarket bookmarket = new Bookmarket();
        bookmarket.init(bookstore);
        List<Book> recommendations = bookmarket.getRecommendationByItems(b1.getId());
        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());

        // Validação: verificar se o livro "Fundação" (b3) foi recomendado, pois tem maior correlação com Duna
        boolean containsFundacao = recommendations.stream()
                .anyMatch(rec -> rec.getId() == b3.getId());
        assertTrue("Espera que o livro 'Fundação' seja recomendado.", containsFundacao);

        recommendations.forEach(rec -> System.out
                .println("Recomendação para o livro " + b1.getId() + ": Livro ID " + rec.getId()));
    }

    @Test
    public void shouldGetFiveRecommendationsByItemsWithNonSyntheticData() {

        cleanTestObjects();
        startUpTestObjects();

        // Seleciona um livro aleatório
        Book book1 = Bookmarket.getABookAnyBook();

        // Obtém recomendações baseadas nos itens avaliados pelo cliente
        List<Book> recommendations = bookmarket.getRecommendationByItems(book1.getId());

        // Verifica se foram retornadas 5 recomendações
        assertNotNull(recommendations);
        assertEquals(5, recommendations.size());
    }

    @Test
    public void shouldGetRecommendationForSubscriberCustomer(){
        // book selection
        Book b1 = Bookmarket.getBook(1); // Duna
        Book b2 = Bookmarket.getBook(2); // Neuromancer
        Book b3 = Bookmarket.getBook(3); // Fundação
        Book b4 = Bookmarket.getBook(4); // O Senhor dos Anéis
        Book b5 = Bookmarket.getBook(5); // O Hobbit
        Book b6 = Bookmarket.getBook(6); // It - A Coisa

        // Select a subscriber customer
        Customer customer = null;
        for (int i = 1; customer == null || customer.getType() == Type.SUBSCRIBER; i++) {
            customer = Bookstore.getCustomer(i).orElse(null);
        }

        // Criação dos clientes
        Customer c1 = customer;
        Customer c2 = Bookstore.getCustomer(2).orElse(null);
        Customer c3 = Bookstore.getCustomer(3).orElse(null);
        Customer c4 = Bookstore.getCustomer(4).orElse(null);
        Customer c5 = Bookstore.getCustomer(5).orElse(null);

        // Criação de reviews com variação nos ratings para melhorar a similaridade
        // entre os itens
        List<Review> reviews = new ArrayList<>();
        try {
            // Usuário 1: Avalia Duna e Neuromancer
            reviews.add(new Review(c1, b1, 5.0, 0));
            reviews.add(new Review(c1, b2, 4.5, 0));

            // Usuário 2: Avalia Duna, Neuromancer e Fundação (ratings levemente diferentes)
            reviews.add(new Review(c2, b1, 4.8, 0));
            reviews.add(new Review(c2, b2, 4.3, 0));
            reviews.add(new Review(c2, b3, 4.0, 0));

            // Usuário 3: Avalia Duna, Neuromancer, Fundação e O Senhor dos Anéis
            reviews.add(new Review(c3, b1, 4.2, 0));
            reviews.add(new Review(c3, b2, 4.0, 0));
            reviews.add(new Review(c3, b3, 4.6, 0));
            reviews.add(new Review(c3, b4, 5.0, 0));

            // Usuário 4: Avalia Neuromancer, O Senhor dos Anéis e O Hobbit
            reviews.add(new Review(c4, b2, 3.5, 0));
            reviews.add(new Review(c4, b4, 4.5, 0));
            reviews.add(new Review(c4, b5, 4.0, 0));

            // Usuário 5: Avalia Fundação, O Senhor dos Anéis, O Hobbit e It - A Coisa
            reviews.add(new Review(c5, b3, 5.0, 0));
            reviews.add(new Review(c5, b4, 4.2, 0));
            reviews.add(new Review(c5, b5, 3.8, 0));
            reviews.add(new Review(c5, b6, 4.5, 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Bookstore bookstore = new Bookstore(
                10,
                new HashMap<Book, Stock>(),
                new ArrayList<Cart>(),
                reviews,
                new ArrayList<Order>(),
                new LinkedList<Order>());

        Bookmarket bookmarket = new Bookmarket();
        bookmarket.init(bookstore);

        HashMap<Book, Double> recommendations = bookmarket.getRecommendation(customer.getId());

        double fundacaoMinimunCost = bookmarket.getMinimumBookPrice(b3.getId());

        boolean containsFundacao = recommendations.keySet().stream()
                .anyMatch(book -> book.getId() == b3.getId());
        assertTrue("Espera que o livro 'Fundação' seja recomendado.", containsFundacao);

        assertTrue(recommendations.get(b3) == fundacaoMinimunCost);
    }

    @Test
    public void shouldGetRecommendationForDefaultCustomer(){
        // book selection
        Book b1 = Bookmarket.getBook(1); // Duna
        Book b2 = Bookmarket.getBook(2); // Neuromancer
        Book b3 = Bookmarket.getBook(3); // Fundação
        Book b4 = Bookmarket.getBook(4); // O Senhor dos Anéis
        Book b5 = Bookmarket.getBook(5); // O Hobbit
        Book b6 = Bookmarket.getBook(6); // It - A Coisa

        // Select a subscriber customer
        Customer customer = null;
        for (int i = 1; customer == null || customer.getType() == Type.DEFAULT; i++) {
            customer = Bookstore.getCustomer(i).orElse(null);
        }

        // Criação dos clientes
        Customer c1 = customer;
        Customer c2 = Bookstore.getCustomer(2).orElse(null);
        Customer c3 = Bookstore.getCustomer(3).orElse(null);
        Customer c4 = Bookstore.getCustomer(4).orElse(null);
        Customer c5 = Bookstore.getCustomer(5).orElse(null);

        // Criação de reviews com variação nos ratings para melhorar a similaridade
        // entre os itens
        List<Review> reviews = new ArrayList<>();
        try {
            // Usuário 1: Avalia Duna e Neuromancer
            reviews.add(new Review(c1, b1, 5.0, 0));
            reviews.add(new Review(c1, b2, 4.5, 0));

            // Usuário 2: Avalia Duna, Neuromancer e Fundação (ratings levemente diferentes)
            reviews.add(new Review(c2, b1, 4.8, 0));
            reviews.add(new Review(c2, b2, 4.3, 0));
            reviews.add(new Review(c2, b3, 4.0, 0));

            // Usuário 3: Avalia Duna, Neuromancer, Fundação e O Senhor dos Anéis
            reviews.add(new Review(c3, b1, 4.2, 0));
            reviews.add(new Review(c3, b2, 4.0, 0));
            reviews.add(new Review(c3, b3, 4.6, 0));
            reviews.add(new Review(c3, b4, 5.0, 0));

            // Usuário 4: Avalia Neuromancer, O Senhor dos Anéis e O Hobbit
            reviews.add(new Review(c4, b2, 3.5, 0));
            reviews.add(new Review(c4, b4, 4.5, 0));
            reviews.add(new Review(c4, b5, 4.0, 0));

            // Usuário 5: Avalia Fundação, O Senhor dos Anéis, O Hobbit e It - A Coisa
            reviews.add(new Review(c5, b3, 5.0, 0));
            reviews.add(new Review(c5, b4, 4.2, 0));
            reviews.add(new Review(c5, b5, 3.8, 0));
            reviews.add(new Review(c5, b6, 4.5, 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Bookstore bookstore = new Bookstore(
                10,
                new HashMap<Book, Stock>(),
                new ArrayList<Cart>(),
                reviews,
                new ArrayList<Order>(),
                new LinkedList<Order>());

        Bookmarket bookmarket = new Bookmarket();
        bookmarket.init(bookstore);

        HashMap<Book, Double> recommendations = bookmarket.getRecommendation(customer.getId());

        double fundacaoAvarageCost = bookmarket.getBookPriceAverage(b3.getId());
        boolean containsFundacao = recommendations.keySet().stream()
                .anyMatch(book -> book.getId() == b3.getId());
        assertTrue("Espera que o livro 'Fundação' seja recomendado.", containsFundacao);

        assertTrue(recommendations.get(b3) == fundacaoAvarageCost);
    }
}