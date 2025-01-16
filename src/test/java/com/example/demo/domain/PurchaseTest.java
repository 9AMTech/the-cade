package com.example.demo.domain;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {
    Purchase purchase;
    Product product;

    @BeforeEach
    void setUp() {
        purchase = new Purchase();
        product = new Product();
    }

    @Test
    void getId() {
        Long idValue = 1L;
        purchase.setId(idValue);
        assertEquals(purchase.getId(), idValue);
    }

    @Test
    void setId() {
        Long idValue = 1L;
        purchase.setId(idValue);
        assertEquals(purchase.getId(), idValue);
    }

    @Test
    void getProduct() {
        Long productId = 1L;
        String productName = "Cabinet";
        product.setId(productId);
        product.setName("Cabinet");
        purchase.setProduct(product);
        assertEquals(purchase.getProduct().getId(), productId);
        assertEquals(purchase.getProduct().getName(), productName);
    }

    @Test
    void setProduct() {
        Long productId = 1L;
        String productName = "Cabinet";
        product.setId(productId);
        product.setName("Cabinet");
        purchase.setProduct(product);
        assertEquals(purchase.getProduct().getId(), productId);
        assertEquals(purchase.getProduct().getName(), productName);
    }

    @Test
    void getSaleDate() {
        Date saleDate = Date.valueOf(LocalDate.now());
        purchase.setSaleDate(saleDate);
        assertEquals(purchase.getSaleDate(), saleDate);
    }

    @Test
    void setSaleDate() {
        Date saleDate = Date.valueOf(LocalDate.now());
        purchase.setSaleDate(saleDate);
        assertEquals(purchase.getSaleDate(), saleDate);
    }

}