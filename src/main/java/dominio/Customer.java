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
     * @param id Id que representa a classe
     * @param uname Nome de identificação do Customer
     * @param passwd senha do Customer
     * @param fname primeiro nome do Customer
     * @param lname último nome do customer
     * @param phone telefone do Customer
     * @param email email do customer
     * @param since data de início do custoemr
     * @param lastVisit última visita ao customer
     * @param login data de login do customer
     * @param expiration data de expiração de login
     * @param discount desconto possibilitado 
     * @param balance balanço contábil
     * @param ytdPmt ytdPmt
     * @param birthdate data de nascimento do Customer
     * @param data Dados do Customer
     * @param address Endereço do Customer
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
        
        if(type == null) {
           	this.type = type;
           	
           	return;
        }
        
        
        this.type = Type.DEFAULT;
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
     * Define a data de login do Customer.
     *
     * @param login representação do horário de login do Customer
     */
    public void setLogin(Date login) {
        this.login = login;
    }

    /**
     * Define uma data de expiração para o login do Customer.
     *
     * @param expiration data representando o limite para expiração do login do Customer.
     */
    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * Recupera o primeiro nome do Customer
     *
     * @return primeiro nome do Customer
     */
    public String getFname() {
        return fname;
    }

    /**
     * Recupera o último nome do Customer
     *
     * @return último nome do Customer
     */
    public String getLname() {
        return lname;
    }

    /**
     * Recupera o ID da classe
     *
     * @return Id que representa a classe
     */
    public int getId() {
        return id;
    }

    /**
     * Recupera a senha do Customer
     *
     * @return senha do Customer 
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Recupera o desconto que este Customer possibilita
     *
     * @return desconto possibilitado pelo Customer.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Recupera endereço do Address
     *
     * @return Endereço do Customer
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Recupera telefone do Customer
     *
     * @return telefone do customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Recupera o email do Customer
     *
     * @return email do Customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * Recupera o nome de identificação do Customer
     *
     * @return nome de identificação do Customer.
     */
    public String getUname() {
        return uname;
    }

    /**
     * Insere uma nova compra no log de compra recente
     *
     * @param order que representa a última compra efetuada
     */
    public void logOrder(Order order) {
        mostRecentOrder = order;
    }

    /**
     * Recupera a última compra efetuada
     *
     * @return última compra efetuada neste Customer.
     */
    public Order getMostRecentOrder() {
        return mostRecentOrder;
    }

    /**
     * Recupera data de início do customer
     *
     * @return data em que o Customer iniciou as atividades
     */
    public Date getSince() {
        return since;
    }

    /**
     * Recupera última visita ao Customer
     *
     * @return Data da última visita ao customer
     */
    public Date getLastVisit() {
        return lastVisit;
    }

    /**
     * Recupera data de login do Customer
     *
     * @return Data de login
     */
    public Date getLogin() {
        return login;
    }

    /**
     * Recupera data de expiração apra o login do Customer
     *
     * @return data de expiração de login
     */
    public Date getExpiration() {
        return expiration;
    }

    /**
     * Recupera o balanço contábil
     *
     * @return balanço contábil
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
     * Recupera data de nascimento do Customer
     *
     * @return Data de nascimento do Customer
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Recupera dados do Customer
     *
     * @return dados do Customer
     */
    public String getData() {
        return data;
    }
    
    public dominio.customer.enums.Type getType(){
    	return this.type;
    }
    
    public void setType(dominio.customer.enums.Type type) {
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
        return true;
    }

   



}
