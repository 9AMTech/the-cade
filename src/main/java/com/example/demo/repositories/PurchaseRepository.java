package com.example.demo.repositories;

import com.example.demo.domain.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
// Using parameterized query for preventing SQL Injection!
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    @Query("" +
            "SELECT p.id, prod.name, prod.price, p.saleDate " +
            "FROM Purchase p " +
            "JOIN p.product prod " +
            "WHERE p.saleDate " +
            "BETWEEN :startDate " +
            "AND :endDate " +
            "ORDER BY p.saleDate ASC"
    )
    List<Object[]> findPurchasesBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

/*
* public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    public List<Product> search(String keyword);
}
*
* */