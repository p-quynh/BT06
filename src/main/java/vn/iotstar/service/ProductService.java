package vn.iotstar.service;


import vn.iotstar.entity.Product;

import java.util.List;



public interface ProductService {


    // =========================
    // CRUD PRODUCT
    // =========================


    List<Product> findAll();


    Product findById(Integer id);


    Product save(Product product);


    void delete(Integer id);



    // Giữ lại cho Controller cũ

    Product insert(Product product);


    Product update(Product product);



    // =========================
    // SEARCH
    // =========================


    List<Product> search(String keyword);



    // =========================
    // HOME
    // =========================


    List<Product> findTop10NewProduct();



    // =========================
    // PAGINATION
    // =========================


    long count();


    List<Product> findPagination(
            int page,
            int size
    );

}