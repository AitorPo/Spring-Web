package com.svalero.springweb.service.order;

import com.svalero.springweb.domain.Order;
import com.svalero.springweb.domain.OrderDetail;
import com.svalero.springweb.domain.Product;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.domain.dto.OrderDTO;
import com.svalero.springweb.exception.OrderNotFoundException;
import com.svalero.springweb.exception.VendorNotFoundException;
import com.svalero.springweb.repository.OrderDetailRepository;
import com.svalero.springweb.repository.OrderRepository;
import com.svalero.springweb.repository.ProductRepository;
import com.svalero.springweb.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Set<Order> findAll() {
        return orderRepository.getAllOrders();
    }

    // Utilizamos el DTO para crear un pedido en nuestra BD
    @Override
    public Order addOrder(OrderDTO orderDTO) {
        String number = UUID.randomUUID().toString();
        Vendor vendor = vendorRepository.getVendor(orderDTO.getVendorId());
        Order newOrder = new Order();
        newOrder.setNumber(number);
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPrice(orderDTO.getPrice());
        newOrder.setVendor(vendor);
        newOrder.setShipped(false);


        // Hasta que no hacemos .save el pedido no está introducido en nuestra BD y por tanto no tiene Id ni existe
        newOrder = orderRepository.save(newOrder);

        for (String productName : orderDTO.getProducts()) {
            Product product = productRepository.getProductByName(productName);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(product.getPrice());
            orderDetail.setEstablishment(vendor.getShop().getName());
            orderDetail.setQuantity(1);
            orderDetail.setOrder(newOrder);
            orderDetail.setByCard(false);
            orderDetail.setCreatedAt(LocalDateTime.now());
            orderDetail.setProduct(product);
            newOrder.addDetail(orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        return newOrder;
    }

    @Override
    public Order modifyOrder(long id, Order newOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        newOrder.setId(order.getId());
        return orderRepository.save(newOrder);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrder(long id) {
        return orderRepository.getOrder(id);
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Float getAveragePrice(long vendorId) {
        if (vendorId == 0)
            return orderRepository.getAveragePrice();
        vendorRepository.findById(vendorId). orElseThrow(() -> new VendorNotFoundException(vendorId));

        return orderRepository.getAveragePricePerVendor(vendorId);
    }

    @Override
    public Optional<Order> getOrderByVendor(long vendorId) {
        return orderRepository.getOrderByVendor(vendorId);
    }
}
