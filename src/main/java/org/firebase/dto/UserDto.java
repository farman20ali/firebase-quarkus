package org.firebase.dto;

public class UserDto {
    public String id;
    public String email;
    public String password;
    @Override
    public String toString() {
        return "UserDto [id=" + id + ", email=" + email + ", password=" + password + "]";
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
}
