/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Acer
 */
public class User {

    private int userID;
    private String username;
    private String password;
    private int role; // 0 = user, 1 = admin
    private Date createdAt;
    private int statusID;

    public User() {
    }

    public User(int userID, String username, String password, int role, Date createdAt, int statusID) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.statusID = statusID;
    }

    public User(int userID, String username, String password, int role, Date createdAt) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public User(int userID, String username, String password, int role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date cretedAt) {
        this.createdAt = cretedAt;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

}
