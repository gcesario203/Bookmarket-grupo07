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
	 * Constructor method of Author's class.
	 *
	 * @param fname     Author's first name.
	 * @param mname     Author's middle name.
	 * @param lname     Author's last name.
	 * @param birthdate Author's birthday.
	 * @param bio       Author's biography.
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
	 * Author's first name getter.
	 *
	 * @return Author's first name.
	 */
    public String getFname() {
        return fname;
    }

	/**
	 * Author's last name getter.
	 * 
	 * @return Author's last name.
	 */
    public String getLname() {
        return lname;
    }

	/**
	 * Author's middle name getter.
	 * 
	 * @return Author's middle name.
	 */
    public String getMname() {
        return mname;
    }

	/**
	 * Author's birthday date getter.
	 * 
	 * @return Date birthdate Author's birthday date.
	 */
    public Date getBirthdate() {
        return birthdate;
    }

	/**
	 * Author's biography getter.
	 * 
	 * @return Author's biography.
	 */
    public String getBio() {
        return bio;
    }

}
