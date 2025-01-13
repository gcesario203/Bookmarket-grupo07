package dominio;

/**
 * <img src="./doc-files/Stock.png" alt="Bookmarket"> <br>
 * <a href="./doc-files/Stock.html"> code </a>
 *
 */
public class Stock {

    private final int idBookstore;
    private final Book book;
    private double cost;
    private int qty;

    /**
     * Cria uma classe que representa o stock do sistema.
     *
     * @param idBookstore Identificador do Bookstore do estoque.
     * @param book Livro em estoque.
     * @param cost Custo unitário do livro em estoque.
     * @param qty Quantidade em estoque.
     */
    public Stock(int idBookstore, Book book, double cost, int qty) {
        this.idBookstore = idBookstore;
        this.book = book;
        this.cost = cost;
        this.qty = qty;
    }

    /**
     * Método que recupera livro em estoque.
     *
     * @return Recupera livro em estoque.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Método que adiciona quantidade em estoque.
     *
     * @param amount Adiciona quantidade em estoque.
     */
    public void addQty(int amount) {
        qty += amount;
    }

    /**
     * Método que recupera custo unitário do livro em estoque.
     *
     * @return Recupera custo em estoque.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Método que define custo unitário do livro em estoque.
     *
     * @param cost Define custo em estoque.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Método que recupera quantidade em estoque.
     *
     * @return Recupera quantidade em estoque.
     */
    public int getQty() {
        return qty;
    }

    /**
     * Método que define quantidade em estoque.
     *
     * @param qty Define quantidade em estoque.
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * Método que recupera o identificador do Bookstore do estoque.
     *
     * @return Recupera o identificador do Bookstore do estoque.
     */
    public int getIdBookstore() {
        return idBookstore;
    }

}
