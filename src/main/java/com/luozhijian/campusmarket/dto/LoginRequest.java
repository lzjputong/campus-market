package com.luozhijian.campusmarket.dto;

public class LoginRequest {
    private String schoolNo;

    public LoginRequest() {
    }

    private String password;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }


}
