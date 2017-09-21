package dao;

import pojo.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {




    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        try {
//            注册驱动
            Class.forName("com.mysql.jdbc.Driver");
//            获取连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ssm", "root", "root");
//            sql语句
            String sql = "select * from book";
//            获取运行平台
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            运行preparedStatement获取结果集
            ResultSet resultSet = preparedStatement.executeQuery();
//            处理结果集
            while(resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setName(resultSet.getString("name"));
                book.setPrice(resultSet.getFloat("price"));
                book.setPic(resultSet.getString("pic"));
                book.setDescription(resultSet.getString("description"));
                list.add(book);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
