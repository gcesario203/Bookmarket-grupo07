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
 *
 * 
 * Esta classe existe com o objetivo final de representar o marketplace dentro
 * do sistema. Ela é responsável por gerenciar uma lista de
 * {@linkplain Bookstore} que representam as lojas existentes. Para realizar o
 * controle das lojas contidas no marketplace, {@linkplain Bookmarket} utiliza
 * uma máquina de estados que é acionada utilizando uma variação do padrão
 * COMMAND. Cada estado desta máquina é representado por uma das lojas
 * ({@linkplain Bookstore}) cadastradas. A variação do padrão COMMAND é baseada
 * em cima da interface {@linkplain Action}. Todas as ações executadas pela
 * máquina de estados implementam esta interface, tornando possível melhor
 * entendimento e desacoplamento da lógica de ações a serem tomadas pelo sistema
 * de acordo com seu estado.
 *
 * Cada pedaço da lógica por baixo de cada condição é separado em um manipulador
 * do comando, tornando possível a criação da máquina de estados simples e pouco
 * verborrágica.
 *
 * O mecanismo de execução criado para os estados da máquina permitem a
 * modularização de comandos para praticamente todos os modelos utilizados
 * dentro do sistema. Para realizar este feito, os comandos específicos dos
 * modelos implementam a interface {@linkplain Action} com um método default e
 * criam uma abstração chamada {@linkplain BookstoreAction}. Esta abstração
 * define que o tipo de dado que é tratado em suas execuções é um stream,
 * facilitando assim a manipulação de listas dentro do sistema atual.
 *
 */
public class Bookmarket {
    private static Random random;
    private static StateMachine stateMachine;

    /**
     * Método utilizado para inicializar o Bookmarket e criar uma lista de
     * Bookstores vinculadas ao Bookmarket.
     *
     * @param state array de Bookstores criados para compor a máquina de estados
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
     * Método utilizado para pegar o customer pelo uname cadastrado na
     * Bookstore.
     *
     * @param UNAME Uname de identificação do Customer
     * @return Customer Customer recuperado na lista cadastrada no sistema
     */
    public static Customer getCustomer(String UNAME) {
        return Bookstore.getCustomer(UNAME).get();
    }

    /**
     * Método utilizado para pegar o nome completo do customer cadastrado na
     * Bookstore pelo id. Retorna um array de string com o nome completo.
     *
     * @param c_id Id
     * @return String[] Nome completo do user sendo: getName()[0] = primeiro
     *         nome; getName()[1] = último nome; getName()[2] = Nome de
     *         identificação;
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
     * Método utilizado para pegar o username do customer cadastrado na
     * Bookstore pelo id. Retorna a string do username.
     *
     * @param C_ID ID
     * @return String Nome de usuário do Customer
     */
    public static String getUserName(int C_ID) {
        return Bookstore.getCustomer(C_ID).get().getUname();
    }

    /**
     * Método utilizado para pegar a password do customer cadastrado na
     * Bookstore pelo username. Retorna a string da password.
     *
     * @param C_UNAME Nome de usuário
     * @return String Senha do usuário especificado
     */
    public static String getPassword(String C_UNAME) {
        return Bookstore.getCustomer(C_UNAME).get().getPasswd();

    }

    /**
     * Método utilizado para pegar a compra mais recente do customer na
     * Bookstore pelo username. Retorna o objeto da compra.
     *
     * @param c_uname Nome do usuário
     * @return Order Compra mais recente do Customer
     */
    public static Order getMostRecentOrder(String c_uname) {
        return Bookstore.getCustomer(c_uname).get().getMostRecentOrder();
    }

    /**
     * Método utilizado para cadastrar um novo usuário na Bookstore. O desconto
     * é gerado randomicamente. É excutado o estado de CreationCustomerAction
     * para a criação do customer. Se algum erro ocorre na criação, é gerado um
     * RuntimeExpection.
     *
     * @param fname       Primeiro nome do usuário
     * @param lname       último nome do usuário
     * @param street1     Endereço 1
     * @param street2     Endereço 2
     * @param city        Cidade do usuário
     * @param state       Estado do usuário
     * @param zip         Código postal
     * @param countryName Nome do país
     * @param phone       telefone do usuário
     * @param email       email do usuário
     * @param birthdate   Data de nascimento do usuário
     * @param data        Dados do usuário
     * @param type 		  Tipo de cliente
     * @return Customer Novo Customer cadastrado na plataforma
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
     * Método utilizado para execução da máquina de estado
     * RefreshCustomerSessionAction, passando como parametros o id do customer
     * cadastrado na Bookstore, e data atual em milesegundos. Esse estado
     * executa o método de refresh interno do Bookstore, que irá renovar a
     * sessão do usuário em duas horas.
     *
     * @param cId Id da sessão
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
     * Método utilizado para retornar um livro específico através do id.
     *
     * @param i_id Id do livro desejado
     * @return Livro recuperado dentro da plataforma
     */
    public static Book getBook(int i_id) {

        return Bookstore.getBook(i_id).get();

    }

    /**
     * Método utilizado para retornar um livro randômico através do
     * {@linkplain Bookstore}.
     *
     * @return Um livro qualquer cadastrado dentro da plataforma
     */
    public static Book getABookAnyBook() {
        return Bookstore.getABookAnyBook(random);

    }

    /**
     * Método utilizado para retornar uma lista de livros filtrados através de
     * um parâmetro do tipo string representando o assunto do livro.
     *
     * @param search_key Assunto do livro para filtrar busca
     * @return Lista de livros que atendem aos critérios de filtro por assunto
     */
    public static List<Book> doSubjectSearch(String search_key) {
        return Bookstore.getBooksBySubject(search_key);
    }

    /**
     * Método utilizado para retornar uma lista de livros filtrados através de
     * um parâmetro do tipo string representando o título do livro.
     *
     * @param search_key Título do livro para filtrar busca
     * @return Lista de livros que atendem aos critérios de filtro por título
     */
    public static List<Book> doTitleSearch(String search_key) {
        return Bookstore.getBooksByTitle(search_key);
    }

    /**
     * Método utilizado para retornar uma lista de livros filtrados através de
     * um parâmetro do tipo string representando o autor do livro.
     *
     * @param search_key Autor do livro para filtrar busca
     * @return Lista de livros que atendem aos critérios de filtro por autor
     */
    public static List<Book> doAuthorSearch(String search_key) {
        return Bookstore.getBooksByAuthor(search_key);
    }

    /**
     * Método utilizado para retornar uma lista de livros cuja data de
     * publicação seja da mais recente para a mais antiga, filtrados pelo
     * assunto.
     *
     * @param subject Assunto do livro para filtrar a busca
     * @return Lista de livros com data de publicação mais recente
     */
    public static List<Book> getNewProducts(String subject) {
        return Bookstore.getNewBooks(subject);
    }

    /**
     * Retorna todos os preços de um determinado livro, passado por parâmetro,
     * para cada {@linkplain Bookstore} existente no {@linkplain Bookmarket}.
     *
     * @param book Livro que será utilizado no filtro de busca
     * @return Lista com os preços de um determinado livro dentro do marketPlace
     */
    public static List<Double> getCosts(Book book) {
                return getBookstoreStream().map(store -> store.getStock(book.getId())).map(stock -> (Double) (stock == null ? 0.0 : stock.getCost()))

                .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista ordenada dos livros mais vendidos a partir de um mapa de vendas totais.
     * A ordenação é feita em ordem decrescente de quantidade vendida.
     *
     * @param salesByBook Mapa contendo a quantidade total de vendas para cada livro
     * @param limit Quantidade de livros a serem retornados
     * @return Lista dos livros mais vendidos, limitada pelo parâmetro limit
     */
    public static List<Book>  sortBooksBySalesDescending(HashMap<Book, Integer> salesByBook, int limit) {
        List<Book> topBooks = salesByBook.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Ordena por vendas (descendente)
                .limit(limit) // Filtra os N primeiros
                .map(Map.Entry::getKey) // Extrai apenas os objetos Book
                .collect(Collectors.toList());
        return topBooks;
    }

    /**
     * Identifica os livros mais vendidos considerando todas as livrarias no sistema.
     *
     * @param numberOfBooks Quantidade de livros a serem retornados (entre 1 e 100)
     * @return Lista dos livros mais vendidos, ordenada por quantidade de vendas decrescente
     * @throws RuntimeException se numberOfBooks estiver fora do intervalo válido (1-100)
     */
    public static List<Book> getBestSellers(Integer numberOfBooks) {
        final Integer MINIMUM_BOOKS = 1;
        final Integer MAXIMUM_BOOKS = 100;

        if (numberOfBooks < MINIMUM_BOOKS || numberOfBooks > MAXIMUM_BOOKS) {
            throw new RuntimeException("Número de livros solicitado deve estar entre 1 e 100");
        }
        // Combina as vendas de todas as lojas em um único mapa
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
     * Consolida o total de vendas de todos os livros em todas as livrarias do
     * sistema.
     * Combina os contadores de vendas de cada livraria em um único mapa agregado.
     *
     * @return Mapa onde a chave é o livro e o valor é a quantidade total vendida
     *         considerando todas as livrarias
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
     * Obtém recomendações de livros com base nos itens previamente avaliados pelo
     * usuário.
     * 
     * @param c_id Identificador único do usuário para o qual as recomendações serão
     *             geradas.
     * @return Uma lista contendo até 5 livros recomendados com base no histórico de
     *         interação do usuário.
     */
    @SuppressWarnings("unchecked")
    public static List<Book> getRecommendationByItens(int c_id) {
        return (List<Book>) stateMachine.execute(new GetRecommendationByItensAction(c_id, 5));
    }

    /**
     * Obtém recomendações de livros com base em usuários com perfis semelhantes.
     * 
     * @param c_id Identificador único do usuário para o qual as recomendações serão
     *             geradas.
     * @return Uma lista contendo até 5 livros recomendados com base em perfis de
     *         usuários similares.
     */
    @SuppressWarnings("unchecked")
    public static List<Book> getRecommendationByUsers(int c_id) {
        return (List<Book>) stateMachine.execute(new GetRecommendationByUsersAction(c_id, 5));
    }

    /**
     * Retorna a disponibilidade um livro na lista de {@linkplain Bookstore} do
     * {@linkplain Bookmarket}, à partir de um parâmetro de identifição do
     * livro.
     *
     * @param idBook ID que representa o livro a ser consultado
     * @return Lista
     */
    public static List<Stock> getStocks(final int idBook) {
        return getBookstoreStream()
                .filter(store -> store.getStock(idBook) != null)
                // transforma o stream de bookstore em stream de stock
                .map(store -> store.getStock(idBook))
                .collect(Collectors.toList());
    }

    /**
     * Retorna a disponibilidade de um livro em uma {@linkplain Bookstore}
     * específica.
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
     * Retorna uma lista de livros que sejam relacionados a um livro específico.
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
     * Método utilizado para atualizar os dados de um livro específico.
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
     * Cria um carrinho de compras vazio atrelado a uma determinada
     * {@linkplain Bookstore}.
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
     * Executa a atualização do carrinho de compras, se o carrinho estiver vazio
     * adiciona um livro qualquer ao carrinho de compras.
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
     * Retorna o carrinho de compras específico de um {@linkplain Bookstore}
     * específico.
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
     * Metodo utilizado para buscar um carrinho de um cliente
     *
     * @param storeId Id do bookstore cujo qual é necessario buscar o carrinho
     * @param customerId Id do cliente
     */
    @SuppressWarnings("unchecked")
    public static Optional<Cart> getCartByCustomer(int storeId, int customerId){
    	return (Optional<Cart>)stateMachine.execute(new GetCartByCustomer(storeId, customerId));
    }

    /**
     * Método utilizado para execução da ação ConfirmBuyAction na máquina de
     * estado para a confirmação da compra.
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
     * Método utilizado para execução da ação ConfirmBuyAction na máquina de
     * estado para a confirmação da compra.
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
     * Método utilizado para pegar o valor medio do livro em todas as bookstores
     *
     * @param bookId
     * @return o preço medio do livro nas bookstores
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
     * Metodo utilizado para pegar o menor custo de um livro determinado
     * das bookstores
     *
     * @param bookId
     * @return Um Optional<Stock>, para caso não existir nenhum stock do livro em
     * nenhuma bookstore, sendo informado no objeto Stock, em qual bookstore ele se
     * encontra
     */
    @SuppressWarnings("unchecked")
    public static Optional<Stock> getMinimumBookPrice(int bookId){
    	return (Optional<Stock>) stateMachine.execute(new GetMinimumBookPriceAction(bookId));
    }

    /**
     * Método utilizado para execução da ação PopulateAction na máquina de
     * estado para adição de dados na {@linkplain Bookstore}.
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
