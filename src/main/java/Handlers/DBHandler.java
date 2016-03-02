package Handlers;

import Products.Product;
import Users.User;

import java.util.HashMap;
import java.util.List;

import java.sql.*;

public class DBHandler {

    public static String JDBC_DRIVER = "";
    public static String JDBC_URL = "";
    public static String JDBC_USER = "";
    public static String JDBC_PASSWORD = "";

    void like (Product product)
    {}
    void dislike (Product product)
    {}
    void saveSearch(User user, String search)
    {}
    void saveProduct(User user, Product product)
    {}
    void signUp(User user)
    {}
    void editSettings(User user, HashMap<String, String> settings)
    {}
    List<Product> getTrending()
    {
        List<Product> temp = null;
        return temp;
    }
}
