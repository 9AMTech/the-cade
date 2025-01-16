package com.example.demo.repositories;

import com.example.demo.domain.Product;
import com.example.demo.domain.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PurchaseRepositoryTest {
    Purchase purchase;
    Product product;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Purchase purchase1 = new Purchase();
        Purchase purchase2 = new Purchase();
        Purchase purchase3 = new Purchase();
        Product product1 = new Product();

        product1.setName("Cabinet");
        product1.setPrice(12.0);
        product1.setInv(5);

        purchase1.setProduct(product1);
        purchase1.setSaleDate(Date.valueOf(LocalDate.now().minusDays(8)));

        purchase2.setProduct(product1);
        purchase2.setSaleDate(Date.valueOf(LocalDate.now().minusDays(5)));

        purchase3.setProduct(product1);
        purchase3.setSaleDate(Date.valueOf(LocalDate.now().minusDays(2)));

        productRepository.save(product1);
        purchaseRepository.save(purchase1);
        purchaseRepository.save(purchase2);
        purchaseRepository.save(purchase3);
    }

    @Test
    void findPurchasesBetweenDates() {
        Date startDate = Date.valueOf(LocalDate.now().minusDays(7));
        Date endDate = Date.valueOf(LocalDate.now());
        List<Object[]> purchases = purchaseRepository.findPurchasesBetweenDates(startDate, endDate);

        assertEquals(purchases.size(), 2);
    }
}