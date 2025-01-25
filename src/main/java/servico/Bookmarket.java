package servico;

import dominio.Address;
import dominio.Book;
import dominio.Cart;
import dominio.Customer;
import dominio.Order;
import dominio.Review;
import dominio.Stock;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import util.TPCW_Util;

/**

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

    private interface Action<STATE> {

        Object executeOn(STATE sm);
    }

    private static class StateMachine {

        private final List<Bookstore> state;

        public StateMachine(final List object) {
            this.state = object;
        }

        Object execute(Action action) {
            return action.executeOn(getStateStream());
        }

        void checkpoint() {

        }

        public Stream<Bookstore> getStateStream() {
            return state.stream();
        }

        private static StateMachine create(Bookstore... state) {
            List list = new ArrayList();
            try {
                list.addAll(Arrays.asList(state));
            } catch (Exception e) {
                throw new UmbrellaException(e);
            }
            return new StateMachine(list);
        }

    }

    private static class UmbrellaException extends RuntimeException {

        /**
         * Default Generated SerialUID
         */
        private static final long serialVersionUID = 8525983676993371110L;

        public UmbrellaException(Exception e) {
            super(e);
        }

    }
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
     * nome; getName()[1] = último nome; getName()[2] = Nome de identificação;
     */
    public static String[] getName(int c_id) {

        Customer customer = Bookstore.getCustomer(c_id);

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
        return Bookstore.getCustomer(C_ID).getUname();
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
     * @param fname Primeiro nome do usuário
     * @param lname último nome do usuário
     * @param street1 Endereço 1
     * @param street2 Endereço 2
     * @param city Cidade do usuário
     * @param state Estado do usuário
     * @param zip Código postal
     * @param countryName Nome do país
     * @param phone telefone do usuário
     * @param email email do usuário
     * @param birthdate Data de nascimento do usuário
     * @param data Dados do usuário
     * @return Customer Novo Customer cadastrado na plataforma
     */
    public static Customer createNewCustomer(String fname, String lname,
            String street1, String street2, String city, String state,
            String zip, String countryName, String phone, String email,
            Date birthdate, String data) {
        double discount = (int) (Math.random() * 51);
        long now = System.currentTimeMillis();
        try {
            return (Customer) stateMachine.execute(new CreateCustomerAction(
                    fname, lname, street1, street2, city, state, zip,
                    countryName, phone, email, discount, birthdate, data, now));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Review createReview(int bookstoreId, Customer customer, Book book, double value) {
    	try {
    		return (Review) stateMachine.execute(new CreateReviewAction(customer, book, value, bookstoreId));
    	}
    	catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
    
    public static List<Review> getReviews()
    {
    	return (List<Review>) stateMachine.execute(new GetReviewsAction());
    }
    
    public static Optional<Review> getReviewById(int bookstoreId, int id){
    	return (Optional<Review>) stateMachine.execute(new GetReviewByIdAction(id, bookstoreId));
    }
    
    public static List<Review> getReviewsByBook(int bookstoreId,Book book){
    	return (List<Review>) stateMachine.execute(new GetReviewByBookAction(book, bookstoreId));
    }
    
    public static List<Review> getReviewsByCustomer(int bookstoreId,Customer customer){
    	return (List<Review>) stateMachine.execute(new GetReviewByCustomerAction(customer, bookstoreId));
    }
    
    public static boolean removeReviewById(int bookstoreId,int id) {
    	return (boolean) stateMachine.execute(new RemoveReviewsByIdAction(id, bookstoreId));
    }
    
    public static boolean changeReviewValue(int bookstoreId, int id, double value) {
    	return (boolean) stateMachine.execute(new ChangeReviewAction(id, value, bookstoreId));
    }
    
    public static List<Review> getReviewsByBookstore(int bookstoreId){
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
        return getBookstoreStream().
                map(store -> store.getStock(book.getId())).
                map(stock -> stock.getCost()).
                collect(Collectors.toList());
    }

    /**
     *
     * @param subject
     * @return
     */
    public static List<Book> getBestSellers(String subject) {
        return null;
    }

    /**
     *
     * @param c_id
     * @return
     */
    public static List<Book> getRecommendationByItens(int c_id) {
        return Bookstore.getRecommendationByItens(c_id);
    }

    /**
     * A recomendação de livros para usuários do Bookmarket aproveita a
     * implementação estática do mesmo método da Bookstore para recuperar os
     * livros.
     *
     * @param c_id Id do usuário que necessita de livros recomendados pelo
     * sistema
     * @return Retorna uma lista de livros com limite de 5 itens recomendados
     * pelo sistema.
     */
    public static List<Book> getRecommendationByUsers(int c_id) {
        return Bookstore.getRecommendationByUsers(c_id);
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
     * @return
     */
    public static int createEmptyCart(int storeId) {
        try {
            return ((Cart) stateMachine.execute(new CreateCartAction(storeId,
                    System.currentTimeMillis()))).getId();
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
    public static Cart doCart(int storeId, int SHOPPING_ID, Integer I_ID, List<Integer> ids,
            List<Integer> quantities) {
        try {
            Cart cart = (Cart) stateMachine.execute(new CartUpdateAction(storeId,
                    SHOPPING_ID, I_ID, ids, quantities,
                    System.currentTimeMillis()));
            if (cart.getLines().isEmpty()) {
                Book book = Bookstore.getABookAnyBook(random);
                cart = (Cart) stateMachine.execute(new CartUpdateAction(storeId,
                        SHOPPING_ID, book.getId(), new ArrayList<>(),
                        new ArrayList<>(), System.currentTimeMillis()));
            }
            return cart;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retorna o carrinho de compras específico de um {@linkplain Bookstore}
     * específico.
     *
     * @param SHOPPING_ID
     * @param storeId
     * @return
     */
    public static Cart getCart(int storeId, int SHOPPING_ID) {
        Bookstore bookstore = getBookstoreStream()
                .filter(store -> store.getId() == storeId)
                .findFirst()
                .get();
        synchronized (bookstore) {
            return bookstore.getCart(SHOPPING_ID);
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

    private static String randomComment() {
        return TPCW_Util.getRandomString(random, 20, 100);
    }

    private static Date randomShippingDate(long now) {
        return new Date(now + 86400000 /* a day */ * (random.nextInt(7) + 1));
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

    /**
     *
     */
    public static void checkpoint() {
        try {
            stateMachine.checkpoint();
        } catch (UmbrellaException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected static abstract class ExpecificBookstoreAction extends BookstoreAction{
    	
    	int bookstoreId;
    	
    	public ExpecificBookstoreAction(int bookstoreId) {
    		this.bookstoreId = bookstoreId;
    	}
    	
    	protected Bookstore getBookstoreById(Stream<Bookstore> bookstores) {
    		return bookstores.filter(b -> b.getId() == this.bookstoreId).findFirst().get();
    	}
    	
    	@Override
    	public abstract Object executeOnBookstore(Stream<Bookstore> bookstore);
    }

    /**
     * Classe abstrata para representar os métodos padrões da implementação das
     * ações na {@linkplain Bookstore}.
     *
     */
    protected static abstract class BookstoreAction implements Action<Stream<Bookstore>>,
            Serializable {

        /**
         * Determina em qual {@linkplain Bookstore} a ação será executada.
         *
         * @param sm
         * @return
         */
        @Override
        public Object executeOn(Stream<Bookstore> sm) {
            return executeOnBookstore(sm);
        }

        /**
         * Método abstrato que será implementado na classe concreta determinando
         * qual ação será executada no {@linkplain Bookstore}.
         *
         * @param bookstore
         * @return
         */
        public abstract Object executeOnBookstore(Stream<Bookstore> bookstore);
    }
    
    protected static class GetReviewsAction extends BookstoreAction{
    	private static final long serialVersionUID = 7439962163328790677L;

		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			List<Review> reviews = new ArrayList<Review>();
			
			bookstore.forEach(b -> reviews.addAll(b.getReviews()));
			
			return reviews;
		}
    }
    
    protected static class GetReviewByIdAction extends ExpecificBookstoreAction{
    	private static final long serialVersionUID = 6439962163328790677L;
    	
    	int id;
    	
    	public GetReviewByIdAction(int id, int bookstoreId) {
    		super(bookstoreId);
    		this.id = id;
    	}
		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			return getBookstoreById(bookstore).getReviewById(this.id);
		}
    }
    
    protected static class GetReviewsByBookstoreAction extends ExpecificBookstoreAction{
    	public GetReviewsByBookstoreAction(int bookstoreId) {
    		super(bookstoreId);
    	}
    	
		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			return getBookstoreById(bookstore).getReviews();
		}
    }
    
    protected static class GetReviewByBookAction extends ExpecificBookstoreAction{
    	private static final long serialVersionUID = 6439962163328790677L;
    	
    	Book book;
    	
    	public GetReviewByBookAction(Book book, int bookstoreId) {
    		super(bookstoreId);
    		this.book = book;
    	}
		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			return getBookstoreById(bookstore).getReviewsByBook(this.book);
		}
    }
    
    protected static class GetReviewByCustomerAction extends ExpecificBookstoreAction{
    	private static final long serialVersionUID = 6439962163328790677L;
    	
    	Customer customer;
    	
    	public GetReviewByCustomerAction(Customer customer, int bookstoreId) {
    		super(bookstoreId);
    		this.customer = customer;
    	}
		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			return getBookstoreById(bookstore).getReviewsByCustomer(this.customer);
		}
    }
    
    protected static class RemoveReviewsByIdAction extends ExpecificBookstoreAction{
    	private static final long serialVersionUID = 6439962163328790677L;
    	
    	int id;
    	
    	public RemoveReviewsByIdAction(int id, int bookstoreId) {
    		super(bookstoreId);
    		this.id = id;
    	}
		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			return getBookstoreById(bookstore).removeReviewById(this.id);
		}
    }
    
    protected static class ChangeReviewAction extends ExpecificBookstoreAction{
    	private static final long serialVersionUID = 6239962163328790677L;
    	
    	int id;
    	double value;
    	
    	public ChangeReviewAction(int id, double value, int bookstoreId) {
    		super(bookstoreId);
    		this.id = id;
    		
    		this.value = value;
    	}

		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			try {
				return getBookstoreById(bookstore).changeReviewValue(id, value);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
    }
    
    protected static class CreateReviewAction extends ExpecificBookstoreAction {
    	private static final long serialVersionUID = 6039962163328790677L;
    	
    	Customer customer;
    	Book book;
    	double value;
    	
    	public CreateReviewAction(Customer customer, Book book, double value, int bookstoreId) {
    		super(bookstoreId);
    		this.customer = customer;
    		
    		this.book = book;
    		
    		this.value = value;
    	}
    	
		@Override
		public Object executeOnBookstore(Stream<Bookstore> bookstore) {
			try {
				return getBookstoreById(bookstore).createReview(this.customer, this.book, this.value);	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
	        }
		}
    }
    /**
     * Classe que implementa as ações relacionadas a criação de cliente.
     */
    protected static class CreateCustomerAction extends BookstoreAction {

        private static final long serialVersionUID = 6039962163348790677L;

        String fname;
        String lname;
        String street1;
        String street2;
        String city;
        String state;
        String zip;
        String countryName;
        String phone;
        String email;
        double discount;
        Date birthdate;
        String data;
        long now;

        /**
         * Método construtor da classe.
         *
         * @param fname
         * @param lname
         * @param street1
         * @param street2
         * @param city
         * @param state
         * @param zip
         * @param countryName
         * @param phone
         * @param email
         * @param discount
         * @param birthdate
         * @param data
         * @param now
         */
        public CreateCustomerAction(String fname, String lname, String street1,
                String street2, String city, String state, String zip,
                String countryName, String phone, String email,
                double discount, Date birthdate, String data, long now) {
            this.fname = fname;
            this.lname = lname;
            this.street1 = street1;
            this.street2 = street2;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.countryName = countryName;
            this.phone = phone;
            this.email = email;
            this.discount = discount;
            this.birthdate = birthdate;
            this.data = data;
            this.now = now;
        }

        /**
         * Método que injeta o cliente com suas respectivas informações dentro
         * da {@linkplain Bookstore}.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            return Bookstore.createCustomer(fname, lname, street1, street2,
                    city, state, zip, countryName, phone, email, discount,
                    birthdate, data, now);
        }
    }

    /**
     * Classe que implementa as ações relacionadas a sessão do cliente.
     */
    protected static class RefreshCustomerSessionAction extends BookstoreAction {

        private static final long serialVersionUID = -5391031909452321478L;

        int cId;
        long now;

        /**
         * Método construtor da classe.
         *
         * @param id
         * @param now
         */
        public RefreshCustomerSessionAction(int id, long now) {
            cId = id;
            this.now = now;
        }

        /**
         * Executa a função do {@linkplain Bookstore} que atualiza a sessão do
         * cliente.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            Bookstore.refreshCustomerSession(cId, now);
            return null;
        }
    }

    /**
     * Classe que implementa as ações relacionadas a atualização do livro.
     */
    protected static class UpdateBookAction extends BookstoreAction {

        private static final long serialVersionUID = -745697943594643776L;

        int bId;
        double cost;
        String image;
        String thumbnail;
        long now;

        /**
         * Método construtor da classe.
         *
         * @param id
         * @param cost
         * @param image
         * @param thumbnail
         * @param now
         */
        public UpdateBookAction(int id, double cost, String image,
                String thumbnail, long now) {
            bId = id;
            this.cost = cost;
            this.image = image;
            this.thumbnail = thumbnail;
            this.now = now;
        }

        /**
         * Executa o método na {@linkplain Bookstore} que atualiza os dados de
         * um livro específico.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            Bookstore.updateBook(bId, image, thumbnail, now);
            return null;
        }
    }

    /**
     * Classe que implementa as ações relacionadas a criação do carrinho de
     * compras.
     */
    protected static class CreateCartAction extends BookstoreAction {

        private static final long serialVersionUID = 8255648428785854052L;

        long now, storeId;

        /**
         * Método construtor do carrinho de compras para um
         * {@linkplain Bookstore} específico.
         *
         * @param idBookstore
         * @param now
         */
        public CreateCartAction(int idBookstore, long now) {
            this.now = now;
            this.storeId = idBookstore;
        }

        /**
         * Cria um carrinho de compras para um {@linkplain Bookstore}
         * específico.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            return bookstore.filter(bs -> bs.getId() == this.storeId).findFirst().get().createCart(now);
        }
    }

    /**
     * Classe que implementa as ações relacionadas a atualização do carrinho de
     * compras.
     */
    protected static class CartUpdateAction extends BookstoreAction {

        private static final long serialVersionUID = -6062032194650262105L;

        final int cId, storeId;
        final Integer bId;
        final List<Integer> bIds;
        final List<Integer> quantities;
        final long now;

        /**
         * Método construtor da classe.
         *
         * @param storeId
         * @param id
         * @param id2
         * @param ids
         * @param quantities
         * @param now
         */
        public CartUpdateAction(int storeId, int id, Integer id2, List<Integer> ids,
                List<Integer> quantities, long now) {
            this.storeId = storeId;
            cId = id;
            bId = id2;
            bIds = ids;
            this.quantities = quantities;
            this.now = now;
        }

        /**
         * Atualiza um carrinho de compras em um {@linkplain Bookstore}.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            return bookstore.filter(bs -> bs.getId() == this.storeId).findFirst().get().cartUpdate(cId, bId, bIds, quantities, now);
        }
    }

    /**
     * Classe que implementa as ações relacionadas a confirmação de compra.
     */
    protected static class ConfirmBuyAction extends BookstoreAction {

        private static final long serialVersionUID = -6180290851118139002L;

        final int customerId, storeId, cartId;
        String comment;
        String ccType;
        long ccNumber;
        String ccName;
        Date ccExpiry;
        String shipping;
        Date shippingDate;
        int addressId;
        long now;

        /**
         * Método construtor da classe.
         *
         * @param storeId
         * @param customerId
         * @param cartId
         * @param comment
         * @param ccType
         * @param ccNumber
         * @param ccName
         * @param ccExpiry
         * @param shipping
         * @param shippingDate
         * @param addressId
         * @param now
         */
        public ConfirmBuyAction(int storeId, int customerId, int cartId,
                String comment, String ccType, long ccNumber,
                String ccName, Date ccExpiry, String shipping,
                Date shippingDate, int addressId, long now) {
            this.storeId = storeId;
            this.customerId = customerId;
            this.cartId = cartId;
            this.comment = comment;
            this.ccType = ccType;
            this.ccNumber = ccNumber;
            this.ccName = ccName;
            this.ccExpiry = ccExpiry;
            this.shipping = shipping;
            this.shippingDate = shippingDate;
            this.addressId = addressId;
            this.now = now;
        }

        /**
         * Confirma a compra de um carrinho de compras de um
         * {@linkplain Bookstore}.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            return bookstore.filter(bs -> bs.getId() == this.storeId).findFirst().get().confirmBuy(customerId, cartId, comment, ccType,
                    ccNumber, ccName, ccExpiry, shipping, shippingDate,
                    addressId, now);
        }
    }

    /**
     * Classe que implementa as ações relacionadas a preenchimento dos dados do
     * {@linkplain Bookstore}.
     */
    protected static class PopulateAction extends BookstoreAction {

        private static final long serialVersionUID = -5240430799502573886L;

        long seed;
        long now;
        int items;
        int customers;
        int addresses;
        int authors;
        int orders;

        /**
         * Método construtor da classe.
         *
         * @param seed
         * @param now
         * @param items
         * @param customers
         * @param addresses
         * @param authors
         * @param orders
         */
        public PopulateAction(long seed, long now, int items, int customers,
                int addresses, int authors, int orders) {
            this.seed = seed;
            this.now = now;
            this.items = items;
            this.customers = customers;
            this.addresses = addresses;
            this.authors = authors;
            this.orders = orders;
        }

        /**
         * Popula os dados do {@linkplain Bookstore}.
         *
         * @param bookstore
         * @return
         */
        @Override
        public Object executeOnBookstore(Stream<Bookstore> bookstore) {
            Bookstore.populate(seed, now, items, customers, addresses, authors);
            Random rand = new Random(seed);
            bookstore.forEach(instance -> instance.populateInstanceBookstore(orders, rand, now));
            return true;
        }
    }

}
