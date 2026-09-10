package vn.iotstar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Product;

import java.util.List;



public interface ProductRepository
        extends JpaRepository<Product,Integer> {



    @Query("select p from Product p where lower(p.product_name) like lower(concat('%', :keyword, '%'))")
    List<Product> searchByName(@Param("keyword") String keyword);


    List<Product> findTop10ByOrderByCreatedDateDesc();


}
