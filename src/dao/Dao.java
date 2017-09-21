package dao;

import pojo.Book;

import java.util.List;

public interface Dao {

    List<Book> findAll();

}
