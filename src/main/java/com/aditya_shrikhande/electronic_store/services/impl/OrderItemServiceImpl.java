package com.aditya_shrikhande.electronic_store.services.impl;

import com.aditya_shrikhande.electronic_store.exceptions.BadApiRequestException;
import com.aditya_shrikhande.electronic_store.exceptions.ResourceNotFoundException;
import com.aditya_shrikhande.electronic_store.repositories.CartRepository;
import com.aditya_shrikhande.electronic_store.repositories.OrderRepository;
import com.aditya_shrikhande.electronic_store.repositories.UserRepository;
import com.aditya_shrikhande.electronic_store.dtos.CreateOrderRequest;
import com.aditya_shrikhande.electronic_store.dtos.OrderDto;
import com.aditya_shrikhande.electronic_store.dtos.PageableResponse;
import com.nikhilkalamdane.electronic_store.entities.*;
import com.aditya_shrikhande.electronic_store.helper.Helper;
import com.aditya_shrikhande.electronic_store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create an order based on the provided order details.
     *
     * @param orderDto The CreateOrderRequest containing order details.
     * @return The created OrderDto.
     */
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {

        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with given id !!!"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.size() < 1) {
            throw new BadApiRequestException("Invalid number of items in cart");
        }

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    /**
     * Remove an order by its ID.
     *
     * @param orderId The ID of the order to remove.
     */
    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Given orderId not found !!!"));
        orderRepository.delete(order);
    }

    /**
     * Get a list of orders for a specific user.
     *
     * @param userId The ID of the user to retrieve orders for.
     * @return A list of OrderDto objects.
     */
    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());

        return orderDtos;
    }

    /**
     * Get a paginated list of orders.
     *
     * @param pageNumber The page number to retrieve.
     * @param pageSize   The number of orders per page.
     * @param sortBy     The field to sort by.
     * @param sortDir    The sort direction (asc or desc).
     * @return A PageableResponse containing a list of OrderDto objects.
     */
    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ?
                (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page, OrderDto.class);
    }
}
