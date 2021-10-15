package com.example.fcmtoken;

public class ModelUsers {


    String  email,id;
    public ModelUsers()
    {
    }
    ModelUsers(String email,String id) {
        this.email = email;
        this.id=id;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getid() {
        return id;
    }


    public void setid(String id) {
        this.id = id;
    }

}
