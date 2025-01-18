package dominio;

/* 
 * Country.java - Stores a country.
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
 * *<img src="./doc-files/Country.png" alt="Country">
 * <br><a href="./doc-files/Country.html"> code </a>
 */
public class Country implements Serializable {

    private static final long serialVersionUID = 5171617014956861344L;

    private final int id;
    private final String name;
    private final String currency;
    private final double exchange;

    /**
     *
     * @param id Id que representa o país 
     * @param name Nome do país
     * @param currency moeda do país
     * @param exchange taxa de cambio do país
     */
    public Country(int id, String name, String currency, double exchange) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.exchange = exchange;
    }

    /**
     * Recupera o ID da classe de representação do país.
     * 
     * @return id da classe que representa o país
     */
    public int getId() {
        return id;
    }

    /**
     * Recupera o nome do país.
     *
     * @return nome do país
     */
    public String getName() {
        return name;
    }

    /**
     * Recupera a moeda local do país
     *
     * @return moeda local.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Reucpera o valor de cambio do país
     * 
     * @return valor de cambio
     */
    public double getExchange() {
        return exchange;
    }

    /**
     * Verifica se um objeto é igual ao outro.
     *
     * @param o Objeto a ser comparado
     * @return Verdade se e somente se o objeto for o mesmo.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Country) {
            Country country = (Country) o;
            return name.equals(country.name);
        }
        return false;
    }

    /**
     * Utilizado em auxílio com o equals para identificar um objeto igual.
     *
     * @return hash de identificação da classe.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
