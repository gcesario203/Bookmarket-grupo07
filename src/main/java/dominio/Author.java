package dominio;

/*
 * Author.java - Data about an author. 
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
 * *<img src="./doc-files/Author.png" alt="Author">
 * <br><a href="./doc-files/Author.html"> code </a>
 */
public class Author implements Serializable {

    private static final long serialVersionUID = 8882043540800200706L;

    private final String fname;
    private final String mname;
    private final String lname;
    private final Date birthdate;
    private final String bio;

	/**
	 * Método construtor da classe Author.
	 *
	 * @param fname     Primeiro nome do autor.
	 * @param mname     Nome do meio do autor.
	 * @param lname     Último nome do autor.
	 * @param birthdate Data de aniversário do autor.
	 * @param bio       Biografia do autor.
	 */
    public Author(String fname, String mname, String lname, Date birthdate,
            String bio) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.birthdate = birthdate;
        this.bio = bio;
    }

	/**
	 * Método que recupera o primeiro nome do autor.
	 *
	 * @return Primeiro nome do autor.
	 */
    public String getFname() {
        return fname;
    }

	/**
	 * Método que recupera o último nome do autor.
	 * 
	 * @return Último nome do autor.
	 */
    public String getLname() {
        return lname;
    }

	/**
	 * Método que recupera o nome do meio do autor.
	 * 
	 * @return Nome do meio do autor.
	 */
    public String getMname() {
        return mname;
    }

	/**
	 * Método que recupera a data de aniversário do autor.
	 * 
	 * @return Date birthdate Data de aniversário do autor.
	 */
    public Date getBirthdate() {
        return birthdate;
    }

	/**
	 * Método que recupera a biografia do autor.
	 * 
	 * @return Biografia do autor.
	 */
    public String getBio() {
        return bio;
    }

}
