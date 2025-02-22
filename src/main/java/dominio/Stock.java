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
     * Creates a class that represents a system stock.
     *
     * @param idBookstore Stock's bookstore identifier.
     * @param book Stock's book.
     * @param cost Stock's book unit cost.
     * @param qty Stock's quantity.
     */
    public Stock(int idBookstore, Book book, double cost, int qty) {
        this.idBookstore = idBookstore;
        this.book = book;
        this.cost = cost;
        this.qty = qty;
    }

    /**
     * Stock's book getter.
     *
     * @return Retrieves Stock's book.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Stock's quantity addition method.
     *
     * @param amount Adds stock quantity.
     */
    public void addQty(int amount) {
        qty += amount;
    }

    /**
     * Stock's book unit cost getter.
     *
     * @return Retrieves Stock's cost.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Stock's book unit cost setter.
     *
     * @param cost Sets Stock's cost.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Stock's quantity getter.
     *
     * @return Retrieves Stock's quantity getter.
     */
    public int getQty() {
        return qty;
    }

    /**
     * Stock's quantity setter.
     *
     * @param qty Sets Stock's quantity.
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * Stock's bookstore identifier getter.
     *
     * @return Retrieves Stock's bookstore identifier.
     */
    public int getIdBookstore() {
        return idBookstore;
    }

}
