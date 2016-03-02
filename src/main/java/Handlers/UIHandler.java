package Handlers;

import Products.Product;
import Users.User;

import java.util.HashMap;

public class UIHandler {
    void login (User user)
    {}
    void logout (User user)
    {}
    void search (String searchQuery, User user)
    {}
    void like (Product product)
    {}
    void dislike (Product product)
    {}
    void getTrending ()
    {}
    void saveProduct (User user,  Product product)
    {}
    void forgotPassword (User user)
    {}
    void signUp (User user)
    {}
    void editSettings (User user,HashMap<String, String> settings)
    {}
    void deleteAccount (User user)
    {}
    void saveSearch (User user, String search)
    {}
}
