package dominio;

/* 
 * Address.java - Stores an address.
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
 * *<img src="./doc-files/Address.png" alt="Address">
 * <br><a href="./doc-files/Address.html"> code </a>
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 3980790290181121772L;

    private final int id;
    private final String street1;
    private final String street2;
    private final String city;
    private final String state;
    private final String zip;
    private final Country country;

    /**
     *
     * @param id -
     * @param street1 -
     * @param street2 -
     * @param city -
     * @param state -
     * @param zip -
     * @param country -
     */
    public Address(int id, String street1, String street2, String city,
            String state, String zip, Country country) {
        this.id = id;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    /**
	 * Retrieves class ID.
     *
	 * @return return an ID that represents address
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves first registered street.
     *
     * @return String street1   First registered street
     */
    public String getStreet1() {
        return street1;
    }

    /**
     * Retrieves second registered street.
     *
     * @return String street2   Second registered street
     */
    public String getStreet2() {
        return street2;
    }

    /**
     * Retrieves registered city.
     *
     * @return String city   Registered city
     */
    public String getCity() {
        return city;
    }

    /**
     * Retrieves registered state.
     *
     * @return String state   Registered state
     */
    public String getState() {
        return state;
    }

    /**
     * Retrieves zipcode.
     *
     * @return String zip   zipcode
     */
    public String getZip() {
        return zip;
    }

    /**
     * Retrieves registered country.
     *
     * @return Country country   Registered country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Verify if an object is the same as the other.
     *
     * @param o Object to be compared
     * @return True only if the object is the same.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Address) {
            Address address = (Address) o;
            return street1.equals(address.street1)
                    && street2.equals(address.street2)
                    && city.equals(address.city)
                    && state.equals(address.state)
                    && zip.equals(address.zip)
                    && country.equals(address.country);
        }
        return false;
    }

    /**
     * Used to help "equals", to identify an identical object.
     *
     * @return identification hash of the class.
     */
    @Override
    public int hashCode() {
        return street1.hashCode() + street2.hashCode() + city.hashCode()
                + state.hashCode() + zip.hashCode() + country.hashCode();
    }
}
