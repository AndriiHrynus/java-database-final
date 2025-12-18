package com.project.code.Service;


import com.project.code.Model.*;
import com.project.code.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 1. Получить или создать клиента
        Customer existingCustomer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail());
        Customer customer = new Customer();
        customer.setName(placeOrderRequest.getCustomerName());
        customer.setEmail(placeOrderRequest.getCustomerEmail());
        customer.setPhone(placeOrderRequest.getCustomerPhone());
        if (existingCustomer == null) {
            customer = customerRepository.save(customer);
        }
        else{
            customer=existingCustomer;
        }
        // 2. Получить магазин
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Магазин не найден"));
        // 3. Создать детали заказа
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomer(customer);
        orderDetails.setStore(store);
        orderDetails.setTotalPrice(placeOrderRequest.getTotalPrice());
        orderDetails.setDate(java.time.LocalDateTime.now()); // Использовать текущее время
        orderDetails = orderDetailsRepository.save(orderDetails);
        // 4. Создать и сохранить позиции заказа (купленные товары)
        List<PurchaseProductDTO> purchaseProducts = placeOrderRequest.getPurchaseProduct();
        for (PurchaseProductDTO productDTO : purchaseProducts) {
            OrderItem orderItem = new OrderItem();
            Inventory inventory=inventoryRepository.findByProductIdandStoreId(productDTO.getId(),placeOrderRequest.getStoreId());
            inventory.setStockLevel(inventory.getStockLevel()-productDTO.getQuantity());
            inventoryRepository.save(inventory);
            orderItem.setOrder(orderDetails); // Связать заказ с позицией заказа
            orderItem.setProduct(productRepository.findByid(productDTO.getId()));
            orderItem.setQuantity(productDTO.getQuantity());
            orderItem.setPrice(productDTO.getPrice()*productDTO.getQuantity());
            orderItemRepository.save(orderItem); // Сохранить позицию заказа
        }
    }
}
