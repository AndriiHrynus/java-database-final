package com.project.code.Model;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id; // MongoDB использует String для поля ID
    @NotNull(message = "Покупатель не может быть пустым")
    private Long customerId; // Покупатель, который создал отзыв
    @NotNull(message = "Продукт не может быть пустым")
    private Long productId; // Продукт, который оценивается
    @NotNull(message = "Магазин не может быть пустым")
    private Long storeId; // Магазин, связанный с продуктом
    @NotNull(message = "Рейтинг не может быть пустым")
    private Integer rating; // Рейтинг от 5
    private String comment; // Опциональный комментарий к продукту
    // Конструкторы
    public Review() {
    }
    public Review(Long customerId, Long productId, Long storeId, Integer rating, String comment) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
        this.comment = comment;
    }
    // Геттеры и Сеттеры
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }


}
