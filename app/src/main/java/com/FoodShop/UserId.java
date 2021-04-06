package com.FoodShop;

public class UserId {
    private String login_text;

    public UserId(String login_text) {
        this.login_text = login_text.toString();
    }

    public String getLogin_text() {
        return login_text;
    }

    public void setLogin_text(String login_text) {
        this.login_text = login_text;
    }

    public int getId() {
        String str = getLogin_text();
        String number = str.replaceAll("[^0-9]", "");
        return Integer.valueOf(number);
    }
}
