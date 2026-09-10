package vn.iotstar.service;

import org.springframework.data.domain.Page;
import vn.iotstar.entity.Category;
import java.util.List;
public interface CategoryService {
    List<Category> findAll();
    List<Category> search(String keyword);
    Page<Category> findPage(String keyword, int page, int size);
    Category findById(Integer id);
    Category save(Category category);
    void delete(Integer id);
}
