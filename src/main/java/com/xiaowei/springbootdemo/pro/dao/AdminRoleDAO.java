package com.xiaowei.springbootdemo.pro.dao;

import com.xiaowei.springbootdemo.pro.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleDAO extends JpaRepository<AdminRole, Integer> {
    AdminRole findById(int id);
}