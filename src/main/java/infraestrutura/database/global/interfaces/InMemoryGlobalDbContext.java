package infraestrutura.database.global.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.Address;
import dominio.Author;
import dominio.Book;
import dominio.Country;
import dominio.Customer;
import infraestrutura.database.global.IGlobalDbContext;

/**
 * Classe responsável por conter o contexto de banco de dados de Bookmarket e Bookstore
 * referente as classes de dominio globais
 */
public class InMemoryGlobalDbContext implements IGlobalDbContext {
    private static List<Country> countryById = new ArrayList<>();
    private static Map<String, Country> countryByName = new HashMap<>();
    private static List<Address> addressById = new ArrayList<>();
    private static Map<Address, Address> addressByAll = new HashMap<>();
    private static List<Customer> customersById = new ArrayList<>();
    private static Map<String, Customer> customersByUsername = new HashMap<>();
    private static List<Author> authorsById = new ArrayList<>();
    private static List<Book> booksById = new ArrayList<>();
    
	@Override
	public List<Country> getCountryById() {
		return countryById;
	}

	@Override
	public Map<String, Country> getCountryByName() {
		return countryByName;
	}

	@Override
	public List<Address> getAddressById() {
		return addressById;
	}

	@Override
	public Map<Address, Address> getAddressByAll() {
		return addressByAll;
	}

	@Override
	public List<Customer> getCustomersById() {
		return customersById;
	}

	@Override
	public Map<String, Customer> getCustomersByUsername() {
		return customersByUsername;
	}

	@Override
	public List<Author> getAuthorsById() {
		return authorsById;
	}

	@Override
	public List<Book> getBooksById() {
		return booksById;
	}

	@Override
	public void flushDatabase() {
        countryById = new ArrayList<>();
        countryByName = new HashMap<>();
        addressById = new ArrayList<>();
        addressByAll = new HashMap<>();
        customersById = new ArrayList<>();
        customersByUsername = new HashMap<>();
        authorsById = new ArrayList<>();
        booksById = new ArrayList<>();
	}
}
