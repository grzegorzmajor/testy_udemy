package ovh.major.testyudemy.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ovh.major.testyudemy.order.Order;
import ovh.major.testyudemy.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

//@RunWith(MockitoJUnitRummer.class) - dla 4
@MockitoSettings(strictness = Strictness.STRICT_STUBS) //zwiększenie restrykcyjności
@ExtendWith(MockitoExtension.class) // dla 5
public class CartServiceTest {

    @InjectMocks //określenie klasy w której znajdują się zależności które w testach zamieniamy w mocki
    private CartService cartService; // ta klasa będzie miała zalezności które będziemy mockować
    @Mock //określa obiekt mockowy
    private CartHandler cartHandler; // tożsama z metodą mock
    @Captor
    private ArgumentCaptor<Cart> argumentCaptor;

    @Test
    void processCartShouldSendToPrepare() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //zastąpione adnotacjami @InjectMocks i @Mock
        //CartHandler cartHandler = mock(CartHandler.class);
        //CartService cartService = new CartService(cartHandler);

        given(cartHandler.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);
        then(cartHandler).should().sendToPrepare(cart); //bdd

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

        given(cartHandler.canHandleCart(cart)).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandler, never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(cart); //bdd
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
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class)); //bdd
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

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

        given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart); //bdd
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

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

        //zastąpione adnotacją @Captor
        //ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

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

    @Test
    void shouldDoNothingWhenProcessCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //CartHandler cartHandler = mock(CartHandler.class);
        //CartService cartService = new CartService(cartHandler);
        given(cartHandler.canHandleCart(cart)).willReturn(true);

        // może się przydać gdy mamy kilkukrotne wywołanie metody i chcemy by za
        // pierwszym razem nie zostało zrobione nic ale za kolejnym  metoda np. rzuciła wyjątkiem
        // mozna wtedy skorzystać z chainowania metod
        // alternatywny zapis: //doNothing().when(cartHandler).sendToPrepare(cart);
        // alternatywny zapis: //willDoNothing().given(cartHandler).sendToPrepare(cart); // bardziej bdd
        //np. taki zapis jak poniżej - przy drugim wywołaniu metody sendToPrepare rzuci wyjątkiem
        willDoNothing().willThrow(IllegalStateException.class).given(cartHandler).sendToPrepare(cart);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }


    @Test
    void shouldAnswerWhenProcessCart() {

        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        //CartHandler cartHandler = mock(CartHandler.class);
        //CartService cartService = new CartService(cartHandler);

        //gdy potrzeba wykonac operacje z argumentami które sa przekazywane sa do metody wykonywanej na mocku
//        doAnswer(invocationOnMock -> {
//            Cart argumentCart = invocationOnMock.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        }).when(cartHandler).canHandleCart(cart);

        //można tak jak poniżej
//        when(cartHandler.canHandleCart(cart)).then(i -> {
//            Cart argumentCart = i.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        });

        //bardziej przyjazne bdd
        willAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).given(cartHandler).canHandleCart(cart);

        //można tak jak poniżej
        given(cartHandler.canHandleCart(cart)).will(i -> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart); //bbd
        assertThat(resultCart.getOrders().size(), equalTo(0));
    }

    @Test
    void deliveryShouldBeFree() {

        //given
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

        // doCallRealMethod().when(cartHandler).isDeliveryFree(cart); - wersja klasyczna
        given(cartHandler.isDeliveryFree(cart)).willCallRealMethod(); // bdd

        //when
        boolean isDeliveryFree = cartHandler.isDeliveryFree(cart);

        //then
        assertTrue(isDeliveryFree);
    }

}
