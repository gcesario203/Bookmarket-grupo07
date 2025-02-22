package servico.bookmarket.statemachine.actions.orders;

import java.util.Date;
import java.util.stream.Stream;

import servico.bookstore.Bookstore;
import servico.bookmarket.statemachine.actions.ExpecificBookstoreAction;

/**
 * Class that adds order confirmation actions.
 */
public class ConfirmBuyAction extends ExpecificBookstoreAction {

    private static final long serialVersionUID = -6180290851118139002L;

    final int customerId, cartId;
    String comment;
    String ccType;
    long ccNumber;
    String ccName;
    Date ccExpiry;
    String shipping;
    Date shippingDate;
    int addressId;
    long now;

    /**
     * Class constructor method.
     *
     * @param customerId
     * @param cartId
     * @param comment
     * @param ccType
     * @param ccNumber
     * @param ccName
     * @param ccExpiry
     * @param shipping
     * @param shippingDate
     * @param addressId
     * @param now
     */
    public ConfirmBuyAction(int storeId, int customerId, int cartId,
            String comment, String ccType, long ccNumber,
            String ccName, Date ccExpiry, String shipping,
            Date shippingDate, int addressId, long now) {
        super(storeId);
        this.customerId = customerId;
        this.cartId = cartId;
        this.comment = comment;
        this.ccType = ccType;
        this.ccNumber = ccNumber;
        this.ccName = ccName;
        this.ccExpiry = ccExpiry;
        this.shipping = shipping;
        this.shippingDate = shippingDate;
        this.addressId = addressId;
        this.now = now;
    }

    /**
     * Confirms a shopping cart order of a {@linkplain Bookstore}.
     *
     * @param bookstore
     * @return
     */
    @Override
    public Object executeOnBookstore(Stream<Bookstore> bookstore) {
        return getBookstoreById(bookstore).confirmBuy(customerId, cartId, comment, ccType,
                ccNumber, ccName, ccExpiry, shipping, shippingDate,
                addressId, now);
    }
}