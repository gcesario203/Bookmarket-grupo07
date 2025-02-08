package dominio;

/*
 * Cart.java - Class stores the necessary components of a shopping cart.
 * 
 ************************************************************************
 *
 * This is part of the the Java TPC-W distribution,
 * written by Harold Cain, Tim Heil, Milo Martin, Eric Weglarz, and Todd
 * Bezenek.  University of Wisconsin - Madison, Computer Sciences
 * Dept. and Dept. of Electrical and Computer Engineering, as a part of
 * Prof. Mikko Lipasti's Fall 1999 ECE 902 course.
 *
 * Copyright (C) 1999, 2000 by Harold Cain, Timothy Heil, Milo Martin, 
 *                             Eric Weglarz, Todd Bezenek.
 * Copyright © 2008 Gustavo Maciel Dias Vieira
 *
 * This source code is distributed "as is" in the hope that it will be
 * useful.  It comes with no warranty, and no author or distributor
 * accepts any responsibility for the consequences of its use.
 *
 * Everyone is granted permission to copy, modify and redistribute
 * this code under the following conditions:
 *
 * This code is distributed for non-commercial use only.
 * Please contact the maintainer for restrictions applying to 
 * commercial use of these tools.
 *
 * Permission is granted to anyone to make or distribute copies
 * of this code, either as received or modified, in any
 * medium, provided that all copyright notices, permission and
 * nonwarranty notices are preserved, and that the distributor
 * grants the recipient permission for further redistribution as
 * permitted by this document.
 *
 * Permission is granted to distribute this code in compiled
 * or executable form under the same conditions that apply for
 * source code, provided that either:
 *
 * A. it is accompanied by the corresponding machine-readable
 *    source code,
 * B. it is accompanied by a written offer, with no time limit,
 *    to give anyone a machine-readable copy of the corresponding
 *    source code in return for reimbursement of the cost of
 *    distribution.  This written offer must permit verbatim
 *    duplication by anyone, or
 * C. it is distributed by someone who received only the
 *    executable form, and is accompanied by a copy of the
 *    written offer of source code that they received concurrently.
 *
 * In other words, you are welcome to use, share and improve this codes.
 * You are forbidden to forbid anyone else to use, share and improve what
 * you give them.
 *
 ************************************************************************/
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * *<img src="./doc-files/Cart.png" alt="Cart">
 * <br><a href="./doc-files/Cart.html"> code </a>
 */
public class Cart implements Serializable {

    private static final long serialVersionUID = -4194553499937996531L;

    private final int id;
    private final Customer customer;
    private final int bookstoreId;
    private Date time;
    private HashMap<Integer, CartLine> linesByBookId;
    private double aggregateCost;
    private int aggregateQuantity;

	/**
	 *
	 * @param id   Identificador do carrinho de compras.
	 * @param time Tempo do carrinho de compras.
	 * @param customer Cliente que está criando o carrinho de compras
	 * @param bookstoreId Id da bookstore cujo qual o carrinho pertence
	 */
    public Cart(int id, Date time, Customer customer, int bookstoreId) {
        this.id = id;
        this.time = time;
        this.customer = customer;
        this.bookstoreId = bookstoreId;
        clear();
    }

	/**
	 * Método que recupera o identificador do carrinho de compras.
	 * 
	 * @return Recupera o identificador do carrinho de compras.
	 */
    public int getId() {
        return id;
    }

	/**
	 * Método que recupera o tempo do carrinho de compras.
	 * 
	 * @return Recupera o tempo do carrinho de compras.
	 */
    public Date getTime() {
        return time;
    }

	/**
	 * Método que define o tempo do carrinho de compras.
	 * 
	 * @param time Define o tempo do carrinho de compras.
	 */
    public void setTime(Date time) {
        this.time = time;
    }

	/**
	 * <pre>
	 * linesByBookId = new HashMap&lt;Integer, CartLine&gt;();
	 * aggregateCost = 0;
	 * aggregateQuantity = 0;
	 * </pre>
	 */
    public void clear() {
        linesByBookId = new HashMap<Integer, CartLine>();
        aggregateCost = 0;
        aggregateQuantity = 0;
    }

	/**
	 * Método que recupera uma lista de itens do carrinho de compras.
	 * 
	 * @return Recupera uma lista de itens do carrinho de compras.
	 */
    public Collection<CartLine> getLines() {
        return linesByBookId.values();
    }

    /**
     * <pre>
     * CartLine line = linesByBookId.get(book.getId());
     * if (line == null) {
     * line = new CartLine(0, book);
     * linesByBookId.put(book.getId(), line);
     * }
     * aggregateCost += book.getCost() * quantity;
     * aggregateQuantity += quantity;
     * line.setQty(line.getQty() + quantity);
     * if (quantity == 0) {
     * linesByBookId.remove(book.getId());
     * }
     * </pre>
     *
     * @param stock Estoque.
     * @param book Livro.
     * @param quantity Quantidade.
     */
    public void increaseLine(Stock stock, Book book, int quantity) {
        CartLine line = linesByBookId.get(book.getId());
        if (line == null) {
            line = new CartLine(0, book);
            linesByBookId.put(book.getId(), line);
        }
        aggregateCost += stock.getCost() * quantity;
        aggregateQuantity += quantity;
        line.setQty(line.getQty() + quantity);
        if (quantity == 0) {
            linesByBookId.remove(book.getId());
        }
    }

    /**
     * <pre>
     *   CartLine line = linesByBookId.get(book.getId());
     * if (line == null) {
     * line = new CartLine(0, book);
     * linesByBookId.put(book.getId(), line);
     * }
     * aggregateCost += book.getCost() * (quantity - line.getQty());
     * aggregateQuantity += (quantity - line.getQty());
     * line.setQty(quantity);
     * if (quantity == 0) {
     * linesByBookId.remove(book.getId());
     * }
     * </pre>
     *
     * @param stock Estoque.
     * @param book Livro.
     * @param quantity Quantidade.
     */
    public void changeLine(Stock stock, Book book, int quantity) {
        CartLine line = linesByBookId.get(book.getId());
        if (line == null) {
            line = new CartLine(0, book);
            linesByBookId.put(book.getId(), line);
        }
        aggregateCost += stock.getCost() * (quantity - line.getQty());
        aggregateQuantity += (quantity - line.getQty());
        line.setQty(quantity);
        if (quantity == 0) {
            linesByBookId.remove(book.getId());
        }
    }

	/**
	 * <pre>
	 * return aggregateCost * ((100 - discount) * 0.01);
	 * </pre>
	 *
	 * @param discount Desconto a se aplicado.
	 * @return valor do livro com desconto.
	 */
    public double subTotal(double discount) {
        return aggregateCost * ((100 - discount) * 0.01);
    }

    /**
     * <pre>
     * return subTotal(discount) * 0.0825;
     * </pre>
     *
     * @param discount Desconto a ser aplicado.
     * @return valor do livro com desconto.
     */
    public double tax(double discount) {
        return subTotal(discount) * 0.0825;
    }

    /**
     * <pre>
     * return 3.00 + (1.00 * aggregateQuantity);
     * </pre>
     *
     * @return valor do frete.
     */
    public double shipCost() {
        return 3.00 + (1.00 * aggregateQuantity);
    }

    /**
     * <pre>
     * return subTotal(discount) + shipCost() + tax(discount);
     * </pre>
     *
     * @param discount Desconto a ser aplicado.
     * @return valor total do livro.
     */
    public double total(double discount) {
        return subTotal(discount) + shipCost() + tax(discount);
    }
    
    public Customer getCustomer() {
    	return this.customer;
    }
    
    public int getBookstoreId() {
    	return this.bookstoreId;
    }

}
