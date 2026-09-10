package vn.iotstar.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.User;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import vn.iotstar.util.Validation;

@WebServlet("/profile")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024
)
public class ProfileController extends UserServiceServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    )
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User account = (User) session.getAttribute("account");

        if(account == null){

            resp.sendRedirect(
                    req.getContextPath()
                            + "/login"
            );

            return;
        }


        req.setAttribute(
                "user",
                account
        );
        req.getRequestDispatcher(
                        "/views/profile.jsp"
                )
                .forward(req,resp);

    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    )
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session =
                req.getSession();
        User account =
                (User) session.getAttribute("account");
        if(account == null){
            resp.sendRedirect(
                    req.getContextPath()
                            + "/login"
            );
            return;
        }
        String fullname =
                req.getParameter("fullname");

        String phone =
                req.getParameter("phone");


// VALIDATION

        String error = null;


        if(Validation.isEmpty(fullname)){

            error = "Fullname không được để trống";

        }
        else if(!Validation.isPhone(phone)){

            error = "Số điện thoại phải gồm 10 chữ số";

        }



        if(error != null){

            req.setAttribute(
                    "error",
                    error
            );


            req.setAttribute(
                    "user",
                    account
            );


            req.getRequestDispatcher(
                            "/views/profile.jsp"
                    )
                    .forward(req,resp);


            return;

        }


// nếu hợp lệ mới update

        account.setFullname(fullname);
        account.setPhone(phone);
        Part part =
                req.getPart("avatar");
        if(part != null
                && part.getSize()>0){
            String fileName =
                    UUID.randomUUID()
                            +"_"
                            +part.getSubmittedFileName();
            String uploadPath =
                    getServletContext()
                            .getRealPath(
                                    "/uploads/avatar"
                            );
            File folder =
                    new File(uploadPath);
            if(!folder.exists()){

                folder.mkdirs();
            }
            part.write(
                    uploadPath
                            + File.separator
                            + fileName
            );
            account.setAvatar(
                    fileName
            );
        }
        userService.update(account);

        /*
          update session
        */
        session.setAttribute(
                "account",
                account
        );
        resp.sendRedirect(
                req.getContextPath()
                        +"/profile"
        );
    }
}
