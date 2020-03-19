package com.xiaowei.springbootdemo.pro.dao;

import com.xiaowei.springbootdemo.pro.entity.Book;
import com.xiaowei.springbootdemo.pro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDAO extends JpaRepository<Book,Integer> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}