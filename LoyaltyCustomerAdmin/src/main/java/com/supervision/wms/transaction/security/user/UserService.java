/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.security.user;

import com.supervision.wms.transaction.security.user.model.UpdateUser;
import com.supervision.wms.transaction.security.user.model.User;
import com.supervision.wms.transaction.security.user.model.User2;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thilina Kalum
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer indexNo) {
        try {
            userRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete this User because there are details in other transaction");
        }
    }

    public User2 findByName(String userName) {
        User2 user2 = new User2(); 
        
        User user = userRepository.findByUserName(userName);
        user2.setBranch(user.getBranch());
        user2.setIndexNo(user.getIndexNo());
        user2.setName(user.getName());
        user2.setPassword(user.getPassword());
        user2.setUserName(user.getUserName());
        user2.setAdminBranch(user.getAdminBranch());
        
        user2.setType(userRepository.getUserType(userName));
        System.out.println("");
        System.out.println("User login - "+userName +" - "+new Date()+" - "+user2.getAdminBranch());
        System.out.println("");
        return user2;
    }
    public List<User> findByNameAndPassword(String userName,String password) {
        return userRepository.findByUserNameAndPassword(userName,password);
    }


    public int updateUserPassword(UpdateUser user) {
        return userRepository.updateUserPassword(user.getUserIndex(),user.getNewPassword(),user.getPassword());
        
    }

}
