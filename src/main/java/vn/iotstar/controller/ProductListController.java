package vn.iotstar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import vn.iotstar.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductListController {



    @Autowired
    private ProductService productService;



    /*
     * Hiển thị danh sách sản phẩm
     *
     * URL:
     *
     * /product?page=0
     *
     */
    @GetMapping
    public String productList(

            @RequestParam(
                    value = "page",
                    defaultValue = "0"
            )
            int page,

            Model model

    ){


        int size = 6;
        page = Math.max(page, 0);



        long totalProducts =
                productService.count();



        int totalPages =
                (int)Math.ceil(
                        (double) totalProducts / size
                );

        if (totalPages > 0 && page >= totalPages) {
            page = totalPages - 1;
        }



        model.addAttribute(
                "products",
                productService.findPagination(
                        page,
                        size
                )
        );



        model.addAttribute(
                "currentPage",
                page
        );



        model.addAttribute(
                "totalPages",
                totalPages
        );



        return "client/product";

    }


}
