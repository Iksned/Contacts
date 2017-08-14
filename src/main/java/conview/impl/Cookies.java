package conview.impl;

public class Cookies {
    public String username;
    private String password;

    public Cookies(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
