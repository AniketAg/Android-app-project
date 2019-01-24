package com.example.scoda.booksharing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


import java.io.Serializable;


public class Book implements Serializable {

    private String name,author;
    private String price,postedBy;

    public Book() {

    }
    public Book(String name,String author, String price,String postedBy) {

        this.name = name;
        this.author = author;
        this.price = price;
        this.postedBy = postedBy;

    }


    @Override
    public String toString() {
        return "Book{" +
               " name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price+
                '}';
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getauthor() {
        return author;
    }

    public void setauthor(String author) {
        this.author = author;
    }

}
