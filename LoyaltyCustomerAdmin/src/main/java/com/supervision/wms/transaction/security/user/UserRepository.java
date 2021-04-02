/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supervision.wms.transaction.security.user;

import com.supervision.wms.transaction.security.user.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thilina Kalum
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUserName(String userName);

    @Query(value = "select m_user_type.`type`\n"
            + "from m_user_type \n"
            + "left JOIN main_m_user on main_m_user.index_no=m_user_type.user\n"
            + "where main_m_user.username=:userName", nativeQuery = true)
    public String getUserType(@Param("userName") String userName);

    public List<User> findByUserNameAndPassword(String userName, String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE main_m_user SET `password` = :newPassword WHERE `index_no` = :userIndex and `password`=:password", nativeQuery = true)
    public int updateUserPassword(@Param("userIndex") Integer userIndex, @Param("newPassword") String newPassword, @Param("password") String password);

}
