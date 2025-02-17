package infraestrutura.database.global.interfaces;

import java.util.List;
import java.util.Map;

import dominio.*;


/**
 * Interface responsável por conter a abstração que repositorio de banco
 * de dados deve conter das classes globais de dominio
 */
public interface IGlobalDbContext {
    public List<Country> getCountryById();
    public Map<String, Country> getCountryByName();
    public List<Address> getAddressById();
    public Map<Address, Address> getAddressByAll();
    public List<Customer> getCustomersById();
    public Map<String, Customer> getCustomersByUsername();
    public List<Author> getAuthorsById();
    public List<Book> getBooksById();
    public void flushDatabase();
}
