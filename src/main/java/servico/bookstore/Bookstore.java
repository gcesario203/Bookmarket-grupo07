package servico.bookstore;

/* 
 * Bookstore.java - holds all the data and operations of the bookstore.
 *
 */
import dominio.Address;
import dominio.Author;
import dominio.Book;
import dominio.CCTransaction;
import dominio.Cart;
import dominio.Country;
import dominio.Customer;
import dominio.Order;
import dominio.OrderLine;
import dominio.Review;
import dominio.Stock;
import servico.bookstore.utils.BookstoreBookCounter;
import util.BookstoreConstants.Backing;
import util.BookstoreConstants.Subject;
import util.TPCW_Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static util.BookstoreConstants.*;

/**
 * Descrição da Arquitetura do Bookstore
 *
 * A classe Bookstore representa o backlog e o armazenamento da loja virtual,
 * pois de acordo com a sua arquitetura, a mesma está associada a todas as
 * classes de domínio e dentre seus métodos há aqueles que irão popular os
 * bancos de dados dessas classes, ou senão cadastrar / adicionar novos itens a
 * estas listas. Além de também proporcionar a correlação com o Bookmarket
 * quanto à consulta ou aquisição de livros e demais dados.
 *
 * Os parâmetros que serão populados são: Autor, Livro, Endereço, Cartão de
 * Crédito, Carrinho de Compras, País, Cliente, Pedido, e Estoque. Esses
 * parâmetros estão interligados através do Id do consumidor ou do Livro. Dessa
 * forma, quando métodos do tipo: getBestSellers, getNewBooks, getBooksByTitle
 * forem chamados, será possivel adquirir todos os dados necessários referente
 * ao que foi solicitado.
 *
 * Suas demais funcionalidades permitem cadastrar itens que não estão inclusos
 * nos bancos de dados atuais dos parâmetros, cadastrar novos clientes, mapear
 * diversas consultas que serão utilizadas posteriormente pela classe Bookmarket
 * e finalizar o pedido de compra, através da transação do cartão de crédito
 * cadastrado do cliente, e em seguida criar a ordem de envio do pedido, como
 * pode ser visualizado pelo método confirmBuy().
 *
 * Vale lembrar que os bancos de dados são populados inicialmente por itens
 * estabelecidos pelos métodos populateCountries, populateStocks,
 * populateAddresses, entre outros, e depois correlacionados entre si de maneira
 * randômica / aleatória, a fim de criar uma grande quantidade de diferentes
 * tipos de consumidores, livros, entre outros.
 *
 */
/**
 *
 */
public class Bookstore implements Serializable {

    private static final long serialVersionUID = -3099048826035606338L;

    private static boolean populated;
    private static final List<Country> countryById;
    private static final Map<String, Country> countryByName;
    private static final List<Address> addressById;
    private static final Map<Address, Address> addressByAll;
    private static final List<Customer> customersById;
    private static final Map<String, Customer> customersByUsername;
    private static final List<Author> authorsById;
    private static final List<Book> booksById;

    private final Map<Book, Stock> stockByBook;
    private final List<Cart> cartsById;
    private final List<Review> reviewsByIds;
    private final List<Order> ordersById;
    private final LinkedList<Order> ordersByCreation;
    private final int id;

    /**
     * Bloco static que executa a inicialização dos atriutos estáticos da
     * Bookstore
     */
    static {
        countryById = new ArrayList<>();
        countryByName = new HashMap<>();
        addressById = new ArrayList<>();
        addressByAll = new HashMap<>();
        customersById = new ArrayList<>();
        customersByUsername = new HashMap<>();
        authorsById = new ArrayList<>();
        booksById = new ArrayList<>();
    }

    /**
     * Construtor da classe Bookstore.Recebe como parâmetro o id da Bookstore.
     * Faz a inicialização dos atributos da instância (objeto) da Bookstore.
     *
     * @param id - o código da Bookstore
     */
    public Bookstore(final int id) {
        this.id = id;
        cartsById = new ArrayList<>();
        ordersById = new ArrayList<>();
        reviewsByIds = new ArrayList<>();
        ordersByCreation = new LinkedList<>();
        stockByBook = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * 
     * @return
     */
    public boolean isPopulated() {
        return populated;
    }

    /**
     * Busca por um objeto Country com o nome passado no seu argumento, se nao
     * encontrar, o Country eh criado sem currency (moeda) e sem com coeficiente
     * de equivalencia (exchange) igual a zero, e assim, sempre retornara o
     * Country buscado.
     *
     * @param name - o nome do país
     * @return um país com o nome definido por <code>name</code>
     */
    private static Country alwaysGetCountry(String name) {
        Country country = countryByName.get(name);
        if (country == null) {
            country = createCountry(name, "", 0);
        }
        return country;
    }

    /**
     * Busca por um objeto Country baseando-se num gerador de aleatorios do tipo
     * Random que deve ser instanciado pelo desenvolvedor que quiser utilizar o
     * metodo.
     * 
     * @param random Dado randômico / aleatório
     * @return countryById País do Id escolhido
     */
    private static Country getACountryAnyCountry(Random random) {
        return countryById.get(random.nextInt(countryById.size()));
    }

    /**
     * Cria e torna um Country, a partir do nome, moeda (currency) e taxa de
     * cambio (exchange), com o Id crescente em relacao ao conjunto de Country's
     * ja existentes.
     *
     *
     * @param name     Nome do país
     * @param currency Moeda corrente do país
     * @param exchange Taxa de câmbio
     * @return country país
     */
    private static Country createCountry(String name, String currency, double exchange) {
        int id = countryById.size();
        Country country = new Country(id, name, currency, exchange);
        countryById.add(country);
        countryByName.put(name, country);
        return country;
    }

    /**
     * Busca por um objeto Address (endereco) a partir de todos os argumentos
     * fornecidos, porem utilzia id = 0 par ao objeto, se nao o encontrar, o
     * mesmo eh criado e entao retornado.
     *
     * 
     * @param street1     Rua/Avenida do endereço
     * @param street2     Rua/Avenida do endereço (complemento)
     * @param city        Cidade do endereço
     * @param state       Estado
     * @param zip         Código postal
     * @param countryName Nome do País
     * @return address Endereço
     */
    public static Address alwaysGetAddress(String street1, String street2,
            String city, String state, String zip, String countryName) {
        Country country = alwaysGetCountry(countryName);
        Address key = new Address(0, street1, street2, city, state, zip, country);
        Address address = addressByAll.get(key);
        if (address == null) {
            address = createAddress(street1, street2, city, state, zip,
                    country);
        }
        return address;
    }

    /**
     * Busca aleatoriamente por um objeto Address (endereco) a partir de um
     * objeto do tipo Random como gerador de aleatorios.
     *
     * @param random Dado randômico / aleatório
     * @return addressById Endereço do Id escolhido
     */
    private static Address getAnAddressAnyAddress(Random random) {
        return addressById.get(random.nextInt(addressById.size()));
    }

    /**
     * Este método cria um endereço novo através dos parâmetros: Rua/Avenida,
     * cidade, estado, código postal e país.
     *
     * @param street1 Rua/Avenida do endereço
     * @param street2 Rua/Avenida do endereço (complemento)
     * @param city    Cidade do endereço
     * @param state   Estado
     * @param zip     Código postal
     * @param country País
     * @return address Endereço
     */
    private static Address createAddress(String street1, String street2,
            String city, String state, String zip, Country country) {
        int id = addressById.size();
        Address address = new Address(id, street1, street2, city, state, zip,
                country);
        addressById.add(address);
        addressByAll.put(address, address);
        return address;
    }

    /**
     * Este método lê e retorna um consumidor.
     *
     * @param cId ID do Consumidor
     * @return customerById ID do Consumidor
     */
    public static Optional<Customer> getCustomer(int cId) {
        return customersById.stream().filter(c -> c.getId() == cId).findFirst();
    }

    /**
     *
     * @param username Nome do Usuário
     * @return customersByUsername Consumidor do Nome de Usuário
     */
    public static Optional<Customer> getCustomer(String username) {
        return Optional.ofNullable(customersByUsername.get(username));
    }

    /**
     * Este método retorna um consumidor de maneira randômica.
     *
     * @param random Dado randômico / aleatório
     * @return customersById Consumidor do Id escolhido
     */
    private Customer getACustomerAnyCustomer(Random random) {
        return customersById.get(random.nextInt(customersById.size()));
    }

    public Review createReview(Customer customer, Book book, double value) throws IOException {
        if (!customersById.contains(customer))
            throw new IOException("Cliente não cadastrado");

        if (!booksById.contains(book))
            throw new IOException("Livro não cadastrado");

        Review review = new Review(customer, book, value, this.id);

        reviewsByIds.add(review);

        return review;
    }

    public boolean changeReviewValue(int id, double value) throws IOException {
        Optional<Review> review = getReviewById(id);

        if (!review.isPresent())
            return false;

        review.get().setRating(value);

        return true;
    }

    public boolean removeReviewById(int id) {
        return getReviews().removeIf(r -> r.getId() == id);
    }

    public List<Review> getReviews() {
        return this.reviewsByIds;
    }

    public Optional<Review> getReviewById(int id) {
        return getReviews().stream().filter(r -> r.getId() == id).findFirst();
    }

    public List<Review> getReviewsByBook(Book book) {
        return getReviews().stream()
                .filter(r -> r.getBook().getId() == book.getId())
                .collect(Collectors.toList());
    }

    public List<Review> getReviewsByCustomer(Customer customer) {
        return getReviews().stream()
                .filter(r -> r.getCustomer().getId() == customer.getId())
                .collect(Collectors.toList());
    }

    /**
     * <pre>
     * Address address = alwaysGetAddress(street1, street2, city, state, zip,
     *         countryName);
     * return createCustomer(fname, lname, address, phone, email,
     *         new Date(now), new Date(now), new Date(now),
     *         new Date(now + 7200000), discount, birthdate,
     *         data);
     * </pre>
     *
     * Este método irá retornar o método de criação do controle do consumidor em
     * relação a data de cadastro, último acesso, login e tempo de expiração da
     * sessão.
     *
     * @param fname       Primeiro nome do consumidor
     * @param lname       Sobrenome / Último nome do consumidor
     * @param street1     Rua/Avenida do endereço
     * @param street2     Rua/Avenida do endereço (complemento)
     * @param city        Cidade do endereço
     * @param state       Estado
     * @param zip         Código postal
     * @param countryName Nome do País
     * @param phone       Telefone
     * @param email       E-mail
     * @param discount    Desconto
     * @param birthdate   Data de Nascimento
     * @param data        Dado referente ao Consumidor
     * @param now         Hora Atual
     * @param type		 Tipo do consumidor
     * @return O método createCustomer com os parâmetros: fname, lname, address,
     *         phone, email, since, lastVisit, login, expiration, discount,
     *         birthdate,
     *         data, type)
     */
    public static Customer createCustomer(String fname, String lname, String street1,
            String street2, String city, String state, String zip,
            String countryName, String phone, String email, double discount,
            Date birthdate, String data, long now, dominio.customer.enums.Type type) {
        Address address = alwaysGetAddress(street1, street2, city, state, zip,
                countryName);
        return createCustomer(fname, lname, address, phone, email,
                new Date(now), new Date(now), new Date(now),
                new Date(now + 7200000 /* 2 hours */), discount, birthdate,
                data, type);
    }

    /**
     * Create a customer and inser it on customer Id list and customer username
     * map.
     * 
     * <pre>
     * int id = customersById.size();
     * String uname = TPCW_Util.DigSyl(id, 0);
     * Customer customer = new Customer(id, uname, uname.toLowerCase(), fname,
     *         lname, phone, email, since, lastVisit, login, expiration,
     *         discount, 0, 0, birthdate, data, address);
     * customersById.add(customer);
     * customersByUsername.put(uname, customer);
     * return customer;
     * </pre>
     *
     * @param fname      Primeiro nome do Consumidor
     * @param lname      Sobrenome / Último nome do consumidor
     * @param address    Endereço do consumidor
     * @param phone      Telefone
     * @param email      Email
     * @param since      Data de Cadastro
     * @param lastVisit  Data do último acesso
     * @param login      Data de acesso ao login da sessão
     * @param expiration Data de expiração da sessão
     * @param discount   Desconto
     * @param birthdate  Data de Nascimento
     * @param data       Dado referente ao Consumidor
     * @param type		 Tipo do consumidor
     * @return customer Consumidor
     */
    private static Customer createCustomer(String fname, String lname, Address address,
            String phone, String email, Date since, Date lastVisit,
            Date login, Date expiration, double discount, Date birthdate,
            String data, dominio.customer.enums.Type type) {
        int id = customersById.size();
        String uname = TPCW_Util.DigSyl(id, 0);
        Customer customer = new Customer(id, uname, uname.toLowerCase(), fname,
                lname, phone, email, since, lastVisit, login, expiration,
                discount, 0, 0, birthdate, data, address, type);
        customersById.add(customer);
        customersByUsername.put(uname, customer);
        return customer;
    }

    /**
     * Updates the customer login expiring date aka refresh session.
     * 
     * <pre>
     * Customer customer = getCustomer(cId);
     * if (customer != null) {
     *     customer.setLogin(new Date(now));
     *     customer.setExpiration(new Date(now + 7200000));
     * }
     * </pre>
     *
     * @param cId Id do Consumidor
     * @param now Tempo / hora atual
     * @throws Exception 
     */
    public static void refreshCustomerSession(int cId, long now) throws Exception {
        Optional<Customer> customer = getCustomer(cId);
        
        if(customer.isEmpty())
        	throw new Exception("Customer not exists");
        
        customer.get().setLogin(new Date(now));
        customer.get().setExpiration(new Date(now + 7200000 /* 2 hours */));
    }

    /**
     * Este método retorna um autor de maneira randômica.
     *
     * @param random Dado randômico / aleatório
     * @return authorsById Autor do Id escolhido
     */
    private static Author getAnAuthorAnyAuthor(Random random) {
        return authorsById.get(random.nextInt(authorsById.size()));
    }

    /**
     * Este método cria um autor através dos parâmetros: nome completo (primeiro
     * nome, nome do meio, e último nome / sobrenome), data de nascimento, e
     * biografia do autor.
     *
     * @param fname     Primeiro nome do Autor
     * @param mname     Nome do meio do Autor
     * @param lname     Sobrenome / Último nome do Autor
     * @param birthdate Data de Nascimento
     * @param bio       Biografia do Autor
     * @return author Autor
     */
    private static Author createAuthor(String fname, String mname, String lname,
            Date birthdate, String bio) {
        Author author = new Author(fname, mname, lname, birthdate, bio);
        authorsById.add(author);
        return author;
    }

    /**
     * Este método é utilizado para recuperar um livro com base em seu índice de
     * inserção
     *
     * @param bId id que representa o índice do book no sistema
     * @return retorna optional do livro correspondente ao índice
     */
    public static Optional<Book> getBook(int bId) {
        return Optional.ofNullable(booksById.get(bId));
    }

    /**
     * Returns a list of recommeded books based on Users
     *
     * @param c_id Customer id
     * @return Não implementado, Null
     */
    public static List<Book> getRecommendationByItens(int c_id) {
        return null;
    }

    /**
     *
     * @param c_id
     * @return
     */
    public static List<Book> getRecommendationByUsers(int c_id) {
        return null;
    }

    /**
     * Randomly rturns a book based on a Random object as the random engine
     *
     * @param random - um valor random
     * @return uma instância de <code>Book</code>
     */
    public static Book getABookAnyBook(Random random) {
        return booksById.get(random.nextInt(booksById.size()));
    }

    /**
     * <pre>
     * ArrayList&lt;Book&gt; books = new ArrayList&lt;&gt;();
     * for (Book book : booksById) {
     *     if (subject.equals(book.getSubject())) {
     *         books.add(book);
     *         if (books.size() &gt; 50) {
     *             break;
     *         }
     *     }
     * }
     * Collections.sort(books, (Book a, Book b) -&gt; a.getTitle().compareTo(b.getTitle()));
     * return books;
     * </pre>
     *
     * @param subject - o assunto do livro
     * @return uma lista de livros
     */
    public static List<Book> getBooksBySubject(String subject) {
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : booksById) {
            if (subject.equals(book.getSubject())) {
                books.add(book);
                if (books.size() > 50) {
                    break;
                }
            }
        }
        Collections.sort(books, (Book a, Book b) -> a.getTitle().compareTo(b.getTitle()));
        return books;
    }

    /**
     * Returns a sorted list of Books which contains the title argument as its
     * own title or a subpart of the title with a maximum size of 51 Books.
     *
     * @param title - o título do livro
     * @return uma lista de livros
     */
    public static List<Book> getBooksByTitle(String title) {
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : booksById) {
            if (book.getTitle().startsWith(title)) {
                books.add(book);
                if (books.size() > 50) {
                    break;
                }
            }
        }
        books.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));
        return books;
    }

    /**
     * Returns a list of books that were written by an specific author.
     *
     * @param author - o autor do livro
     * @return uma lista de livros
     */
    public static List<Book> getBooksByAuthor(String author) {
        Pattern regex = Pattern.compile("^" + author);
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : booksById) {
            if (regex.matcher(book.getAuthor().getLname()).matches()) {
                books.add(book);
                if (books.size() > 50) {
                    break;
                }
            }
        }
        Collections.sort(books, (Book a, Book b) -> a.getTitle().compareTo(b.getTitle()));
        return books;
    }

    /**
     * Retorna os 50 livros mais recentes (PubDate) por assunto
     *
     * @param subject - o assunto do livro
     * @return uma lista de livros
     */
    public static List<Book> getNewBooks(String subject) {
        ArrayList<Book> books = new ArrayList<>();
        for (Book book : booksById) {
            if (subject.equals(book.getSubject())) {
                books.add(book);
            }
        }
        Collections.sort(books, new Comparator<Book>() {
            public int compare(Book a, Book b) {
                int result = b.getPubDate().compareTo(a.getPubDate());
                if (result == 0) {
                    result = a.getTitle().compareTo(b.getTitle());
                }
                return result;
            }
        });
        return books.subList(0, books.size() >= 50 ? 50 : books.size());
    }

    /**
     * Calcula o total de vendas de cada livro nesta livraria.
     * Processa todos os pedidos e suas linhas para contabilizar a quantidade
     * total vendida de cada livro.
     *
     * @return Mapa onde a chave é o livro e o valor é a quantidade total vendida nesta livraria
     */
    public HashMap<Book, Integer> getConsolidatedBookSales() {
        HashMap<Book, Integer> salesByBook = new HashMap<Book, Integer>();

        for (Order order : ordersById) {
            for (OrderLine line : order.getLines()) {
                Book book = line.getBook();
                Integer qtd = line.getQty();
                if (salesByBook.containsKey(book)) {
                    salesByBook.put(book, salesByBook.get(book) + qtd);
                } else {
                    salesByBook.put(book, qtd);
                }
            }

        }
        return salesByBook;
    }

    /**
     * Returns a list of Orders.
     *
     * @return a list of Orders
     */
    public List<Order> getOrdersById() {
        return ordersById;
    }

    /**
     *
     * @param subject
     * @return
     */

    private static Book createBook(String title, Date pubDate, String publisher,
            String subject, String desc, String thumbnail,
            String image, double srp, Date avail, String isbn,
            int page, String backing, String dimensions, Author author) {
        int id = booksById.size();
        Book book = new Book(id, title, pubDate, publisher, subject, desc,
                thumbnail, image, srp, avail, isbn, page, backing,
                dimensions, author);
        booksById.add(book);
        return book;
    }

    /**
     * Updates Book image path and Thumbnail path of a Book
     *
     * @param bId       - Book Id
     * @param image     - Image path
     * @param thumbnail - thumbnail
     * @param now       - Publication Date.
     */
    public static void updateBook(int bId, String image,
            String thumbnail, long now) {
        Book book = getBook(bId).get();
        book.setImage(image);
        book.setThumbnail(thumbnail);
        book.setPubDate(new Date(now));
    }

    /**
     * Upadtes the stock of a Book based on Book Id and updates its cost.
     *
     * @param bId  - Book Id
     * @param cost - o custo
     */
    public void updateStock(int bId, double cost) {
        Book book = getBook(bId).get();
        if (!stockByBook.containsKey(book)) {
            int stock = TPCW_Util.getRandomInt(rand, 10, 30);
            stockByBook.put(book, new Stock(this.id, book, cost, stock));
        }
        stockByBook.get(book).setCost(cost);
    }

    /**
     * Returns the stock of a Book based on Book Id.
     *
     * @param bId - Book Id
     * @return uma instância de <code>Stock</code>
     */
    public Stock getStock(int bId) {
        final Book book = getBook(bId).get();
        final Stock stock = stockByBook.get(book);
        return stock;
    }

    public List<Stock> getStocks() {
        return new ArrayList<>(stockByBook.values());
    }
    
    /**
     * Updates the related books recommendations for a given book based on customer purchase history.
     * The recommendations are generated by analyzing the last 10,000 orders and identifying
     * books frequently purchased together with the target book.
     *
     * @param targetBook the book for which to update related recommendations
     */
    public void updateRelatedBooks(Book targetBook) {
        Set<Integer> clientIds = getClientIdsWhoBoughtTargetBook(targetBook);

        Map<Integer, BookstoreBookCounter> purchaseFrequency = getPurchaseFrequency(clientIds, targetBook);
        Book[] topRelatedBooks = getTopFiveRelatedBooks(purchaseFrequency, targetBook);
        setRelatedBooks(targetBook, topRelatedBooks);
    }

     /**
     * Retrieves the IDs of customers who purchased the specified book in the last 10,000 orders.
     *
     * @param targetBook the book to search for in customer orders
     * @return a set of customer IDs who purchased the target book
     */
    private Set<Integer> getClientIdsWhoBoughtTargetBook(Book targetBook) {
        Set<Integer> clientIds = new HashSet<>();
        Iterator<Order> orderIterator = ordersByCreation.iterator();

        int orderOldLimit = 10000;
        for (int orderCount = 0; orderIterator.hasNext() && orderCount < orderOldLimit; orderCount++) {
            Order order = orderIterator.next();
            order.getLines().stream()
                .filter(line -> line.getBook().getId() == targetBook.getId())
                .findFirst()
                .ifPresent(line -> clientIds.add(order.getCustomer().getId()));
        }

        return clientIds;
    }

     /**
     * Counts the frequency of books purchased by the specified customers, excluding the target book.
     *
     * @param clientIds set of customer IDs to analyze purchases for
     * @param targetBook the book to exclude from the counting
     * @return a map of book IDs to their purchase frequency counters
     */
    private Map<Integer, BookstoreBookCounter> getPurchaseFrequency(Set<Integer> clientIds, Book targetBook) {
        Map<Integer, BookstoreBookCounter> purchaseFrequency = new HashMap<>();

        ordersByCreation.stream()
            .filter(order -> clientIds.contains(order.getCustomer().getId()))
            .forEach(order -> {
                order.getLines().forEach(line -> {
                    Book book = line.getBook();
                    if (book.getId() != targetBook.getId()) {
                        purchaseFrequency.computeIfAbsent(book.getId(), id -> new BookstoreBookCounter(book))
                                .addQuantity(line.getQty()); //conta os outros livros comprados
                    }
                });
            });

        return purchaseFrequency;
    }

    /**
     * Identifies the top five most frequently purchased books from the frequency map.
     * If fewer than five related books are found, the remaining slots are filled with the target book.
     *
     * @param purchaseFrequency map of book purchase frequencies
     * @param targetBook the original book used to fill empty slots if necessary
     * @return an array of the top five related books
     */
    private Book[] getTopFiveRelatedBooks(Map<Integer, BookstoreBookCounter> purchaseFrequency, Book targetBook) {
        // Ordena os livros mais vendidos e pega os cinco primeiros
        List<BookstoreBookCounter> sortedFrequencies = purchaseFrequency.values().stream()
            .sorted(Comparator.comparingInt(BookstoreBookCounter::getCount).reversed())
            .collect(Collectors.toList()); // Ordena os livros mais vendidos e pega os cinco primeiro

       int top = 5;
        Book[] relatedBooks = new Book[top];
        for (int i = 0; i < Math.min(top, sortedFrequencies.size()); i++) {
            relatedBooks[i] = sortedFrequencies.get(i).getBook();
        }
        Arrays.fill(relatedBooks, relatedBooks.length, top, targetBook);

        return relatedBooks;
    }

    /**
     * Sets the five related book references in the target book.
     *
     * @param targetBook the book to update with related recommendations
     * @param relatedBooks array of five books to set as related items
     */
    private void setRelatedBooks(Book targetBook, Book[] relatedBooks) {
        targetBook.setRelated1(relatedBooks[0]);
        targetBook.setRelated2(relatedBooks[1]);
        targetBook.setRelated3(relatedBooks[2]);
        targetBook.setRelated4(relatedBooks[3]);
        targetBook.setRelated5(relatedBooks[4]);
    }

    /**
     * Returns the Shopping cart.
     *
     * @param id - Cart Id.
     * @return uma instância de <code>Cart</code>
     */
    public Optional<Cart> getCart(int id) {
        return cartsById.stream().filter(x -> x.getId() == id).findFirst();
    }

    /**
     * Creates a Shopping cart
     *
     * @param customerId - customer id
     * @param now - Date from now.
     * @return uma instância de <code>Cart</code>
     */
    public Optional<Cart> createCart(int customerId, long now) {
    	Optional<Customer> customer = getCustomer(customerId);
    	
    	if(customer.isEmpty())
    		return Optional.empty();
    	
    	Optional<Cart> createdCart = getCartByCustomer(customerId);
    	if(createdCart.isEmpty()) {
            int idCart = cartsById.size();
            Cart cart = new Cart(idCart, new Date(now), customer.get(), this.getId());
            cartsById.add(cart);
            
            return Optional.of(cart);	
    	}
    	
    	return createdCart;
    }
    
    /**
     * Metodo utilizado para buscar um carrinho de um cliente
     *
     * @param customerId Id do cliente
     */
    public Optional<Cart> getCartByCustomer(int customerId){
    	
    	return cartsById.stream()
				.filter(cart -> cart.getCustomer().getId() == customerId)
				.findFirst();
    }
   
    /**
     * Update shopping cart for one book or many books.
     *
     * @param cId        - cart Id
     * @param bId        - book Id
     * @param bIds       - books Id
     * @param quantities - quantities if many books.
     * @param now        - Date from now.
     * @return uma instância de <code>Cart</code>
     */
    public Optional<Cart> cartUpdate(int cId, Integer bId, List<Integer> bIds,
            List<Integer> quantities, long now) {
        Optional<Cart> cart = getCart(cId);
        
        if(cart.isEmpty())
        	return Optional.empty();
        
        if (bId != null) {
            cart.get().increaseLine(stockByBook.get(getBook(bId).get()), getBook(bId).get(), 1);
        }

        if ((bIds != null && bIds.size() > 0) && (quantities != null && quantities.size() > 0)) {
            for (int i = 0; i < bIds.size(); i++) {
                cart.get().changeLine(stockByBook.get(getBook(bId).get()), booksById.get(bIds.get(i)), quantities.get(i));
            }
        }

        cart.get().setTime(new Date(now));

        return cart;
    }

    /**
     * Este método irá finalizar a ordem do pedido de compra através dos
     * parâmetros: consumidor, dados do carrinho de compras, do cartão de
     * crédito e do envio do produto.
     *
     * O método irá finalizar o pedido, enviar a transferência do cartão de
     * crédito e criar uma ordem para que o produto seja enviado.
     *
     * @param customerId   ID do Consumidor
     * @param cartId       ID do Carrinho de Compras
     * @param comment      Comentário
     * @param ccType       Tipo de Cartão de Crédito
     * @param ccNumber     Número do Cartão de Crédito
     * @param ccName       Name do titular do Cartão de Crédito
     * @param ccExpiry     Data de Expiração do Cartão de Crédito
     * @param shipping     Remessa do Pedido
     * @param shippingDate Data de Envio
     * @param addressId    Endereço do Consumidor
     * @param now          Tempo / hora atual
     * @return O método createOrder com os parâmetros: Consumidor, Data atual,
     *         carrinho de compras, comentários, dados para envio da ordem, e a
     *         transação do cartão.
     */
    public Order confirmBuy(int customerId, int cartId, String comment,
            String ccType, long ccNumber, String ccName, Date ccExpiry,
            String shipping, Date shippingDate, int addressId, long now) {
        Customer customer = getCustomer(customerId).get();
        Cart cart = getCart(cartId).get();
        Address shippingAddress = customer.getAddress();
        if (addressId != -1) {
            shippingAddress = addressById.get(addressId);
        }
        cart.getLines().stream().map((cartLine) -> {
            Book book = cartLine.getBook();
            stockByBook.get(book).addQty(-cartLine.getQty());
            return book;
        }).filter((book) -> (stockByBook.get(book).getQty() < 10)).forEachOrdered((book) -> {
            stockByBook.get(book).addQty(21);
        });
        CCTransaction ccTransact = new CCTransaction(ccType, ccNumber, ccName,
                ccExpiry, "", cart.total(customer.getDiscount()),
                new Date(now), shippingAddress.getCountry());
        return createOrder(customer, new Date(now), cart, comment, shipping,
                shippingDate, "Pending", customer.getAddress(),
                shippingAddress, ccTransact);
    }
    
    public Optional<Customer> updateCustomerType(int customerId, dominio.customer.enums.Type type) {
    	Optional<Customer> customerToChange = getCustomer(customerId);
    	
    	if(customerToChange.isEmpty())
    		return Optional.empty();
    	
    	customerToChange.get().setType(type);
    	
    	return customerToChange;
    }

    private Order createOrder(Customer customer, Date date, Cart cart,
            String comment, String shipType, Date shipDate,
            String status, Address billingAddress, Address shippingAddress,
            CCTransaction cc) {
        int idOrder = ordersById.size();
        Order order = new Order(idOrder, customer, date, cart, comment, shipType,
                shipDate, status, billingAddress, shippingAddress, cc);
        ordersById.add(order);
        ordersByCreation.addFirst(order);
        customer.logOrder(order);
        cart.clear();
        return order;
    }

    private static Random rand;

    /**
     * Randomly populates Addresses, Customers, Authors and Books lists.
     *
     * @param seed      - um valor random
     * @param now       - data e hora atual
     * @param items     - a quantidde de itens
     * @param customers - a quantidade de clientes
     * @param addresses - a quantidade de enderços
     * @param authors   - a quantidade de autores
     * @return true always
     */
    public static boolean populate(long seed, long now, int items, int customers,
            int addresses, int authors) {
        if (items < 0 || customers < 0 || addresses < 0 || authors < 0)
            throw new RuntimeException("Parametros invalidos");

        if (populated) {
            return false;
        }
        rand = new Random(seed);
        populateCountries();
        populateAddresses(addresses, rand);
        populateCustomers(customers, rand, now);
        populateAuthorTable(authors, rand);
        populateBooks(items,rand);
        populateEvaluation(rand);
        populated = true;
        return true;
    }

    /**
     * Este método irá popular inicialmente quais países, taxas de câmbio, e
     * moedas correntes são possíveis de serem buscados e cadastrados junto ao
     * consumidor.
     */
    private static void populateCountries() {

        for (int i = 0; i < COUNTRIES.length; i++) {
            createCountry(COUNTRIES[i], CURRENCIES[i], EXCHANGES[i]);
        }

    }

    private static void populateAddresses(int number, Random rand) {
        System.out.print("Creating " + number + " addresses...");

        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }
            Country country = getACountryAnyCountry(rand);
            createAddress(
                    TPCW_Util.getRandomString(rand, 15, 40),
                    TPCW_Util.getRandomString(rand, 15, 40),
                    TPCW_Util.getRandomString(rand, 4, 30),
                    TPCW_Util.getRandomString(rand, 2, 20),
                    TPCW_Util.getRandomString(rand, 5, 10),
                    country);
        }

    }

    private static void populateCustomers(int number, Random rand, long now) {
        System.out.print("Creating " + number + " customers...");
        
        dominio.customer.enums.Type[] typeValues = dominio.customer.enums.Type.values();
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }
            Address address = getAnAddressAnyAddress(rand);
            long since = now - TPCW_Util.getRandomInt(rand, 1, 730) * 86400000 /* a day */;
            long lastLogin = since + TPCW_Util.getRandomInt(rand, 0, 60) * 86400000 /* a day */;
            createCustomer(
                    TPCW_Util.getRandomString(rand, 8, 15),
                    TPCW_Util.getRandomString(rand, 8, 15),
                    address,
                    TPCW_Util.getRandomString(rand, 9, 16),
                    TPCW_Util.getRandomString(rand, 2, 9) + "@"
                            + TPCW_Util.getRandomString(rand, 2, 9) + ".com",
                    new Date(since),
                    new Date(lastLogin),
                    new Date(now),
                    new Date(now + 7200000 /* 2 hours */),
                    rand.nextInt(51),
                    TPCW_Util.getRandomBirthdate(rand),
                    TPCW_Util.getRandomString(rand, 100, 500),
                    typeValues[new Random().nextInt(typeValues.length)]);
        }

    }

    private static void populateAuthorTable(int number, Random rand) {
        System.out.print("Creating " + number + " authors...");

        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }
            createAuthor(
                    TPCW_Util.getRandomString(rand, 3, 20),
                    TPCW_Util.getRandomString(rand, 1, 20),
                    TPCW_Util.getRandomLname(rand),
                    TPCW_Util.getRandomBirthdate(rand),
                    TPCW_Util.getRandomString(rand, 125, 500));
        }

    }

    private static void setRelatedBooks(int number, Random rand) {
        for (int i = 0; i < number; i++) {
            Book book = booksById.get(i);
            HashSet<Book> related = new HashSet<>();
            while (related.size() < 5) {
                Book relatedBook = getABookAnyBook(rand);
                if (relatedBook.getId() != i) {
                    related.add(relatedBook);
                }
            }
            Book[] relatedArray = related.toArray(new Book[0]);
            book.setRelated1(relatedArray[0]);
            book.setRelated2(relatedArray[1]);
            book.setRelated3(relatedArray[2]);
            book.setRelated4(relatedArray[3]);
            book.setRelated5(relatedArray[4]);
        }
    }

    public void publicPopulateBooks(int numberOfBooks) {
        populateBooks(numberOfBooks, new Random());
    }

    /**
     * Este método irá popular quais assuntos são possíveis de serem buscados
     * pelo consumidor.
     */
    private static void populateBooks(int number,Random rand) {
        System.out.print("Creating " + number + " books...");

        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }

            Author author = getAnAuthorAnyAuthor(rand);
            Date pubdate = TPCW_Util.getRandomPublishdate(rand);
            double srp = TPCW_Util.getRandomInt(rand, 100, 99999) / 100.0;
            Subject subject = Subject.values()[rand.nextInt(Subject.values().length)];
            String title = subject.name() + " " + TPCW_Util.getRandomString(rand, 14, 60);
            Backing backing = Backing.values()[rand.nextInt(Backing.values().length)];

            createBook(
                    title,
                    pubdate,
                    TPCW_Util.getRandomString(rand, 14, 60),
                    subject.name(),
                    TPCW_Util.getRandomString(rand, 100, 500),
                    "img" + i % 100 + "/thumb_" + i + ".gif",
                    "img" + i % 100 + "/image_" + i + ".gif",
                    srp,
                    new Date(pubdate.getTime() + TPCW_Util.getRandomInt(rand, 1, 30) * 86400000L),
                    TPCW_Util.getRandomString(rand, 13, 13),
                    TPCW_Util.getRandomInt(rand, 20, 9999),
                    backing.name(),
                    (TPCW_Util.getRandomInt(rand, 1, 9999) / 100.0) + "x"
                            + (TPCW_Util.getRandomInt(rand, 1, 9999) / 100.0) + "x"
                            + (TPCW_Util.getRandomInt(rand, 1, 9999) / 100.0),
                    author);
        }
        setRelatedBooks(number, rand);

    }

    public void populateInstanceBookstore(int number, Random rand, long now) {
        populateOrders(number, rand, now);
        populateStocks(number, rand, now);
        populateReviews(number, rand);
    }

    private void populateReviews(int number, Random rand) {
        if (number < 0)
            throw new RuntimeException("Parâmetros inválidos");

        System.out.print("Creating " + number + " reviews");

        for (int i = 0; i < number; i++) {
            try {
                createReview(getACustomerAnyCustomer(rand), getABookAnyBook(rand), (int) (Math.random() * 6));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void populateStocks(int number, Random rand, long now) {
        System.out.print("Creating " + number + " stocks...");
        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }
            int nBooks = TPCW_Util.getRandomInt(rand, 1, 5);
            for (int j = 0; j < nBooks; j++) {
                Book book = getABookAnyBook(rand);
                if (!stockByBook.containsKey(book)) {
                    double cost = TPCW_Util.getRandomInt(rand, 50, 100) / 100.0;
                    int quantity = TPCW_Util.getRandomInt(rand, 300, 400);
                    stockByBook.put(book, new Stock(this.id, book, cost, quantity));
                }
            }
        }
    }

    public void publicpopulateOrders(int numberOforders) {
        Date now = new Date();
        populateOrders(numberOforders, new Random(), now.getTime());
}

    private void populateOrders(int number, Random rand, long now) {
        System.out.print("Creating " + number + " orders...");

        for (int i = 0; i < number; i++) {
            if (i % 10000 == 0) {
                System.out.print(".");
            }
            
            Customer customer = getACustomerAnyCustomer(rand);
            
            int nBooks = TPCW_Util.getRandomInt(rand, 1, 5);
            Cart cart = createCart(customer.getId(), now).get();
            
            String comment = TPCW_Util.getRandomString(rand, 20, 100);

            for (int j = 0; j < nBooks; j++) {
                Book book = getABookAnyBook(rand);
                int quantity = TPCW_Util.getRandomInt(rand, 1, 300);

                if (!stockByBook.containsKey(book)) {
                    double cost = TPCW_Util.getRandomInt(rand, 50, 100) / 100.0;
                    int stock = TPCW_Util.getRandomInt(rand, 300, 400);
                    stockByBook.put(book, new Stock(this.id, book, cost, stock));
                }

                cart.changeLine(stockByBook.get(book), book, quantity);
            }

            CCTransaction ccTransact = new CCTransaction(
                    CREDIT_CARDS[rand.nextInt(CREDIT_CARDS.length)],
                    TPCW_Util.getRandomLong(rand, 1000000000000000L, 9999999999999999L),
                    TPCW_Util.getRandomString(rand, 14, 30),
                    new Date(now + TPCW_Util.getRandomInt(rand, 10, 730) * 86400000 /* a day */),
                    TPCW_Util.getRandomString(rand, 15, 15),
                    cart.total(customer.getDiscount()),
                    new Date(now),
                    getACountryAnyCountry(rand));

            long orderDate = now - TPCW_Util.getRandomInt(rand, 53, 60) * 86400000 /* a day */;
            long shipDate = orderDate + TPCW_Util.getRandomInt(rand, 0, 7) * 86400000 /* a day */;

            createOrder(
                    customer,
                    new Date(orderDate),
                    cart, comment,
                    SHIP_TYPES[rand.nextInt(SHIP_TYPES.length)],
                    new Date(shipDate),
                    STATUS_TYPES[rand.nextInt(STATUS_TYPES.length)],
                    getAnAddressAnyAddress(rand),
                    getAnAddressAnyAddress(rand),
                    ccTransact);
        }

    }

    private static void populateEvaluation(Random rand) {
    }

}
