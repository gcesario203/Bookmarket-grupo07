package dominio;

/*
 * OrderLine.java - Class contains the data pertinent to a single
 *                  item in a single order.
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
 * *<img src="./doc-files/OrderLine.png" alt="OrderLine"> <br>
 * <a href="./doc-files/OrderLine.html"> code </a>
 *
 * Esta classe existe para que seja possível criar uma representaçào da venda de
 * um item na biblioteca. É importante salientar que todas as modificações que
 * existem na quantidade são refletidas . Isto é feito pois cada OrderLine
 * possui relação com um {@linkplain Book}
 */
public class OrderLine implements Serializable {

    private static final long serialVersionUID = -5063511252485472431L;

    private final Book book;
    private final int qty;
    private final double discount;
    private final String comments;

    /**
     *
     * @param book Livro do item da venda..
     * @param qty Quantidade do item da venda..
     * @param discount Dessconto do item da venda..
     * @param comments Comentários do item da venda..
     */
    public OrderLine(Book book, int qty, double discount, String comments) {
        this.book = book;
        this.qty = qty;
        this.discount = discount;
        this.comments = comments;
    }

    /**
     * Método que recupera o livro do item da venda.
     *
     * @return Recupera o livro do item da venda.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Método que recupera a quantidade do item da venda.
     *
     * @return Recupera a quantidade do item da venda.
     */
    public int getQty() {
        return qty;
    }

    /**
     * Método que recupera o desconto do item da venda.
     *
     * @return Recupera o desconto do item da venda.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Método que recupera os comentários do item da venda.
     *
     * @return Recupera os comentários do item da venda.
     */
    public String getComments() {
        return comments;
    }

}
