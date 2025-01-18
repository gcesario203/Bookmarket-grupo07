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
     * @param type Tipo do cartão de crédito.
     * @param num Número do cartão de crédito.
     * @param name Nome do cartão de crédito.
     * @param expire Data de expiração do cartão de crédito.
     * @param authId Id de autenticação do cartão de crédito.
     * @param amount Quantidade do cartão de crédito.
     * @param date Data do cartão de crédito.
     * @param country País do cartão de crédito.
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
	 * Método que recupera o tipo do cartão de crédito.
	 * 
	 * @return Recupera o tipo do cartão de crédito.
	 */
    public String getType() {
        return type;
    }

	/**
	 * Método que recupera o número do cartão de crédito.
	 * 
	 * @return Recupera o número do cartão de crédito.
	 */
    public long getNum() {
        return num;
    }

	/**
	 * Método que recupera o nome do cartão de crédito.
	 * 
	 * @return Recupera o nome do cartão de crédito.
	 */
    public String getName() {
        return name;
    }

	/**
	 * Método a data de expiração do cartão de crédito.
	 * 
	 * @return Recupera a data de expiração do cartão de crédito.
	 */
    public Date getExpire() {
        return expire;
    }

	/**
	 * Método que recupera o id de autentição do cartão de crédito.
	 * 
	 * @return Recupera o id de autenticação do cartão de crédito.
	 */
    public String getAuthId() {
        return authId;
    }

	/**
	 * Método que recupera a quantidade do cartão de crédito.
	 * 
	 * @return Recupera a quantidade do cartão de crédito.
	 */
    public double getAmount() {
        return amount;
    }

	/**
	 * Método que recupera a data do cartão de crédito.
	 * 
	 * @return Recupera a data do cartão de crédito.
	 */
    public Date getDate() {
        return date;
    }

	/**
	 * Método que recupera o país do cartão de crédito.
	 * 
	 * @return Recupera o país do cartão de crédito.
	 */
    public Country getCountry() {
        return country;
    }

}
