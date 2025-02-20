package servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dominio.Address;
import dominio.Author;
import dominio.Book;
import dominio.Cart;
import dominio.Customer;
import dominio.Order;
import dominio.OrderLine;
import dominio.Review;
import dominio.Stock;
import dominio.customer.enums.Type;
import servico.bookmarket.Bookmarket;
import servico.bookstore.Bookstore;
import servico.bookstore.utils.BookstoreBookCounter;

/**
 *
 * @author INF329
 */
public class BookstoreTest {

    public BookstoreTest() {
    }

    static Bookstore instance;

    private static void populateInstance() {
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
        instance.populateInstanceBookstore(orders, new Random(seed), now);
    }
    @BeforeClass
    public static void setUpClass() {
    	populateInstance();
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
        assertEquals(stock.get(0).getIdBookstore(), bookstore.getId());
    }
    
    /**
     * Teste para verificar se a população de bookstore, está levando em conta
     * o seed de criação do Rand para uma criação de objetos controlados
     */
    @Test
    public void shouldPopulateTheWholeBookstoreBySeed() {
        long firstSeed = 5;
        long secondSeed = 12782;
        long now = System.currentTimeMillis();
        int items = 83;
        int customers = 4;
        int addresses = 13;
        int authors = 1;
        
        Bookstore amazon = new Bookstore(1);
        
        Bookstore saraiva = new Bookstore(2);
       
        
        /// Repopula os dados globais de acordo com a primeira seed(5)
        Bookstore.populate(firstSeed, now, items, customers, addresses, authors);
        
        /// Popula os dados da amazon para que seja populado baseado na seed(5)
        amazon.populateInstanceBookstore(10, new Random(firstSeed), now);

        // Repopula os dados globais de acordo com a segunda seed(12782)
        Bookstore.populate(secondSeed, now, items, customers, addresses, authors);
        
        // Popula os dados da saraiva para que seja populado baseado na seed(12782)
        saraiva.populateInstanceBookstore(10, new Random(secondSeed), now);
        
        /// Verifica se TODAS as reviews da saraiva são diferentes da amazon
        assertFalse(saraiva.getReviews().containsAll(amazon.getReviews()));
        
        // Vamos popular novamente os objetos globais com a primeira seed
        Bookstore.populate(firstSeed, now, items, customers, addresses, authors);
        
        /// Popula os dados da amazon para que seja populado baseado na seed(5)
        amazon.populateInstanceBookstore(10, new Random(firstSeed), now);
        
        /// Iremos repopular saraiva com a mesma seed(5) da amazon
        saraiva.populateInstanceBookstore(10, new Random(firstSeed), now);
        
        /// Verifica se TODAS as reviews da saraiva são iguais da amazon
        assertTrue(saraiva.getReviews().containsAll(amazon.getReviews()));
        
        /// Repopula os objetos de teste da classe
        populateInstance();
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
        Customer result = Bookstore.getCustomer(cId).get();
        assertEquals(cId, result.getId());
    }

    /**
     * Test of getCustomer method, of class Bookstore.
     */
    @Test
    public void testGetCustomer_String() {
        int id = 0;
        Optional<Customer> customer = Bookstore.getCustomer(id);
        while (!customer.isPresent()) {
            id++;
            customer = Bookstore.getCustomer(id);
        }
        String username = customer.get().getUname();
        Customer result = Bookstore.getCustomer(username).get();
        assertEquals(username, result.getUname());

    }

    /**
     * Test of createCustomer method, of class Bookstore.
     */
    @Test
    public void testCreateCustomer() {
        String fname = "João";
        String lname = "Silva";
        String street1 = "Rua das Flores, 123";
        String street2 = "Apto 45B";
        String city = "São Paulo";
        String state = "SP";
        String zip = "01000-000";
        String countryName = "Brasil";
        String phone = "+55 11 98765-4321";
        String email = "joao.silva@email.com";
        double discount = 10.5;
        Date birthdate = new Date(90, 4, 15); // 15 de maio de 1990 (ano baseado em 1900)
        String data = "2025-02-02";
        long now = System.currentTimeMillis();

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
     * 
     * @throws Exception
     */
    @Test
    public void testRefreshCustomerSession() throws Exception {
        int cId = 1;
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
        // assertEquals(cost, book.getCost(), 0.0);
        assertEquals(image, book.getImage());
        assertEquals(thumbnail, book.getThumbnail());
    }

    /**
     * Test of createCart method, of class Bookstore.
     */
    @Test
    public void testCreateCart() {
        long now = 0L;
        Customer customer = instance.getCustomer(1).get();

        Cart cart = instance.createCart(customer.getId(), now).get();

        assertTrue(cart.getId() > 0);

        assertEquals(cart.getId(), instance.getCart(cart.getId()).get().getId());

        assertEquals(cart.getBookstoreId(), instance.getId());

        assertEquals(cart.getCustomer().getId(), customer.getId());
    }

    /**
     * Test of getCart method, of class Bookstore.
     */
    @Test
    public void testGetCart() {
        Cart cart = instance.getCart(1).get();

        assertFalse(cart == null);

    }

    /**
     * Test of cartUpdate method, of class Bookstore.
     */
    @Test
    public void testCartUpdate() {
        Cart cart = instance.getCart(1).get();
        Book book = instance.getABookAnyBook(new Random(4));

        long now = 0L;

        Cart result = instance.cartUpdate(cart.getId(), book.getId(), null, null, now).get();

        assertTrue(result.getLines().stream().anyMatch(x -> x.getBook().getId() == book.getId()));

    }

    @Test
    public void shouldNotCreateANewCartWhenACartForTheCustomerAlreadyExists() {
    	populateInstance();
        Cart cart = instance.getCart(1).get();

        Customer customer = cart.getCustomer();

        Cart cartCreated = instance.createCart(customer.getId(), 0L).get();

        assertEquals(cart.getId(), cartCreated.getId());

        assertTrue(cart.getTime() == cartCreated.getTime());
    }

    @Test
    public void shouldGetAEmptyCartWhenCreatingForAInvalidCustomer() {
        Optional<Cart> cart = instance.createCart(-1, 0L);

        assertEquals(!cart.isPresent(), true);
    }

    @Test
    public void shouldGetAEmptyCartWhenFindingAInexistentCart() {
        assertTrue(!instance.getCart(-1).isPresent());
    }

    /**
     * Test of confirmBuy method, of class Bookstore.
     */
    // @Test
    public void testConfirmBuy() {
        int customerId = 1;
        int cartId = 67890;
        String comment = "Cliente frequente, gosta de livros de ficção.";
        String ccType = "Visa";
        long ccNumber = 4111111111111111L; // Número fictício para exemplo
        String ccName = "João Silva";
        Date ccExpiry = new Date(126, 11, 31); // 31 de dezembro de 2026 (anos baseados em 1900)
        String shipping = "Expresso";
        Date shippingDate = new Date(); // Data atual
        int addressId = 54321;
        long now = System.currentTimeMillis();

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
        Customer customer = instance.getCustomer(1).get();

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

        while (randomValue == lastReview || randomValue > 5)
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

        instance.createReview(new Customer(-2, null, null, null, null, null, null, null, null, null, null, 0, 0, 0,
                null, null, null, null), book, 2);
    }

    @Test(expected = IOException.class)
    public void cannotCreateAReviewWithoutAExistingBook() throws IOException {
        Customer customer = instance.getCustomer(1).get();

        instance.createReview(customer,
                new Book(-2, null, null, null, null, null, null, null, 0, null, null, 0, null, null, null), 0);
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
        for (int i = 0; i < 10000; i++) {
            assertEquals(i, result.get(i).getId());
        }
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

    @Test
    public void shouldUpdateACustomerType() {
        Customer customer = instance.getCustomer(1).get();

        Customer updatedCustomer = instance.updateCustomerType(customer.getId(), Type.SUBSCRIBER).get();

        assertTrue(customer.getId() == updatedCustomer.getId() && updatedCustomer.getType() == Type.SUBSCRIBER);
    }

    @Test
    public void shouldCreateACustomerWithDefaultTypeWhenTypeParamIsEmpty() {
        String fname = "João";
        String lname = "Silva";
        String street1 = "Rua das Flores, 123";
        String street2 = "Apto 45B";
        String city = "São Paulo";
        String state = "SP";
        String zip = "01000-000";
        String countryName = "Brasil";
        String phone = "+55 11 98765-4321";
        String email = "joao.silva@email.com";
        double discount = 10.5;
        Date birthdate = new Date(90, 4, 15); // 15 de maio de 1990 (ano baseado em 1900)
        String data = "2025-02-02";
        long now = System.currentTimeMillis();
        
        Customer newCustomer = instance.createCustomer(fname, lname, street1, street2, city, state, zip, countryName, phone, email, discount, birthdate, data, now, null);
        
        assertTrue(newCustomer.getType() == Type.DEFAULT);
    }

    @Test
    public void shouldUpdateRelatedBooks() {
    	populateInstance();
    	
        Book randomBook = Bookstore.getABookAnyBook(new Random());
        // Garante que o livro tenha todos os relacionamentos
        while (randomBook.getRelated1() == null ||
                randomBook.getRelated2() == null ||
                randomBook.getRelated3() == null ||
                randomBook.getRelated4() == null ||
                randomBook.getRelated5() == null) {
            randomBook = Bookstore.getABookAnyBook(new Random());
        }
        List<Book> relatedBooks = new ArrayList<>();
        relatedBooks.add(randomBook.getRelated1());
        relatedBooks.add(randomBook.getRelated2());
        relatedBooks.add(randomBook.getRelated3());
        relatedBooks.add(randomBook.getRelated4());
        relatedBooks.add(randomBook.getRelated5());

        // Obtem o número de vendas conjuntas com o livro mais relacionado
        Set<Integer> clientIds = instance.getClientIdsWhoBoughtTargetBook(randomBook);
        Map<Integer, BookstoreBookCounter> purchaseFrequency = instance.getPurchaseFrequency(clientIds, randomBook);
        BookstoreBookCounter maxRelated = purchaseFrequency.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue().getCount() > entry2.getValue().getCount() ? 1 : -1)
                .get().getValue();
        int numSales = maxRelated.getCount();

        // Pega um novo livro não relacionado
        Book newRelated = Bookstore.getABookAnyBook(new Random());
        while (relatedBooks.contains(newRelated)) {
            newRelated = Bookstore.getABookAnyBook(new Random());
        }

        // Cria vendas conjuntas com o novo livro
        for (int i = 0; i < numSales + 1; i++) {
            createOrder(instance, null, List.of(newRelated.getId(), randomBook.getId()), List.of(10, 10));
        }

        instance.updateRelatedBooks(randomBook);

        Book updatedBook = Bookstore.getBook(randomBook.getId()).get();

        List<Book> updatedRelatedBooks = new ArrayList<>();
        updatedRelatedBooks.add(updatedBook.getRelated1());
        updatedRelatedBooks.add(updatedBook.getRelated2());
        updatedRelatedBooks.add(updatedBook.getRelated3());
        updatedRelatedBooks.add(updatedBook.getRelated4());
        updatedRelatedBooks.add(updatedBook.getRelated5());

        assertTrue(updatedRelatedBooks.contains(newRelated));
    }

    public Order createOrder(Bookstore bookstoreInstance, Customer customer, List<Integer> bookIds,
            List<Integer> quantities) {
        if (customer == null) {
            customer = Bookstore.createCustomer("John",
                    "Doe",
                    "123 Main St",
                    "Apt 4B",
                    "Springfield",
                    "IL",
                    "62704",
                    "USA",
                    "555-1234",
                    "john.doe@example.com",
                    10.0,
                    new Date(90, 4, 15),
                    "data",
                    System.currentTimeMillis(),
                    dominio.customer.enums.Type.DEFAULT);
        }

        // Passo 2: Crie um carrinho de compras
        Optional<Cart> cartOpt = bookstoreInstance.createCart(customer.getId(), System.currentTimeMillis());
        Cart cart = cartOpt.get();

        // Passo 3: Adicione livros ao carrinho
        bookstoreInstance.cartUpdate(cart.getId(), null, bookIds, quantities, System.currentTimeMillis());

        // Passo 4: Confirme a compra
        String comment = "Cliente frequente, gosta de livros de ficção.";
        String ccType = "Visa";
        long ccNumber = 4111111111111111L; // Número fictício para exemplo
        String ccName = "John Doe";
        Date ccExpiry = new Date(126, 11, 31); // 31 de dezembro de 2026 (anos baseados em 1900)
        String shipping = "Expresso";
        Date shippingDate = new Date(); // Data atual
        int addressId = -1; // Use o endereço padrão do cliente
        long now = System.currentTimeMillis();

        return bookstoreInstance.confirmBuy(
                customer.getId(),
                cart.getId(),
                comment,
                ccType,
                ccNumber,
                ccName,
                ccExpiry,
                shipping,
                shippingDate,
                addressId,
                now);
    }

    @Test
    public void shouldReturnRightHashmapOnConsolidatedBookSales() {
        HashMap<Book, Integer> consolidatedSales = instance.getConsolidatedBookSales(null);
        assertNotNull(consolidatedSales);
        List<Order> orders = instance.getOrdersById();
        for (Map.Entry<Book, Integer> entry : consolidatedSales.entrySet()) {
            Book book = entry.getKey();
            Integer sales = entry.getValue();
            for (Order order : orders) {
                for (OrderLine line : order.getLines()) {
                    Book orderBook = line.getBook();
                    if (book.getId() == orderBook.getId()) {
                        assertTrue(sales >= line.getQty());
                    }
                }
            }
        }
    }
    
    @Test
    public void shouldReturnNullOnConsolidatedBookSales() {
        Bookstore bookstore = new Bookstore(5);
        HashMap<Book, Integer> consolidatedSales = bookstore.getConsolidatedBookSales(null);
        assertTrue(consolidatedSales.size() == 0);
    }

    @Test
    public void shouldReturnValidRecommendationsFromSyntheticDataset() {
    	
    	populateInstance();
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

        // O usuário 1 avaliou apenas b1 e b2; esperamos recomendações de outros itens
        List<Book> recommendations = bookstore.getRecommendationByUsers(c1.getId(), 3);
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
    public void shouldReturnValidItemBasedRecommendationsFromSyntheticDataset() {
    	populateInstance();
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

        // Usuário 1 avaliou somente b1 e b2; espera-se que itens similares a eles sejam
        // recomendados.
        List<Book> recommendations = bookstore.getRecommendationByUsers(c1.getId(), 3);
        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());

        // Validação: verifica se o livro "Fundação" (b3) está entre as recomendações
        boolean containsFundacao = recommendations.stream()
                .anyMatch(rec -> rec.getId() == b3.getId());
        assertTrue("Espera que o livro 'Fundação' seja recomendado.", containsFundacao);

        // Impressão das recomendações para depuração
        recommendations.forEach(rec -> System.out
                .println("Item-based recommendation for user " + c1.getId() + ": Book ID " + rec.getId()));
    }

}
