package dominio;

/* 
 * CartLine.java - Class stores the necessary data for a single item in
 *                 a single shopping cart.
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

/**
 * *<img src="./doc-files/CartLine.png" alt="CartLine">
 * <br><a href="./doc-files/CartLine.html"> code </a>
 */
public class CartLine implements Serializable {

    private static final long serialVersionUID = 7390646727961714957L;

    private int qty;
    private final Book book;

	/**
	 *
	 * @param qty  Quantidade itens relacionados ao mesmo Book para o carrinho de
	 *             compras.
	 * @param book Livro relacionado com o carrinho de compras.
	 */
    public CartLine(int qty, Book book) {
        this.qty = qty;
        this.book = book;
    }

	/**
	 * Define a quantidade de itens relacionados ao mesmo Book para o carrinho
	 *
	 * @param qty quantidade de itens
	 */
    public void setQty(int qty) {
        this.qty = qty;
    }

	/**
	 * Recupera a quantidade de itens relacionados com o livro em um carrinho
	 *
	 * @return Quantidade de itens do carrinho para o mesmo livro
	 */
    public int getQty() {
        return qty;
    }

	/**
	 * Recupera o livro relacionado com o carrinho
	 *
	 * @return Um dos livros que está no carrinho
	 */
    public Book getBook() {
        return book;
    }

}
