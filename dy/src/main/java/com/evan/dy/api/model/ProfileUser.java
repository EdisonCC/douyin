package com.evan.dy.api.model;

import com.evan.dy.api.model.dy.DyProfileUser;
import com.evan.dy.utils.JsonUtils;

public final class ProfileUser {
    private final Object userObj;
    private DyProfileUser user;

    public ProfileUser(Object userObj) {
        this.userObj = userObj;
    }

    public DyProfileUser getUser() {
        if (this.user == null) {
            try {
                this.user = new DyProfileUser().parseFrom(userObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public String toString() {
        return "ProfileUser{" +
            "userObj=" + JsonUtils.toJson(userObj) +
            ", user=" + user +
            '}';
    }
}
