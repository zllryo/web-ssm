package com.ryo.model;

import java.io.Serializable;

public class Depart implements Serializable {
    private Integer id;

    private String departname;

    private String departcode;

    private String departdesc;

    private Integer fatherid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private  User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname == null ? null : departname.trim();
    }

    public String getDepartcode() {
        return departcode;
    }

    public void setDepartcode(String departcode) {
        this.departcode = departcode == null ? null : departcode.trim();
    }

    public String getDepartdesc() {
        return departdesc;
    }

    public void setDepartdesc(String departdesc) {
        this.departdesc = departdesc == null ? null : departdesc.trim();
    }

    public Integer getFatherid() {
        return fatherid;
    }

    public void setFatherid(Integer fatherid) {
        this.fatherid = fatherid;
    }
}