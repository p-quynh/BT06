package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.iotstar.entity.Category;


import java.util.List;


public interface CategoryRepository
        extends JpaRepository<Category,Integer>{


    @Query("select c from Category c where lower(c.cate_name) like lower(concat('%', :keyword, '%')) order by c.cate_id asc")
    List<Category> searchByName(@Param("keyword") String keyword);

    @Query(value = "select c from Category c where lower(c.cate_name) like lower(concat('%', :keyword, '%')) order by c.cate_id asc",
            countQuery = "select count(c) from Category c where lower(c.cate_name) like lower(concat('%', :keyword, '%'))")
    Page<Category> searchByName(@Param("keyword") String keyword, Pageable pageable);


}
