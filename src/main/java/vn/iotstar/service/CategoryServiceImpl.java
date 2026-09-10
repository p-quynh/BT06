package vn.iotstar.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.CategoryRepository;
import vn.iotstar.service.CategoryService;


import java.util.List;


@Service
public class CategoryServiceImpl
        implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public List<Category> findAll() {

        return categoryRepository.findAll();

    }



    @Override
    public List<Category> search(String keyword) {


        if(keyword == null || keyword.trim().isEmpty()){

            return categoryRepository.findAll();

        }


        return categoryRepository
                .searchByName(keyword.trim());

    }

    @Override
    public Page<Category> findPage(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), size);
        return categoryRepository.searchByName(
                keyword == null ? "" : keyword.trim(),
                pageable
        );
    }



    @Override
    public Category findById(Integer id) {

        return categoryRepository
                .findById(id)
                .orElse(null);

    }



    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);

    }



    @Override
    public void delete(Integer id) {

        categoryRepository.deleteById(id);

    }


}
