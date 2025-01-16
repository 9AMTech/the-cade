package com.example.demo.controllers;

import com.example.demo.domain.Product;
import com.example.demo.domain.Purchase;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class BuyProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping("/buy-product")
    public String buyProduct(@RequestParam("productId") long id, Model theModel) {
        Optional<Product> purchasedProduct = productRepository.findById(id);
        if (purchasedProduct.isEmpty()) {
            theModel.addAttribute("title", "Buy Error");
            theModel.addAttribute("content", "buyProductFailure");
            return "layout";
        }


        Product product = purchasedProduct.get();
        if (product.getInv() < 1) {
            theModel.addAttribute("title", "Buy Error");
            theModel.addAttribute("content", "buyProductFailure");
            return "layout";
        }

        product.setInv(product.getInv() - 1);
        productRepository.save(product);

        Purchase purchase = new Purchase(product);

        purchase.setSaleDate(Date.valueOf(LocalDate.now()));
        purchaseRepository.save(purchase);

        theModel.addAttribute("title", "Buy Success");
        theModel.addAttribute("content", "buyProductSuccess");
        return "layout";
    }
}
