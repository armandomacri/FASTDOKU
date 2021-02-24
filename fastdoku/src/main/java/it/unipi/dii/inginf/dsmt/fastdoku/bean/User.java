package it.unipi.dii.inginf.dsmt.fastdoku.bean;

public class User {
    private String username; // identifier
    private int points;
    private String password;

    /**
     * Constructor log in phase
     * @param username
     * @param password
     * @param points
     */
    public User (final String username, final String password, final int points) {
        this.username = username;
        this.password = password;
        this.points = points;
    }

    /**
     * Constructor sign up phase
     * @param username
     * @param password
     */
    public User (final String username, final String password) {
        this(username, password, 0);
    }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public  void setPoints(int points) { this.points = points; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\''+
                ", score='" + points + '}';
    }

}