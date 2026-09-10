package vn.iotstar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iotstar.entity.User;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.util.PasswordUtil;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    // ADMIN CRUD USER
    @Override
    public List<User> findAll(){

        return userRepository.findAll();

    }
    @Override
    public List<User> search(String keyword){


        if(keyword == null
                || keyword.trim().isEmpty()){

            return userRepository.findAll();

        }
        return userRepository
                .findByUsernameContainingIgnoreCase(keyword);

    }
    @Override
    public User findById(Integer id){

        return userRepository
                .findById(id)
                .orElse(null);

    }
    @Override
    public User save(User user){

        return userRepository.save(user);

    }
    @Override
    public void delete(Integer id){

        userRepository.deleteById(id);

    }

    // LOGIN / REGISTER / OTP
    @Override
    public User findByEmail(String email){

        return userRepository.findByEmail(email);

    }
    @Override
    public User findByUsername(String username){

        return userRepository.findByUsername(username);

    }
    @Override
    public User login(
            String username,
            String password){
        User user = userRepository.findByUsername(username);
        if (user == null || !PasswordUtil.matches(password, user.getPassword())) {
            return null;
        }
        if (PasswordUtil.needsUpgrade(user.getPassword())) {
            user.setPassword(PasswordUtil.hash(password));
            user = userRepository.save(user);
        }
        return user;
    }
    @Override
    public User register(User user){

        return userRepository.save(user);

    }
    @Override
    public User update(User user){

        return userRepository.save(user);

    }


}
