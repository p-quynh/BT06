package vn.iotstar.service;


import vn.iotstar.entity.User;

import java.util.List;


public interface UserService {


    List<User> findAll();


    List<User> search(String keyword);


    User findById(Integer id);


    User save(User user);


    void delete(Integer id);


    User findByEmail(String email);


    User findByUsername(String username);


    User login(
            String username,
            String password
    );
    User register(User user);

    User update(User user);



}