package Models.ttk2008;

public class UserInfo {

    private int userId;
    private String username;
    private String passwordHash;
    private int role;
    private String createdAt;
    private int statusId;
    private String contactFullname;
    private String contactEmail;
    private String contactPhone;

    public UserInfo() {
    }

    public UserInfo(int userId, String username, String passwordHash, int role, String createdAt,
            int statusId, String contactFullname, String contactEmail, String contactPhone) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
        this.statusId = statusId;
        this.contactFullname = contactFullname;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    // === Getters & Setters ===
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getContactFullname() {
        return contactFullname;
    }

    public void setContactFullname(String contactFullname) {
        this.contactFullname = contactFullname;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
