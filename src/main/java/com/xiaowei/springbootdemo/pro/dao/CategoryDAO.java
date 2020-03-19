package com.xiaowei.springbootdemo.pro.dao;

import com.xiaowei.springbootdemo.pro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

}