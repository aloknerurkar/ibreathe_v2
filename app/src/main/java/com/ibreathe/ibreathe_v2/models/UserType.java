package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 7/26/15.
 */
public class UserType implements Serializable {

    public String user_id;
    public String user_name;
    public String email;
    public String mobile;
    public String org_id;
    public String gcm_reg_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getGcm_reg_id() {
        return gcm_reg_id;
    }

    public void setGcm_reg_id(String gcm_reg_id) {
        this.gcm_reg_id = gcm_reg_id;
    }
}
