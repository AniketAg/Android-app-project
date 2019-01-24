package com.example.scoda.booksharing;

/**
 * Created by Scoda
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


import java.io.Serializable;

public class User implements Serializable{
    private String fullName, email, password, phoneNumber, img_base64;
    Boolean newMessage;

    public User() {
        this.newMessage = false;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.newMessage = false;
    }

    public User(String fullName, String email, String password, String phoneNumber, String img_base64) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.img_base64 = img_base64;
        this.newMessage = false;
    }

    public void setUser(User u) {
        this.fullName = u.getFullName();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.phoneNumber = u.getPhoneNumber();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImg_base64() {
        return img_base64;
    }
    public void setImg_base64(String img_base64) {
        this.img_base64 = img_base64;
    }

    public Bitmap imgProfile() {
        if(img_base64==null) {
            return null;
        }
        byte[] decodedString = Base64.decode(img_base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", img_base64='" + img_base64 + '\'' +
                '}';
    }

    public boolean equals(User u) {
        return this.email.equals(u.getEmail());
    }
}
