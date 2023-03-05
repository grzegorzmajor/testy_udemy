package ovh.major.testyudemy.cart;

public interface CartHandler {

    boolean canHandleCart(Cart cart);

    void sendToPrepare(Cart cart);

    default boolean isDeliveryFree(Cart cart) {
        return cart.getOrders().size() > 2;
    }
}
