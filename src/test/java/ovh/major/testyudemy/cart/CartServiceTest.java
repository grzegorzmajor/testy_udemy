package ovh.major.testyudemy.cart;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import ovh.major.testyudemy.order.Order;
import ovh.major.testyudemy.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);
        then(cartHandler).should().sendToPrepare(cart); //bbd

        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        //poniżej test kolejności wykonania metod
        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldNotSendToPrepare() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(cart); //bbd
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }


    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);
        //zabronione jest mieszanie matcherów i prawdziwych wartości!!!!!
        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(any(Cart.class));
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class)); //bbd
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);

        given(cartHandler.canHandleCart(any(Cart.class))).willReturn(true, false, false, true);

        //when

        //then
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(false));
        assertThat(cartHandler.canHandleCart(cart), equalTo(true));

    }


    @Test
    void processCartShouldSendToPrepareWithLambdas() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart); //bbd
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willThrow(IllegalArgumentException.class);

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> cartService.processCart(cart));
    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {

        // poprzednio sprawdzalismy czy dana metoda została wywołana na danym mocku
        // argumentCaptor - możemy sprawdzić z jakimi argumentami została wywołana metoda

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        //verify(cartHandler).sendToPrepare(argumentCaptor.capture());
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }


}
