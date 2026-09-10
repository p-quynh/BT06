package vn.iotstar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import vn.iotstar.service.ProductService;



@Controller
public class HomeController {



    @Autowired
    private ProductService productService;




    @GetMapping({"/","/home"})
    public String home(Model model){



        model.addAttribute(
                "products",
                productService.findTop10NewProduct()
        );



        return "client/home";

    }


}