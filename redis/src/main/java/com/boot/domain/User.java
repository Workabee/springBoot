package com.boot.domain;

import java.io.Serializable;

/**
 * Created by abee on 17-3-10.
 */
public class User implements Serializable {
    private static final long serialVersionUID = -1L;
    private String username;
    private Long age;

    public User(String username, Long age) {
        this.username = username;
        this.age = age;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}