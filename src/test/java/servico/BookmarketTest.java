package servico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import dominio.Book;
import dominio.Cart;
import dominio.Customer;
import dominio.Order;
import servico.bookstore.Bookstore;
import dominio.Review;
import dominio.Stock;
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

        Bookmarket.init(amazon, saraiva);
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
        double discount = 10.5;
        String birthdate = "1990-01-01";
        Date data = new Date(); // Instância atual de Date
        long now = System.currentTimeMillis(); // Timestamp atual

        Customer createdCustomer = Bookmarket.createNewCustomer(fname, lname, street1, street2, city, state, zip,
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

        Customer createdCustomer = Bookmarket.createNewSubscriber(fname, lname, street1, street2, city, state, zip,
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
        List<Review> reviewsFromAmazon = amazon.getReviews();

        List<Review> reviewsFromSaraiva = saraiva.getReviews();

        List<Review> allReviews = Bookmarket.getReviews();

        assertEquals(reviewsFromAmazon.size() + reviewsFromSaraiva.size(), allReviews.size());

        assertFalse(reviewsFromAmazon.equals(reviewsFromSaraiva));
    }

    @Test
    public void shouldGetAReviewThatExistsInABookstore() {
        List<Review> amazonReviews = Bookmarket.getReviewsByBookstore(amazon.getId());

        List<Review> saraivaReviews = Bookmarket.getReviewsByBookstore(saraiva.getId());

        assertTrue(TPCW_Util.areReviewFromAUniqueBookstore(saraivaReviews, saraiva.getId()));

        assertTrue(TPCW_Util.areReviewFromAUniqueBookstore(amazonReviews, amazon.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAReviewWithInvalidValues() {
        Bookmarket.createReview(-1, null, null, -5);
    }

    @Test
    public void shouldCreateAReviewForABookstore() {
        Customer amazonCustomer = Bookstore.getCustomer(1).get();

        Book amazonBook = Bookstore.getABookAnyBook(new Random(0));

        Review amazonReview = Bookmarket.createReview(amazon.getId(), amazonCustomer, amazonBook, Math.random() * 5);

        assertTrue(
                Bookmarket.getReviewById(amazon.getId(), amazonReview.getId()).get().getId() == amazonReview.getId());

        assertFalse(Bookmarket.getReviewById(saraiva.getId(), amazonReview.getId()).isPresent());
    }

    @Test
    public void shouldChangeAReviewFromABookstore() {
        Review saraivaReview = saraiva.getReviews().get(0);

        double reviewLastValue = saraivaReview.getRating();

        double newValue = Math.random() * 6;

        while (reviewLastValue == newValue || newValue > 5)
            newValue = Math.random() * 6;

        boolean result = Bookmarket.changeReviewValue(saraiva.getId(), saraivaReview.getId(), newValue);

        Review changedSaraivaReview = Bookmarket.getReviewById(saraiva.getId(), saraivaReview.getId()).get();

        assertTrue(result);

        assertEquals(changedSaraivaReview.getId(), saraivaReview.getId());

        assertTrue(changedSaraivaReview.getRating() == newValue);

        assertFalse(changedSaraivaReview.getRating() == reviewLastValue);
    }

    @Test
    public void shouldRemoveAReviewFromABookstore() {
        List<Review> saraivaReviews = Bookmarket.getReviewsByBookstore(saraiva.getId());

        Review saraivaReview = saraivaReviews.get(0);

        int saraivaLastSize = saraivaReviews.size();

        boolean result = Bookmarket.removeReviewById(saraiva.getId(), saraivaReview.getId());

        assertTrue(result);

        assertTrue(saraivaLastSize == (Bookmarket.getReviewsByBookstore(saraiva.getId()).size() + 1));

        assertFalse(Bookmarket.removeReviewById(saraiva.getId(), saraivaReview.getId()));

        assertFalse(Bookmarket.getReviewById(saraiva.getId(), saraivaReview.getId()).isPresent());
    }

    @Test
    public void shouldAllReviewTobeFromTheSameBook() {
        Book amazonBook = Bookstore.getABookAnyBook(new Random(0));

        Book saraivaBook = Bookstore.getABookAnyBook(new Random(2));

        List<Review> amazonReviews = Bookmarket.getReviewsByBook(amazon.getId(), amazonBook);

        List<Review> saraivaReviews = Bookmarket.getReviewsByBook(saraiva.getId(), saraivaBook);

        assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(saraivaReviews, saraivaBook));

        assertTrue(TPCW_Util.areAllReviewsFromTheSameBook(amazonReviews, amazonBook));
    }

    @Test
    public void shouldAllReviewTobeFromTheSameCustomer() {
        Customer amazonCustomer = Bookstore.getCustomer(1).get();

        Customer saraivaCustomer = Bookstore.getCustomer(2).get();

        List<Review> amazonReviews = Bookmarket.getReviewsByCustomer(amazon.getId(), amazonCustomer);

        List<Review> saraivaReviews = Bookmarket.getReviewsByCustomer(saraiva.getId(), saraivaCustomer);

        assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(saraivaReviews, saraivaCustomer));

        assertTrue(TPCW_Util.areAllReviewsFromTheSameCustomer(amazonReviews, amazonCustomer));
    }

//     @Test
    public void shouldRefreshUserSessionByTwoHours() {
        long now = System.currentTimeMillis();

        Date dateNow = new Date(now);

        Customer amazonCustomer = amazon.getCustomer(1).get();

        bookmarket.refreshSession(amazonCustomer.getId());

        assertTrue(amazonCustomer.getExpiration().getHours() == dateNow.getHours() + 2);

    }

    @Test(expected = RuntimeException.class)
    public void shouldThrownAExceptionWhenRefreshingSessionOfAInvalidUser() {
        Bookmarket.refreshSession(-1);
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

        List<Double> amazonBookCosts = Bookmarket.getCosts(amazonBook);

        List<Double> saraivaBookCosts = Bookmarket.getCosts(saraivaBook);

        assertTrue(amazonBookCosts.contains(amazonBookCost));

        assertTrue(saraivaBookCosts.contains(saraivaBookCost));
    }

    @Test
    public void shouldGetABookStock() {
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        Stock amazonBookStock = amazon.getStock(amazonBook.getId());

        assertTrue(amazonBookStock == Bookmarket.getStock(amazon.getId(), amazonBook.getId()));
    }

    @Test
    public void shouldGetABookStocks() {
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        Stock amazonBookStock = amazon.getStock(amazonBook.getId());

        assertTrue(Bookmarket.getStocks(amazonBook.getId()).contains(amazonBookStock));
    }

    @Test
    public void shouldGetRelatedBooks() {
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        List<Book> relatedBooksByBookmarket = Bookmarket.getRelated(amazonBook.getId());

        assertTrue(relatedBooksByBookmarket.containsAll(amazonBook.getAllRelated()));
    }

    @Test
    public void shouldUpdateABook() {
        Book amazonBook = Bookstore.getABookAnyBook(new Random(1));

        double oldCost = amazon.getStock(amazonBook.getId()).getCost();

        Bookmarket.adminUpdate(amazonBook.getId(), 23.50, null, null);

        Book updatedBook = Bookmarket.getBook(amazonBook.getId());

        double newCost = amazon.getStock(updatedBook.getId()).getCost();

        assertEquals(amazonBook.getId(), updatedBook.getId());

        assertTrue(newCost == 23.50);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowAExceptionWhenUpdateAInvalidBook() {
        Bookmarket.adminUpdate(-1, 23.50, null, null);
    }

    @Test
    public void shouldCreateAEmptyCart() {
    	Customer customer = amazon.getCustomer(2).get();
        int newCartId = Bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        assertTrue(newCartId == amazon.getCart(newCartId).get().getId());

        assertTrue(newCartId == Bookmarket.getCart(amazon.getId(), newCartId).get().getId());
    }
    
    @Test
    public void shouldGetACartByCustomer() {
    	Customer customer = amazon.getCustomer(2).get();
    	
    	Optional<Cart> cart = bookmarket.getCartByCustomer(amazon.getId(), customer.getId());
    	
    	assertTrue(cart.isPresent());
    }
    
    @Test
    public void shouldNotGetANonExistingCartByCustomer() {
    	assertTrue(bookmarket.getCartByCustomer(amazon.getId(), -1).isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateAEmptyCartWithAInvalidStoreId() {
        Bookmarket.createEmptyCart(-1, -2);
    }

    @Test
    public void shouldGetACartFromABookstore() {
    	Customer customer = amazon.getCustomer(2).get();
        int newCartId = Bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        Cart amazonCart = Bookmarket.getCart(amazon.getId(), newCartId).get();

        assertEquals(amazonCart.getId(), amazon.getCart(newCartId).get().getId());
    }

    @Test
    public void shouldCreateACartWithBooks() {
    	Customer customer = amazon.getCustomer(2).get();
        int newCartId = Bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        Book book = Bookmarket.getABookAnyBook();

        List<Integer> bookIds = new ArrayList<Integer>(book.getId());

        List<Integer> bookQtdy = new ArrayList<Integer>(2);

        Cart cart = Bookmarket.doCart(amazon.getId(), newCartId, book.getId(), bookIds, bookQtdy).get();

        assertEquals(newCartId, cart.getId());

        assertTrue(cart.getLines().stream().anyMatch(line -> line.getBook().getId() == book.getId()));

        int newEmptyCart = Bookmarket.createEmptyCart(saraiva.getId(), saraiva.getCustomer(3).get().getId());

        Cart cartWithRandomBook = Bookmarket.doCart(saraiva.getId(), newEmptyCart, null, null, null).get();

        assertEquals(newEmptyCart, cartWithRandomBook.getId());

        assertTrue(cart.getLines().size() > 0);

        assertTrue(cart.getLines().stream().anyMatch(x -> x.getBook().getId() == book.getId()));
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateACartWithInvalidCartId() {
        Bookmarket.doCart(-1, 0, null, null, null);
    }

    @Test
    public void shouldCreateAOrder() {
    	Customer amazonCustomer = amazon.getCustomer(5).get();
        int newCartId = Bookmarket.createEmptyCart(amazon.getId(), amazonCustomer.getId());

        Book book = Bookmarket.getABookAnyBook();

        Customer customer = Bookstore.getCustomer(5).get();

        Cart cart = Bookmarket.doCart(amazon.getId(), newCartId, book.getId(), null, null).get();

        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = customer.getFname() + customer.getLname();
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";

        Order order = Bookmarket.doBuyConfirm(amazon.getId(), newCartId, customer.getId(), ccType, ccNumber, ccName,
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
    	Customer customer = Bookstore.getCustomer(5).get();
        int newCartId = Bookmarket.createEmptyCart(amazon.getId(), customer.getId());

        Book book = Bookmarket.getABookAnyBook();

        

        Cart cart = Bookmarket.doCart(amazon.getId(), newCartId, book.getId(), null, null).get();

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

        Order order = Bookmarket.doBuyConfirm(amazon.getId(), newCartId, customer.getId(), ccType, ccNumber, ccName,
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

        Order order = Bookmarket.doBuyConfirm(amazon.getId(), -1, -32, ccType, ccNumber, ccName, ccExpiry, shipping,
                street1, street2, city, state, zip, country);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreatAOrderWithInvalidParamsSecondMethod() {
        String ccType = "VISA";
        int ccNumber = 123456789;
        String ccName = "Teste";
        Date ccExpiry = new Date(2025 - 1900, 5 - 1, 15); // Maio 15, 2025 (ano - 1900, mês - 1)
        String shipping = "123 Main St, Springfield, IL, 62704";

        Order order = Bookmarket.doBuyConfirm(amazon.getId(), -4, -2, ccType, ccNumber, ccName, ccExpiry, shipping);

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

        HashMap<Book, Integer> salesByBook = bookStore.getConsolidatedBookSales();

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

        HashMap<Book, Integer> salesByBook = bookStore.getConsolidatedBookSales();

        assertTrue(salesByBook.isEmpty());
    }

    @Test
    public void shouldSortBooksBySalesDescending() {
        Bookstore bookStore = new Bookstore(3);
        bookStore.publicPopulateBooks(10);
        bookStore.publicpopulateOrders(20);
        HashMap<Book, Integer> totalSales = bookStore.getConsolidatedBookSales();
        List<Book> result = Bookmarket.sortBooksBySalesDescending(totalSales, 10);

        assertEquals(10, result.size());
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(totalSales.get(result.get(i)) >= totalSales.get(result.get(i + 1)));
        }

    }

    @Test
    public void shouldGetBestSellers() {
        Bookstore livraria = new Bookstore(3);

        Bookstore sebo = new Bookstore(4);

        Bookmarket bookmarketTest = new Bookmarket();

        bookmarketTest.init(livraria, sebo);

        bookmarketTest.populate(1000, 500, 100, 1000, 200);

        HashMap<Book, Integer> totalSales = Bookmarket.getConsolidatedBookSales();
        
        cleanTestObjects();
         
        List<Book> tenBestSellers = bookmarketTest.getBestSellers(10);
        List<Book> twentyBestSellers = bookmarketTest.getBestSellers(20);
        assertTrue(tenBestSellers.size() == 10);
        assertTrue(twentyBestSellers.size() == 20);
        List<Book> tenBestBooksIntwentyBestSellers = twentyBestSellers.subList(0, 10);
        List<Book> tenWorstBooksIntwentyBestSellers = twentyBestSellers.subList(10, 20);
        assertEquals(tenBestSellers, tenBestBooksIntwentyBestSellers);

        for (Book worstBook : tenWorstBooksIntwentyBestSellers) {
            for (Book bestBook : tenBestSellers) {
                assertTrue(totalSales.get(worstBook) < totalSales.get(bestBook));
            }
        }
        
        startUpTestObjects();
    }

  @Test
  public void shouldGetTheMinimumBookValueCost() {

    Optional<Stock> saraivaMinCost = saraiva.getStocks().stream().min(Comparator.comparingDouble(Stock::getCost));

    Stock amazonSameBookMinCost = amazon.getStock(saraivaMinCost.get().getBook().getId());
    	
		Optional<Stock> bookmarketMinCost = bookmarket.getMinimumBookPrice(saraivaMinCost.get().getBook().getId());
        
		assertTrue(bookmarketMinCost.get().getCost() == saraivaMinCost.get().getCost());
		
		assertTrue(bookmarketMinCost.get().getIdBookstore() == saraiva.getId());
		
		assertTrue(amazonSameBookMinCost.getCost() > saraivaMinCost.get().getCost());
    }

    @Test
    public void shouldGetTheAverageCostFromABook() {
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
        List<Review> uniqueReviews = Bookmarket.getUniqueReviews();

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

}