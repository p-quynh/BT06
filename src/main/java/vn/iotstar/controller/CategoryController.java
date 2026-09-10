package vn.iotstar.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;
import vn.iotstar.util.CsrfUtil;
import vn.iotstar.util.ImageStorage;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    private static final int DEFAULT_PAGE_SIZE = 5;

    private final CategoryService categoryService;

    @Value("${app.student.name:Họ và tên sinh viên}")
    private String studentName;

    @Value("${app.student.id:MSSV}")
    private String studentId;

    @Value("${app.student.class-name:Lớp học phần}")
    private String studentClassName;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ModelAttribute
    public void addLayoutInformation(Model model) {
        model.addAttribute("studentName", studentName);
        model.addAttribute("studentId", studentId);
        model.addAttribute("studentClassName", studentClassName);
    }

    @GetMapping
    public String list(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "5") int size,
                       Model model,
                       HttpServletRequest request) {
        page = Math.max(page, 0);
        size = normalizePageSize(size);

        Page<Category> categoryPage = categoryService.findPage(keyword, page, size);
        if (categoryPage.getTotalPages() > 0 && page >= categoryPage.getTotalPages()) {
            page = categoryPage.getTotalPages() - 1;
            categoryPage = categoryService.findPage(keyword, page, size);
        }

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("keyword", keyword.trim());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        addCsrf(model, request);
        return "thymeleaf/category/list";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpServletRequest request) {
        model.addAttribute("category", new Category());
        model.addAttribute("formTitle", "Thêm danh mục");
        addCsrf(model, request);
        return "thymeleaf/category/form";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam("id") Integer id,
                           Model model,
                           HttpServletRequest request) {
        model.addAttribute("category", requireCategory(id));
        model.addAttribute("formTitle", "Cập nhật danh mục");
        addCsrf(model, request);
        return "thymeleaf/category/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category submittedCategory,
                       BindingResult bindingResult,
                       @RequestParam(value = "iconsFile", required = false) MultipartFile icon,
                       HttpServletRequest request,
                       Model model,
                       RedirectAttributes redirect) {
        checkCsrf(request);
        boolean editing = submittedCategory.getCate_id() > 0;

        if (bindingResult.hasErrors()) {
            model.addAttribute("formTitle", editing ? "Cập nhật danh mục" : "Thêm danh mục");
            addCsrf(model, request);
            return "thymeleaf/category/form";
        }

        try {
            Category category = editing
                    ? requireCategory(submittedCategory.getCate_id())
                    : new Category();
            category.setCate_name(submittedCategory.getCate_name().trim());

            String fileName = ImageStorage.store(icon, "categories");
            if (fileName != null) {
                category.setIcons(fileName);
            }

            categoryService.save(category);
            redirect.addFlashAttribute(
                    "flashMessage",
                    editing ? "Cập nhật danh mục thành công" : "Thêm danh mục thành công"
            );
            return "redirect:/admin/category";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            model.addAttribute("formTitle", editing ? "Cập nhật danh mục" : "Thêm danh mục");
            addCsrf(model, request);
            return "thymeleaf/category/form";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id,
                         HttpServletRequest request,
                         RedirectAttributes redirect) {
        checkCsrf(request);
        try {
            categoryService.delete(id);
            redirect.addFlashAttribute("flashMessage", "Xóa danh mục thành công");
        } catch (RuntimeException exception) {
            redirect.addFlashAttribute("flashType", "error");
            redirect.addFlashAttribute("flashMessage", "Không thể xóa danh mục đang có sản phẩm");
        }
        return "redirect:/admin/category";
    }

    private int normalizePageSize(int size) {
        return size == 10 || size == 20 ? size : DEFAULT_PAGE_SIZE;
    }

    private Category requireCategory(Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục");
        }
        return category;
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
