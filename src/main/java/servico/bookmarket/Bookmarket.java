package servico.bookmarket;

import dominio.Address;
import dominio.Book;
import dominio.Cart;
import dominio.Customer;
import dominio.Order;
import dominio.Review;
import dominio.Stock;
import dominio.customer.enums.Type;
import servico.bookstore.Bookstore;
import servico.bookmarket.exceptions.UmbrellaException;
import servico.bookmarket.statemachine.StateMachine;
import servico.bookmarket.statemachine.actions.BookstoreAction;
import servico.bookmarket.statemachine.actions.books.GetMinimumBookPriceAction;
import servico.bookmarket.statemachine.actions.books.GetBookPriceAverageAction;
import servico.bookmarket.statemachine.actions.books.UpdateBookAction;
import servico.bookmarket.statemachine.actions.carts.CartUpdateAction;
import servico.bookmarket.statemachine.actions.carts.CreateCartAction;
import servico.bookmarket.statemachine.actions.carts.GetCartByCustomer;
import servico.bookmarket.statemachine.actions.carts.GetCartById;
import servico.bookmarket.statemachine.actions.customers.CreateCustomerAction;
import servico.bookmarket.statemachine.actions.customers.RefreshCustomerSessionAction;
import servico.bookmarket.statemachine.actions.orders.ConfirmBuyAction;
import servico.bookmarket.statemachine.actions.reviews.ChangeReviewAction;
import servico.bookmarket.statemachine.actions.reviews.CreateReviewAction;
import servico.bookmarket.statemachine.actions.reviews.GetReviewByBookAction;
import servico.bookmarket.statemachine.actions.reviews.GetReviewByCustomerAction;
import servico.bookmarket.statemachine.actions.reviews.GetReviewByIdAction;
import servico.bookmarket.statemachine.actions.reviews.GetReviewsAction;
import servico.bookmarket.statemachine.actions.reviews.GetUniqueReviewsAction;
import servico.bookmarket.statemachine.actions.reviews.GetReviewsByBookstoreAction;
import servico.bookmarket.statemachine.actions.reviews.RemoveReviewsByIdAction;
import servico.bookmarket.statemachine.actions.recomendations.GetRecommendationByItensAction;
import servico.bookmarket.statemachine.actions.recomendations.GetRecommendationByUsersAction;
import servico.bookmarket.statemachine.actions.shared.PopulateAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import util.TPCW_Util;

/**
 * This class exists with final object of represent a bookmarket inside the system.
 * It is responsible for managing a list of {@linkplain Bookstore} that represents
 * existing bookmarkets. To use contained bookstores contained on bookmarket control,
 * {@linkplain Bookmarket} uses a state machine that is activated using a COMMAND
 * pattern variation. Each state of this machine is represented by one of the
 * registered ({@linkplain Bookstore}). The COMMAND pattern variation is based on top
 * of {@linkplain Action} interface. All executed actions by state machine implements
 *  this interface, making possible a better understanding e decouple of actions
 *  logic to be taken by the system, accoring it state.
 *
 * <p>
 * Each piece of logic beneath each condition is separated into a handler,
 * making possible a simple states machine creation and less verbose.
 *</p>
 *
 * <p>
 * An execution mechanism created for machine states allows a modularization for
 * praticaly all used models inside the system. To accomplish this feat, specific
 * models commands implements the interface {@linkplain Action} with a default method
 * and creates a abstraction called {@linkplain BookstoreAction}. This abstraction
 * defines a data type that is treated in your executions is a stream, making ease
 * the manipulation of inside lists of the actual system.
 * </p>
 */
public class Bookmarket {
    private static Random random;
    private static StateMachine stateMachine;

    /**
     * This method is used for class initialization and creates a Bookstores linked
     * with Bookmarket.
     *
     * @param state Array of created Bookstores to compose the state machine.
     */
    public static void init(Bookstore... state) {
        random = new Random();
        try {
            stateMachine = StateMachine.create(state);
        } catch (UmbrellaException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Bookstore> getBookstoreStream() {
        return stateMachine.getStateStream();
    }

    /**
     * This method is used to get customer by registered uname on Bookstore.
     *
     * @param UNAME Customer's uname identifier
     * @return Customer Retrieved customer on system registered list.
     */
    public static Customer getCustomer(String UNAME) {
        return Bookstore.getCustomer(UNAME).get();
    }

    /**
     * This method is used to get customer's complete name registered o Bookstore
     * by id. Returns a string array with complete name.
     *
     * @param c_id Id
     * @return String[] User's complete name: getName()[0] =  first name;
     *         getName()[1] = Last name; getName()[2] = Identifier name;
     */
    public static String[] getName(int c_id) {

        Customer customer = Bookstore.getCustomer(c_id).get();

        String name[] = new String[3];
        name[0] = customer.getFname();
        name[1] = customer.getLname();
        name[2] = customer.getUname();
        return name;
    }

    /**
     * This method is used to get customer's username registered on Bookstore
     * by id. Returns a username string.
     *
     * @param C_ID ID
     * @return String customer's string name
     */
    public static String getUserName(int C_ID) {
        return Bookstore.getCustomer(C_ID).get().getUname();
    }

    /**
     * This method gets customer's password registered on Bookstore by username
     * Returns password string
     *
     * @param C_UNAME User name
     * @return String Specified user's password
     */
    public static String getPassword(String C_UNAME) {
        return Bookstore.getCustomer(C_UNAME).get().getPasswd();

    }

    /**
     * This method is used to gets most recent order by the customer on
     * Bookstore by username. Returns order's object.
     *
     * @param c_uname User's name
     * @return Order Customer's recent order
     */
    public static Order getMostRecentOrder(String c_uname) {
        return Bookstore.getCustomer(c_uname).get().getMostRecentOrder();
    }

    /**
     * This methods is used to register a new customer on Bookstore. The discount
     * is randomly generated. It is executed by CreationCustomerAction state for
     * customer creation. If an error occurs during creation, it is generated a
     * RuntimeExpection.
     *
     * @param fname       Customer's first name
     * @param lname       Customer's last name
     * @param street1     Address 1
     * @param street2     Address 2
     * @param city        Customer's city
     * @param state       Customer's state
     * @param zip         Zip code
     * @param countryName Country's name
     * @param phone       Customer's telephone
     * @param email       Customer's email
     * @param birthdate   Customer's birthday date
     * @param data        Customer's data
     * @param type 		  Customer's type
     * @return Customer New customer registered on platform
     */
    private static Customer createNewCustomer(String fname, String lname,
            String street1, String street2, String city, String state,
            String zip, String countryName, String phone, String email,
            Date birthdate, String data, dominio.customer.enums.Type type) {
        double discount = (int) (Math.random() * 51);
        long now = System.currentTimeMillis();
        try {
            return (Customer) stateMachine.execute(new CreateCustomerAction(
                    fname, lname, street1, street2, city, state, zip,
                    countryName, phone, email, discount, birthdate, data, now, type));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Customer createNewSubscriber(String fname, String lname,
            String street1, String street2, String city, String state,
            String zip, String countryName, String phone, String email,
            Date birthdate, String data) {
    	return createNewCustomer(fname, lname, street1, street2, city, state, zip, countryName, phone, email, birthdate, data, Type.SUBSCRIBER);
    	
    }
    public static Customer createNewCustomer(String fname, String lname,
            String street1, String street2, String city, String state,
            String zip, String countryName, String phone, String email,
            Date birthdate, String data) {
    	return createNewCustomer(fname, lname, street1, street2, city, state, zip, countryName, phone, email, birthdate, data, Type.DEFAULT);
    	
    }

    public static Review createReview(int bookstoreId, Customer customer, Book book, double value) {
        try {
            return (Review) stateMachine.execute(new CreateReviewAction(customer, book, value, bookstoreId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Review> getReviews() {
        return (List<Review>) stateMachine.execute(new GetReviewsAction());
    }

    @SuppressWarnings("unchecked")
    public static List<Review> getUniqueReviews() {
        return (List<Review>) stateMachine.execute(new GetUniqueReviewsAction());
    }

    @SuppressWarnings("unchecked")
    public static Optional<Review> getReviewById(int bookstoreId, int id) {
        return (Optional<Review>) stateMachine.execute(new GetReviewByIdAction(id, bookstoreId));
    }

    @SuppressWarnings("unchecked")
    public static List<Review> getReviewsByBook(int bookstoreId, Book book) {
        return (List<Review>) stateMachine.execute(new GetReviewByBookAction(book, bookstoreId));
    }

    @SuppressWarnings("unchecked")
    public static List<Review> getReviewsByCustomer(int bookstoreId, Customer customer) {
        return (List<Review>) stateMachine.execute(new GetReviewByCustomerAction(customer, bookstoreId));
    }

    public static boolean removeReviewById(int bookstoreId, int id) {
        return (boolean) stateMachine.execute(new RemoveReviewsByIdAction(id, bookstoreId));
    }

    public static boolean changeReviewValue(int bookstoreId, int id, double value) {
        return (boolean) stateMachine.execute(new ChangeReviewAction(id, value, bookstoreId));
    }

    @SuppressWarnings("unchecked")
    public static List<Review> getReviewsByBookstore(int bookstoreId) {
        return (List<Review>) stateMachine.execute(new GetReviewsByBookstoreAction(bookstoreId));
    }

    /**
     * This method executes a state machine RefreshCustomerSessionAction,
     * passing as parameter id and customer registered on Bookstore, and actual
     * date, in milliseconds. This state executes the internal Bookstore method,
     * that will renew customer session in two hours.
     *
     * @param cId Session Id
     */
    public static void refreshSession(int cId) {
        try {
            stateMachine.execute(new RefreshCustomerSessionAction(cId,
                    System.currentTimeMillis()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Specific book by id getter.
     *
     * @param i_id Desired book id
     * @return Retrieved book inside the platform
     */
    public static Book getBook(int i_id) {

        return Bookstore.getBook(i_id).get();

    }

    /**
     * Random book through {@linkplain Bookstore} getter.
     *
     * @return Any book registered within the platform.
     */
    public static Book getABookAnyBook() {
        return Bookstore.getABookAnyBook(random);

    }

    /**
     * This method is used to return a list of filtered books by a parameter of
     * String type represented by book subject.
     *
     * @param search_key Book subject for filter search.
     * @return A list of books that serve filter by subject criteria.
     */
    public static List<Book> doSubjectSearch(String search_key) {
        return Bookstore.getBooksBySubject(search_key);
    }

    /**
     * This method used to return a list of books filtered through String type parameter,
     * representing book's title.
     *
     * @param search_key Book title to filter search.
     * @return A list of book that meet criteria filter by title.
     */
    public static List<Book> doTitleSearch(String search_key) {
        return Bookstore.getBooksByTitle(search_key);
    }

    /**
     * This method returns a list of filtered books through String type
     * parameter represented by book's author.
     *
     * @param search_key Book's author to filter search.
     * @return A list of books that accomplish filter criteria by author.
     */
    public static List<Book> doAuthorSearch(String search_key) {
        return Bookstore.getBooksByAuthor(search_key);
    }

    /**
     * A list of books that publication date is from most recent to most old,
     * filtering by subject getter.
     *
     * @param subject Book subject to search filter.
     * @return A list of books with most recent publication date.
     */
    public static List<Book> getNewProducts(String subject) {
        return Bookstore.getNewBooks(subject);
    }

    /**
     * All prices determined by a book, passed by parameter, for each
     * {@linkplain Bookstore} existing on {@linkplain Bookmarket} getter.
     *
     * @param book A book that will be used on search filter.
     * @return A list of prices, determined by book inside Bookmarket.
     */
    public static List<Double> getCosts(Book book) {
                return getBookstoreStream().map(store -> store.getStock(book.getId())).map(stock -> (Double) (stock == null ? 0.0 : stock.getCost()))

                .collect(Collectors.toList());
    }

    /**
     * Returns an ordered list of most sold books from a total orders map.
     * The ordering is done by descending order quantity order.
     *
     * @param salesByBook Map containing total quantity of orders, of each book.
     * @param limit Books to be returned quantity.
     * @return A list of most sold books, limited by limit parameter.
     */
    public static List<Book>  sortBooksBySalesDescending(HashMap<Book, Integer> salesByBook, int limit) {
        List<Book> topBooks = salesByBook.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Ordena por vendas (descendente)
                .limit(limit) // N firsts filter
                .map(Map.Entry::getKey) // Extracts only Book objects.
                .collect(Collectors.toList());
        return topBooks;
    }

    /**
     * Identifies most sold books considering all bookstores in the system.
     *
     * @param numberOfBooks Returned books to be returned quantity (1 to 100).
     * @return A list of most sold books, ordered by descending number of orders.
     * @throws RuntimeException If numberOfBooks is out of valid interval (1-100).
     */
    public static List<Book> getBestSellers(Integer numberOfBooks) {
        final Integer MINIMUM_BOOKS = 1;
        final Integer MAXIMUM_BOOKS = 100;

        if (numberOfBooks < MINIMUM_BOOKS || numberOfBooks > MAXIMUM_BOOKS) {
            throw new RuntimeException("Requested numbers need to be between 1 to 100.");
        }
        // Combine orders of all bookstores in a single map
        HashMap<Book, Integer> consolidatedBookSales = stateMachine.getStateStream()
                .map(Bookstore::getConsolidatedBookSales)
                .reduce(new HashMap<>(), (map1, map2) -> {
                    map2.forEach((book, qty) -> map1.put(book, map1.getOrDefault(book, 0) + qty));
                    return map1;
                });
        List<Book> sortBooksBySalesDescending = sortBooksBySalesDescending(consolidatedBookSales, numberOfBooks);

        return sortBooksBySalesDescending;
    }

    /**
     * Consolidate order total of all books in all system's bookstores.
     * Combine orders counters of each bookstore in an unique aggregate map.
     *
     * @return Map where book is the key and value is order quantity total, considering
     *         all bookstores.
     */
    public static HashMap<Book, Integer> getConsolidatedBookSales() {
        return stateMachine.getStateStream()
                .map(Bookstore::getConsolidatedBookSales)
                .reduce(new HashMap<>(), (accumulatedSales, currentStoreSales) -> {
                    currentStoreSales.forEach((book, quantity) -> accumulatedSales.put(book,
                            accumulatedSales.getOrDefault(book, 0) + quantity));
                    return accumulatedSales;
                });
    }

    /**
     * Books recommendations based on previous rating items by the customer.
     * 
     * @param c_id Unique identifier of customer, that recommendations will
     *             be generated.
     * @return A list of 5 recommended books based on iteration customer history.
     */
    @SuppressWarnings("unchecked")
    public static List<Book> getRecommendationByItens(int c_id) {
        return (List<Book>) stateMachine.execute(new GetRecommendationByItensAction(c_id, 5));
    }

    /**
     * Books recommendation based on similar profiles getter.
     * 
     * @param c_id Unique identifier of customer, for which recommendation will be generated.
     * @return A list containing 5 recommend books based on customers profile similarities.
     */
    @SuppressWarnings("unchecked")
    public static List<Book> getRecommendationByUsers(int c_id) {
        return (List<Book>) stateMachine.execute(new GetRecommendationByUsersAction(c_id, 5));
    }

    /**
     * Book availability on {@linkplain Bookstore} list of {@linkplain Bookmarket},
     * based on book identification parameter getter.
     *
     * @param idBook Id that represents a book to be consulted.
     * @return List
     */
    public static List<Stock> getStocks(final int idBook) {
        return getBookstoreStream()
                .filter(store -> store.getStock(idBook) != null)
                // transforma o stream de bookstore em stream de stock
                .map(store -> store.getStock(idBook))
                .collect(Collectors.toList());
    }

    /**
     * Available book of a specific {@linkplain Bookstore} getter.
     *
     * @param idBookstore
     * @param idBook
     * @return
     */
    public static Stock getStock(final int idBookstore, final int idBook) {
        return getBookstoreStream()
                .filter(store -> store.getId() == idBookstore)
                // transforma o stream de bookstore em stream de stock
                .map(store -> store.getStock(idBook))
                .findFirst()
                .get();
    }

    /**
     * A list of books related to a specific book getter.
     *
     * @param i_id
     * @return
     */
    public static List<Book> getRelated(int i_id) {
        Book book = Bookstore.getBook(i_id).get();
        ArrayList<Book> related = new ArrayList<>(5);
        related.add(book.getRelated1());
        related.add(book.getRelated2());
        related.add(book.getRelated3());
        related.add(book.getRelated4());
        related.add(book.getRelated5());
        return related;
    }

    /**
     * Updates data on specific book.
     *
     * @param iId
     * @param cost
     * @param image
     * @param thumbnail
     */
    public static void adminUpdate(int iId, double cost, String image,
            String thumbnail) {
        try {
            stateMachine.execute(new UpdateBookAction(iId, cost, image,
                    thumbnail, System.currentTimeMillis()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an empty shopping cart linked with a determined {@linkplain Bookstore}.
     *
     * @param storeId
     * @param customerId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static int createEmptyCart(int storeId, int customerId) {
        try {
            return ((Optional<Cart>) stateMachine.execute(new CreateCartAction(storeId,
                    System.currentTimeMillis(), customerId))).get().getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method executes a shopping cart update. If cart is empty, adds any book
     * to shopping cart.
     *
     * @param storeId
     * @param SHOPPING_ID
     * @param I_ID
     * @param ids
     * @param quantities
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Optional<Cart> doCart(int storeId, int SHOPPING_ID, Integer I_ID, List<Integer> ids,
            List<Integer> quantities) {
        try {
        	Optional<Cart> cart = (Optional<Cart>) stateMachine.execute(new CartUpdateAction(storeId,
                    SHOPPING_ID, I_ID, ids, quantities,
                    System.currentTimeMillis()));
            if (cart.get().getLines().isEmpty()) {
                Book book = getExistingBookInAStock(storeId);
                cart = (Optional<Cart>) stateMachine.execute(new CartUpdateAction(storeId,
                        SHOPPING_ID, book.getId(), new ArrayList<>(),
                        new ArrayList<>(), System.currentTimeMillis()));
            }
            return cart;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Book getExistingBookInAStock(int storeId) {
        Bookstore bookstoreInstance = getBookstoreStream().filter(bk -> bk.getId() == storeId)
                .collect(Collectors.toList())
                .get(0);

        return bookstoreInstance.getStocks().get(0).getBook();
    }

    /**
     * Specified shopping cart of {@linkplain Bookstore} getter.
     *
     * @param SHOPPING_ID
     * @param storeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Optional<Cart> getCart(int storeId, int SHOPPING_ID) {
    	return (Optional<Cart>)stateMachine.execute(new GetCartById(storeId, SHOPPING_ID));
    }
    
    /**
     * Get a shopping cart of a customer getter.
     *
     * @param storeId Bookstore id, used to get shopping cart.
     * @param customerId Customer's id.
     */
    @SuppressWarnings("unchecked")
    public static Optional<Cart> getCartByCustomer(int storeId, int customerId){
    	return (Optional<Cart>)stateMachine.execute(new GetCartByCustomer(storeId, customerId));
    }

    /**
     * This method executes a ConfirmBuyAction of state machine for order
     * confirmation.
     *
     * @param storeId
     * @param shopping_id
     * @param customer_id
     * @param cc_type
     * @param cc_number
     * @param cc_name
     * @param cc_expiry
     * @param shipping
     * @return
     */
    public static Order doBuyConfirm(int storeId, int shopping_id, int customer_id,
            String cc_type, long cc_number, String cc_name, Date cc_expiry,
            String shipping) {
        long now = System.currentTimeMillis();
        try {
            return (Order) stateMachine.execute(new ConfirmBuyAction(storeId,
                    customer_id, shopping_id, randomComment(),
                    cc_type, cc_number, cc_name, cc_expiry, shipping,
                    randomShippingDate(now), -1, now));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method executes an action ConfirmBuyAction of a state machine
     * for order confirmation.
     *
     * @param storeId
     * @param shopping_id
     * @param customer_id
     * @param cc_type
     * @param cc_number
     * @param cc_name
     * @param cc_expiry
     * @param shipping
     * @param street_1
     * @param street_2
     * @param city
     * @param state
     * @param zip
     * @param country
     * @return
     */
    public static Order doBuyConfirm(int storeId, int shopping_id, int customer_id,
            String cc_type, long cc_number, String cc_name, Date cc_expiry,
            String shipping, String street_1, String street_2, String city,
            String state, String zip, String country) {
        Address address = Bookstore.alwaysGetAddress(street_1, street_2,
                city, state, zip, country);
        long now = System.currentTimeMillis();
        try {
            return (Order) stateMachine.execute(new ConfirmBuyAction(storeId,
                    customer_id, shopping_id, randomComment(),
                    cc_type, cc_number, cc_name, cc_expiry, shipping,
                    randomShippingDate(now), address.getId(), now));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Average price on all bookstores getter
     *
     * @param bookId
     * @return An average price on all bookstores
     */
    public static double getBookPriceAverage(int bookId) {
    	return (double) stateMachine.execute(new GetBookPriceAverageAction(bookId));
    }

    private static String randomComment() {
        return TPCW_Util.getRandomString(random, 20, 100);
    }

    private static Date randomShippingDate(long now) {
        return new Date(now + 86400000 /* a day */ * (random.nextInt(7) + 1));
    }
    
    /**
     * Lowest cost of a book determined on bookstores getter
     *
     * @param bookId
     * @return An Optional<Stock>, in case there is no book stock in no bookstore, informed
     * on Stock object, on which bookstore is found
     */
    @SuppressWarnings("unchecked")
    public static Optional<Stock> getMinimumBookPrice(int bookId) {
    	return (Optional<Stock>) stateMachine.execute(new GetMinimumBookPriceAction(bookId));
    }

    /**
     * This method executes a PopulateAction on state machine for data addition
     * on {@linkplain Bookstore}.
     *
     * @param items
     * @param customers
     * @param addresses
     * @param authors
     * @param orders
     * @return
     */
    public static boolean populate(int items, int customers, int addresses,
            int authors, int orders) {
        try {
            return (Boolean) stateMachine.execute(new PopulateAction(random.nextLong(),
                    System.currentTimeMillis(), items, customers, addresses,
                    authors, orders));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
