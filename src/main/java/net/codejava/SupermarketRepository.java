package net.codejava;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupermarketRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p " +
           "WHERE (:code IS NULL OR p.code LIKE CONCAT(:code, '%')) " +
           "AND (:description IS NULL OR p.description LIKE CONCAT(:description, '%')) " +
           "AND (:category IS NULL OR p.category LIKE CONCAT(:category, '%'))")
    List<Product> find(@Param("code") String code, 
                       @Param("description") String description, 
                       @Param("category") String category);
}
