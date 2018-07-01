package com.example.demo.model;

import com.example.demo.MyTest;

public class WebUser {
    private String account;

    private String name;

    private String telephone;

    private Integer age;

    private Integer sex;

    public WebUser(String account, String name, String telephone, Integer age, Integer sex) {
        this.account = account;
        this.name = name;
        this.telephone = telephone;
        this.age = age;
        this.sex = sex;
    }

    public WebUser(String account){
        MyTest t=new MyTest();
        this.account=account;
        this.name=t.buildName();
        this.telephone=t.buildPhoneNumber();
        this.age=t.buildAge();
        this.sex=t.buildSex();
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}