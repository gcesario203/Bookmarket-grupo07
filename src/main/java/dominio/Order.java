package dominio;

/*
 * Order.java - Order class stores data pertinent to a single order.
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
import java.util.ArrayList;
import java.util.Date;

/**
 * *<img src="./doc-files/Order.png" alt="Order">
 * <br><a href="./doc-files/Order.html"> code </a>
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -1106285234830970111L;

    private final int id;
    private final Customer customer;
    private final Date date;
    private final double subtotal;
    private final double tax;
    private final double total;
    private final String shipType;
    private final Date shipDate;
    private final String status;
    private final Address billingAddress;
    private final Address shippingAddress;
    private final CCTransaction cc;
    private final ArrayList<OrderLine> lines;

    /**
	 * <pre>
	 * this.id = id;
	 * this.customer = customer;
	 * this.date = date;
	 * subtotal = cart.subTotal(customer.getDiscount());
	 * tax = 8.25;
	 * total = cart.total(customer.getDiscount());
	 * this.shipType = shipType;
	 * this.shipDate = shipDate;
	 * this.status = status;
	 * this.billingAddress = billingAddress;
	 * this.shippingAddress = shippingAddress;
	 * this.cc = cc;
	 * lines = new ArrayList&lt;OrderLine&gt;(cart.getLines().size());
	 * for (CartLine cartLine : cart.getLines()) {
	 * 	OrderLine line = new OrderLine(cartLine.getBook(), cartLine.getQty(), customer.getDiscount(), comment);
	 * 	lines.add(line);
	 * }
	 * </pre>
	 *
	 * @param id              Identficador da venda.
	 * @param customer        Cliente da venda.
	 * @param date            Data da venda.
	 * @param cart            Carrinho de compras da venda.
	 * @param comment         Coementário da venda.
	 * @param shipType        Tipo do frete da venda.
	 * @param shipDate        Data do frete da venda.
	 * @param status          Status da venda.
	 * @param billingAddress  Endereço de cobrança da venda.
	 * @param shippingAddress Endereço de entrega da venda.
	 * @param cc              Cartão de crédito da venda.
	 */
    public Order(int id, Customer customer, Date date, Cart cart,
            String comment, String shipType, Date shipDate, String status,
            Address billingAddress, Address shippingAddress, CCTransaction cc) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        subtotal = cart.subTotal(customer.getDiscount());
        tax = 8.25;
        total = cart.total(customer.getDiscount());
        this.shipType = shipType;
        this.shipDate = shipDate;
        this.status = status;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.cc = cc;
        lines = new ArrayList<OrderLine>(cart.getLines().size());
        for (CartLine cartLine : cart.getLines()) {
            OrderLine line = new OrderLine(cartLine.getBook(),
                    cartLine.getQty(), customer.getDiscount(),
                    comment);
            lines.add(line);
        }
    }

	/**
	 * Método que recupera o identificador da venda.
	 * 
	 * @return Recupera o identificador da venda.
	 */
    public int getId() {
        return id;
    }

	/**
	 * Método que recupera o cliente da venda.
	 * 
	 * @return Recupera o cliente da venda.
	 */
    public Customer getCustomer() {
        return customer;
    }

	/**
	 * Método que recupera a data da venda.
	 * 
	 * @return Recupera a data da venda.
	 */
    public Date getDate() {
        return date;
    }

	/**
	 * Método que recupera o subtotal da venda.
	 * 
	 * @return Recupera o subtotal da venda.
	 */
    public double getSubtotal() {
        return subtotal;
    }

	/**
	 * Método que recupera a taxa da venda.
	 * 
	 * @return Recupera a taxa da venda.
	 */
    public double getTax() {
        return tax;
    }

	/**
	 * Método que recupera o valor total da venda.
	 * 
	 * @return Recupera o valor total da venda.
	 */
    public double getTotal() {
        return total;
    }

	/**
	 * Método que recupera o tipo de frete da venda.
	 * 
	 * @return Recupera o tipo de frete da venda.
	 */
    public String getShipType() {
        return shipType;
    }

	/**
	 * Método que recupera a data do frete da venda.
	 * 
	 * @return Recupera a data do frete da venda.
	 */
    public Date getShipDate() {
        return shipDate;
    }

	/**
	 * Método que recupera o status da venda.
	 * 
	 * @return Recupera o status da venda.
	 */
    public String getStatus() {
        return status;
    }

	/**
	 * Método que recupera o endereço de cobrança da venda.
	 * 
	 * @return Recupera o endereço de cobrança da venda.
	 */
    public Address getBillingAddress() {
        return billingAddress;
    }

	/**
	 * Método que recupera o endereço de entrega da venda.
	 * 
	 * @return Recupera o endereço de entrega da venda.
	 */
    public Address getShippingAddress() {
        return shippingAddress;
    }

	/**
	 * Método que recupera o cartão de crédito da venda.
	 * 
	 * @return Recupera o cartão de crédito da venda.
	 */
    public CCTransaction getCC() {
        return cc;
    }

    /**
     * Método que recupera itens da venda.
     * @return Recupera itens da venda.
     */
    public ArrayList<OrderLine> getLines() {
        return lines;
    }

}
