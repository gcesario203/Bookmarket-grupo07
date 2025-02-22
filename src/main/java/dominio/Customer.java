package dominio;

/* 
 * Customer.java - stores the important information for a single customer. 
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
import java.util.Objects;

import dominio.customer.enums.Type;

/**
 * *<img src="./doc-files/Customer.png" alt="Customer">
 * <br><a href="./doc-files/Customer.html"> code </a>
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = -7297414189618511748L;

    private final int id;
    private final String uname;
    private final String passwd;
    private final String fname;
    private final String lname;
    private final String phone;
    private final String email;
    private final Date since;
    private final Date lastVisit;
    private Date login;
    private Date expiration;
    private final double discount;
    private final double balance;
    private final double ytdPmt;
    private final Date birthdate;
    private final String data;
    private final Address address;
    private Order mostRecentOrder;
    private dominio.customer.enums.Type type;

    /**
     *
     * @param id Class Id
     * @param uname Customer's identification name
     * @param passwd Customer's password
     * @param fname Customer's first name
     * @param lname Customer's last name
     * @param phone Customer's telephone
     * @param email Customer's email
     * @param since Customer's initial date
     * @param lastVisit Customer's last visit
     * @param login Customer's login date
     * @param expiration Login expiration date
     * @param discount Possible discount
     * @param balance Accounting balance
     * @param ytdPmt ytdPmt
     * @param birthdate Customer's birthday date
     * @param data Customer's data
     * @param address Customer's address
     */
    public Customer(int id, String uname, String passwd, String fname,
            String lname, String phone, String email, Date since,
            Date lastVisit, Date login, Date expiration, double discount,
            double balance, double ytdPmt, Date birthdate, String data,
            Address address, dominio.customer.enums.Type type) {
        this.id = id;
        this.uname = uname;
        this.passwd = passwd;
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.email = email;
        this.since = since;
        this.lastVisit = lastVisit;
        this.login = login;
        this.expiration = expiration;
        this.discount = discount;
        this.balance = balance;
        this.ytdPmt = ytdPmt;
        this.birthdate = birthdate;
        this.data = data;
        this.address = address;
        mostRecentOrder = null;
        
        setType(type);
    }

    /**
     *
     * @param id
     * @param uname
     * @param toLowerCase
     * @param fname
     * @param lname
     * @param phone
     * @param email
     * @param since
     * @param lastVisit
     */
    public Customer(int id, String uname, String toLowerCase, String fname, String lname, String phone, String email, Date since, Date lastVisit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Sets Customer's login date.
     *
     * @param login  Customer's login date representation
     */
    public void setLogin(Date login) {
        this.login = login;
    }

    /**
     * Sets Customer's expiration login date.
     *
     * @param expiration Customer's expiration login date representation
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * Customer's first name getter
     *
     * @return Customer's first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Customer's last name getter
     *
     * @return Customer's last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Class Id getter
     *
     * @return Class Id
     */
    public int getId() {
        return id;
    }

    /**
     * Customer's password getter
     *
     * @return Customer's password
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Possible discount getter
     *
     * @return Possible discount for the Customer.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Customer's address getter
     *
     * @return Customer's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Customer's telephone getter
     *
     * @return Customer's telephone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Customer's email getter
     *
     * @return Customer's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Customer's identification name getter
     *
     * @return Customer's identification name
     */
    public String getUname() {
        return uname;
    }

    /**
     * Insert a new recent order log into a new order
     *
     * @param order that represents order made
     */
    public void logOrder(Order order) {
        mostRecentOrder = order;
    }

    /**
     * Last order made getter
     *
     * @return Customer's last order made
     */
    public Order getMostRecentOrder() {
        return mostRecentOrder;
    }

    /**
     * Customer's initial date getter
     *
     * @return Customer's initial date
     */
    public Date getSince() {
        return since;
    }

    /**
     * Customer's last visit getter
     *
     * @return Customer's last visit
     */
    public Date getLastVisit() {
        return lastVisit;
    }

    /**
     * Customer's login date getter
     *
     * @return Customer's login date
     */
    public Date getLogin() {
        return login;
    }

    /**
     * Customer's login expiration date getter
     *
     * @return Customer's login expiration date
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * Accounting balance getter
     *
     * @return Accounting balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Recupera ytdPmt
     *
     * @return ytdPmt
     */
    public double getYtdPmt() {
        return ytdPmt;
    }

    /**
     * Customer's birthday date getter
     *
     * @return Customer's birthday date
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Customer's data getter
     *
     * @return Customer's data
     */
    public String getData() {
        return data;
    }
    
    public dominio.customer.enums.Type getType(){
    	return this.type;
    }
    
    public void setType(dominio.customer.enums.Type type) {
    	if(type == null) {
    		this.type = Type.DEFAULT;
    		
    		return;
    	}
    	
    	this.type = type;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.uname);
        hash = 29 * hash + Objects.hashCode(this.passwd);
        hash = 29 * hash + Objects.hashCode(this.fname);
        hash = 29 * hash + Objects.hashCode(this.lname);
        hash = 29 * hash + Objects.hashCode(this.phone);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.since);
        hash = 29 * hash + Objects.hashCode(this.lastVisit);
        hash = 29 * hash + Objects.hashCode(this.login);
        hash = 29 * hash + Objects.hashCode(this.expiration);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.discount) ^ (Double.doubleToLongBits(this.discount) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.balance) ^ (Double.doubleToLongBits(this.balance) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.ytdPmt) ^ (Double.doubleToLongBits(this.ytdPmt) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.birthdate);
        hash = 29 * hash + Objects.hashCode(this.data);
        hash = 29 * hash + Objects.hashCode(this.address);
        hash = 29 * hash + Objects.hashCode(this.mostRecentOrder);
        hash = 29 * hash + Objects.hashCode(this.type);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.discount) != Double.doubleToLongBits(other.discount)) {
            return false;
        }
        if (Double.doubleToLongBits(this.balance) != Double.doubleToLongBits(other.balance)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ytdPmt) != Double.doubleToLongBits(other.ytdPmt)) {
            return false;
        }
        if (!Objects.equals(this.uname, other.uname)) {
            return false;
        }
        if (!Objects.equals(this.passwd, other.passwd)) {
            return false;
        }
        if (!Objects.equals(this.fname, other.fname)) {
            return false;
        }
        if (!Objects.equals(this.lname, other.lname)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.since, other.since)) {
            return false;
        }
        if (!Objects.equals(this.lastVisit, other.lastVisit)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.expiration, other.expiration)) {
            return false;
        }
        if (!Objects.equals(this.birthdate, other.birthdate)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.mostRecentOrder, other.mostRecentOrder)) {
            return false;
        }
        
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        
        return true;
    }

}
