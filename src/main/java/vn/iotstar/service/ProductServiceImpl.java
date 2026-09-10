package vn.iotstar.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.Product;
import vn.iotstar.repository.ProductRepository;
import java.util.List;


@Service
public class ProductServiceImpl
        implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll(){

        return productRepository.findAll();

    }
    @Override
    public Product findById(Integer id){

        return productRepository
                .findById(id)
                .orElse(null);

    }
    @Override
    public Product save(Product product){

        return productRepository.save(product);

    }
    @Override
    public void delete(Integer id){

        productRepository.deleteById(id);

    }
    @Override
    public Product insert(Product product){

        return productRepository.save(product);

    }



    @Override
    public Product update(Product product){

        return productRepository.save(product);

    }




    @Override
    public List<Product> findTop10NewProduct(){

        return productRepository
                .findTop10ByOrderByCreatedDateDesc();

    }




    @Override
    public long count(){

        return productRepository.count();

    }




    @Override
    public List<Product> findPagination(
            int page,
            int size){


        org.springframework.data.domain.Pageable pageable =
                org.springframework.data.domain.PageRequest
                        .of(page, size);



        return productRepository
                .findAll(pageable)
                .getContent();

    }
    @Override
    public List<Product> search(String keyword){


        if(keyword == null
                || keyword.trim().isEmpty()){

            return productRepository.findAll();

        }
        return productRepository
                .searchByName(keyword.trim());
    }
}
