package vn.iotstar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import vn.iotstar.entity.Product;
import vn.iotstar.service.ProductService;



@Controller
@RequestMapping("/product-detail")
public class ProductDetailController {



    @Autowired
    private ProductService productService;





    /*
     * Chi tiết sản phẩm
     *
     * URL:
     *
     * /product-detail?id=1
     *
     */
    @GetMapping
    public String detailProduct(

            @RequestParam("id")
            Integer id,

            Model model

    ){



        Product product =
                productService.findById(id);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm");
        }



        model.addAttribute(
                "product",
                product
        );



        return "client/product-detail";

    }



}
