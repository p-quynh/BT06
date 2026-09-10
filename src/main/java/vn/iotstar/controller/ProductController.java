package vn.iotstar.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.service.CategoryService;
import vn.iotstar.service.ProductService;
import vn.iotstar.util.CsrfUtil;
import vn.iotstar.util.ImageStorage;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("products", productService.findAll());
        addCsrf(model, request);
        return "admin/product/list-product";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpServletRequest request) {
        prepareForm(model, request);
        return "admin/product/add-product";
    }

    @PostMapping("/add")
    public String add(@RequestParam("product_name") String name,
                      @RequestParam("price") BigDecimal price,
                      @RequestParam(value = "description", required = false) String description,
                      @RequestParam("cate_id") Integer categoryId,
                      @RequestParam(value = "image", required = false) MultipartFile image,
                      HttpServletRequest request, Model model, RedirectAttributes redirect) {
        checkCsrf(request);
        try {
            Product product = new Product();
            applyValues(product, name, price, description, categoryId);
            product.setImage(ImageStorage.store(image, "products"));
            productService.save(product);
            redirect.addFlashAttribute("flashMessage", "Thêm sản phẩm thành công");
            return "redirect:/admin/product";
        } catch (Exception ex) {
            model.addAttribute("message", ex.getMessage());
            prepareForm(model, request);
            return "admin/product/add-product";
        }
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam("id") Integer id, Model model, HttpServletRequest request) {
        model.addAttribute("product", requireProduct(id));
        prepareForm(model, request);
        return "admin/product/edit-product";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("product_id") Integer id,
                       @RequestParam("product_name") String name,
                       @RequestParam("price") BigDecimal price,
                       @RequestParam(value = "description", required = false) String description,
                       @RequestParam("cate_id") Integer categoryId,
                       @RequestParam(value = "image", required = false) MultipartFile image,
                       HttpServletRequest request, Model model, RedirectAttributes redirect) {
        checkCsrf(request);
        Product product = requireProduct(id);
        try {
            applyValues(product, name, price, description, categoryId);
            String fileName = ImageStorage.store(image, "products");
            if (fileName != null) product.setImage(fileName);
            productService.save(product);
            redirect.addFlashAttribute("flashMessage", "Cập nhật sản phẩm thành công");
            return "redirect:/admin/product";
        } catch (Exception ex) {
            model.addAttribute("product", product);
            model.addAttribute("message", ex.getMessage());
            prepareForm(model, request);
            return "admin/product/edit-product";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id, HttpServletRequest request,
                         RedirectAttributes redirect) {
        checkCsrf(request);
        productService.delete(id);
        redirect.addFlashAttribute("flashMessage", "Xóa sản phẩm thành công");
        return "redirect:/admin/product";
    }

    private void applyValues(Product product, String name, BigDecimal price,
                             String description, Integer categoryId) {
        String value = name == null ? "" : name.trim();
        if (value.isEmpty() || value.length() > 255) {
            throw new IllegalArgumentException("Tên sản phẩm phải có từ 1 đến 255 ký tự");
        }
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("Giá sản phẩm phải lớn hơn 0");
        }
        Category category = categoryService.findById(categoryId);
        if (category == null) throw new IllegalArgumentException("Category không tồn tại");
        product.setProduct_name(value);
        product.setPrice(price);
        product.setDescription(description == null ? null : description.trim());
        product.setCategory(category);
    }

    private Product requireProduct(Integer id) {
        Product product = productService.findById(id);
        if (product == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm");
        return product;
    }

    private void prepareForm(Model model, HttpServletRequest request) {
        model.addAttribute("categories", categoryService.findAll());
        addCsrf(model, request);
    }

    private void addCsrf(Model model, HttpServletRequest request) {
        model.addAttribute("csrfToken", CsrfUtil.getOrCreateToken(request.getSession()));
    }

    private void checkCsrf(HttpServletRequest request) {
        if (!CsrfUtil.isValid(request)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CSRF token không hợp lệ");
        }
    }
}
