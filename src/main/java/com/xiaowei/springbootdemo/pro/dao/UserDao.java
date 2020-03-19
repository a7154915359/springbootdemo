package com.xiaowei.springbootdemo.pro.dao;

import com.xiaowei.springbootdemo.pro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);
}
