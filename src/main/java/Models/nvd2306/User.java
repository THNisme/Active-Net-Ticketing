/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.nvd2306;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author NguyenDuc
 */
public class User {

    private int userID;
    private String username;
    private String passwordHash;
    private int role;
    private Date createdAt;
    private String contactEmail;
    private String contactFullname;
    private String contactPhone;
    private int statusID;
    private String OTPCode;
    private java.sql.Timestamp OTPExpiredAt;
    private boolean isVerified;

    public User() {
    }

    public User(int userID, String username, String passwordHash, int role, Date createdAt, String contactEmail) {
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.contactEmail = contactEmail;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getOTPCode() {
        return OTPCode;
    }

    public void setOTPCode(String OTPCode) {
        this.OTPCode = OTPCode;
    }

    public Timestamp getOTPExpiredAt() {
        return OTPExpiredAt;
    }

    public void setOTPExpiredAt(Timestamp OTPExpiredAt) {
        this.OTPExpiredAt = OTPExpiredAt;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactFullname() {
        return contactFullname;
    }

    public void setContactFullname(String contactFullname) {
        this.contactFullname = contactFullname;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}
