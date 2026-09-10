package vn.iotstar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.User;

import java.util.List;



public interface UserRepository
        extends JpaRepository<User,Integer>{


    List<User> findByUsernameContainingIgnoreCase(
            String keyword
    );


    User findByEmail(String email);


    User findByUsername(String username);


}
