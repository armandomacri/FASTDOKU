package it.unipi.dii.inginf.dsmt.fastdoku.bean;

public class User {
    private String username; // identifier
    private String password;


    public User (final String username, final String password)
    {
        this.username = username;
        this.password = password;

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

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\''+'}';
    }

}