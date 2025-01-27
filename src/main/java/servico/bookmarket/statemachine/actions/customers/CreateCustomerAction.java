package servico.bookmarket.statemachine.actions.customers;

import java.util.Date;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.BookstoreAction;

/**
 * Classe que implementa as ações relacionadas a criação de cliente.
 */
public class CreateCustomerAction extends BookstoreAction {

    private static final long serialVersionUID = 6039962163348790677L;

    String fname;
    String lname;
    String street1;
    String street2;
    String city;
    String state;
    String zip;
    String countryName;
    String phone;
    String email;
    double discount;
    Date birthdate;
    String data;
    long now;

    /**
     * Método construtor da classe.
     *
     * @param fname
     * @param lname
     * @param street1
     * @param street2
     * @param city
     * @param state
     * @param zip
     * @param countryName
     * @param phone
     * @param email
     * @param discount
     * @param birthdate
     * @param data
     * @param now
     */
    public CreateCustomerAction(String fname, String lname, String street1,
            String street2, String city, String state, String zip,
            String countryName, String phone, String email,
            double discount, Date birthdate, String data, long now) {
        this.fname = fname;
        this.lname = lname;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.countryName = countryName;
        this.phone = phone;
        this.email = email;
        this.discount = discount;
        this.birthdate = birthdate;
        this.data = data;
        this.now = now;
    }

    /**
     * Método que injeta o cliente com suas respectivas informações dentro
     * da {@linkplain Bookstore}.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        return Bookstore.createCustomer(fname, lname, street1, street2,
                city, state, zip, countryName, phone, email, discount,
                birthdate, data, now);
    }
}
