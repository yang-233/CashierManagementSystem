package com.example.demo.model;

public class WebAccount {
    private String account;

    private String password;

    private String question;

    private String answer;

    private Integer type;

    public WebAccount(String account, String password, String question, String answer, Integer type) {
        this.account = account;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    @Override
    public String toString() {
        return "WebAccount{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", type=" + type +
                '}';
    }

    public String getAccount() {
        return account;
    }


    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}