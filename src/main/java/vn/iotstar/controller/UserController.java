package vn.iotstar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;



@Controller
@RequestMapping("/admin/user")
public class UserController {


    @Autowired
    private UserService userService;



    /*
     * Danh sách User
     * Có tìm kiếm theo username
     *
     * URL:
     * /admin/user
     *
     * /admin/user?keyword=admin
     */
    @GetMapping
    public String listUser(
            @RequestParam(
                    value = "keyword",
                    required = false
            ) String keyword,
            Model model) {


        model.addAttribute(
                "users",
                userService.search(keyword)
        );


        model.addAttribute(
                "keyword",
                keyword
        );


        return "admin/user/list";

    }




    /*
     * Form thêm User
     */
    @GetMapping("/add")
    public String addUser(Model model) {


        model.addAttribute(
                "user",
                new User()
        );


        return "admin/user/add";

    }




    /*
     * Lưu User
     */
    @PostMapping("/save")
    public String saveUser(
            @ModelAttribute("user")
            User user) {


        userService.save(user);


        return "redirect:/admin/user";

    }




    /*
     * Form chỉnh sửa User
     */
    @GetMapping("/edit/{id}")
    public String editUser(
            @PathVariable("id")
            Integer id,
            Model model) {


        User user =
                userService.findById(id);



        model.addAttribute(
                "user",
                user
        );


        return "admin/user/edit";

    }




    /*
     * Xóa User
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable("id")
            Integer id) {


        userService.delete(id);


        return "redirect:/admin/user";

    }

}