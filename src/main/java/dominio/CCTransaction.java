package dominio;

/* 
 * CCTransaction.java - Class holds data for a single credit card
 *                      transaction.
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
import java.util.Date;

/**
 * *<img src="./doc-files/CCTransaction.png" alt="CCTransaction">
 * <br><a href="./doc-files/CCTransaction.html"> code </a>
 */
public class CCTransaction implements Serializable {

    private static final long serialVersionUID = 5470177450411822726L;

    private final String type;
    private final long num;
    private final String name;
    private final Date expire;
    private final String authId;
    private final double amount;
    private final Date date;
    private final Country country;

    /**
     *
     * @param type Credit card type.
     * @param num Credit card number.
     * @param name Credit card name.
     * @param expire Credit card expiration date.
     * @param authId Credit card authenticator id.
     * @param amount Credit card amount.
     * @param date Credit card date.
     * @param country Credit card country.
     */
    public CCTransaction(String type, long num, String name, Date expire,
            String authId, double amount, Date date, Country country) {
        this.type = type;
        this.num = num;
        this.name = name;
        this.expire = expire;
        this.authId = authId;
        this.amount = amount;
        this.date = date;
        this.country = country;
    }

	/**
	 * Credit card type getter.
	 * 
	 * @return Retrieves credit card type.
	 */
    public String getType() {
        return type;
    }

	/**
	 * Credit card number getter.
	 * 
	 * @return Retrieves credit card number.
	 */
    public long getNum() {
        return num;
    }

	/**
	 * Credit card name getter.
	 * 
	 * @return Retrieves credit card name.
	 */
    public String getName() {
        return name;
    }

	/**
	 * Credit card expiration date getter.
	 * 
	 * @return Retrieves credit card expiration date.
	 */
    public Date getExpire() {
        return expire;
    }

	/**
	 * Credit card authenticator id getter.
	 * 
	 * @return Retrieves credit card authenticator id.
	 */
    public String getAuthId() {
        return authId;
    }

	/**
	 * Credit card amount getter.
	 * 
	 * @return Retrieves dredit card amount.
	 */
    public double getAmount() {
        return amount;
    }

	/**
	 * Credit card date getter.
	 * 
	 * @return Retrieves credit card date.
	 */
    public Date getDate() {
        return date;
    }

	/**
	 * Credit card country getter.
	 * 
	 * @return Retrieves credit card country.
	 */
    public Country getCountry() {
        return country;
    }

}
