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
	 * @param id              Order identifier.
	 * @param customer        Order's Customer.
	 * @param date            Sell date.
	 * @param cart            Order's shopping cart.
	 * @param comment         Order's comment.
	 * @param shipType        Order's ship type.
	 * @param shipDate        Order's ship date.
	 * @param status          Order's status.
	 * @param billingAddress  Order's billing address.
	 * @param shippingAddress Order's shipping address.
	 * @param cc              Order's credit card.
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
	 * Order's identifier getter.
	 * 
	 * @return Retrieves Order's identifier.
	 */
    public int getId() {
        return id;
    }

	/**
	 * Order's Customer getter.
	 * 
	 * @return Retrieves Order's Customer.
	 */
    public Customer getCustomer() {
        return customer;
    }

	/**
	 * Sell date getter.
	 * 
	 * @return Retrieves sell date.
	 */
    public Date getDate() {
        return date;
    }

	/**
	 * Order's subtotal getter.
	 * 
	 * @return Retrieves Order's subtotal.
	 */
    public double getSubtotal() {
        return subtotal;
    }

	/**
	 * Order's tax getter.
	 * 
	 * @return Retrieves Order's tax.
	 */
    public double getTax() {
        return tax;
    }

	/**
	 * Order's total value getter.
	 * 
	 * @return Retrieves Order's total value.
	 */
    public double getTotal() {
        return total;
    }

	/**
	 * Order's ship type getter.
	 * 
	 * @return Retrieves Order's ship type.
	 */
    public String getShipType() {
        return shipType;
    }

	/**
	 * Order's ship date getter.
	 * 
	 * @return Retrieves Order's ship date.
	 */
    public Date getShipDate() {
        return shipDate;
    }

	/**
	 * Order's status getter.
	 * 
	 * @return Retrieves Order's status.
	 */
    public String getStatus() {
        return status;
    }

	/**
	 * Order's billing address getter.
	 * 
	 * @return Retrieves Order's billing address.
	 */
    public Address getBillingAddress() {
        return billingAddress;
    }

	/**
	 * Order's shipping address getter.
	 * 
	 * @return Retrieves Order's shipping address.
	 */
    public Address getShippingAddress() {
        return shippingAddress;
    }

	/**
	 * Order's credit card getter.
	 * 
	 * @return Retrieves Order's credit card.
	 */
    public CCTransaction getCC() {
        return cc;
    }

    /**
     * Order's items getter.
     * @return Retrieves Order's items.
     */
    public ArrayList<OrderLine> getLines() {
        return lines;
    }

}
